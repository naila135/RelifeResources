package net.legendahlupa.com.permtrading;

import net.legendahlupa.com.permtrading.Guis.SelectPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TradingCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        SelectPlayer selectPlayer = new SelectPlayer();
        selectPlayer.createGui(player);

        return false;
    }
}
