package net.legendahlupa.com.siege.commands;

import net.legendahlupa.com.siege.Settings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SiegeFreezingCommand extends Settings implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        try {
            if (sender.hasPermission("siege.death")) {
                switch (args[0]) {
                    case "allunfreeze":
                        sender.sendMessage(ChatColor.GREEN + "Список игроков которы не могут двигаться очищен");
                        sender.sendMessage(ChatColor.GREEN + "Список умерших очищен");
                        ar.clear();
                        deathPlayers.clear();
                        break;
                    case "unfreeze":
                        if (args.length > 1) {
                            Player player = Bukkit.getPlayer(args[1]);
                            if (player == null) {
                                sender.sendMessage(ChatColor.RED + "Игрок не найден");
                                return true;
                            }
                            ar.remove(player);
                            sender.sendMessage(ChatColor.GREEN + (player.getName() + " Был убран из списка умерших"));
                        } else {
                            sender.sendMessage(indexerror());
                        }
                        break;
                    case "freeze":
                        if (args.length > 1) {
                            Player player1 = Bukkit.getPlayer(args[1]);
                            if (player1 == null) {
                                sender.sendMessage(ChatColor.RED + "Игрок не найден");
                                return true;
                            }
                            sender.sendMessage(ChatColor.RED + "Внимание" + ChatColor.GREEN + " Данная команда будет работать только при включенной войне");
                            ar.add(player1);
                            deathPlayers.add(player1);
                            sender.sendMessage(ChatColor.GREEN + (player1.getName() + " Был добавлен в список умерших"));
                        } else {
                            sender.sendMessage(indexerror());
                        }
                }
            }
        } catch (IndexOutOfBoundsException e){
            sender.sendMessage(indexerror());
        }
        return true;
    }

    public String indexerror() {
        return ChatColor.RED + "Проверьте правильность команды";
    }
}
