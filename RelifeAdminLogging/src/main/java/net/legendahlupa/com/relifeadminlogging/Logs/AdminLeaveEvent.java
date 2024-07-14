package net.legendahlupa.com.relifeadminlogging.Logs;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public class AdminLeaveEvent implements Listener {

    public List<UUID> admins;


    public AdminLeaveEvent(List<UUID> admins) {
        this.admins = admins;
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        String playerusername = player.getName();

        if (admins.contains(player.getUniqueId())) {
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
                fileWriter.write(formattedTime + " ---> " + "Администратор " + playerusername + " вышел с сервера" + "\n");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
