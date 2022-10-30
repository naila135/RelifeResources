package net.legendahlupa.com.relifedonv2;

import net.legendahlupa.com.relifedonv2.Event.EventJoin;
import net.legendahlupa.com.relifedonv2.licence.AdvancedLicense;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public final class RelifeDonV2 extends JavaPlugin {

    private Connection connection;
    public String host, database, username, password, tableprefix;
    public int port;
    public static RelifeDonV2 instance;
    @Override
    public void onEnable() {
        saveDefaultConfig();
        instance = this;
        try {
            mysqlSetup();
        } catch (Exception e){
            getLogger().warning("Проверьте правильность заполнения формы MySQL");
            getPluginLoader().disablePlugin(this);
        }
        Objects.requireNonNull(getCommand("dmoney")).setExecutor(new DmoneyCommand());
        getServer().getPluginManager().registerEvents(new EventJoin(), this);
         if(!new AdvancedLicense(getConfig().getString("License"), "http://65.109.21.161:92/verify.php", this).setConsoleLog(AdvancedLicense.LogType.NORMAL).register()) return;
    }


    public void mysqlSetup() {
        host = getConfig().getString("MySQL.host");
        port = getConfig().getInt("MySQL.port");
        database = getConfig().getString("MySQL.database");
        username = getConfig().getString("MySQL.username");
        password = getConfig().getString("MySQL.password");
        tableprefix = getConfig().getString("MySQL.tableprefix");

        try {

            synchronized (this) {
                if (getConnection() != null && !getConnection().isClosed()) {
                    return;
                }

                setConnection(DriverManager.getConnection("jdbc:mysql://" + this.host + ":"
                        + this.port + "/" + this.database, this.username, this.password));

                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "База данных MySQL успешно подключена");
                createTable();
                createtablelog();

            }
        } catch (SQLException e) {
            getLogger().warning("Ошибка подключения к MySQL");
        }
    }

    public void createTable() {
        PreparedStatement ps;
        try {
            ps = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS " + getConfig().getString("MySQL.tableprefix") + "money"
                    + "(NAME VARCHAR(100),UUID VARCHAR(100),DMONEY INT(100), PRIMARY KEY(NAME))");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createtablelog(){
        PreparedStatement ps1;
        try {
            ps1 = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS " + getConfig().getString("MySQL.tableprefix") + "logs"
                    + "(ADMIN VARCHAR(100),ACTION VARCHAR(100),AMOUNT INT(100),PLAYER VARCHAR(100), SERVER_LICENCE TEXT(100))");
            ps1.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Connection getConnection() {
        return connection;
    }
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    public static RelifeDonV2 getInstance(){return  instance;}
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
