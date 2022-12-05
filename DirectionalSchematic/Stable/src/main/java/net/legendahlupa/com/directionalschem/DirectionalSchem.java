package net.legendahlupa.com.directionalschem;

import net.legendahlupa.com.directionalschem.commands.Command;
import org.bukkit.plugin.java.JavaPlugin;

public final class DirectionalSchem extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getCommand("dirschem").setExecutor(new Command(getDataFolder()));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
