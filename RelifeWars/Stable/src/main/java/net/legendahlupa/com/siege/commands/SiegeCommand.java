package net.legendahlupa.com.siege.commands;

import com.palmergames.bukkit.towny.TownyMessaging;
import com.palmergames.bukkit.towny.object.Town;
import net.legendahlupa.com.siege.Settings;
import net.legendahlupa.com.siege.Siege;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class SiegeCommand extends Settings implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length >= 1) {
            if (args[0].equals("end")) {
                sender.sendMessage(ChatColor.GREEN + "Война окончена");
                townyList.forEach(town -> TownyMessaging.sendPrefixedTownMessage(town, "Война окончена"));
                Siege.stop();
                return true;
            }
            switch (args[0]) {
                case "list":
                    return listSubCommnad(args, sender);
                case "start":
                    Siege.start();
                    townyList.forEach(town -> TownyMessaging.sendPrefixedTownMessage(town, "Началась война"));
                    return true;
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Проверьте правильность команды");
        }
        return true;
    }

    private boolean listSubCommnad(String[] args, CommandSender sender) { //подкоманда list вынесена в отдельный метод
        if (args.length == 1) {
            sender.sendMessage(ChatColor.GREEN + "Список городов: ");
            townyList.forEach((s) -> sender.sendMessage(s.getName()));
        } else if (args.length > 2) {

            Town t = tAPI.getTown(args[2]);
            if (t == null) {
                sender.sendMessage(ChatColor.RED + "Город не найден");
                return false;
            }
            switch (args[1]) {
                case "add":
                    if (!sender.hasPermission("siege.list.add")) return false;
                    townyList.add(t);
                    sender.sendMessage(ChatColor.GREEN + "Город" + " town" + " был успешно добавлен в список");
                    return true;
                case "remove":
                    if (!sender.hasPermission("siege.list.remove")) return false;
                    if (townyList.contains(t)) {
                        townyList.remove(t);
                        sender.sendMessage(ChatColor.GREEN + "Город" + " town" + " был успешно удален из списка");
                        return true;
                    } else {
                        sender.sendMessage(ChatColor.RED + "Этого города нет в списке");
                        return false;
                    }
                case "clear":
                    if (!sender.hasPermission("siege.list.remove")) return false;
                    townyList.clear();
            }
        }
        return false;
    }

}
