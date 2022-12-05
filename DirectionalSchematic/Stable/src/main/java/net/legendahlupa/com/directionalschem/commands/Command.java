package net.legendahlupa.com.directionalschem.commands;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownBlock;
import com.palmergames.bukkit.towny.object.WorldCoord;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.transform.AffineTransform;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.session.SessionManager;
import com.sk89q.worldedit.session.SessionOwner;
import com.sk89q.worldedit.world.World;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;

public class Command implements CommandExecutor {
    private File datafolder;

    public Command(File dataFolder) {
        this.datafolder = dataFolder;
    }

    private ClipboardHolder clipboard;
    Clipboard clipboard1;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {

        /**
         * вывод ошибки когда пользователь не указал аргументы
         */
        if (args.length < 1) {
            sender.sendMessage("Напишите название схематики");
            return true;
        }
        /**
         * @p - получение из отправителя команды игрока
         * @actor - объект сессии WorldEdit
         * @localsession - сессия редактирования WorldEdit
         */
        Player p = (Player) sender;
        SessionOwner actor = BukkitAdapter.adapt(p);
        SessionManager manager = WorldEdit.getInstance().getSessionManager();
        LocalSession localSession = manager.get(actor);


        /**
         * Проверка игрок в своём городе или нет
         */
        try {
            WorldCoord.parseWorldCoord(p).getTownBlock().hasTown();
        } catch (Exception e) {
            p.sendMessage(ChatColor.RED + "Вы не в своем городе");
            return true;
        }

        /**
         * @resident - получение резидента из обычного игрока
         */
        Resident resident = TownyAPI.getInstance().getResident(p);
        /**
         * Проверка игрок мер города или нет
         */
        if (!(resident.isMayor())) {
            p.sendMessage(ChatColor.RED + "Вы не мер");
            return true;
        }


        /**
         * @x - получение координаты X
         * @y - получение координаты Y
         * @z - получение координаты Z
         */
        double x = p.getLocation().getX();
        double y = p.getLocation().getY();
        double z = p.getLocation().getZ();
        /**
         * создание WorldEdit объекта мир
         */
        World world = BukkitAdapter.adapt(p.getWorld());

        /**
         * Получение файла из папки /schematics/%File_name%.schem
         */
        File file = new File(datafolder.getPath() + "/schematics/" + args[0] + ".schem");
        ClipboardFormat format = ClipboardFormats.findByFile(file);
        try (ClipboardReader reader = format.getReader(Files.newInputStream(file.toPath()))) {
            clipboard1 = reader.read();
            localSession.setClipboard(new ClipboardHolder(clipboard1));
        } catch (Exception e) {
            p.sendMessage("Файл не найден");
            return true;
        }
        /**
         * @rotate - вызов метода поворота
         */
        rotate(localSession, getDirection(p));
        /**
         * вставка изменённой схематики
         */
        try (EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {
            Operation operation = clipboard
                    .createPaste(editSession)
                    .to(BlockVector3.at(x, y, z))
                    .build();
            Operations.complete(operation);
            p.sendMessage(ChatColor.GREEN + "Постройка успешно поставлена");
            localSession.remember(editSession);
            /**
             * Вызов метода логирования
             */
            setLogs(p.getName(), args[0] + ".schem", x, y, z, getDirectionString(p));
            return true;
        } catch (Exception e) {
            p.sendMessage(String.valueOf(e));
        }


        return true;
    }

    public void setLogs(String playername, String schemname, double x, double y, double z, String direction) {

        /**
         * Создание идентификатора лога
         */
        UUID uuid = UUID.randomUUID();


        /**
         * Получение времени в формате "dd/MM/yyyy HH:mm:ss"
         */

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        /**
         * Ставим таймзону "на москву"
         */
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+02"));


        /**
         * Создание лог файла
         */

        File logsfile = new File(datafolder.getPath(), "logs.yml");
        FileConfiguration logs = YamlConfiguration.loadConfiguration(logsfile);


        /**
         * Путь до строки в которой нужно вписывать
         */
        String patch_to_player = String.join(".", uuid.toString(), "Player");
        String patch_to_schematic_name = String.join(".", uuid.toString(), "SchematicName");
        String patch_to_direction = String.join(".", uuid.toString(), "Direction");
        String patch_to_time = String.join(".", uuid.toString(), "Time");
        String patch_to_x = String.join(".", uuid.toString(), "X");
        String patch_to_y = String.join(".", uuid.toString(), "Y");
        String patch_to_z = String.join(".", uuid.toString(), "Z");

        /**
         * Вписывание значений в файл
         */
        logs.set(patch_to_player, playername);
        logs.set(patch_to_schematic_name, schemname);
        logs.set(patch_to_direction, direction);
        logs.set(patch_to_time ,formatter.format(date) + " - По москве");
        logs.set(patch_to_x, x);
        logs.set(patch_to_y, y);
        logs.set(patch_to_z, z);

        /**
         * Сохранение лог файла
         */
        try {
            logs.save(logsfile);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /**
     * Функция получения оси взгляда игрока
     *
     * @param player - игрок, который ставит
     * @return - возвращение взгляда, если он никуда не смотрит, но такое невозможно
     */
    private double getDirection(Player player) {
        double rot = (player.getLocation().getYaw());
        // -45 to 45
        if (-45 < rot && rot <= 45) {
            //south
            return 180;
        } else if (45 < rot && rot <= 135) {
            //west
            return 90;
        } else if (135 < rot || rot <= -135) {
            return 0;
        } else if (-135 < rot && rot <= -45) {
            //east
            return 270;

        }
        return rot;
    }

    /**
     *
     * @param player - игрок, который ставит
     * @return - возвращение направление взгляда
     */
    private String getDirectionString(Player player) {
        double rot = (player.getLocation().getYaw());
        // -45 to 45
        if (-45 < rot && rot <= 45) {
            //south
            return "South";
        } else if (45 < rot && rot <= 135) {
            //west
            return "West";
        } else if (135 < rot || rot <= -135) {
            //north
            return "North";
        } else if (-135 < rot && rot <= -45) {
            //east
            return "East";

        }
        return "Забань его нахуй как то получилось что он никуда не смотрит";
    }


    /**
     * функция поворота схематики
     *
     * @param session - локальная сессия перед вставкой схематики
     * @param y - направление куда повернуть
     */

    public void rotate(LocalSession session, double y) {
        try {
            clipboard = session.getClipboard();
        } catch (Exception ex) {
            return;
        }
        AffineTransform transform = new AffineTransform();
        clipboard.setTransform(clipboard.getTransform().combine(transform.rotateY(y)));
    }
}
