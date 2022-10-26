package net.legendahlupa.com.siege;

import net.legendahlupa.com.siege.commands.LivesCommand;
import net.legendahlupa.com.siege.commands.SiegeCommand;
import net.legendahlupa.com.siege.listeners.SiegeListener;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Siege extends JavaPlugin {

    private static Siege instance;
    public static Listener listener;
    @Override
    public void onEnable() {
        instance = this;
        listener = new SiegeListener();
        getCommand("siege").setExecutor(new SiegeCommand());
        getCommand("siegealive").setExecutor(new LivesCommand());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static void start(){
        Settings settings = new Settings();
        HandlerList.unregisterAll(listener);
        Bukkit.getPluginManager().registerEvents(listener,  getInstance());
    }

    public static void stop(){
        Settings.quituuid.clear();
        Settings.deathPlayers.clear();
        Settings.townyList.clear();
        HandlerList.unregisterAll(listener);
    }
    public static Siege getInstance() {
        return instance;
    }

}
