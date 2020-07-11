package nl.tacticaldev.essentials.listeners.bukkit.players.afk.tasks;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import nl.tacticaldev.essentials.Essentials;
import nl.tacticaldev.essentials.interfaces.IEssentials;
import nl.tacticaldev.essentials.listeners.custom.AFKChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class AfkTask extends BukkitRunnable {

    public int times = 0;
    private Player player;
    private PlayerMoveEvent event;

    public AfkTask(Player player, PlayerMoveEvent event) {
        this.player = player;
        this.event = event;
    }

    IEssentials ess = Essentials.getInstance();

    @Override
    public void run() {
        times++;

        int x = player.getLocation().getBlockX();
        int y = player.getLocation().getBlockY();
        int z = player.getLocation().getBlockZ();

        AFKChangeEvent afkChangeEvent = new AFKChangeEvent(player);

        if (x == event.getTo().getBlockX()) {
            if (y == event.getTo().getBlockY()) {
                if (z == event.getTo().getBlockZ()) {
                    if (times == ess.getSettings().getAutoAfk()) {
                        Bukkit.getPluginManager().callEvent(afkChangeEvent);

                        for (int i = 0; i < 1; i++) {
                            times = 0;
                            player.sendMessage("U bent nu afk!");
                            cancel();
                        }
                    }
                } else {
                    times = 0;
                }
            } else {
                times = 0;
            }
        } else {
            times = 0;
        }
    }
}
