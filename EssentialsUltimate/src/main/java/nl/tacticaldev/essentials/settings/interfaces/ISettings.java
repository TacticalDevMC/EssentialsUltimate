package nl.tacticaldev.essentials.settings.interfaces;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import essentialsapi.config.EssentialsConfig;
import nl.tacticaldev.essentials.interfaces.IConf;

import java.util.List;

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

    boolean isToSpawnOnJoin();

    String getSpawnOnJoin();

    String getNewbiesSpawn();

    String getNewbiesMessage();

    String getDefaultSpawn();

    String getDefaultAppealMessage();

    String getBannedKickMessage();

    Integer getHistoryExpireyMinutes();

    String getDefaultReason();

    String getPlayerBannedAnnouncement();

    String getTempBanKickMessage();

    Integer getMaxTempbanTime();

    String getPlayerTempBannedAnnouncement();

    boolean isUsingEssentialsSigns();

    List<String> getEnabledSigns();

    boolean isUsingCooldownsOnSign();

    Integer getCooldownOnSign();

}
