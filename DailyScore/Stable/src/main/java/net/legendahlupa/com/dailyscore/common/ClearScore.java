package net.legendahlupa.com.dailyscore.common;

import net.legendahlupa.com.dailyscore.DailyScore;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class ClearScore {

    private final DailyScore plugin;
    private final File dataFolder;

    BukkitScheduler scheduler = Bukkit.getScheduler();
    public ClearScore(DailyScore plugin, File dataFolder){
        this.plugin = plugin;
        this.dataFolder = dataFolder;
    }

    /**
     * @method clear - автоматическая очистка data.yml в 12:00
     */
    public void clear(){
        scheduler.runTaskTimer(plugin, () -> {
            /*
             * date - Получение сегодняшней даты
             * minute - Получение минут из date
             * hours - Получение часов из date
             */
            Date date = new Date(System.currentTimeMillis());
            int minute = date.getMinutes();
            int hours = date.getHours();

            /*
             * Проверка если сейчас 22:59 то выполняем действия
             */
            if(hours == 22  && minute == 59){
                try {
                    /*
                     * Очистка файла
                     */
                    new FileOutputStream(dataFolder.getPath() + "data.yml").close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        },20,20);
    }
}
