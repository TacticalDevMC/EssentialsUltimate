package nl.tacticaldev.essentials.listeners.bukkit.players;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import essentialsapi.utils.Utils;
import nl.tacticaldev.essentials.Essentials;
import nl.tacticaldev.essentials.settings.interfaces.ISettings;
import nl.tacticaldev.essentials.listeners.custom.DatabaseEvent;
import nl.tacticaldev.essentials.managers.spawn.Spawns;
import nl.tacticaldev.essentials.managers.spawn.exception.SpawnNotFoundException;
import nl.tacticaldev.essentials.player.EssentialsPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Objects;

public class PlayerConnectionListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws SpawnNotFoundException {
        Player player = event.getPlayer();
        EssentialsPlayer base = new EssentialsPlayer(player);

        ISettings settings = Essentials.getInstance().getSettings();
        Spawns spawns = Essentials.getInstance().getSpawns();

        // CREATE DATABASE PLAYER
        DatabaseEvent databaseEvent = new DatabaseEvent(player);

        Bukkit.getPluginManager().callEvent(databaseEvent);

        // JOIN MESSAGE
        if (settings.isCustomJoinMessage()) {
            event.setJoinMessage(Utils.replaceColor(settings.getCustomJoinMessage()).replace("{PLAYER}", player.getName()));
        }

        if (settings.isToSpawnOnJoin()) {
            if (settings.getSpawnOnJoin().equals("none")) {
                Location worldSpawnpoint = Objects.requireNonNull(Bukkit.getWorld(player.getWorld().getName())).getSpawnLocation();

                base.teleport(worldSpawnpoint);
            } else if (!(settings.getSpawnOnJoin().equals("none"))) {
                String spawnName = settings.getSpawnOnJoin();

                if (spawns.getSpawn(spawnName) == null) {
                    throw new SpawnNotFoundException("The spawn '" + spawnName + "' can't be found!");
                }

                Location loc = spawns.getSpawnLocation(spawnName);

                base.teleport(loc);
            }
        }

        if (base.isExistsOnDatabase()) {
            if (!base.hasPlayedBefore()) {
                if (settings.getNewbiesSpawn().equals("none")) {
                    Location worldSpawnpoint = Objects.requireNonNull(Bukkit.getWorld(player.getWorld().getName())).getSpawnLocation();

                    base.teleport(worldSpawnpoint);
                } else if (!(settings.getNewbiesSpawn().equals("none"))) {
                    // TODO: Get the setted spawn from the config.yml, end get the spawn name from the spawns.yml file
                    String spawnName = settings.getNewbiesSpawn();

                    if (spawns.getSpawn(spawnName) == null) {
                        throw new SpawnNotFoundException("The spawn '" + spawnName + "' can't be found!");
                    }

                    Location loc = spawns.getSpawnLocation(spawnName);

                    base.teleport(loc);
                }

                Bukkit.broadcastMessage(Utils.replaceColor(settings.getNewbiesMessage().replace("{USERNAME}", base.getBase().getDisplayName()).replace("{PLAYER}", base.getBase().getName())));
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        EssentialsPlayer player = new EssentialsPlayer(event.getPlayer());
        Player base = player.getBase();

        player.updateLastLocation(base.getWorld(), base.getLocation().getBlockX(), base.getLocation().getBlockY(), base.getLocation().getBlockZ(), base.getLocation().getYaw(), base.getLocation().getPitch());
    }
}
