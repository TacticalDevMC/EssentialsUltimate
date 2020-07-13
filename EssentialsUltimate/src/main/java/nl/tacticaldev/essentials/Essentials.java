package nl.tacticaldev.essentials;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import lombok.AccessLevel;
import lombok.Getter;
import nl.tacticaldev.essentials.interfaces.IConf;
import nl.tacticaldev.essentials.interfaces.IDatabaseSettings;
import nl.tacticaldev.essentials.interfaces.IEssentials;
import nl.tacticaldev.essentials.interfaces.ISettings;
import nl.tacticaldev.essentials.managers.messages.MessageManager;
import nl.tacticaldev.essentials.managers.spawn.Spawns;
import nl.tacticaldev.essentials.perm.PermissionsHandler;
import nl.tacticaldev.essentials.perm.impl.IPermissionsHandler;
import nl.tacticaldev.essentials.settings.DatabaseSettings;
import nl.tacticaldev.essentials.settings.Settings;
import essentialsapi.EssentialsAPI;
import essentialsapi.interfaces.IAPI;
import essentialsapi.utils.logger.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class Essentials extends JavaPlugin implements IEssentials {

    @Getter(AccessLevel.PUBLIC)
    private static Essentials instance;

    private static ISettings settings;
    private static IDatabaseSettings databaseSettings;
    private static IPermissionsHandler permissionsHandler;
    private static Spawns spawns;

    private static MessageManager messageManager;
    private static Metrics metrics;

    private EssentialsAPI api;
    private List<IConf> confList = new ArrayList<IConf>();

    private long start, end;

    @Override
    public void onEnable() {
        instance = this;
        this.start = System.currentTimeMillis();

        api = new EssentialsAPI(this);
        Logger.INFO.log("Plugin starting! V-" + this.getDescription().getVersion() + " Author(s): " + this.getDescription().getAuthors().toString().replace("[", "").replace("]", ""));

        settings = new Settings(this);
        databaseSettings = new DatabaseSettings(this);
        confList.add(settings);
        confList.add(databaseSettings);

        if (!settings.isUseMysqlStorage()) {
            spawns = new Spawns(this);
            confList.add(spawns);
        }

        permissionsHandler = new PermissionsHandler(this);
        confList.add(permissionsHandler);

        messageManager = new MessageManager(this);
        confList.add(messageManager);

        Logger.INFO.log("----------------------");
        api.getDatabase().tryToStart();
        if (api.getDatabase().isLoaded()) {
            api.getDatabase().start();
            api.createTables();
            if (settings.isUseMysqlStorage()) {
                spawns = new Spawns(api.getDatabase());

                confList.add(spawns);
            }
            Logger.INFO.log("Database loaded!");
        }

        api.loadListeners(this);
        Logger.INFO.log("Listeners loaded!");

        api.loadCommands();
        Logger.INFO.log("Commands loaded!");
        Logger.INFO.log("----------------------");

        metrics = new Metrics(this, 7955);

        if (metrics.isEnabled()) {
            getLogger().info("Starting Metrics. Opt-out using the global bStats config.");
        } else {
            getLogger().info("Metrics disabled per bStats config.");
        }

        metrics.addCustomChart(new Metrics.SingleLineChart("players", new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                // (This is useless as there is already a player chart by default.)
                return Bukkit.getOnlinePlayers().size();
            }
        }));

        this.end = System.currentTimeMillis();
        Logger.INFO.log("Plugin started! Took(" + (start - end) + "ms) ♥");
    }

    @Override
    public void onDisable() {
        instance = null;
        this.start = System.currentTimeMillis();

        Logger.INFO.log("Plugin disabling! V-" + this.getDescription().getVersion() + " Author(s): " + this.getDescription().getAuthors().toString().replace("[", "").replace("]", ""));

        api.getDatabase().stop();

        this.end = System.currentTimeMillis();
        Logger.INFO.log("Plugin stopped! Took(" + (start - end) + "ms) ♥");
    }

    @Override
    public void reload() {
        for (IConf iConf : confList) {
            iConf.reloadConfig();
            Logger.CONFIG.log("Config " + iConf.getName() + " reloaded!");
        }

        api.loadListeners(this);
        api.loadCommands();
        api.getDatabase().restart();
    }

    @Override
    public ISettings getSettings() {
        return settings;
    }

    @Override
    public IDatabaseSettings getDBSettings() {
        return databaseSettings;
    }

    @Override
    public IAPI getAPI() {
        return api;
    }

    @Override
    public IPermissionsHandler getPermissionsHandler() {
        return permissionsHandler;
    }

    @Override
    public Spawns getSpawns() {
        return spawns;
    }

    @Override
    public MessageManager getMessageManager() {
        return messageManager;
    }

    @Override
    public Metrics getMetrics() {
        return metrics;
    }

    @Override
    public BukkitScheduler getScheduler() {
        return this.getServer().getScheduler();
    }

    @Override
    public BukkitTask runTaskAsynchronously(final Runnable run) {
        return this.getScheduler().runTaskAsynchronously(this, run);
    }

    @Override
    public BukkitTask runTaskLaterAsynchronously(final Runnable run, final long delay) {
        return this.getScheduler().runTaskLaterAsynchronously(this, run, delay);
    }

    @Override
    public BukkitTask runTaskTimerAsynchronously(final Runnable run, final long delay, final long period) {
        return this.getScheduler().runTaskTimerAsynchronously(this, run, delay, period);
    }

    @Override
    public int scheduleSyncDelayedTask(final Runnable run) {
        return this.getScheduler().scheduleSyncDelayedTask(this, run);
    }

    @Override
    public int scheduleSyncDelayedTask(final Runnable run, final long delay) {
        return this.getScheduler().scheduleSyncDelayedTask(this, run, delay);
    }

    @Override
    public int scheduleSyncRepeatingTask(final Runnable run, final long delay, final long period) {
        return this.getScheduler().scheduleSyncRepeatingTask(this, run, delay, period);
    }

    private int tps = 0;
    private long second = 0;

    private void initTps() {
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            long sec;
            int ticks;

            @Override
            public void run() {
                sec = (System.currentTimeMillis() / 1000);

                if (second == sec) {
                    ticks++;
                } else {
                    second = sec;
                    tps = (tps == 0 ? ticks : ((tps = ticks) / 2));
                    ticks = 0;
                }
            }
        }, 20, 1);
    }
}
