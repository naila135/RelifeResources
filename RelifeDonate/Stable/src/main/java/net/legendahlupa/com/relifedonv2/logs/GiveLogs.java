package net.legendahlupa.com.relifedonv2.logs;

import net.legendahlupa.com.relifedonv2.RelifeDonV2;
import net.legendahlupa.com.relifedonv2.mysqlcreate.createplayer;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GiveLogs {

    public static void givelogsonline(Player target, CommandSender sender, int dmoney) {

        try {
            PreparedStatement logs = RelifeDonV2.getInstance().getConnection()
                    .prepareStatement("INSERT INTO dmoney_logs(ADMIN,ACTION,AMOUNT,PLAYER,SERVER_LICENCE) VALUES(?,?,?,?,?)");
            logs.setString(1, sender.getName());
            logs.setString(2, "GIVE");
            logs.setString(3, String.valueOf(dmoney));
            logs.setString(4, target.getName());
            logs.setString(5, RelifeDonV2.getInstance().getConfig().getString("License"));

            logs.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void givelogoffline(OfflinePlayer targetoffline, CommandSender sender, int dmoney) {
        try {
            if (!createplayer.playerExists(targetoffline.getUniqueId())) {
                sender.sendMessage(ChatColor.RED + "Игрок не найден в базе данных");
            } else {
                PreparedStatement logs = RelifeDonV2.getInstance().getConnection()
                        .prepareStatement("INSERT INTO dmoney_logs(ADMIN,ACTION,AMOUNT,PLAYER,SERVER_LICENCE) VALUES(?,?,?,?,?)");
                logs.setString(1, sender.getName());
                logs.setString(2, "GIVE");
                logs.setString(3, String.valueOf(dmoney));
                logs.setString(4, targetoffline.getName());
                logs.setString(5, RelifeDonV2.getInstance().getConfig().getString("License"));

                logs.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}