package net.legendahlupa.com.relifedonv2.mysqlcreate;

import net.legendahlupa.com.relifedonv2.RelifeDonV2;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class createplayer {

    public static void creationplayer(final UUID uuid, Player player){
        try {
            PreparedStatement statement = RelifeDonV2.getInstance().getConnection()
                    .prepareStatement("SELECT * FROM " + "dmoney_money" + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            ResultSet results = statement.executeQuery();
            results.next();
            System.out.print(1);
            if (playerExists(uuid) != true) {
                PreparedStatement insert = RelifeDonV2.getInstance().getConnection()
                        .prepareStatement("INSERT INTO " + "dmoney_money" + " (UUID,NAME,DMONEY) VALUES (?,?,?)");
                insert.setString(1, uuid.toString());
                insert.setString(2, player.getName());
                insert.setInt(3, 0);
                insert.executeUpdate();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static boolean playerExists(UUID uuid) {
        try {
            PreparedStatement statement = RelifeDonV2.getInstance().getConnection()
                    .prepareStatement("SELECT * FROM " + "dmoney_money" + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            ResultSet results = statement.executeQuery();
            if (results.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
