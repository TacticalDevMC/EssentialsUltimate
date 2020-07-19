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
        return config.getConfiguration().getBoolean("debug", false);
    }

    @Override
    public String getLanguage() {
        return config.getConfiguration().getString("language", "nl");
    }

    @Override
    public String getPrefix() {
        return replaceColor(config.getConfiguration().getString("prefix", "&6&lESSENTIALS &8● "));
    }

    @Override
    public String getNoPermissions() {
        return replaceColor(config.getConfiguration().getString("noPerms", "&cU hebt hier geen permissions voor! U hebt de permission &4%permission% &cnodig!"));
    }

    @Override
    public boolean isCustomJoinMessage() {
        return config.getConfiguration().getBoolean("onJoin.message.isCustom", false);
    }

    @Override
    public String getCustomJoinMessage() {
        return config.getConfiguration().getString("onJoin.message.text");
    }

    @Override
    public String getCurrencySymbol() {
        return config.getConfiguration().getString("currency-symbol", "$").concat("$").substring(0, 1).replaceAll("[0-9]", "$");
    }

    @Override
    public boolean isCurrencySymbolSuffixed() {
        return config.getConfiguration().getBoolean("currency-symbol-suffix", false);
    }

    @Override
    public boolean isCommandCooldown(String command) {
        return config.getConfiguration().getBoolean("cooldowns." + command + ".enabled", false);
    }

    @Override
    public Integer getCommandCooldownDelay(String command) {
        return config.getConfiguration().getInt("cooldowns." + command + ".delay", 5);
    }

    @Override
    public String getAfkListName() {
        return config.getConfiguration().getString("afk-list-name", "none");
    }

    @Override
    public Integer getAutoAfkKick() {
        return config.getConfiguration().getInt("auto-afk-kick", -1);
    }

    @Override
    public Integer getAutoAfk() {
        return config.getConfiguration().getInt("auto-afk", 300);
    }

    @Override
    public boolean isToSpawnOnJoin() {
        return config.getConfiguration().getBoolean("onJoin.toSpawn", true);
    }

    @Override
    public String getSpawnOnJoin() {
        return config.getConfiguration().getString("onJoin.spawnOnJoin", "none");
    }

    @Override
    public String getNewbiesSpawn() {
        return config.getConfiguration().getString("newBies.spawn", "none");
    }

    @Override
    public String getNewbiesMessage() {
        return config.getConfiguration().getString("newBies.message", "&6{PLAYER} &5Is nieuw op de server!");
    }

    @Override
    public String getDefaultSpawn() {
        return config.getConfiguration().getString("default-spawn", "none");
    }

    @Override
    public String getDefaultAppealMessage() {
        return config.getConfiguration().getString("default-appeal-message", "none");
    }

    @Override
    public String getBannedKickMessage() {
        return config.getConfiguration().getString("disconnection.you-are-banned", "&fU bent verbannen!\n Reason: &a'{reason}'&f\nDoor &a{banner}&f.\n{appeal-message}");
    }

    @Override
    public Integer getHistoryExpireyMinutes() {
        return config.getConfiguration().getInt("history-expirey.minutes", 10080);
    }

    @Override
    public String getDefaultReason() {
        return config.getConfiguration().getString("default-reason", "Misconduct");
    }

    @Override
    public String getPlayerBannedAnnouncement() {
        return config.getConfiguration().getString("announcement.player-was-banned", "&a{banner}&f banned &a{name}&f voor &a'{reason}'&f.");
    }

    @Override
    public String getTempBanKickMessage() {
        return config.getConfiguration().getString("disconnection.you-are-temp-banned", "&fU bent voor een tijdje verbannen!\nReason: &a'{reason}'&f\nDoor &a{banner}&f.\nEindigd in: {time}.\n{appeal-message}");
    }

    @Override
    public Integer getMaxTempbanTime() {
        return config.getConfiguration().getInt("MaxTempbanTime", 604800);
    }

    @Override
    public String getPlayerTempBannedAnnouncement() {
        return config.getConfiguration().getString("announcement.player-was-tempbanned", "&a{banner}&f tempbanned &a{name}&f voor {time} voor &a'{reason}'&f");
    }

    @Override
    public boolean isUsingEssentialsSigns() {
        return config.getConfiguration().getBoolean("using-essentials-signs", true);
    }

    @Override
    public List<String> getEnabledSigns() {
        return config.getConfiguration().getStringList("enabledSigns");
    }

    @Override
    public boolean isUsingCooldownsOnSign() {
        return config.getConfiguration().getBoolean("use-cooldowns-on-signs", false);
    }

    @Override
    public Integer getCooldownOnSign() {
        return config.getConfiguration().getInt("cooldown-on-sign", 3);
    }
}
