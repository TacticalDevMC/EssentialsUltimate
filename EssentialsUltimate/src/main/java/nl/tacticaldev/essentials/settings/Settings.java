package nl.tacticaldev.essentials.settings;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import nl.tacticaldev.essentials.interfaces.IEssentials;
import nl.tacticaldev.essentials.settings.interfaces.ISettings;
import essentialsapi.config.EssentialsConfig;

import java.util.List;

import static essentialsapi.utils.Utils.replaceColor;

public class Settings implements ISettings {

    private final IEssentials ess;
    private static EssentialsConfig config;

    public Settings(IEssentials ess) {
        this.ess = ess;
        config = new EssentialsConfig("config");

        reloadConfig();
    }

    @Override
    public EssentialsConfig getConfig() {
        return config;
    }

    @Override
    public void reloadConfig() {
        config.reload();
    }

    @Override
    public String getName() {
        return config.getFile().getName();
    }

    @Override
    public boolean isDebug() {
        return config.getBoolean("debug", false);
    }

    @Override
    public String getLanguage() {
        return config.getString("language", "nl");
    }

    @Override
    public void setLanguage(String language) {
        config.set("language", language.toUpperCase());
    }

    @Override
    public String getPrefix() {
        return replaceColor(config.getString("prefix", "&6&lESSENTIALS &8● "));
    }

    @Override
    public String getNoPermissions() {
        return replaceColor(config.getString("noPerms", "&cU hebt hier geen permissions voor! U hebt de permission &4%permission% &cnodig!"));
    }

    @Override
    public boolean isCustomJoinMessage() {
        return config.getBoolean("onJoin.message.isCustom", false);
    }

    @Override
    public String getCustomJoinMessage() {
        return config.getString("onJoin.message.text");
    }

    @Override
    public String getCurrencySymbol() {
        return config.getString("currency-symbol", "$").concat("$").substring(0, 1).replaceAll("[0-9]", "$");
    }

    @Override
    public boolean isCurrencySymbolSuffixed() {
        return config.getBoolean("currency-symbol-suffix", false);
    }

    @Override
    public boolean isCommandCooldown(String command) {
        return config.getBoolean("cooldowns." + command + ".enabled", false);
    }

    @Override
    public Integer getCommandCooldownDelay(String command) {
        return config.getInt("cooldowns." + command + ".delay", 5);
    }

    @Override
    public String getAfkListName() {
        return config.getString("afk-list-name", "none");
    }

    @Override
    public Integer getAutoAfkKick() {
        return config.getInt("auto-afk-kick", -1);
    }

    @Override
    public Integer getAutoAfk() {
        return config.getInt("auto-afk", 300);
    }

    @Override
    public boolean isToSpawnOnJoin() {
        return config.getBoolean("onJoin.toSpawn", true);
    }

    @Override
    public String getSpawnOnJoin() {
        return config.getString("onJoin.spawnOnJoin", "none");
    }

    @Override
    public String getNewbiesSpawn() {
        return config.getString("newbies.spawn", "none");
    }

    @Override
    public String getNewbiesMessage() {
        return config.getString("newbies.message", "&6{PLAYER} &5Is nieuw op de server!");
    }

    @Override
    public String getDefaultSpawn() {
        return config.getString("default-spawn", "none");
    }

    @Override
    public String getDefaultAppealMessage() {
        return config.getString("default-appeal-message", "none");
    }

    @Override
    public String getBannedKickMessage() {
        return config.getString("disconnection.you-are-banned", "&fU bent verbannen!\nReden: &a'{reason}'&f\nDoor &a{banner}&f.\n{appeal-message}");
    }

    @Override
    public Integer getHistoryExpireyMinutes() {
        return config.getInt("history-expirey.minutes", 10080);
    }

    @Override
    public String getDefaultReason() {
        return config.getString("default-reason", "Misconduct");
    }

    @Override
    public String getPlayerBannedAnnouncement() {
        return config.getString("announcement.player-was-banned", "&a{banner}&f banned &a{name}&f voor &a'{reason}'&f.");
    }

    @Override
    public String getTempBanKickMessage() {
        return config.getString("disconnection.you-are-temp-banned", "&fU bent voor een tijdje verbannen!\nReden: &a'{reason}'&f\nDoor &a{banner}&f.\nEindigd in: {time}.\n{appeal-message}");
    }

    @Override
    public Integer getMaxTempbanTime() {
        return config.getInt("MaxTempbanTime", 604800);
    }

    @Override
    public String getPlayerTempBannedAnnouncement() {
        return config.getConfiguration().getString("announcement.player-was-tempbanned", "&a{banner}&f tempbanned &a{name}&f voor {time} voor &a'{reason}'&f");
    }

    @Override
    public String getIPBanKickMessage() {
        return config.getString("disconnection.you-are-ipbanned", "&fU bent IP banned!\nReden: &a'{reason}'&f\nDoor &a{banner}&f.\n{appeal-message}");
    }

    @Override
    public String getTempIPBanKickMessage() {
        return config.getString("you-are-temp-ipbanned", "&fU bent tijdelijk IP Banned!\nReden: &a'{reason}'&f\nDoor &a{banner}&f.\nEindigd in: {time}.\n{appeal-message}");
    }

    @Override
    public String getPlayerIpBannedAnnouncement() {
        return config.getString("announcement.player-was-ip-banned", "&a{banner} &fIP banned &a{name} &f('&a{ip}&f') voor &a'{reason}'&f.");
    }

    @Override
    public boolean isUsingEssentialsSigns() {
        return config.getBoolean("using-essentials-signs", true);
    }

    @Override
    public List<String> getEnabledSigns() {
        return config.getStringList("enabledSigns");
    }

    @Override
    public boolean isUsingCooldownsOnSign() {
        return config.getBoolean("use-cooldowns-on-signs", false);
    }

    @Override
    public Integer getCooldownOnSign() {
        return config.getInt("cooldown-on-sign", 3);
    }

    @Override
    public boolean getPerWarpPermission() {
        return config.getBoolean("per-warp-permission", false);
    }

    @Override
    public Integer getHealFeedCooldown() {
        return config.getInt("heal-cooldown", 60);
    }

    @Override
    public boolean isWorldChangeSpeedReset() {
        return config.getBoolean("world-change-speed-reset", true);
    }
}
