package nl.tacticaldev.essentials.managers.messages;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import essentialsapi.config.EssentialsConfig;
import nl.tacticaldev.essentials.interfaces.IConf;
import nl.tacticaldev.essentials.interfaces.IEssentials;
import nl.tacticaldev.essentials.settings.interfaces.ISettings;

public class MessageManager implements IConf {

    private static IEssentials ess;
    private EssentialsConfig configNL, configEN;

    public MessageManager(IEssentials ess) {
        MessageManager.ess = ess;
        configNL = new EssentialsConfig("messages/messages_nl");
        configEN = new EssentialsConfig("messages/messages_en");

        reloadConfig();
    }

    public EssentialsConfig getConfig(String language) {
        switch (language) {
            case "nl":
                return configNL;
            case "en":
                return configEN;
            default:
                break;
        }
        return null;
    }

    @Override
    public void reloadConfig() {
        configNL.reload();
        configEN.reload();
    }

    public String get(String path) {
        if (ess.getSettings().getLanguage().equals("NL")) {
            return configNL.getConfiguration().getString(path);
        } else if (ess.getSettings().getLanguage().equals("EN")) {
            return configEN.getConfiguration().getString(path);
        }
        return "No language found!";
    }

    @Override
    public String getName() {
        return "MessagesNl: " + configNL.getFile().getName() + ", MessagesEN: " + configEN.getFile().getName();
    }

}
