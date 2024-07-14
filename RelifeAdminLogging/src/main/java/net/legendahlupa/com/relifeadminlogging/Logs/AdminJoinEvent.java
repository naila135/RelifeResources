package net.legendahlupa.com.relifeadminlogging.Logs;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class AdminJoinEvent implements Listener {
    public List<UUID> admins;


    public AdminJoinEvent(List<UUID> admins) {
        this.admins = admins;
    }

    @EventHandler
    public void onjoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerusername = player.getName();

        if (admins.contains(player.getUniqueId())) {
            createFile(playerusername);
            // Получаем текущее время
            LocalTime currentTime = LocalTime.now();
            // Форматируем время в виде "чч:мм:сс"
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String formattedTime = currentTime.format(timeFormatter);

            // Получаем текущую дату для имени файла
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = currentDate.format(dateFormatter);

            String directoryPath = "Administrator_Logs/data/" + playerusername + "/Join_Quit";
            String fileName = formattedDate + ".txt";
            String filePath = directoryPath + "/" + fileName;


            try (FileWriter fileWriter = new FileWriter(filePath, true)) { // true для добавления времени в файл
                fileWriter.write(formattedTime + " ---> " + "Администратор " + playerusername + " зашел на сервер" + "\n");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    private void createFile(String playerusername) {
        // Получить текущую дату
        Date now = new Date();

        // Форматировать дату
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = dateFormat.format(now);

        // Указать директорию, в которой будет создан файл
        String directoryPath = "Administrator_Logs/data/" + playerusername + "/Join_Quit";// Замените на нужный путь

        // Создать имя файла
        String fileName = dateStr + ".txt";

        // Полный путь к файлу
        String filePath = directoryPath + File.separator + fileName;

        // Создать файл
        File file = new File(filePath);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
