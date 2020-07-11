package nl.tacticaldev.essentials.database.tables;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import essentialsapi.utils.logger.Logger;
import nl.tacticaldev.essentials.Essentials;
import nl.tacticaldev.essentials.database.EssentialsDatabase;
import nl.tacticaldev.essentials.database.sql.SQLManager;
import nl.tacticaldev.essentials.interfaces.IPlayerTable;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PlayerTable implements IPlayerTable {

    private SQLManager sqlManager = new SQLManager(EssentialsDatabase.getInstance());

    @Override
    public Connection getConnection() {
        return Essentials.getInstance().getAPI().getDatabase().getConnection();
    }

    @Override
    public void insertPlayer(Player player) {
        try (Connection connection = getConnection()) {
            String query = "INSERT INTO " + sqlManager.getTable("players") + " " +
                    "(player_uuid, " +
                    "player_name, " +
                    "player_ip, " +
                    "fly, " +
                    "vanish, " +
                    "banned, " +
                    "muted, " +
                    "money, " +
                    "lastLocationWorld, " +
                    "lastLocationX, " +
                    "lastLocationY, " +
                    "lastLocationZ, " +
                    "lastLocationYaw, " +
                    "lastLocationPitch, " +
                    "lastSeen, " +
                    "totalBans, " +
                    "totalKicks, " +
                    "totalMutes, " +
                    "totalWarns, " +
                    "afk)" +
                    " VALUES " +
                    "('" + player.getUniqueId().toString() + "', " +
                    "'" + player.getName() + "', " +
                    "'" + player.getAddress().getAddress().toString().replace("/", "") + "', " +
                    "'false', " +
                    "'false', " +
                    "'false', " +
                    "'false', " +
                    "'" + 0 + "', " +
                    "'Geen', " +
                    "'" + 0 + "', " +
                    "'" + 0 + "', " +
                    "'" + 0 + "', " +
                    "'" + 0 + "', " +
                    "'" + 0 + "', " +
                    "'Unknown', " +
                    "'" + 0 + "', " +
                    "'" + 0 + "', " +
                    "'" + 0 + "', " +
                    "'" + 0 + "', " +
                    "'false')";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.execute();
            connection.close();
            preparedStatement.close();
        } catch (SQLException e) {
            Logger.ERROR.log(e);
        }
    }

    @Override
    public void deletePlayer(UUID uuid) {
        try (Connection connection = getConnection()) {
            connection.prepareStatement("DELETE FROM " + sqlManager.getTable("players") + " WHERE player_uuid=" + uuid.toString()).close();
        } catch (SQLException e) {
            Logger.ERROR.log(e);
        }
    }

    @Override
    public boolean existsPlayer(UUID uuid) {
        try (Connection connection = getConnection()) {
            String query = "SELECT * FROM " + sqlManager.getTable("players") + " WHERE player_uuid=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, uuid.toString());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                return true;
            }
            resultSet.close();
            connection.close();
            preparedStatement.close();
        } catch (SQLException e) {
            Logger.ERROR.log(e);
        }
        return false;
    }
}
