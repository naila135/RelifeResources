package net.legendahlupa.com.relifedonv2;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import net.legendahlupa.com.relifedonv2.transactions.*;

public class DmoneyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 3){
            Player target = Bukkit.getPlayer(args[1]);
            Integer dmoney = Integer.parseInt(args[2]);
            if (args[0].equals("give")) {
                if (!sender.hasPermission("dmoney.give")) {
                    sender.sendMessage(ChatColor.RED + "Недостаточно прав");
                    return true;
                }
                if (target == null) {
                    OfflinePlayer targetofline = Bukkit.getOfflinePlayer(args[1]);
                    GiveMoney.givemoneytransactionoffline(dmoney, targetofline, sender);
                } else {
                    GiveMoney.givemoneytransaction(dmoney, target.getUniqueId(), target, sender);
                }
            }
            if (args[0].equals("take")) {
                if (!sender.hasPermission("dmoney.take")) {
                    sender.sendMessage(ChatColor.RED + "Недостаточно прав");
                    return true;
                }
                if (target == null) {
                    OfflinePlayer targetofline = Bukkit.getOfflinePlayer(args[1]);
                    takemoney.takemoneyonlineoffline(dmoney, targetofline, sender);
                } else {
                    Player targetonline = Bukkit.getPlayer(args[1]);
                    takemoney.takemoneyonline(dmoney, target.getUniqueId(), sender, targetonline);
                }
            }
            if (args[0].equals("set")) {
                if (!sender.hasPermission("dmoney.set")) {
                    sender.sendMessage(ChatColor.RED + "Недостаточно прав");
                    return true;
                }
                if (target == null) {
                    OfflinePlayer targetoffline = Bukkit.getOfflinePlayer(args[1]);
                    SetMoney.setoffline(dmoney, targetoffline, sender);

                } else {
                    SetMoney.setonline(dmoney, target.getUniqueId(), target, sender);
                }
            }
        } else if (args.length == 2){
            Player target = Bukkit.getPlayer(args[1]);
            if (args[0].equals("check")) {
                if (!sender.hasPermission("dmoney.check")) {
                    sender.sendMessage(ChatColor.RED + "Недостаточно прав");
                    return true;
                }
                if (target == null) {
                    OfflinePlayer targetoffline = Bukkit.getOfflinePlayer(args[1]);
                    CheckMoney.checkplayermoneyoffline(targetoffline, sender);
                } else {
                    CheckMoney.checkplayermoney(target, sender);
                }
            }
        } else {
            sender.sendMessage(ChatColor.GOLD + "/dmoney give <player> <value> - Выдать донат валюту игроку");
            sender.sendMessage(ChatColor.GOLD + "/dmoney take <player> <value> - Забрать донат валюту у игрока");
            sender.sendMessage(ChatColor.GOLD + "/dmoney set <player> <value> - Выставить значение донат валюты игроку");
            sender.sendMessage(ChatColor.GOLD + "/dmoney check <player> - Посмотреть сколько на счёту у игрока донат валюты");
        }



        return false;
    }
}
