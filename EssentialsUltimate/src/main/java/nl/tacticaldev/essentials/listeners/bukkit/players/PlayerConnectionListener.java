package nl.tacticaldev.essentials.listeners.bukkit.players;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import essentialsapi.utils.Utils;
import nl.tacticaldev.essentials.Essentials;
import nl.tacticaldev.essentials.interfaces.ISettings;
import nl.tacticaldev.essentials.listeners.custom.DatabaseEvent;
import nl.tacticaldev.essentials.player.EssentialsPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnectionListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ISettings settings = Essentials.getInstance().getSettings();

        // CREATE DATABASE PLAYER
        DatabaseEvent databaseEvent = new DatabaseEvent(player);

        Bukkit.getPluginManager().callEvent(databaseEvent);

        // JOIN MESSAGE
        if (settings.isCustomJoinMessage()) {
            event.setJoinMessage(Utils.replaceColor(settings.getCustomJoinMessage()).replace("{PLAYER}", player.getName()));
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        EssentialsPlayer player = new EssentialsPlayer(event.getPlayer());
        Player base = player.getBase();

        player.updateLastLocation(base.getWorld(), base.getLocation().getBlockX(), base.getLocation().getBlockY(), base.getLocation().getBlockZ(), base.getLocation().getYaw(), base.getLocation().getPitch());
    }
}
