package nl.tacticaldev.essentials.interfaces;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import essentialsapi.config.EssentialsConfig;

public interface ISettings extends IConf {

    EssentialsConfig getConfig();

    void reloadConfig();

    boolean isDebug();

    String getLanguage();

    String getPrefix();

    String getNoPermissions();

    boolean isCustomJoinMessage();

    String getCustomJoinMessage();

    String getCurrencySymbol();

    boolean isCurrencySymbolSuffixed();

    boolean isCommandCooldown(String command);

    Integer getCommandCooldownDelay(String command);

    String getAfkListName();

    Integer getAutoAfkKick();

    Integer getAutoAfk();

}
