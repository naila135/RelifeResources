package net.legendahlupa.com.dailyscore.common;

import net.legendahlupa.com.dailyscore.DailyScore;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

public class Logs {

    private final File dataFolder;

    public Logs( File dataFolder){
        this.dataFolder = dataFolder;
    }


    public void setLogs(String player, CommandSender admin, int amount, String action){
        /*
         * Взаимодействия с файлом logs.yml
         */
        File Datafile = new File(dataFolder.getPath(), "logs.yml");
        FileConfiguration logs = YamlConfiguration.loadConfiguration(Datafile);

        /*
         * Генерация случайного UUID
         */
        UUID uuid = UUID.randomUUID();

        /*
         * Получение времени в формате "dd/MM/yyyy HH:mm:ss"
         */
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();


        /*
         * Установка значения на +2
         */
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+02"));


        /*
         * Запись данных и сохранение файла
         */
        try {
            logs.set(uuid + ".player", player);
            logs.set(uuid + ".admin", admin.getName());
            logs.set(uuid + ".amount", amount);
            logs.set(uuid + ".action", action);
            logs.set(uuid + ".time" ,formatter.format(date) + " - GMT+02");
            logs.save(Datafile);
        } catch (Exception e){
            e.printStackTrace();
        }

    }



}
