package net.legendahlupa.com.relifeadminpassword.events;

import net.legendahlupa.com.relifeadminpassword.db.DataBase;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class Join implements Listener {

    private FileConfiguration config;
    private LuckPerms api;

    public Join(FileConfiguration config, LuckPerms api) {
        this.config = config;
        this.api = api;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        for (int i = 0; i < config.getList("AdminGroups").size(); i++) {
            if (e.getPlayer().hasPermission("group." + config.getList("AdminGroups").get(i))) {
                DataBase dataBase = new DataBase(config);
                User user = api.getPlayerAdapter(Player.class).getUser(player);
                try {
                    String group = getPlayerGroup(e.getPlayer(), (Collection<String>) config.getList("AdminGroups"));
                    if (!dataBase.AdminExistsInDB(player.getUniqueId().toString(), group)) {
                        player.sendMessage("debug2");
                        String password = UUID.randomUUID().toString().replace("-", "").substring(0, 12);
                        dataBase.addNewAdminToDB(player.getDisplayName(), player.getUniqueId().toString(), group, password);
                        player.kickPlayer(ChatColor.RED + "Ваших данных как админа не было в базе " + "\n" + " Зайдите заново");
                        player.sendMessage("debug3");
                    }
                    user.data().remove(Node.builder("group." + config.getList("AdminGroups").get(i)).build());
                    api.getUserManager().saveUser(user);
//                    user.data().remove(Node.builder("group." + dataBase.getAdminGroup(player.getUniqueId().toString(), "null")).build());
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }


            }
        }
    }

    public String getPlayerGroup(Player player, Collection<String> possibleGroups) {
        for (String group : possibleGroups) {
            if (player.hasPermission("group." + group)) {
                return group;
            }
        }
        return null;
    }

}
