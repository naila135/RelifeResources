package net.legendahlupa.com.permtrading.Guis;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.io.File;

public class AcceptingMenu {

    public void createAcceptGui(Player sender, Player taker, String perm, int price) {
        File file = new File("plugins/PermTrading/permissions.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        Gui gui = Gui.gui()
                .title(Component.text("GUI Title!"))
                .rows(3)
                .create();


        ItemStack Accept = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        ItemMeta AcceptMeta = Accept.getItemMeta();
        AcceptMeta.setDisplayName(ChatColor.GREEN + "Принять");
        Accept.setItemMeta(AcceptMeta);


        ItemStack Cancel = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta CancelMeta = Cancel.getItemMeta();
        CancelMeta.setDisplayName(ChatColor.RED + "Отклонить");
        Cancel.setItemMeta(CancelMeta);

        ItemStack Filler_glass = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta Filler_glassMeta = Filler_glass.getItemMeta();
        Filler_glassMeta.setDisplayName(" ");
        Filler_glass.setItemMeta(Filler_glassMeta);

        ItemStack SenderHead = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta SenderHeadMeta = SenderHead.getItemMeta();
        SenderHeadMeta.setDisplayName(ChatColor.BLUE + sender.getDisplayName());
        SenderHead.setItemMeta(SenderHeadMeta);

        ItemStack Price = new ItemStack(Material.GOLD_BLOCK);
        ItemMeta PriceMeta = Price.getItemMeta();
        PriceMeta.setDisplayName(ChatColor.GOLD + String.valueOf(price));
        Price.setItemMeta(PriceMeta);

        ItemStack PermBlock = new ItemStack(Material.EMERALD_BLOCK);
        ItemMeta PermBlockMeta = Price.getItemMeta();
        PermBlockMeta.setDisplayName(ChatColor.GOLD + perm);
        PermBlock.setItemMeta(PermBlockMeta);



        GuiItem accept = ItemBuilder.from(Accept).asGuiItem(event -> {
            event.setCancelled(true);
            RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
            RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
            Economy econ = rsp.getProvider();

            if (provider != null) {

                LuckPerms api = provider.getProvider();
                User takerluckperms = api.getPlayerAdapter(Player.class).getUser(taker);
                User senderluckperms = api.getPlayerAdapter(Player.class).getUser(sender);


                config.getConfigurationSection("Permissions").getKeys(false).forEach(key -> {

                    String patchtoperm = String.join(".", "Permissions", perm.replace("§a", ""), "permission");
                    if (!sender.hasPermission(config.getString(patchtoperm))) {
                        sender.sendMessage(ChatColor.RED + "Что то произошло и у вас пропала группа");
                        taker.sendMessage(ChatColor.RED + "Что то произошло и у оппонента пропала группа");
                        return;
                    }
                    if (econ.getBalance(taker) >= price) {
                        econ.withdrawPlayer(taker, price);
                        econ.depositPlayer(sender, price);
                        takerluckperms.data().add(Node.builder(config.getString(patchtoperm)).build());
                        senderluckperms.data().remove(Node.builder(config.getString(patchtoperm)).build());
                        taker.closeInventory();
                        api.getUserManager().saveUser(takerluckperms);
                        api.getUserManager().saveUser(senderluckperms);
                        taker.closeInventory();
                    } else {
                        taker.sendMessage(ChatColor.RED + "У вас недостаточно средств");
                        sender.sendMessage(ChatColor.RED + "У " + taker.getDisplayName() + " недостаточно средств");
                        return;
                    }


                });
            }});
        GuiItem cancel = ItemBuilder.from(Cancel).asGuiItem(event -> {
            event.setCancelled(true);
            sender.sendMessage("Ваш обмен отменен");
            taker.closeInventory();
        });

        GuiItem filler_glass = ItemBuilder.from(Filler_glass).asGuiItem(event -> {
            event.setCancelled(true);
        });

        GuiItem senderhead = ItemBuilder.from(SenderHead).asGuiItem(event -> {
            event.setCancelled(true);
        });

        GuiItem priceblock = ItemBuilder.from(Price).asGuiItem(event -> {
            event.setCancelled(true);
        });

        GuiItem permblock = ItemBuilder.from(PermBlock).asGuiItem(event -> {
            event.setCancelled(true);
        });

        gui.setItem(1,5, permblock);
        gui.setItem(2,5, senderhead);
        gui.setItem(3,5, priceblock);
        gui.setItem(1, 4, filler_glass);
        gui.setItem(1, 6, filler_glass);
        gui.setItem(2, 4, filler_glass);
        gui.setItem(2, 6, filler_glass);
        gui.setItem(3, 4, filler_glass);
        gui.setItem(3, 6, filler_glass);

        for (int i = 6; i < 9; i++) {
            if (gui.getGuiItem(i) == null) {
                gui.setItem(i, accept);
            }
        }
        for (int i = 15; i < 18; i++) {
            if (gui.getGuiItem(i) == null) {
                gui.setItem(i, accept);
            }
        }

        for (int i = 24; i < 27; i++) {
            if (gui.getGuiItem(i) == null) {
                gui.setItem(i, accept);
            }
        }

        for (int i = 0; i < 3; i++) {
            if (gui.getGuiItem(i) == null) {
                gui.setItem(i, cancel);
            }
        }
        for (int i = 9; i < 12; i++) {
            if (gui.getGuiItem(i) == null) {
                gui.setItem(i, cancel);
            }
        }

        for (int i = 18; i < 21; i++) {
            if (gui.getGuiItem(i) == null) {
                gui.setItem(i, cancel);
            }
        }

        gui.open(taker);

    }


}
