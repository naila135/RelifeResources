package net.legendahlupa.com.dailyscore.common;


import net.legendahlupa.com.dailyscore.DailyScore;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.util.*;

public class Timer {

    private final DailyScore plugin;
    private final File dataFolder;


    public Timer(DailyScore plugin, File datafolder) {
        this.plugin = plugin;
        this.dataFolder = datafolder;
    }

    BukkitScheduler timer = Bukkit.getScheduler();

    int time;

    /**
     * @method - запуск таймера
     * @param player - игрок
     * @param hashId - хеш мап с игроком и его уникальным TaskID
     * @param hashTime - хеш мап с игроком и его временем
     */
    public void startTimer(Player player, HashMap<UUID, Integer> hashId, HashMap<UUID, Integer> hashTime) {
        /*
         * Cоздание нового повторяющегося действия
         */
        BukkitTask task = timer.runTaskTimer(plugin, () -> {
            /*
             * Увиличаем значение времени на 1
             */
            time = time + 1;
            /*
             * Удаляем и добавляем значение в HashMap
             */
            hashTime.remove(player.getUniqueId());
            hashTime.put(player.getUniqueId(), time);
            /*
             * Вызов метода добавления времени в data.yml когда игрок в игре
             */
            addTimeInGame(player);
        }, 20, 20);
        /*
         * Получение айди таска
         */
        int id = task.getTaskId();
        /*
         * Добавляем игрока и его айди
         */
        hashId.put(player.getUniqueId(), id);
    }


    /**
     * @method - остановка таймера
     * @param player - игрок с таймером
     * @param hashID - хеш мап с игроком и его уникальным Task ID
     * @param hashTime - хеш мап с игроком и его временем
     */

    public void stopTimer(Player player, HashMap<UUID, Integer> hashID, HashMap<UUID, Integer> hashTime) {


        /* Переменные
         * time - получение числа таймера
         * id - ID таска
         */
        int time = hashTime.get(player.getUniqueId());
        int id = hashID.get(player.getUniqueId());


        /*
         * Остановка таймера
         */
        timer.cancelTask(id);


        /*
         * Получаем файл data.yml
         */
        File Datafile = new File(dataFolder.getPath(), "data.yml");
        FileConfiguration data = YamlConfiguration.loadConfiguration(Datafile);



        /*
         * Получение значение секунд из data.yml
         */
        String second_from_config = data.getString(player.getName() + ".second");

        /*
         * Проверка, что бы строка second не была null
         */
        if (data.getString(player.getName() + ".second") == null){
            second_from_config = "0";
        }

        /* Переменные
         * hours - Из секунд делаем минуты
         * minute - Из секунд делаем час
         */
        int hours = (Integer.parseInt(Objects.requireNonNull(second_from_config)) + time) / 3600;
        int minute = (Integer.parseInt(second_from_config) + time) / 60;


        try {
            /*
             * Запись данных в data.yml
             */
            data.set(player.getName() + ".second", Integer.parseInt(second_from_config) + time);
            data.set(player.getName() + ".minute", minute);
            data.set(player.getName() + ".hours", hours);
            data.set(player.getName() + ".score", hours);
            /*
             * Сохранение файла data.yml
             */
            data.save(Datafile);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @method - Добавление времени в конфиг когда игрок в игре
     * @param player - игрок
     */
    public void addTimeInGame(Player player){
        /*
         * Получаем файл data.yml
         */
        File Datafile = new File(dataFolder.getPath(), "data.yml");
        FileConfiguration data = YamlConfiguration.loadConfiguration(Datafile);

        /*
         * Получение значение секунд из data.yml
         */
        String second_from_config = data.getString(player.getName() + ".second");

        /*
         * Проверка, что бы секунды из конфига не были null
         */
        if (second_from_config == null){
            second_from_config = "0";
        }



        try {
            /* Переменные
             * hours - Из секунд делаем минуты
             * minute - Из секунд делаем час
             */
            int minute = Integer.parseInt(second_from_config) / 60;
            int hours = Integer.parseInt(second_from_config) / 3600;
            /*
             * Запись данных в data.yml
             */
            data.set(player.getName() + ".second", Integer.parseInt(second_from_config) + 1);
            data.set(player.getName() + ".minute", minute);
            data.set(player.getName() + ".hours", hours);
            data.set(player.getName() + ".score", hours);
            /*
             * Сохранение файла data.yml
             */
            data.save(Datafile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
