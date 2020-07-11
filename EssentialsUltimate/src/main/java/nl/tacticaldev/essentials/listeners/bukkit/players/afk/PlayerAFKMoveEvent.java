package nl.tacticaldev.essentials.listeners.bukkit.players.afk;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import nl.tacticaldev.essentials.Essentials;
import nl.tacticaldev.essentials.interfaces.ISettings;
import nl.tacticaldev.essentials.listeners.bukkit.players.afk.tasks.AfkTask;
import nl.tacticaldev.essentials.listeners.custom.AFKChangeEvent;
import nl.tacticaldev.essentials.player.EssentialsPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerAFKMoveEvent implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        EssentialsPlayer base = new EssentialsPlayer(player);
        ISettings settings = Essentials.getInstance().getSettings();

        if(settings.getAutoAfk() != -1) {
            if (base.isAfk()) {
                AFKChangeEvent afkChangeEvent = new AFKChangeEvent(player);
                AFKChangeEvent.setCause(AFKChangeEvent.Cause.MOVE);

                Bukkit.getPluginManager().callEvent(afkChangeEvent);
            } else {
                AfkTask afkTask = new AfkTask(player, event);

                afkTask.run();
            }
        }
    }
}
