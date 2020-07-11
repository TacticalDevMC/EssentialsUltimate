package nl.tacticaldev.essentials.managers.messages.enums;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import essentialsapi.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.tacticaldev.essentials.Essentials;
import nl.tacticaldev.essentials.interfaces.IEssentials;
import nl.tacticaldev.essentials.interfaces.ISettings;
import nl.tacticaldev.essentials.managers.messages.MessageManager;
import org.bukkit.command.CommandSender;

import static essentialsapi.utils.Utils.replaceColor;

@AllArgsConstructor
@Getter
public enum EssentialsMessages {

    // GENERAL
    PLAYER_NOT_FOUND_IN_DATABASE("commands.general.playerNotFoundInDatabase"),
    IN_COOLDOWN("commands.general.inCooldown"),

    // TELEPORT
    TELEPORT_ARGS("commands.teleport.tp.args"),
    TELEPORT_TELEPORTED_TO_OFFLINE("commands.teleport.tp.teleportedToOffline"),
    TELEPORT_TELEPORTED_TO_ONLINE("commands.teleport.tp.teleportedToOnline"),
    TELEPORT_OFFLINE_ARGS("commands.teleport.tpoffline.args"),
    TELEPORT_OFFLINE_PLAYER_IS_ONLINE("commands.teleport.tpoffline.playerIsOnline"),
    TELEPORT_OFFLINE_TELEPORTED("commands.teleport.tpoffline.teleportedToOffline");

    private final String path;

    @Override
    public String toString() {
        IEssentials ess = Essentials.getInstance();
        ISettings settings = ess.getSettings();

        MessageManager messageManager = Essentials.getInstance().getMessageManager();

        if (settings.getLanguage().equals("NL")) {
            return replaceColor(messageManager.getConfig("nl").getConfiguration().getString(path)).replace("%prefix%", settings.getPrefix());
        } else if (settings.getLanguage().equals("EN")) {
            return replaceColor(messageManager.getConfig("en").getConfiguration().getString(path)).replace("%prefix%", settings.getPrefix());
        }
        return "No Language found!";
    }

    public void send(CommandSender sender) {
        sender.sendMessage(toString());
    }

    public void send(CommandSender sender, String replacement) {
        sender.sendMessage(toString().replace("%s", replaceColor(replacement)).replace("%prefix%", Essentials.getInstance().getSettings().getPrefix()));
    }

//    public void send(CommandSender sender, String replacePath, String replaceTo) {
//        sender.sendMessage(toString().replace(replacePath, Utils.replaceColor(replaceTo)).replace("%prefix%", Essentials.getInstance().getSettings().getPrefix()));
//    }

    public void send(CommandSender sender, Object... object) {
        sender.sendMessage(String.format(toString(), object));
    }

}
