package nl.tacticaldev.essentials.geoip;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import essentialsapi.utils.logger.Logger;
import nl.tacticaldev.essentials.Metrics;
import nl.tacticaldev.essentials.interfaces.IEssentials;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class EssentialsGeoIP extends JavaPlugin {

    private transient Metrics metrics = null;

    @Override
    public void onEnable() {
        final PluginManager pm = getServer().getPluginManager();
        final IEssentials ess = (IEssentials) pm.getPlugin("EssentialsUltimate");
        if (!this.getDescription().getVersion().equals(ess.getDescription().getVersion())) {
            Logger.ERROR.log("Version mismatching! Plugin version: " + this.getDescription().getVersion() + " EssentialsUltimate version: " + ess.getDescription().getVersion());
            return;
        }
        if (!ess.isEnabled()) {
            this.setEnabled(false);
            return;
        }

        final EssentialsGeoIPPlayerListener playerListener = new EssentialsGeoIPPlayerListener(getDataFolder(), ess);
        pm.registerEvents(playerListener, this);

        Logger.INFO.log("This product includes GeoLite2 data created by MaxMind, available from http://www.maxmind.com/.");

        if (metrics == null) {
            metrics = new Metrics(this, 7955);
        }
    }
}
