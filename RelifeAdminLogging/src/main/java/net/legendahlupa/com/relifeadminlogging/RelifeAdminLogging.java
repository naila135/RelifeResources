package net.legendahlupa.com.relifeadminlogging;


import net.legendahlupa.com.relifeadminlogging.Logs.AdminCommandEvent;
import net.legendahlupa.com.relifeadminlogging.Logs.AdminJoinEvent;
import net.legendahlupa.com.relifeadminlogging.Logs.AdminLeaveEvent;
import net.legendahlupa.com.relifeadminlogging.Logs.AdminMessageEvent;
import net.legendahlupa.com.relifeadminlogging.LuckPerms.GetAdmins;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public final class RelifeAdminLogging extends JavaPlugin {

    public List<UUID> Admins;
    private LuckPerms api;

    @Override
    public void onEnable() {
        registerLuckyPerms();

        Admins = new GetAdmins().getUsersInGroup("admin", api);

        saveDefaultConfig();
        createDirectoryIfNotExist();
        ActivateMessage();

        registerEvents();

        System.out.println(Admins);

    }



    public void registerLuckyPerms() {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            api = provider.getProvider();
        }
        if (provider == null) {
            System.err.println("LuckyPerms не обнаружен!");
            Bukkit.getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new AdminJoinEvent(Admins), this);
        getServer().getPluginManager().registerEvents(new AdminLeaveEvent(Admins), this);
        getServer().getPluginManager().registerEvents(new AdminCommandEvent(Admins), this);
        getServer().getPluginManager().registerEvents(new AdminMessageEvent(Admins), this);
    }

    private void createDirectoryIfNotExist() {
        for (int i = 0; i < Admins.size(); i++) {
            String directoryPath = "Administrator_Logs/data/" + Bukkit.getOfflinePlayer(Admins.get(i)).getName();
            // Check if the directory exists, create it if it doesn't
            Path directory = Paths.get(directoryPath);

            if (!Files.exists(directory)) {
                try {
                    Files.createDirectories(directory);
                    System.out.println("Directory created successfully!");
                } catch (IOException e) {
                    System.err.println("Failed to create directory: " + e.getMessage());

                }
            }


            String[] subDirectories = {"Join_Quit", "Commands", "Messages"};
            for (String subDir : subDirectories) {
                Path subDirectory = directory.resolve(subDir);
                if (!Files.exists(subDirectory)) {
                    try {
                        Files.createDirectories(subDirectory);
                        System.out.println("Subdirectory " + subDir + " created successfully!");
                    } catch (IOException e) {
                        System.err.println("Failed to create subdirectory " + subDir + ": " + e.getMessage());
                    }
                }
            }
        }
    }

    private void ActivateMessage() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "----------------------------------");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "ReLifeAdministrators Activated");
        Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "----------------------------------");
        Bukkit.getConsoleSender().sendMessage("   ....             .....            ....             .....            .....            .....       \n" +
                " ..... . . .  . . .  . .   .  . .  ..... . .  . . .  . ..   .  .  . . ....   .  .  . . ..... . . .  \n" +
                "....         .           .   .   .....       .         .  .     .  . ...  .    .        .          .\n" +
                " .   .  .  .    .  .  .    .      ...  .  .     .  . .      . .   .....     .    . .  .  . .  .  .  \n" +
                "   .         ....    .  .     . . .      .  .  . .      . .        ..   .  .  .                     \n" +
                "     . . . ..... .        .  .       . .     ......  .       .  .    .   .      .... . . .  . . . . \n" +
                " . .      .....    .  .    ..   .  .      . .....      .  .    .  .   .     . ......       .        \n" +
                "      . .....   .   .   . ....       .  .  ..... . .. .    ....     .   .  . .....    .  .   .. .  .\n" +
                "  .   .....   .   .    ......  . .  .    ..... %S. ;8: . .....  .  .     . ....    .       . ...    \n" +
                "    .....   .        . ....        .   ..... :88 %:8@XS .....    .    .   ....  .   .  . ....... .  \n" +
                "  .  ... .    . .  . ....    . .     ...... t;%8.;:;;@ Xt...  .     .  ......    .    .   ....     .\n" +
                "    .      .     . ....  .  .    .    .. . tXS88.% @. @t :     .  .    ....   .    .   ....    .    \n" +
                "  .   .  .   .   .....         .   .     . S88 XX@@X XX;88 . .      .   .      .    . ..... .    .  \n" +
                "    .      .   .  ..  . . .  .   .   .  .. X@% @888@8.8t8@ .    . .   .    . .   .  .....     .   . \n" +
                "..     . .      .   .      .   ...       ..8S;XS;S@t88% 8t .  .  .  .    .        . ...    .    .   \n" +
                "   .  .     . .   .    .     ..... . . .. . @8@ X8@t;S8@.;:     ....   .    .  .        .    .   ...\n" +
                "         . ...       .   . ......      . 8X8 .@S8X%@X@:88@8:. .....  .    .     .  .  .   .    . ...\n" +
                " .  .  . ....  .  .    .  . ...   .  .. %88888@ 88StStX8888 :....       .   ....        .   . ..... \n" +
                "   .   .....     .  .   .....          @8888@888888tX@8888888 :   . .  .    .... .  .     .  ....   \n" +
                "     .....   .  .     ......   .  . ..S:8@@88888@SX.8888888X8t .         ......    .  .    ..... .  \n" +
                " . .....   .      .  ....    .   . . 8%8@8888888. .;X@888@88888.. .  . . ....   .      . .....     .\n" +
                "  .....  .   . .    .  . .     .    :;8X888888X; . .. @8@88888X; .    .....   .   .  .  ....    .   \n" +
                " ....            .         .     ..;S8@8888888@..   .X.888888888; . ......         .    .. .  .   . \n" +
                "  .  .  . . .  ..  .  .  .   .  . % 888888888 .  .. ..t 888888888: .....   . .  .     .         .   \n" +
                "      .       ...:.    .   .   . .X@88888X8t: ..       .@@X8888888S ..         .  ..     .  . .    .\n" +
                "  . .    .  .....@; .        . . X88888t@t@;;8S; .. :t@8:;8. X8888S8 .  . .  .  ..@8.. .         .  \n" +
                "       .  .....%:XS;;.. . . ....88888 8:.8;SSSX..    .X88.%@ ;.X8888S .       ..8888@@. ...  .  .   \n" +
                "  .  .   .  . 8X888@;8 .   ..: %X88X8  %tSS:t8 @tStSt8 @8:8.%X StX88@X .  . ..  888888S .     ..   .\n" +
                "    .  ..tX:X 888888@@@S..... .888@X;SS% 8t;@ S.88t88:8 S%:8X%:88 8@8.X.   . @ 88888888X :t ......  \n" +
                "  .   .....@ S 8888888888@...t8888% 8; St.: 88; X@8X8 ;@@ ::t; @t 8888%8.. % 8@888888t:@SS .....    \n" +
                "    . ...   .:88@S@8888X@8; t@88X8 .;8  .:: 88;  @8%@ tX8 :.. :X%   88X;:.@8@888888.X%X: .....   .  \n" +
                "  .   .  .  . 8@  S8X8@888X S8888:. @%% t8. X %%; S.ttX 8 :XSX8.@. S:88X@S888888@8:.S8S .....  .   .\n" +
                "    .  .     . . . % 8888;S@888; .  S S:;@8@ : 88: ;8%  :8@8XX..@ ..% 888 S88888%; @.......      .  \n" +
                "..        .     . ;@%@S88.X88X8:;  :t%:.. %t.@%t%@X@@ 8X:8S8.%@8t . 8t @@@@@X8X::S ......   . .     \n" +
                ".. .  . .   .     .:S t S.88888X: .%%8X::;%8%888@888@8@@S%.;;St.; X888: 8888 8:SS: . ...  .     .  .\n" +
                ".    .    .  . .   .  St;@88S 8888S;t S%:X:St8S%tSt@.8.S8%@t8X%.8X88888%%@888@t .            .    ..\n" +
                "  .     .  ....  .    :tX8SS88@@88@@;8t: t8  8% @S%.@SS. Xt  8@:8@@8888@; 8@X@ .  .    . . .   . ...\n" +
                "    .  .  ....     ..; @88@888%@888888@S %S .S   XS ..@. .:;.S @@8888@  X.:@88: ..  .          .....\n" +
                "  .     ....  . . . S 8@8S::; .888888888X8.: 8@  XS . Xt ..t.8@888888t 8%@% 8@88 .    .  .  ......  \n" +
                "    . ..... .     .  8@t8 : SX@@S 88888888  :  . ;8  . . : 8X88888@:X%t@:. %t@@X. . .       ....    \n" +
                "  .  ....      . ..888@:t... .:t .8888888@8 @..      . ;;t8@888888X:8S8.. .:8;:88t .  . . .....  .  \n" +
                "   .... .  . .  .;S88  ..    . :;8 %;@8@88888.;..  ..%@@8888888@8.;t%.. ... . tS88: .    ....     . \n" +
                "  ....   .     . 8@8; .  .     .. 8S :88888888X . : X888888888@ .%;.. .....   . XX8S .   . .  . .   \n" +
                "  . .  .    . . t@ @. .    .    . .S.::%@88888@8X;8S888888888:% 8@.  ....    . .::X@8 .            .\n" +
                "          .  . X:X..   .  .  .    . .;@; @%88888888888888% 8@8: .   . .   .      ..@;. . .  .  .    \n" +
                " . .  .     ...:   .        .      .  :S 8888888@888888@;%t@ .       .    .  .  .:..    .   .  .  \n" +
                " .   .   ...... .     .  . .. . .   ...X@:8 X8@@8888; @8X%:..   . .    .      .....  .    .     . \n" +
                " . .   .. ...     . .   . ....    .   . @;  t8888888S % .   ...     .    . . ....  .   .     ...  \n" +
                " .    .....   .  .    . ....    .   .   . %X8%%.SS8SStX ...... .  .   .    ....  .       .  ..... \n" +
                " :  ...... .        .  ....  .    .   .....t;; :8.8.;.. .....   .       . .... .    . .   .....   \n" +
                " ..  ...     . .  . .....  .   .     . ... . SX @:8; . ...   .     . . .....      .     .....  .  \n" +
                " .       .      .  ....      .   .  . ..    .. X; . ..... .    .  .    ....  .  .    . ....  .   .\n" +
                " .  .    . .   .....  .  .       .    . .    ..  .....   .        .   .     .    .  .... .      \n" +
                " ..  .   .      .  ..  .       . .     .           ....   .   . . .    .    .    .   ....      . . \n" +
                " :    .   .  .          . .  . .... .    .  . .  .     .         . .     .   .    . ...  .  .     \n" +
                " ...        .    .  . .      . ....     .           .  .   . .  .... .  .   .   .           .   . .\n" +
                " :.  .  . .....   .     .  . ....   .  .   . ... .       .    .....       .    .  .  .  . .   .  ..\n" +
                " ::   .  ..... .     .     .....  .      .  ....   . .     . ..... .  . .    ....     .        ....\n" +
                " ..    .....    .  .  . .....      . .   .....  .     . .  ....     .     . ....  .    .  . ..... \n" +
                "   .....    .    .   .....   .  .     .....       .     ....   .      . .....     .      .....  .");
    }
}
