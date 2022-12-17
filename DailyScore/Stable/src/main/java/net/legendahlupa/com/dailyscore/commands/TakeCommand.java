package net.legendahlupa.com.dailyscore.commands;

import net.legendahlupa.com.dailyscore.common.Logs;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Objects;

public class TakeCommand implements CommandExecutor {
    private final File dataFolder;
    public TakeCommand(File dataFolder) {
        this.dataFolder = dataFolder;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        /*
         * Проверка количества аргументов
         */
        if (args.length < 2){
            sender.sendMessage("Недостаточно аргументов");
            return false;
        }
        /*
         * Взаимодействия с файлом data.yml
         */
        File Datafile = new File(dataFolder.getPath(), "data.yml");
        FileConfiguration data = YamlConfiguration.loadConfiguration(Datafile);
        /*
         * Проверка есть ли игрок в базе
         */
        if (data.get(args[0]) == null){
            sender.sendMessage("Игрок не обнаружен в базе попросите игрока зайти");
            return false;
        }

        try {
            /*
             * Получение значений из файла data.yml
             */
            int score = Integer.parseInt(Objects.requireNonNull(data.getString(args[0] + ".score")));
            int second_from_config = Integer.parseInt(Objects.requireNonNull(data.getString(args[0] + ".second")));
            /*
             * Отправка сообщения об успешной выдачи очков
             */
            if (second_from_config < Integer.parseInt(args[1]) * 3600){
                sender.sendMessage("У игрока недостаточно очков");
                return true;
            }
            /*
             * Запись данных в logs.yml
             */
            sender.sendMessage(ChatColor.GREEN + "Вы успешно забрали " + args[1] + " очко у игрока " + args[0]);
            data.set(args[0] + ".second",second_from_config - (Integer.parseInt(args[1]) * 3600));
            data.set(args[0] + ".score" ,score + Integer.parseInt(args[1]));
        } catch (NumberFormatException e){
            sender.sendMessage("Количество должно быть числом");
            return false;
        }
        /*
         * Вызов метода записи логов
         */
        Logs logs = new Logs(dataFolder);
        logs.setLogs(args[0], sender, Integer.parseInt(args[1]), "Take");
        /*
         * Сохранение файла
         */
        try {
            data.save(Datafile);
        } catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

}
