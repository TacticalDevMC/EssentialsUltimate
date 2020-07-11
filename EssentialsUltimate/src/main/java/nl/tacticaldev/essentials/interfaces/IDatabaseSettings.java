package nl.tacticaldev.essentials.interfaces;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import essentialsapi.config.EssentialsConfig;

public interface IDatabaseSettings extends IConf {

    EssentialsConfig getConfig();

    void reloadConfig();

    String getDBHost();

    String getDBUser();

    String getDBPassword();

    String getDatabase();

    Integer getDBPort();

    boolean isDBPoolSizeEnabled();

    Integer getDBMaxPoolSize();

    Integer getDBMinPoolSize();

    String getTable(String table);

}
