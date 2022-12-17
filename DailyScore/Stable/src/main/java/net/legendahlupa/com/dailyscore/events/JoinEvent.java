package net.legendahlupa.com.dailyscore.events;

import net.legendahlupa.com.dailyscore.DailyScore;
import net.legendahlupa.com.dailyscore.common.Timer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public class JoinEvent implements Listener {


    private final File dataFolder;
    private final DailyScore plugin;
    public JoinEvent(File dataFolder, DailyScore plugin) {
        this.dataFolder = dataFolder;
        this.plugin = plugin;
    }

    public HashMap<UUID, Integer> hashId = new HashMap<UUID, Integer>();
    public HashMap<UUID, Integer> hashTime = new HashMap<UUID, Integer>();
    @EventHandler
    public void join(PlayerJoinEvent e){
        /*
         * Вызов метода запуска таймера
         */
        Timer timer = new Timer(plugin, dataFolder);
        timer.startTimer(e.getPlayer(), hashId, hashTime);
    }

    @EventHandler
    public void leave(PlayerQuitEvent e){
        /*
         * Вызов метода выключение таймера
         */
        Timer timer = new Timer(plugin, dataFolder);
        timer.stopTimer(e.getPlayer(), hashId, hashTime);
    }

}
