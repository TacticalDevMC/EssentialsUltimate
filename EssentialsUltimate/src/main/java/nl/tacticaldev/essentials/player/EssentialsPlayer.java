package nl.tacticaldev.essentials.player;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import essentialsapi.utils.Utils;
import essentialsapi.utils.logger.Logger;
import net.md_5.bungee.api.chat.BaseComponent;
import nl.tacticaldev.essentials.Essentials;
import nl.tacticaldev.essentials.database.sql.SQLManager;
import nl.tacticaldev.essentials.database.tables.PlayerTable;
import nl.tacticaldev.essentials.listeners.custom.UserSpawnEvent;
import nl.tacticaldev.essentials.listeners.custom.UserWarpEvent;
import nl.tacticaldev.essentials.managers.messages.enums.EssentialsMessages;
import nl.tacticaldev.essentials.managers.spawn.exception.SpawnNotFoundException;
import nl.tacticaldev.essentials.managers.warp.exceptions.InvalidWorldException;
import nl.tacticaldev.essentials.managers.warp.exceptions.WarpNotFoundException;
import nl.tacticaldev.essentials.settings.interfaces.ISettings;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class EssentialsPlayer {

    private Player base;
    private InetAddress ip;
    private SQLManager sqlManager = new SQLManager(Essentials.getInstance().getAPI().getDatabase());

    public EssentialsPlayer(Player base) {
        this.base = base;
    }

    public EssentialsPlayer(String name) {
        this.base = Bukkit.getPlayer(name);
    }

    public EssentialsPlayer(InetAddress ip) {
        this.ip = ip;
        this.ip = base.getAddress().getAddress();
    }

    public String getIP() {
        try (Connection connection = Essentials.getInstance().getAPI().getDatabase().getConnection()) {
            String query = "SELECT * FROM " + sqlManager.getTable("players") + " WHERE player_uuid=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, getUUID().toString());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                return resultSet.getString("player_ip");
            }

            resultSet.close();
            connection.close();
            preparedStatement.close();
        } catch (SQLException e) {
            Logger.ERROR.log(e);
        }
        return "Geen ip gevonden!";
    }

    public Player getBase() {
        return base;
    }

    public UUID getUUID() {
        return base.getUniqueId();
    }

    public boolean isAuthorized(String permission) {
        return base.hasPermission(permission);
    }

    public String getPermission(String permission) {
        return permission;
    }

    public void sendMessage(String message) {
        base.sendMessage(message);
    }

    public void sendMessage(Object object) {
        base.sendMessage(String.valueOf(object));
    }

    public void sendMessage(String... message) {
        base.sendMessage(message);
    }

    public void sendMessage(BaseComponent message) {
        base.spigot().sendMessage(message);
    }

    public void sendMessage(BaseComponent... message) {
        base.spigot().sendMessage(message);
    }

    public void sendMessage(EssentialsMessages message) {
        message.send(base);
    }

    public void sendMessage(EssentialsMessages message, Object... object) {
        message.send(base, object);
    }

    public void teleport(Location location) {
        base.teleport(location);
    }

    public void teleport(Player player, Location location) {
        player.teleport(location);
    }

    public void teleportTo(Player player) {
        base.teleport(player.getLocation());
    }

    public void teleportWorld(World world) {
        base.teleport(world.getSpawnLocation());
    }

    public void teleportLocation(int x, int y, int z) {
        Location location = new Location(getLocation().getWorld(), x, y, z);

        teleport(location);
    }

    public void teleport(Entity entity) {
        base.teleport(entity);
    }

    public Inventory getInventory() {
        return base.getInventory();
    }

    public void spawn(String spawn, CompletableFuture<Boolean> future) {
        UserSpawnEvent event = new UserSpawnEvent(this, spawn);

        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        spawn = event.getSpawn();
        Location loc;
        try {
            loc = Essentials.getInstance().getSpawns().getSpawnLocation(spawn);
        } catch (SpawnNotFoundException e) {
            future.completeExceptionally(e);
            return;
        }

        if (spawn.equals(Essentials.getInstance().getSettings().getDefaultSpawn())) {
            sendMessage(EssentialsMessages.SPAWN_TELEPORTED_TO_DEFAULT);
        } else {
            sendMessage(EssentialsMessages.SPAWN_TELEPORTED, spawn);
        }

        teleport(loc);
    }

    public void warp(EssentialsPlayer otherUser, String warp, CompletableFuture<Boolean> future) {
        UserWarpEvent event = new UserWarpEvent(otherUser, warp);

        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        warp = event.getWarp();
        Location loc;
        try {
            loc = Essentials.getInstance().getWarps().getWarp(warp);
        } catch (WarpNotFoundException | InvalidWorldException e) {
            future.completeExceptionally(e);
            return;
        }

        EssentialsMessages.WARP_WARPING_TO.send(otherUser.getBase(), warp, loc.getWorld().getName(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
        if (!otherUser.equals(base)) {
            EssentialsMessages.WARP_WARPING_TO.send(getBase(), warp, loc.getWorld().getName(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
        }
        teleport(otherUser.getBase(), loc);
    }

    public boolean hasPlayedBefore() {
        return base.hasPlayedBefore();
    }

    public boolean isExistsOnDatabase() {
        PlayerTable playerTable = new PlayerTable();

        return playerTable.existsPlayer(base.getUniqueId());
    }

    public int getBlockX() {
        return getBase().getLocation().getBlockX();
    }

    public int getBlockY() {
        return getBase().getLocation().getBlockY();
    }

    public int getBlockZ() {
        return getBase().getLocation().getBlockZ();
    }

    public float getYaw() {
        return getBase().getLocation().getYaw();
    }

    public float getPitch() {
        return getBase().getLocation().getPitch();
    }

    public Location getLocation() {
        return getBase().getLocation();
    }

    public World getWorld() {
        return getBase().getWorld();
    }

    public void setAFKNickname() {
        ISettings settings = Essentials.getInstance().getSettings();

        this.base.setPlayerListName(settings.getAfkListName().replace("{USERNAME}", getBase().getName()).replace("{PLAYER}", getBase().getDisplayName()));
    }

    public void heal(int health) {
        base.setHealth(health);
    }

    public void feed(int feed) {
        base.setFoodLevel(feed);
    }

    public Double getHealth() {
        return base.getHealth();
    }

    public Integer getFood() {
        return base.getFoodLevel();
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

    public boolean isAfk() {
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
        return false;
    }

    public void updateAfk(boolean bool) {
        try (Connection connection = Essentials.getInstance().getAPI().getDatabase().getConnection()) {
            String query = "UPDATE " + sqlManager.getTable("players") + " SET afk=? WHERE player_uuid=?";
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

    public void updateLastSeen(String lastSeen) {
        try (Connection connection = Essentials.getInstance().getAPI().getDatabase().getConnection()) {
            String query = "UPDATE " + sqlManager.getTable("players") + " SET lastSeen=? WHERE player_uuid=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, lastSeen);
            preparedStatement.setString(2, base.getUniqueId().toString());

            preparedStatement.executeUpdate();

            connection.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            Logger.ERROR.log(ex);
        }
    }

}
