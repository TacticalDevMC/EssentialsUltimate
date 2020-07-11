package nl.tacticaldev.essentials.settings;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import nl.tacticaldev.essentials.interfaces.IEssentials;
import nl.tacticaldev.essentials.interfaces.ISettings;
import essentialsapi.config.EssentialsConfig;

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
}
