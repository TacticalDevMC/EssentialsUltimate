package nl.tacticaldev.essentials.player;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import essentialsapi.utils.logger.Logger;
import net.md_5.bungee.api.chat.BaseComponent;
import nl.tacticaldev.essentials.Essentials;
import nl.tacticaldev.essentials.database.sql.SQLManager;
import nl.tacticaldev.essentials.database.tables.PlayerTable;
import nl.tacticaldev.essentials.settings.interfaces.ISettings;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class EssentialsPlayerIP {

    private Player base;
    private String ip;
    private SQLManager sqlManager = new SQLManager(Essentials.getInstance().getAPI().getDatabase());

    public EssentialsPlayerIP(Player base) {
        this.base = base;
    }

    public EssentialsPlayerIP(String ip) {
        this.ip = ip;
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
        if (base == null) return null;

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

    public void teleport(Location location) {
        base.teleport(location);
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

    public boolean isBanned(String ip) {
        try (Connection connection = Essentials.getInstance().getAPI().getDatabase().getConnection()) {
            String query = "SELECT * FROM " + sqlManager.getTable("players") + " WHERE player_ip=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, ip);

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

    public void updateBanned(String ip, boolean bool) {
        try (Connection connection = Essentials.getInstance().getAPI().getDatabase().getConnection()) {
            String query = "UPDATE " + sqlManager.getTable("players") + " SET banned=? WHERE player_ip=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, String.valueOf(bool));
            preparedStatement.setString(2, ip);

            preparedStatement.executeUpdate();
            connection.close();
            preparedStatement.close();
        } catch (SQLException e) {
            Logger.ERROR.log(e);
        }
    }

}
