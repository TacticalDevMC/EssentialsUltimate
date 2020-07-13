package nl.tacticaldev.essentials.interfaces;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import essentialsapi.interfaces.IAPI;
import nl.tacticaldev.essentials.Metrics;
import nl.tacticaldev.essentials.managers.messages.MessageManager;
import nl.tacticaldev.essentials.managers.spawn.Spawns;
import nl.tacticaldev.essentials.perm.impl.IPermissionsHandler;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public interface IEssentials extends Plugin {

    void reload();

    ISettings getSettings();

    IDatabaseSettings getDBSettings();

    IAPI getAPI();

    IPermissionsHandler getPermissionsHandler();

    Spawns getSpawns();

    MessageManager getMessageManager();

    Metrics getMetrics();

    BukkitScheduler getScheduler();

    BukkitTask runTaskAsynchronously(Runnable run);

    BukkitTask runTaskLaterAsynchronously(Runnable run, long delay);

    BukkitTask runTaskTimerAsynchronously(Runnable run, long delay, long period);

    int scheduleSyncDelayedTask(Runnable run);

    int scheduleSyncDelayedTask(Runnable run, long delay);

    int scheduleSyncRepeatingTask(Runnable run, long delay, long period);

}
