package net.legendahlupa.com.relifeadminpassword.commands;

import net.legendahlupa.com.relifeadminpassword.db.DataBase;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.UUID;

public class AGenerate implements CommandExecutor {

    private final FileConfiguration config;
    public AGenerate(FileConfiguration config) {
        this.config = config;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        if (!player.hasPermission("alogin.generate")) {
            player.sendMessage(ChatColor.RED + "У вас недостаточно прав");
            return true;
        }
        if (args.length < 2){
            player.sendMessage("Проверьте правильность набранной команды");
            return true;
        }
        Player adminplayer = Bukkit.getPlayer(args[1]);
        DataBase dataBase = new DataBase(config);
        if (adminplayer == null){
            sender.sendMessage(ChatColor.RED + "Игрок оффлайн");
            return true;
        }
        try {
            String group = args[2];
            if (!dataBase.AdminExistsInDB(adminplayer.getUniqueId().toString(), group)){
                String password = UUID.randomUUID().toString().replace("-", "").substring(0, 12);
                dataBase.addNewAdminToDB(adminplayer.getName(), adminplayer.getUniqueId().toString(), group, password);
            } else {
                sender.sendMessage(ChatColor.RED + "Данный админ уже есть в базе");
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
