package net.legendahlupa.com.dailyscore.common;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.legendahlupa.com.dailyscore.DailyScore;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class PlaceHolderExpansion extends PlaceholderExpansion {

    private DailyScore plugin;

    public PlaceHolderExpansion(DailyScore plugin){
        this.plugin = plugin;
    }


    @Override
    public String getAuthor() {
        return "LegendaHluPa";
    }

    @Override
    public String getIdentifier() {
        return "DailyScore";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        File Datafile = new File(plugin.getDataFolder().getPath(), "data.yml");
        FileConfiguration data = YamlConfiguration.loadConfiguration(Datafile);
        if(params.equalsIgnoreCase("score")){
            return data.getString(player.getName() + ".score");
        }

        return null;
    }

}
