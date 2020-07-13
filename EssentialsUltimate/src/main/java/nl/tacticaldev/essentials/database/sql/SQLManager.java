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
            case "spawns":
                return section.getString("spawns");
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

    public void createTableSpawns() {
        try (Connection connection = database.getConnection()) {
            String query = "CREATE TABLE IF NOT EXISTS " + getTable("spawns") + " " +
                    "(name varchar(255), " +
                    "World varchar(255), " +
                    "x int, " +
                    "y int, " +
                    "z int, " +
                    "yaw int, " +
                    "pitch int, " +
                    "PRIMARY KEY (name))";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.execute();
            connection.close();
            preparedStatement.close();
        } catch (SQLException e) {
            Logger.ERROR.log(e);
        }
    }

}
