package nl.tacticaldev.essentials;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import essentialsapi.utils.essentialsutils.VersionUtil;
import lombok.AccessLevel;
import lombok.Getter;
import nl.tacticaldev.essentials.interfaces.IConf;
import nl.tacticaldev.essentials.managers.powertool.PowerTool;
import nl.tacticaldev.essentials.managers.powertool.interfaces.IPowerTool;
import nl.tacticaldev.essentials.managers.punishments.BanManager;
import nl.tacticaldev.essentials.managers.warp.Warps;
import nl.tacticaldev.essentials.managers.warp.interfaces.IWarps;
import nl.tacticaldev.essentials.metrics.MetricsWrapper;
import nl.tacticaldev.essentials.settings.interfaces.IDatabaseSettings;
import nl.tacticaldev.essentials.interfaces.IEssentials;
import nl.tacticaldev.essentials.settings.interfaces.ISettings;
import nl.tacticaldev.essentials.managers.messages.MessageManager;
import nl.tacticaldev.essentials.managers.spawn.Spawns;
import nl.tacticaldev.essentials.perm.impl.IPermissionsHandler;
import nl.tacticaldev.essentials.settings.DatabaseSettings;
import nl.tacticaldev.essentials.settings.Settings;
import essentialsapi.EssentialsAPI;
import essentialsapi.interfaces.IAPI;
import essentialsapi.utils.logger.Logger;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class Essentials extends JavaPlugin implements IEssentials {

    @Getter(AccessLevel.PUBLIC)
    private static Essentials instance;

    private static ISettings settings;
    private static IDatabaseSettings databaseSettings;
    private static IPermissionsHandler permissionsHandler;
    private static Spawns spawns;
    private static IWarps warps;
    private static IPowerTool powerTool;

    private static MessageManager messageManager;
    private static BanManager banManager;

    private static MetricsWrapper metrics;
    private EssentialsAPI api;
    private List<IConf> confList = new ArrayList<IConf>();

    private long start, end;

    @Override
    public void onEnable() {
        instance = this;
        this.start = System.currentTimeMillis();

        if (!VersionUtil.isServerSupported()) {
            getLogger().severe("You are running an unsupported server version!");
        }

        try {
            api = new EssentialsAPI(this);
            Logger.INFO.log("Plugin starting! V-" + this.getDescription().getVersion() + " Author(s): " + this.getDescription().getAuthors().toString().replace("[", "").replace("]", ""));

            settings = new Settings(this);
            databaseSettings = new DatabaseSettings(this);
            confList.add(settings);

//        permissionsHandler = new PermissionsHandler(this);
//        confList.add(permissionsHandler);

            messageManager = new MessageManager(this);
            confList.add(messageManager);

            spawns = new Spawns(this);
            confList.add(spawns);

//            warps = new Warps(getServer(), this.getDataFolder());
//            confList.add(warps);

            powerTool = new PowerTool(this);
            confList.add(powerTool);

            Logger.INFO.log("----------------------");
            api.getDatabase().tryToStart();
            if (api.getDatabase().isLoaded()) {
                api.getDatabase().start();
                api.createTables();
                Logger.INFO.log("Database loaded!");
            }

            banManager = new BanManager(this);

            api.loadListeners(this);
            Logger.INFO.log("Listeners loaded!");

            api.loadCommands();
            Logger.INFO.log("Commands loaded!");
            Logger.INFO.log("----------------------");

            metrics = new MetricsWrapper(this, 7955, true);

            this.end = System.currentTimeMillis();
            Logger.INFO.log("Plugin started! Took(" + (start - end) + "ms) ♥");
        } catch (Exception ex) {
            try {
                throw new PluginLoadErrorException("Plugin cannot be loading, there was found an error.");
            } catch (PluginLoadErrorException e) {
                Logger.ERROR.log(e);
                e.printStackTrace();
            }
        }
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
    public IWarps getWarps() {
        return warps;
    }

    @Override
    public IPowerTool getPowerTool() {
        return powerTool;
    }

    @Override
    public MessageManager getMessageManager() {
        return messageManager;
    }

    @Override
    public BanManager getBanManager() {
        return banManager;
    }

    @Override
    public MetricsWrapper getMetrics() {
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
