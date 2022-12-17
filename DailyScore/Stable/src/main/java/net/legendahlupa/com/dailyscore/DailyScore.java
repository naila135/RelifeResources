package net.legendahlupa.com.dailyscore;

import net.legendahlupa.com.dailyscore.commands.GiveCommand;
import net.legendahlupa.com.dailyscore.commands.TakeCommand;
import net.legendahlupa.com.dailyscore.common.ClearScore;
import net.legendahlupa.com.dailyscore.common.PlaceHolderExpansion;
import net.legendahlupa.com.dailyscore.common.Score;
import net.legendahlupa.com.dailyscore.events.JoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class DailyScore extends JavaPlugin {




    @Override
    public void onEnable() {
        /*
         * Регистрация ивента
         */
        getServer().getPluginManager().registerEvents(new JoinEvent(getDataFolder(), this), this);
        /*
         * Вызов метода авто-синхронизации data.yml
         */
        Score score = new Score(this, getDataFolder());
        score.addScore();
        /*
         * Вызов метода авто-очистки data.yml
         */
        ClearScore clearScore = new ClearScore(this, getDataFolder());
        clearScore.clear();

        /*
         * Регистрация команд
         */
        Objects.requireNonNull(getCommand("DailyScoreGive")).setExecutor(new GiveCommand(getDataFolder()));
        Objects.requireNonNull(getCommand("DailyScoreTake")).setExecutor(new TakeCommand(getDataFolder()));
        /*
         * Регистрация плейс-холдера
         */
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceHolderExpansion(this).register();
        }
    }






    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
    }
}
