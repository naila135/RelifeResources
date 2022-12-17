package net.legendahlupa.com.relifeadminpassword;

import net.legendahlupa.com.relifeadminpassword.commands.Alogin;
import net.legendahlupa.com.relifeadminpassword.commands.AGenerate;
import net.legendahlupa.com.relifeadminpassword.db.DataBase;
import net.legendahlupa.com.relifeadminpassword.discord.DiscordBot;
import net.legendahlupa.com.relifeadminpassword.events.Join;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class RelifeAdminPassword extends JavaPlugin {

    @Override
    public void onEnable() {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            DiscordBot discordBot = new DiscordBot(getConfig());
            try {
                discordBot.create();
            } catch (Exception e) {
                return;
            }
            LuckPerms api = provider.getProvider();
            saveDefaultConfig();
            DataBase dataBase = new DataBase(getConfig());
            try {
                dataBase.initializeDatabase();
                getCommand("alogin").setExecutor(new Alogin(getConfig(), api));
                getCommand("agenerate").setExecutor(new AGenerate(getConfig()));
                getServer().getPluginManager().registerEvents(new Join(getConfig(),api), this);
            } catch (SQLException e) {
                getPluginLoader().disablePlugin(this);
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
