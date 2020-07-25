package nl.tacticaldev.essentials.settings;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import essentialsapi.config.EssentialsConfig;
import nl.tacticaldev.essentials.settings.interfaces.IDatabaseSettings;
import nl.tacticaldev.essentials.interfaces.IEssentials;
import org.bukkit.configuration.ConfigurationSection;

public class DatabaseSettings implements IDatabaseSettings {

    private final transient IEssentials ess;
    private final transient EssentialsConfig config;

    public DatabaseSettings(IEssentials ess) {
        this.ess = ess;
        config = ess.getSettings().getConfig();

        reloadConfig();
    }

    @Override
    public EssentialsConfig getConfig() {
        return config;
    }

    @Override
    public String getDBHost() {
        return config.getString("MySQL.host", "");
    }

    @Override
    public String getDBUser() {
        return config.getString("MySQL.user", "");
    }

    @Override
    public String getDBPassword() {
        return config.getString("MySQL.password", "");
    }

    @Override
    public String getDatabase() {
        return config.getString("MySQL.database", "");
    }

    @Override
    public Integer getDBPort() {
        return config.getInt("MySQL.port", 3306);
    }

    @Override
    public boolean isDBPoolSizeEnabled() {
        return config.getBoolean("MySQL.poolSize.enabled", false);
    }

    @Override
    public Integer getDBMaxPoolSize() {
        return config.getInt("MySQL.poolSize.max", 30);
    }

    @Override
    public Integer getDBMinPoolSize() {
        return config.getInt("MySQL.poolSize.min", 10);
    }

    @Override
    public boolean isReadOnly() {
        return config.getBoolean("MySQL.read-only", false);
    }

    @Override
    public void reloadConfig() {
        config.reload();
    }

    @Override
    public String getName() {
        return config.getFile().getName();
    }
}
