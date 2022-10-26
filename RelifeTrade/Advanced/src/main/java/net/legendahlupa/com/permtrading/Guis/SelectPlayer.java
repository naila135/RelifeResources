package net.legendahlupa.com.permtrading.Guis;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class SelectPlayer {
    public void createGui(Player player){
        PaginatedGui gui = Gui.paginated()
                .title(Component.text("GUI Title!"))
                .rows(6)
                .pageSize(28)
                .create();
        ArrayList<Player> players = new ArrayList<Player>(player.getServer().getOnlinePlayers());
        gui.setItem(6, 4, ItemBuilder.from(Material.PAPER).setName("Previous").asGuiItem(event ->{
            event.setCancelled(true);
            gui.previous();
        }));
        gui.setItem(6, 6, ItemBuilder.from(Material.PAPER).setName("Next").asGuiItem(event ->{
            event.setCancelled(true);
            gui.next();
        }));


        ItemStack fillitem = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta fillitemmeta = fillitem.getItemMeta();
        fillitemmeta.setDisplayName(" ");
        fillitem.setItemMeta(fillitemmeta);


        GuiItem FilledPane = ItemBuilder.from(fillitem).asGuiItem(event -> {
            event.setCancelled(true);
            return;
        });
        for (int i = 0; i < 9; i++) {
            if (gui.getGuiItem(i) == null) {
                gui.setItem(i, FilledPane);
            }
        }


        gui.setItem(2,1, FilledPane);
        gui.setItem(2,9, FilledPane);
        gui.setItem(3,1, FilledPane);
        gui.setItem(3,9, FilledPane);
        gui.setItem(4,1, FilledPane);
        gui.setItem(4,9, FilledPane);
        gui.setItem(5,1, FilledPane);
        gui.setItem(5,9, FilledPane);
        gui.setItem(6,1, FilledPane);
        gui.setItem(6,2, FilledPane);
        gui.setItem(6,3, FilledPane);
        gui.setItem(6,5, FilledPane);
        gui.setItem(6,7, FilledPane);
        gui.setItem(6,8, FilledPane);
        gui.setItem(6,9, FilledPane);



        for (int i = 0; i < players.size(); i++) {
            ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD, 1);
            ItemMeta meta = playerHead.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + players.get(i).getDisplayName());
            ArrayList<String> lore = new ArrayList<>();
            meta.setLore(lore);
            playerHead.setItemMeta(meta);
            GuiItem playerheads = ItemBuilder.from(playerHead).asGuiItem(event -> {
                event.setCancelled(true);
                SelectPermission selectPermission = new SelectPermission();
                Player sender = (Player) event.getWhoClicked();

                String taker = event.getCurrentItem().getItemMeta().getDisplayName();
                event.getCurrentItem().getItemMeta().setDisplayName(ChatColor.GREEN + taker);
                String tt = event.getCurrentItem().getItemMeta().getDisplayName().replace("§a", "");
                Player takerplayer = Bukkit.getPlayer(tt);
                if (Bukkit.getOperators().contains(takerplayer)){
                    sender.sendMessage("Игроку запрещено торговаться");
                    return;
                }

                if (takerplayer == event.getWhoClicked()){
                    sender.sendMessage(ChatColor.RED + "Вы не можете отправить обмен себе");
                    return;
                }

                if (takerplayer == player){
                    sender.closeInventory();
                    sender.sendMessage(ChatColor.RED + "Вы не можете отправить себе трейд");
                    return;
                }
                if (takerplayer == null){
                    sender.closeInventory();
                    sender.sendMessage(ChatColor.RED + "Игрок вышел");
                    return;
                }
                selectPermission.selectPermsGui(takerplayer, sender);
            });
            gui.addItem(playerheads);
        }
        gui.open(player);
    }
}
