package net.legendahlupa.com.dailyscore.common;

import net.legendahlupa.com.dailyscore.DailyScore;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class Score {

    private final DailyScore plugin;
    private final File dataFolder;

    /**
     * @method - каждую секунду вычисление очков и добавление в data.yml
     */

    BukkitScheduler addScore = Bukkit.getScheduler();

    public Score(DailyScore plugin, File dataFolder) {
        this.plugin = plugin;
        this.dataFolder = dataFolder;
    }

    public void addScore() {
        /*
         * Автоматическое исполнение с промежутком
         */

        addScore.runTaskTimer(plugin, () -> {

            /*
             * OnlinePlayers - список всех игроков сервера
             */
            ArrayList<Player> OnlinePlayers = new ArrayList<>(Bukkit.getOnlinePlayers());
            for (int i = 0; i < OnlinePlayers.size(); i++) {
                /*
                 * Получаем файл data.yml
                 */
                File Datafile = new File(dataFolder.getPath(), "data.yml");
                FileConfiguration data = YamlConfiguration.loadConfiguration(Datafile);

                /*
                 * secondInt - получение значения секунд из конфига
                 * hoursInt - получение значения минут из конфига
                 */
                int secondInt = Integer.parseInt(Objects.requireNonNull(data.getString(OnlinePlayers.get(i).getName() + ".second")));
                int hoursInt = Integer.parseInt(Objects.requireNonNull(data.getString(OnlinePlayers.get(i).getName() + ".hours")));

                try {
                    /*
                     * Запись данных в data.yml
                     */
                    data.set(OnlinePlayers.get(i).getName() + ".minute", secondInt / 60);
                    data.set(OnlinePlayers.get(i).getName() + ".hours", secondInt / 3600);
                    data.set(OnlinePlayers.get(i).getName() + ".score", hoursInt);
                    /*
                     * Сохранение файла
                     */
                    data.save(Datafile);
                    /*
                     * Очистка листа игроков
                     */
                    OnlinePlayers.clear();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 20, 20);
    }

}
