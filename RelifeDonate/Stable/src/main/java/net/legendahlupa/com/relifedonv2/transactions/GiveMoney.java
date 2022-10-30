package net.legendahlupa.com.relifedonv2.transactions;

import net.legendahlupa.com.relifedonv2.RelifeDonV2;
import net.legendahlupa.com.relifedonv2.mysqlcreate.createplayer;
import net.legendahlupa.com.relifedonv2.logs.*;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class GiveMoney {

    public static void givemoneytransaction(int dmoney, UUID uuid, Player target, CommandSender sender){
        try{
            PreparedStatement statement = RelifeDonV2.getInstance().getConnection()
                    .prepareStatement("UPDATE " + "dmoney_money" + " SET DMONEY = DMONEY +? WHERE UUID=?");
            statement.setInt(1, dmoney);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
            CheckMoney.checkplayermoney(target, sender);
            GiveLogs.givelogsonline(target, sender,dmoney);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    public static void givemoneytransactionoffline(int dmoney, OfflinePlayer targetoffline, CommandSender sender){
        try{
            if (!createplayer.playerExists(targetoffline.getUniqueId())){
                sender.sendMessage(ChatColor.RED + "Игрок не найден в базе данных");
            } else {
                PreparedStatement statement = RelifeDonV2.getInstance().getConnection()
                        .prepareStatement("UPDATE " + "dmoney_money" + " SET DMONEY = DMONEY +? WHERE UUID=?");
                statement.setInt(1, dmoney);
                statement.setString(2, targetoffline.getUniqueId().toString());
                statement.executeUpdate();
                CheckMoney.checkplayermoneyoffline(targetoffline, sender);
                GiveLogs.givelogoffline(targetoffline, sender, dmoney);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
