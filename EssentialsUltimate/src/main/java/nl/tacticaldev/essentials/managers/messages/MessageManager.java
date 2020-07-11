package nl.tacticaldev.essentials.managers.messages;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import essentialsapi.config.EssentialsConfig;
import nl.tacticaldev.essentials.interfaces.IConf;
import nl.tacticaldev.essentials.interfaces.IEssentials;

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

    @Override
    public String getName() {
        return "MessagesNl: " + configNL.getFile().getName() + ", MessagesEN: " + configEN.getFile().getName();
    }

}
