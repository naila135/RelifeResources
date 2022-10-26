package net.legendahlupa.com.permtrading;

import net.legendahlupa.com.permtrading.Config.CreatePermissionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class PermTrading extends JavaPlugin {
    @Override
    public void onEnable() {
        getCommand("trading").setExecutor(new TradingCommand());
        createPermissionFile();
    }

    public void createPermissionFile() {

        File config = new File(getDataFolder(), "permissions.yml");
        if(!config.exists()) {
            saveResource("permissions.yml", false);
        }

        reloadConfig();
    }




    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
