package net.legendahlupa.com.siege;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteStreams;
import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.UUID;

public class Settings {
    public static TownyAPI tAPI = TownyAPI.getInstance();
    public static ArrayList<Town> townyList = new ArrayList<Town>();
    public static ArrayList<UUID> quituuid = new ArrayList<UUID>();
    public static ArrayList<Player> deathPlayers = new ArrayList<Player>();




}
