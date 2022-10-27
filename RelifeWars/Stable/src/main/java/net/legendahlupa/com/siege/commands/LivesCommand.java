package net.legendahlupa.com.siege.commands;

import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import net.legendahlupa.com.siege.Settings;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class LivesCommand extends Settings implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        String town = args[0];
        Player player = (Player) sender;
        if (player.hasPermission("siege.alive")){
            if (args.length != 1){
                sender.sendMessage(ChatColor.RED + "Неверный параметр");
                return true;
            }
            if (!townyList.toString().contains(town)){
                sender.sendMessage(ChatColor.RED + "Данный город не воюет");
                return true;
            }
            ArrayList<Resident> ar = new ArrayList<Resident>(tAPI.getTown(town).getResidents());
            ArrayList<String > arlive = new ArrayList<>();
            if (ar.isEmpty()){
                return true;
            }
            if (deathPlayers.isEmpty()){
                return true;
            }

            for (int i = 0; i < ar.size() ; i++) {
                for (int j = 0; j < deathPlayers.size(); j++) {
                    if (!ar.get(i).getName().equals(deathPlayers.get(j).getName())){
                        arlive.add(ar.get(i).getName());
                    }
                }
            }
            sender.sendMessage(arlive.toString().replace("[", "").replace("]", ""));
        } else {
            sender.sendMessage(ChatColor.RED + "У вас недостаточно прав");
        }
        return true;
    }


}
