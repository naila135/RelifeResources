package net.legendahlupa.com.siege.listeners;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Town;
import net.legendahlupa.com.siege.Settings;
import net.legendahlupa.com.siege.Siege;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class SiegeListener extends Settings implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        Town t = TownyAPI.getInstance().getResident(e.getPlayer()).getTownOrNull();
        if (t == null) return;
        if(townyList.contains(t)){
            deathPlayers.add(e.getPlayer());
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        Bukkit.getScheduler().runTaskLater(Siege.getInstance(),() ->{
            Player player = e.getPlayer();
            if (deathPlayers.contains(player)){
                e.setCancelled(true);
                player.sendTitle(ChatColor.DARK_RED + "Вы погибли" + ChatColor.WHITE + "\ue280", ChatColor.RED + "Дождитесь окончания боя");
            }
        }, 40);

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player player = e.getPlayer();
        if (deathPlayers.contains(player)){
            UUID uuid = player.getUniqueId();
            quituuid.add(uuid);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        UUID id2 = e.getPlayer().getUniqueId();
        if (quituuid.contains(id2)) {
            deathPlayers.add(e.getPlayer());
            quituuid.remove(id2);
        }

    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            if (deathPlayers.contains(e.getEntity())) {
                e.setCancelled(true);
            }

            if (deathPlayers.contains(e.getDamager())) {
                e.setCancelled(true);
            }

        }
    }

}
