package net.legendahlupa.com.permtrading.Guis;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class SelectPrice {

    public void SelectPriceMenu(Player sender, Player taker, String permission){

        Gui gui = Gui.gui()
                .title(Component.text("GUI Title!"))
                .rows(3)
                .create();


        ItemStack PlusOne= new ItemStack(Material.IRON_NUGGET, 1);
        ItemMeta PlusOneMeta = PlusOne.getItemMeta();
        PlusOneMeta.setDisplayName(ChatColor.BLUE + "+1");
        PlusOne.setItemMeta(PlusOneMeta);

        ItemStack PlusTen = new ItemStack(Material.IRON_NUGGET, 10);
        ItemMeta PlusTenMeta = PlusTen.getItemMeta();
        PlusTenMeta.setDisplayName(ChatColor.BLUE + "+10");
        PlusTen.setItemMeta(PlusTenMeta);

        ItemStack PlusHundred = new ItemStack(Material.IRON_NUGGET, 32);
        ItemMeta PlusHundredMeta = PlusHundred.getItemMeta();
        PlusHundredMeta.setDisplayName(ChatColor.BLUE + "+100");
        PlusHundred.setItemMeta(PlusHundredMeta);

        ItemStack PlusThousand = new ItemStack(Material.IRON_NUGGET, 64);
        ItemMeta PlusThousandMeta = PlusThousand.getItemMeta();
        PlusThousandMeta.setDisplayName(ChatColor.BLUE + "+1000");
        PlusThousand.setItemMeta(PlusThousandMeta);


        ItemStack MinusOne= new ItemStack(Material.IRON_NUGGET, 1);
        ItemMeta MinusOneMeta = MinusOne.getItemMeta();
        MinusOneMeta.setDisplayName(ChatColor.BLUE + "-1");
        MinusOne.setItemMeta(MinusOneMeta);

        ItemStack MinusTen = new ItemStack(Material.IRON_NUGGET, 10);
        ItemMeta MinusTenMeta = MinusTen.getItemMeta();
        MinusTenMeta.setDisplayName(ChatColor.BLUE + "-10");
        MinusTen.setItemMeta(MinusTenMeta);

        ItemStack MinusHundred = new ItemStack(Material.IRON_NUGGET, 32);
        ItemMeta MinusHundredMeta = MinusHundred.getItemMeta();
        MinusHundredMeta.setDisplayName(ChatColor.BLUE + "-100");
        MinusHundred.setItemMeta(MinusHundredMeta);

        ItemStack MinusThousand = new ItemStack(Material.IRON_NUGGET, 64);
        ItemMeta MinusThousandMeta = MinusThousand.getItemMeta();
        MinusThousandMeta.setDisplayName(ChatColor.BLUE + "-1000");
        MinusThousand.setItemMeta(MinusThousandMeta);



        ItemStack PriceBlock = new ItemStack(Material.GOLD_BLOCK);
        ItemMeta PriceBlockMeta = PriceBlock.getItemMeta();
        PriceBlockMeta.setDisplayName(ChatColor.GREEN + "100");
        PriceBlock.setItemMeta(PriceBlockMeta);


        ItemStack Accept = new ItemStack(Material.GREEN_CONCRETE);
        ItemMeta AcceptMeta = Accept.getItemMeta();
        AcceptMeta.setDisplayName(ChatColor.GREEN + "Принять");
        Accept.setItemMeta(AcceptMeta);

        ItemStack Cancel = new ItemStack(Material.RED_CONCRETE);
        ItemMeta CancelMeta = Cancel.getItemMeta();
        CancelMeta.setDisplayName(ChatColor.RED + "Отмена");
        Cancel.setItemMeta(CancelMeta);


        GuiItem accept = ItemBuilder.from(Accept).asGuiItem(event -> {
            event.setCancelled(true);
            sender.closeInventory();
            AcceptingMenu acceptingMenu = new AcceptingMenu();
            String price = Objects.requireNonNull(gui.getGuiItem(13)).getItemStack().getItemMeta().getDisplayName().replace("§a", "");
            acceptingMenu.createAcceptGui(sender, taker, permission, Integer.parseInt(price));

        });

        GuiItem priceBlock = ItemBuilder.from(PriceBlock).asGuiItem(event -> {
            event.setCancelled(true);
        });

        GuiItem cancel = ItemBuilder.from(Cancel).asGuiItem(event -> {
            Player player = (Player) event.getWhoClicked();
            player.closeInventory();
        });

//        ItemStack item = new ItemStack(Material.GOLD_BLOCK);
//        ItemMeta imeta = item.getItemMeta();
        
        GuiItem plusOne = ItemBuilder.from(PlusOne).asGuiItem(event -> {
            event.setCancelled(true);
            String price = event.getView().getItem(13).getItemMeta().getDisplayName().replace("§a", "");
            PriceBlockMeta.setDisplayName(ChatColor.GREEN + String.valueOf(Integer.parseInt(price) + 1));
            PriceBlock.setItemMeta(PriceBlockMeta);
            gui.setItem(13, priceBlock);
            gui.update();
        });
        GuiItem plusTen = ItemBuilder.from(PlusTen).asGuiItem(event -> {
            event.setCancelled(true);
            String price = event.getView().getItem(13).getItemMeta().getDisplayName().replace("§a", "");
            PriceBlockMeta.setDisplayName(ChatColor.GREEN + String.valueOf(Integer.parseInt(price) + 10));
            PriceBlock.setItemMeta(PriceBlockMeta);
            gui.setItem(13, priceBlock);
            gui.update();
        });
        GuiItem plusHundred = ItemBuilder.from(PlusHundred).asGuiItem(event -> {
            event.setCancelled(true);
            String price = event.getView().getItem(13).getItemMeta().getDisplayName().replace("§a", "");
            PriceBlockMeta.setDisplayName(ChatColor.GREEN + String.valueOf(Integer.parseInt(price) + 100));
            PriceBlock.setItemMeta(PriceBlockMeta);
            gui.setItem(13, priceBlock);
            gui.update();
        });
        GuiItem plusThousand= ItemBuilder.from(PlusThousand).asGuiItem(event -> {
            event.setCancelled(true);
            String price = event.getView().getItem(13).getItemMeta().getDisplayName().replace("§a", "");
            PriceBlockMeta.setDisplayName(ChatColor.GREEN + String.valueOf(Integer.parseInt(price) + 1000));
            PriceBlock.setItemMeta(PriceBlockMeta);
            gui.setItem(13, priceBlock);
            gui.update();
        });
        GuiItem minusOne = ItemBuilder.from(MinusOne).asGuiItem(event -> {
            event.setCancelled(true);
            String price = event.getView().getItem(13).getItemMeta().getDisplayName().replace("§a", "");
            if (Integer.parseInt(price) < 1) {
                event.getWhoClicked().sendMessage(ChatColor.BLUE + "Сумма не может быть меньше нуля");
                return;
            }
            PriceBlockMeta.setDisplayName(ChatColor.GREEN + String.valueOf(Integer.parseInt(price) - 1));
            PriceBlock.setItemMeta(PriceBlockMeta);
            gui.setItem(13, priceBlock);
            gui.update();
        });
        GuiItem minusTen = ItemBuilder.from(MinusTen).asGuiItem(event -> {
            event.setCancelled(true);
            String price = event.getView().getItem(13).getItemMeta().getDisplayName().replace("§a", "");
            if (Integer.parseInt(price) < 10) {
                event.getWhoClicked().sendMessage(ChatColor.BLUE + "Сумма не может быть меньше нуля");
                return;
            }
            PriceBlockMeta.setDisplayName(ChatColor.GREEN + String.valueOf(Integer.parseInt(price) - 10));
            PriceBlock.setItemMeta(PriceBlockMeta);
            gui.setItem(13, priceBlock);
            gui.update();
        });
        GuiItem minusHundred = ItemBuilder.from(MinusHundred).asGuiItem(event -> {
            event.setCancelled(true);
            String price = event.getView().getItem(13).getItemMeta().getDisplayName().replace("§a", "");
            if (Integer.parseInt(price) < 100) {
                event.getWhoClicked().sendMessage(ChatColor.BLUE + "Сумма не может быть меньше нуля");
                return;
            }
            PriceBlockMeta.setDisplayName(ChatColor.GREEN + String.valueOf(Integer.parseInt(price) - 100));
            PriceBlock.setItemMeta(PriceBlockMeta);
            gui.setItem(13, priceBlock);
            gui.update();
        });
        GuiItem minusThousand = ItemBuilder.from(MinusThousand).asGuiItem(event -> {
            event.setCancelled(true);
            String price = event.getView().getItem(13).getItemMeta().getDisplayName().replace("§a", "");
            if (Integer.parseInt(price) < 1000) {
                event.getWhoClicked().sendMessage(ChatColor.BLUE + "Сумма не может быть меньше нуля");
                return;
            }
            PriceBlockMeta.setDisplayName(ChatColor.GREEN + String.valueOf(Integer.parseInt(price) - 1000));
            PriceBlock.setItemMeta(PriceBlockMeta);
            gui.setItem(13, priceBlock);
            gui.update();
        });


        gui.setItem(9, plusOne);
        gui.setItem(10, plusTen);
        gui.setItem(11, plusHundred);
        gui.setItem(12, plusThousand);
        gui.setItem(13, priceBlock);
        gui.setItem(14, minusThousand);
        gui.setItem(15, minusHundred);
        gui.setItem(16, minusTen);
        gui.setItem(17, minusOne);
        gui.setItem(3, 9, accept);
        gui.setItem(3, 1, cancel);

        gui.open(sender);

    }



}
