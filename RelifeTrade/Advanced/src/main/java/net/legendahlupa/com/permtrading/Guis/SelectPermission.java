package net.legendahlupa.com.permtrading.Guis;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import net.kyori.adventure.text.Component;
import net.legendahlupa.com.permtrading.Config.CreatePermissionFile;
import net.legendahlupa.com.permtrading.PermTrading;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SelectPermission {
    File file = new File("plugins/PermTrading/permissions.yml");
    FileConfiguration config = YamlConfiguration.loadConfiguration(file);

    public void selectPermsGui(Player takerPerm, Player sender) {
        PaginatedGui gui = Gui.paginated()
                .title(Component.text("GUI Title!"))
                .rows(6)
                .pageSize(28)
                .create();
        gui.setItem(6, 4, ItemBuilder.from(Material.PAPER).setName("Предыдущая страница").asGuiItem(event -> {
            event.setCancelled(true);
            gui.previous();
        }));
        gui.setItem(6, 6, ItemBuilder.from(Material.PAPER).setName("Следующая страница").asGuiItem(event -> {
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



        config.getConfigurationSection("Permissions").getKeys(false).forEach(key -> {
            String patch = String.join(".", "Permissions", key, "name");
            String patchtoperm = String.join(".", "Permissions", key, "permission");
            if (sender.hasPermission(config.getString(patchtoperm))) {


                ItemStack perm = new ItemStack(Material.GREEN_CONCRETE, 1);
                ItemMeta permmeta = perm.getItemMeta();
                ArrayList<String> lore = new ArrayList<>();
                permmeta.setDisplayName(ChatColor.GREEN + config.getString(patch));
                permmeta.setLore(lore);
                perm.setItemMeta(permmeta);
                GuiItem perms = ItemBuilder.from(perm).asGuiItem(event -> {
                    event.setCancelled(true);
                    String permission = event.getCurrentItem().getItemMeta().getDisplayName();
                    new SelectPrice().SelectPriceMenu(sender, takerPerm, permission);

                });
                gui.addItem(perms);
            }
        });


        gui.open(sender);
    }


}
