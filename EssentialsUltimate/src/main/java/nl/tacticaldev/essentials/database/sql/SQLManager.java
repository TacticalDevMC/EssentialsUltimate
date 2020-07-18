package nl.tacticaldev.essentials.database.sql;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import essentialsapi.config.EssentialsConfig;
import essentialsapi.interfaces.IDatabase;
import essentialsapi.utils.logger.Logger;
import nl.tacticaldev.essentials.Essentials;
import org.bukkit.configuration.ConfigurationSection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLManager {

    private IDatabase database;

    private static EssentialsConfig config;
    private static ConfigurationSection section;

    public SQLManager(IDatabase database) {
        this.database = database;
        config = Essentials.getInstance().getSettings().getConfig();
        section = config.getConfiguration().getConfigurationSection("MySQL.tables");
    }

    public String getTable(String name) {
        switch (name) {
            case "players":
                return section.getString("players");
            case "bans":
                return section.getString("bans");
            case "history":
                return section.getString("history");
            default:
                break;
        }
        return null;
    }

    public void createTablePlayers() {
        try (Connection connection = database.getConnection()) {
            String query = "CREATE TABLE IF NOT EXISTS " + getTable("players") + " " +
                    "(player_uuid varchar(255), " +
                    "player_name varchar(255), " +
                    "player_ip varchar(255), " +
                    "fly varchar(255), " +
                    "vanish varchar(255), " +
                    "banned varchar(255), " +
                    "muted varchar(255), " +
                    "money int, " +
                    "lastLocationWorld varchar(255), " +
                    "lastLocationX int, " +
                    "lastLocationY int, " +
                    "lastLocationZ int, " +
                    "lastLocationYaw int, " +
                    "lastLocationPitch int, " +
                    "lastSeen varchar(255), " +
                    "totalBans int, " +
                    "totalKicks int, " +
                    "totalMutes int, " +
                    "totalWarns int, " +
                    "afk varchar(255), " +
                    "PRIMARY KEY (player_uuid))";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.execute();
            connection.close();
            preparedStatement.close();
        } catch (SQLException e) {
            Logger.ERROR.log(e);
        }
    }

    public void createTableBans() {
        try (Connection connection = database.getConnection()) {
            String query = "CREATE TABLE IF NOT EXISTS " + getTable("bans") + " " +
                    "(name TEXT(30) NOT NULL, " +
                    "reason TEXT(100), " +
                    "banner TEXT(30), " +
                    "time BIGINT NOT NULL DEFAULT 0, " +
                    "expires BIGINT NOT NULL DEFAULT 0)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.execute();
            connection.close();
            preparedStatement.close();
        } catch (SQLException e) {
            Logger.ERROR.log(e);
        }
    }

    public void createTableHistory() {
        try (Connection connection = database.getConnection()) {
            String query = "CREATE TABLE IF NOT EXISTS " + getTable("history") + " " +
                    "(created BIGINT NOT NULL, " +
                    "message TEXT(100), " +
                    "banner TEXT(30), " +
                    "name TEXT(30))";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.execute();
            connection.close();
            preparedStatement.close();
        } catch (SQLException e) {
            Logger.ERROR.log(e);
        }
    }


}
