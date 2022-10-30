package net.legendahlupa.com.relifedonv2.transactions;

import net.legendahlupa.com.relifedonv2.RelifeDonV2;
import net.legendahlupa.com.relifedonv2.logs.*;
import net.legendahlupa.com.relifedonv2.mysqlcreate.createplayer;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class takemoney {
    public static void takemoneyonline(int dmoney, UUID uuid, CommandSender sender, Player targetonline) {

        try {
            PreparedStatement haveornot = RelifeDonV2.getInstance().getConnection()
                    .prepareStatement("SELECT * FROM dmoney_money WHERE UUID=?");
            haveornot.setString(1, uuid.toString());
            ResultSet results = haveornot.executeQuery();
            results.next();
            if (results.getInt("DMONEY") < dmoney) {
                sender.sendMessage(ChatColor.GREEN + "У игрока " + targetonline.getName() + " недостаточно средств");
                return;
            }
            PreparedStatement statement = RelifeDonV2.getInstance().getConnection()
                    .prepareStatement("UPDATE " + "dmoney_money" + " SET DMONEY = DMONEY -? WHERE UUID=?");
            statement.setInt(1, dmoney);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
            CheckMoney.checkplayermoney(targetonline, sender);
            TakeLogs.takelogsonline(sender, dmoney, targetonline);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void takemoneyonlineoffline(int dmoney, OfflinePlayer targetoffline, CommandSender sender) {
        try {
            if (!createplayer.playerExists(targetoffline.getUniqueId())) {
                sender.sendMessage(ChatColor.RED + "Игрок не найден в базе данных");
            } else {
                PreparedStatement haveornot = RelifeDonV2.getInstance().getConnection()
                        .prepareStatement("SELECT * FROM dmoney_money WHERE UUID=?");
                haveornot.setString(1, targetoffline.getUniqueId().toString());
                ResultSet results = haveornot.executeQuery();
                results.next();
                if (results.getInt("DMONEY") < dmoney) {
                    sender.sendMessage(ChatColor.GREEN + "У игрока " + targetoffline.getName() + " недостаточно средств");
                    return;
                }
                PreparedStatement statement = RelifeDonV2.getInstance().getConnection()
                        .prepareStatement("UPDATE " + "dmoney_money" + " SET DMONEY = DMONEY -? WHERE UUID=?");
                statement.setInt(1, dmoney);
                statement.setString(2, targetoffline.getUniqueId().toString());
                statement.executeUpdate();
                CheckMoney.checkplayermoneyoffline(targetoffline, sender);
                TakeLogs.takelogsoffline(targetoffline, sender, dmoney);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
