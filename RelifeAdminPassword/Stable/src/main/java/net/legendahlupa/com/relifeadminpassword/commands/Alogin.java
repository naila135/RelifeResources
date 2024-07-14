package net.legendahlupa.com.relifeadminpassword.commands;

import net.legendahlupa.com.relifeadminpassword.db.DataBase;
import net.legendahlupa.com.relifeadminpassword.discord.DiscordBot;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.ArrayList;


public class Alogin implements CommandExecutor {

    private FileConfiguration config;
    private LuckPerms api;

    public Alogin(FileConfiguration config, LuckPerms api) {
        this.config = config;
        this.api = api;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        if (!player.hasPermission("alogin.cmd")) {
            player.sendMessage(ChatColor.RED + "У вас недостаточно прав");
            return true;
        }
        if (args.length < 1) {
            player.sendMessage("Проверьте правильность набранной команды");
            return true;
        }
        DataBase dataBase = new DataBase(config);
        try {
            if (args[0].equals(dataBase.getAdminPassword(player.getUniqueId().toString(), args[0]))) {
                User user = api.getPlayerAdapter(Player.class).getUser(player);
                ArrayList<String> group = dataBase.getAdminGroup(player.getUniqueId().toString(), args[0]);
                if (player.isOp()) {
                    player.sendMessage(ChatColor.RED + "У вас оп по этому плагин не действует на вас");
                    return true;
                }
                if (player.hasPermission("group." + group)) {
                    player.sendMessage(ChatColor.GREEN + "Вы уже авторизовались");
                    return true;
                }
                player.sendMessage(ChatColor.GREEN + "Запрос успешно отправлен, ожидайте когда вас одобрят");
                DiscordBot discordBot = new DiscordBot(config);
                discordBot.sendAcceptMessage(user, player, group.get(0), api);

            } else {
                player.sendMessage(ChatColor.RED + "Пароль введен не правильно");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public void accepted(User user, Player player, String group, LuckPerms api) {
        this.api = api;
        user.data().add(Node.builder("group." + group).build());
        player.sendMessage(ChatColor.GREEN + "Вы успешно авторизовались");
        api.getUserManager().saveUser(user);
    }
    public void dennied(User user, Player player, String group, LuckPerms api) {
        this.api = api;
        player.sendMessage(ChatColor.RED + "Вам отказали в получении прав");
        return;
    }
}
