package net.legendahlupa.com.relifeadminpassword.db;

import org.bukkit.configuration.file.FileConfiguration;

import java.sql.*;
import java.util.ArrayList;

public class DataBase {


    public String host, database, username, password, tableprefix;
    public int port;
    private Connection connection;

    private final FileConfiguration configuration;


    public DataBase(FileConfiguration configuration) {
        this.configuration = configuration;
    }


    public Connection getConnection() throws SQLException {
        if (connection != null) {
            return connection;
        }

        host = configuration.getString("MySQL.host");
        port = configuration.getInt("MySQL.port");
        database = configuration.getString("MySQL.database");
        username = configuration.getString("MySQL.username");
        password = configuration.getString("MySQL.password");
        tableprefix = configuration.getString("MySQL.table-prefix");


        String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true";
        Connection connection = DriverManager.getConnection(url, username, password);

        this.connection = connection;

        return connection;


    }

    public void initializeDatabase() throws SQLException {

        Statement statement = getConnection().createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS " + this.tableprefix + "admins (uuid VARCHAR(255), admin VARCHAR(255), admingroup VARCHAR(255), adminpassword VARCHAR(255))";

        statement.execute(sql);

        statement.close();
    }


    public void addNewAdminToDB(String nickNewAdmin, String uuid, String group, String password) throws SQLException {
        PreparedStatement preparedStatement = getConnection()
                .prepareStatement("INSERT INTO " + this.tableprefix + "admins(uuid ,admin, admingroup, adminpassword)  VALUES (?, ?, ?, ?)");
        preparedStatement.setString(1, uuid);
        preparedStatement.setString(2, nickNewAdmin);
        preparedStatement.setString(3, group);
        preparedStatement.setString(4, password);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public boolean AdminExistsInDB(String AdminUUID, String group) throws SQLException {
        PreparedStatement prepareStatement = getConnection()
                .prepareStatement("SELECT * FROM " + tableprefix + "admins WHERE uuid=? AND admingroup=?");
        prepareStatement.setString(1, AdminUUID);
        prepareStatement.setString(2, group);
        ResultSet resultSet = prepareStatement.executeQuery();
        if (resultSet.next()) {
            prepareStatement.close();
            return true;
        }
        prepareStatement.close();
        return false;
    }

    public boolean RemoveFromDB(String AdminUUID) throws SQLException {
        PreparedStatement prepareStatement = getConnection()
                .prepareStatement("SELECT * FROM " + tableprefix + "admins WHERE uuid=?");
        prepareStatement.setString(1, AdminUUID);
        ResultSet resultSet = prepareStatement.executeQuery();
        if (resultSet.next()) {
            prepareStatement.close();
            return true;
        }
        prepareStatement.close();
        return false;
    }

    public ArrayList<String> getAdminGroup(String AdminUUID, String AdminPassword) throws SQLException {
        if (AdminPassword.equals("null")){
            PreparedStatement prepareStatement = getConnection()
                    .prepareStatement("SELECT * FROM " + tableprefix + "admins WHERE uuid=?");
            prepareStatement.setString(1, AdminUUID);
            ResultSet resultSet = prepareStatement.executeQuery();
            resultSet.next();
            String result = resultSet.getString("admingroup");
            ArrayList<String> resultlist = new ArrayList<>();
            while (resultSet.next()){
                resultlist.add(resultSet.getString("admingroup"));
            }
            prepareStatement.close();
            return resultlist;
        } else {
            PreparedStatement prepareStatement = getConnection()
                    .prepareStatement("SELECT * FROM " + tableprefix + "admins WHERE uuid=? AND adminpassword=?");
            prepareStatement.setString(1, AdminUUID);
            prepareStatement.setString(2, AdminPassword);
            ResultSet resultSet = prepareStatement.executeQuery();
            resultSet.next();
            String result = resultSet.getString("admingroup");
            ArrayList<String> ar = new ArrayList<>();
            ar.add(result);
            prepareStatement.close();
            return ar;
        }
    }


    public String getAdminPassword(String AdminUUID, String password) throws SQLException {
        PreparedStatement prepareStatement = getConnection()
                .prepareStatement("SELECT adminpassword FROM " + tableprefix + "admins WHERE uuid=?");
        prepareStatement.setString(1, AdminUUID);
        ResultSet resultSet = prepareStatement.executeQuery();
        ArrayList<String> resultlist = new ArrayList<>();
        while (resultSet.next()){
            resultlist.add(resultSet.getString("adminpassword"));
        }
        prepareStatement.close();
        if (resultlist.contains(password)){
            return password;
        }
        return null;
    }


}
