package nl.tacticaldev.essentials.database;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import com.zaxxer.hikari.HikariDataSource;
import essentialsapi.interfaces.IDatabase;
import essentialsapi.utils.logger.Logger;
import nl.tacticaldev.essentials.Essentials;
import nl.tacticaldev.essentials.database.sql.SQLManager;
import nl.tacticaldev.essentials.interfaces.IEssentials;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class EssentialsDatabase implements IDatabase {

    // jdbc:mysql://138.201.225.15:3306/s8_essentialsultimate?useSSL=false&serverTimezone=UTC

    private static EssentialsDatabase instance;
    private SQLManager sqlManager = new SQLManager(this);

    public static EssentialsDatabase getInstance() {
        if (instance == null) {
            instance = new EssentialsDatabase();
        }
        return instance;
    }

    private IEssentials ess = Essentials.getInstance();
    private HikariDataSource hikari;

    private String host, user, password, database;
    private int port, minPoolSize, maxPoolSize;
    private boolean poolEnabled;

    private boolean loaded = false;

    @Override
    public boolean isLoaded() {
        return loaded;
    }

    @Override
    public void tryToStart() {

        host = ess.getDBSettings().getDBHost();
        user = ess.getDBSettings().getDBUser();
        password = ess.getDBSettings().getDBPassword();
        database = ess.getDBSettings().getDatabase();
        port = ess.getDBSettings().getDBPort();

        if (host.equals("")) {
            Logger.WARNING.log("Host must be set.");
            loaded = false;
        } else if (user.equals("")) {
            Logger.WARNING.log("User must be set.");
            loaded = false;
        } else if (password.equals("")) {
            Logger.WARNING.log("Password must be set.");
            loaded = false;
        } else if (database.equals("")) {
            Logger.WARNING.log("Database must be set.");
            loaded = false;
            return;
        }

        maxPoolSize = ess.getDBSettings().getDBMaxPoolSize();
        minPoolSize = ess.getDBSettings().getDBMinPoolSize();
        poolEnabled = ess.getDBSettings().isDBPoolSizeEnabled();

        if (poolEnabled) {
            if (maxPoolSize <= minPoolSize) {
                Logger.MYSQL.log("Maximum pool size can not be lower then the minimum pool size. (Min= " + minPoolSize + ")");
                loaded = false;
            } else if (minPoolSize >= maxPoolSize) {
                Logger.MYSQL.log("Minimum pool size can not be higher then the maximum pool size. (Max= " + maxPoolSize + ")");
                loaded = false;
                return;
            }
        }

        loaded = true;
    }

    @Override
    public void start() {
        hikari = new HikariDataSource();

        hikari.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        hikari.addDataSourceProperty("serverName", host);
        hikari.addDataSourceProperty("user", user);
        hikari.addDataSourceProperty("password", password);
        hikari.addDataSourceProperty("databaseName", database);
        hikari.addDataSourceProperty("port", port);

        hikari.setPoolName("EssentialsUltimate Pool");
        if (poolEnabled) {
            hikari.setMaximumPoolSize(maxPoolSize);
        }
        Logger.WARNING.log("Database started!");
    }

    @Override
    public void stop() {
        if (hikari != null) {
            hikari.close();
            Logger.MYSQL.log("Hikari closed!");
        } else {
            Logger.MYSQL.log("Hikari was not opened!");
        }
    }

    @Override
    public void restart() {

    }

    @Override
    public HikariDataSource getHikari() {
        return hikari;
    }

    @Override
    public Connection getConnection() {
        try {
            return hikari.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getIPFromPlayer(UUID uuid) {
        String db = "No IP found for the uuid " + uuid.toString() + "(" + Bukkit.getOfflinePlayer(uuid).getName() + ")";

        try (Connection connection = getConnection()) {
            String query = "SELECT * FROM " + sqlManager.getTable("players") + " WHERE player_uuid=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, uuid.toString());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                db = resultSet.getString("player_ip");
                resultSet.close();
                return db;
            }
            connection.close();
            preparedStatement.close();
        } catch (SQLException e) {
            Logger.ERROR.log(e);
        }
        return db;
    }

    public String getIPFromPlayer(String name) {
        String db = "No IP found for the name " + name;

        try (Connection connection = getConnection()) {
            String query = "SELECT * FROM " + sqlManager.getTable("players") + " WHERE player_name=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                db = resultSet.getString("player_ip");
                resultSet.close();
                return db;
            }
            connection.close();
            preparedStatement.close();
        } catch (SQLException e) {
            Logger.ERROR.log(e);
        }
        return db;
    }

    private ArrayList<String> names = new ArrayList<>();

    public ArrayList<String> getAllPlayerNames() {
        try (Connection connection = hikari.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + sqlManager.getTable("players"));

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                names.add(resultSet.getString("player_name"));
            }

            resultSet.close();
            connection.close();
            preparedStatement.close();
        } catch (SQLException e) {
            Logger.ERROR.log(e);
        }
        return names;
    }

}
