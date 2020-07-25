package nl.tacticaldev.essentials.player;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import essentialsapi.utils.logger.Logger;
import net.md_5.bungee.api.chat.BaseComponent;
import nl.tacticaldev.essentials.Essentials;
import nl.tacticaldev.essentials.database.sql.SQLManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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
        this.base = Bukkit.getOfflinePlayer(name);
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

    public void updateTotalBans(Integer bans) {
        try (Connection connection = Essentials.getInstance().getAPI().getDatabase().getConnection()) {
            String query = "UPDATE " + sqlManager.getTable("players") + " SET totalBans=? WHERE player_uuid=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, bans);
            preparedStatement.setString(2, base.getUniqueId().toString());

            preparedStatement.executeUpdate();

            connection.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            Logger.ERROR.log(ex);
        }
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

    public void updateTotalKicks(Integer kicks) {
        try (Connection connection = Essentials.getInstance().getAPI().getDatabase().getConnection()) {
            String query = "UPDATE " + sqlManager.getTable("players") + " SET totalKicks=? WHERE player_uuid=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, kicks);
            preparedStatement.setString(2, base.getUniqueId().toString());

            preparedStatement.executeUpdate();

            connection.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            Logger.ERROR.log(ex);
        }
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

    public void updateTotalMutes(Integer mutes) {
        try (Connection connection = Essentials.getInstance().getAPI().getDatabase().getConnection()) {
            String query = "UPDATE " + sqlManager.getTable("players") + " SET totalMutes=? WHERE player_uuid=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, mutes);
            preparedStatement.setString(2, base.getUniqueId().toString());

            preparedStatement.executeUpdate();

            connection.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            Logger.ERROR.log(ex);
        }
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

    public void updateTotalWarns(Integer warns) {
        try (Connection connection = Essentials.getInstance().getAPI().getDatabase().getConnection()) {
            String query = "UPDATE " + sqlManager.getTable("players") + " SET totalWarns=? WHERE player_uuid=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, warns);
            preparedStatement.setString(2, base.getUniqueId().toString());

            preparedStatement.executeUpdate();

            connection.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            Logger.ERROR.log(ex);
        }
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

    public Location getLastLocation() {
        try (Connection connection = Essentials.getInstance().getAPI().getDatabase().getConnection()) {
            String query = "SELECT * FROM " + sqlManager.getTable("players") + " WHERE player_uuid=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, getBase().getUniqueId().toString());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                return new Location(Bukkit.getWorld(getLastLocationWorld()), resultSet.getInt("lastLocationX"), resultSet.getInt("lastLocationY"), resultSet.getInt("lastLocationZ"));
            }

            resultSet.close();
            connection.close();
            preparedStatement.close();
        } catch (SQLException e) {
            Logger.ERROR.log(e);
        }
        return Bukkit.getWorld(getLastLocationWorld()).getSpawnLocation();
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

    public boolean isBanned() {
        try (Connection connection = Essentials.getInstance().getAPI().getDatabase().getConnection()) {
            String query = "SELECT * FROM " + sqlManager.getTable("players") + " WHERE player_uuid=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, getBase().getUniqueId().toString());

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
        return true;
    }

    public void updateBanned(boolean bool) {
        try (Connection connection = Essentials.getInstance().getAPI().getDatabase().getConnection()) {
            String query = "UPDATE " + sqlManager.getTable("players") + " SET banned=? WHERE player_uuid=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, String.valueOf(bool));
            preparedStatement.setString(2, getBase().getUniqueId().toString());

            preparedStatement.executeUpdate();
            connection.close();
            preparedStatement.close();
        } catch (SQLException e) {
            Logger.ERROR.log(e);
        }
    }

    public String getLastSeen() {
        try (Connection connection = Essentials.getInstance().getAPI().getDatabase().getConnection()) {
            String query = "SELECT * FROM " + sqlManager.getTable("players") + " WERE player_uuid=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, base.getUniqueId().toString());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                return resultSet.getString("lastSeen");
            }

            resultSet.close();
            connection.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            Logger.ERROR.log(ex);
        }
        return "Unknown";
    }

}
