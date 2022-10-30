package net.legendahlupa.com.relifedonv2.transactions;

import net.legendahlupa.com.relifedonv2.RelifeDonV2;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckMoney {
    public static void checkplayermoney(Player target, CommandSender sender){

        try {
            PreparedStatement statement = RelifeDonV2.getInstance().getConnection()
                    .prepareStatement("SELECT * FROM dmoney_money WHERE UUID=?");
            statement.setString(1, target.getUniqueId().toString());
            ResultSet results = statement.executeQuery();
            results.next();
            sender.sendMessage(ChatColor.GREEN + ("У игрока " + target.getName() + " на балансе: " + results.getInt("DMONEY") + " донат валюты"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void checkplayermoneyoffline(OfflinePlayer targetoffline, CommandSender sender){
        try {
            PreparedStatement statement = RelifeDonV2.getInstance().getConnection()
                    .prepareStatement("SELECT * FROM dmoney_money WHERE UUID=?");
            statement.setString(1, targetoffline.getUniqueId().toString());
            ResultSet results = statement.executeQuery();
            results.next();
            sender.sendMessage(ChatColor.GREEN + ("У игрока " + targetoffline.getName() + " на балансе: " + results.getInt("DMONEY") + " донат валюты"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
