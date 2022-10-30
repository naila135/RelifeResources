package net.legendahlupa.com.relifedonv2.Event;
import net.legendahlupa.com.relifedonv2.mysqlcreate.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class EventJoin implements Listener {

    @EventHandler
    public void onjoin(PlayerJoinEvent e){
            Player player = e.getPlayer();
            createplayer.creationplayer(player.getUniqueId(), player);
        }
    }
