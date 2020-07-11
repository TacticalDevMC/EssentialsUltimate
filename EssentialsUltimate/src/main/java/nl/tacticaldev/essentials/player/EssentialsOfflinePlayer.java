package nl.tacticaldev.essentials.player;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import essentialsapi.utils.logger.Logger;
import net.md_5.bungee.api.chat.BaseComponent;
import nl.tacticaldev.essentials.Essentials;
import nl.tacticaldev.essentials.database.sql.SQLManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class EssentialsOfflinePlayer {

    private OfflinePlayer base;
    private SQLManager sqlManager = new SQLManager(Essentials.getInstance().getAPI().getDatabase());

    public EssentialsOfflinePlayer(OfflinePlayer base) {
        this.base = base;
    }

    public EssentialsOfflinePlayer(String name) {
        this.base = Bukkit.getPlayer(name);
    }

    public OfflinePlayer getBase() {
        return base;
    }

    public UUID getUUID() {
        return base.getUniqueId();
    }

    public Integer getTotalBans() {
        try (Connection connection = Essentials.getInstance().getAPI().getDatabase().getConnection()) {
            String query = "SELECT * FROM " + sqlManager.getTable("players") + " WHERE player_uuid=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, getBase().getUniqueId().toString());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                return resultSet.getInt("totalBans");
            }

            resultSet.close();
            connection.close();
            preparedStatement.close();
        } catch (SQLException e) {
            Logger.ERROR.log(e);
        }
        return 0;
    }

    public Integer getTotalKicks() {
        try (Connection connection = Essentials.getInstance().getAPI().getDatabase().getConnection()) {
            String query = "SELECT * FROM " + sqlManager.getTable("players") + " WHERE player_uuid=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, getBase().getUniqueId().toString());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                return resultSet.getInt("totalKicks");
            }

            resultSet.close();
            connection.close();
            preparedStatement.close();
        } catch (SQLException e) {
            Logger.ERROR.log(e);
        }
        return 0;
    }

    public Integer getTotalMutes() {
        try (Connection connection = Essentials.getInstance().getAPI().getDatabase().getConnection()) {
            String query = "SELECT * FROM " + sqlManager.getTable("players") + " WHERE player_uuid=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, getBase().getUniqueId().toString());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                return resultSet.getInt("totalMutes");
            }

            resultSet.close();
            connection.close();
            preparedStatement.close();
        } catch (SQLException e) {
            Logger.ERROR.log(e);
        }
        return 0;
    }

    public Integer getTotalWarns() {
        try (Connection connection = Essentials.getInstance().getAPI().getDatabase().getConnection()) {
            String query = "SELECT * FROM " + sqlManager.getTable("players") + " WHERE player_uuid=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, getBase().getUniqueId().toString());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                return resultSet.getInt("totalWarns");
            }

            resultSet.close();
            connection.close();
            preparedStatement.close();
        } catch (SQLException e) {
            Logger.ERROR.log(e);
        }
        return 0;
    }

    public String getLastLocationWorld() {
        try (Connection connection = Essentials.getInstance().getAPI().getDatabase().getConnection()) {
            String query = "SELECT * FROM " + sqlManager.getTable("players") + " WHERE player_uuid=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, getBase().getUniqueId().toString());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                return resultSet.getString("lastLocationWorld");
            }

            resultSet.close();
            connection.close();
            preparedStatement.close();
        } catch (SQLException e) {
            Logger.ERROR.log(e);
        }
        return "Geen";
    }

    public Integer getLastLocation(String key) {
        try (Connection connection = Essentials.getInstance().getAPI().getDatabase().getConnection()) {
            String query = "SELECT * FROM " + sqlManager.getTable("players") + " WHERE player_uuid=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, getBase().getUniqueId().toString());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                switch (key) {
                    case "x":
                        return resultSet.getInt("lastLocationX");
                    case "y":
                        return resultSet.getInt("lastLocationY");
                    case "z":
                        return resultSet.getInt("lastLocationZ");
                    case "yaw":
                        return resultSet.getInt("lastLocationYaw");
                    case "pitch":
                        return resultSet.getInt("lastLocationPitch");
                    default:
                        break;
                }
            }

            resultSet.close();
            connection.close();
            preparedStatement.close();
        } catch (SQLException e) {
            Logger.ERROR.log(e);
        }
        return 0;
    }

    public void updateLastLocationWorld(World world) {
        try (Connection connection = Essentials.getInstance().getAPI().getDatabase().getConnection()) {
            String query = "UPDATE " + sqlManager.getTable("players") + " SET lastLocationWorld=? WHERE player_uuid=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, world.getName());
            preparedStatement.setString(2, getBase().getUniqueId().toString());

            preparedStatement.executeUpdate();

            connection.close();
            preparedStatement.close();
        } catch (SQLException e) {
            Logger.ERROR.log(e);
        }
    }

    public void updateLastLocation(World world, Integer x, Integer y, Integer z, Float yaw, Float pitch) {
        try (Connection connection = Essentials.getInstance().getAPI().getDatabase().getConnection()) {
            String query = "UPDATE " + sqlManager.getTable("players") + " SET lastLocationWorld=?, lastLocationX=?, lastLocationY=?, lastLocationZ=?, lastLocationYaw=?, lastLocationPitch=? WHERE player_uuid=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, world.getName());
            preparedStatement.setInt(2, x);
            preparedStatement.setInt(3, y);
            preparedStatement.setInt(4, z);
            preparedStatement.setFloat(5, yaw);
            preparedStatement.setFloat(6, pitch);
            preparedStatement.setString(7, getBase().getUniqueId().toString());

            preparedStatement.executeUpdate();

            connection.close();
            preparedStatement.close();
        } catch (SQLException e) {
            Logger.ERROR.log(e);
        }
    }

}
