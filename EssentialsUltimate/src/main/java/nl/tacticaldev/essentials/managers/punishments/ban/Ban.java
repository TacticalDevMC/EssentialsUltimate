package nl.tacticaldev.essentials.managers.punishments.ban;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import nl.tacticaldev.essentials.Essentials;
import nl.tacticaldev.essentials.managers.punishments.BanManager;
import nl.tacticaldev.essentials.managers.punishments.punishment.Punishment;
import nl.tacticaldev.essentials.settings.interfaces.ISettings;
import org.bukkit.ChatColor;

public class Ban extends Punishment {

    public Ban(final String user, final String reason, final String banner, final long created) {
        super(user, reason, banner, created);
    }

    public String getKickMessage() {
        ISettings settings = Essentials.getInstance().getSettings();
        BanManager banManager = Essentials.getInstance().getBanManager();

        return settings.getBannedKickMessage().replace("{reason}", this.reason).replace("{banner}", this.banner).replace("{appeal-message}", banManager.getAppealMessage());
    }
}
