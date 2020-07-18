package nl.tacticaldev.essentials.managers.punishments.ban.tempban;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import essentialsapi.utils.Utils;
import essentialsapi.utils.essentialsutils.DateUtil;
import nl.tacticaldev.essentials.Essentials;
import nl.tacticaldev.essentials.managers.punishments.ban.Ban;
import nl.tacticaldev.essentials.managers.punishments.punishment.Temporary;
import nl.tacticaldev.essentials.settings.interfaces.ISettings;

public class TempBan extends Ban implements Temporary {

    private long expires;

    public TempBan(final String user, final String reason, final String banner, final long created, final long expires) {
        super(user, reason, banner, created);
        this.expires = expires;
    }

    @Override
    public long getExpires() {
        return this.expires;
    }

    @Override
    public boolean hasExpired() {
        return System.currentTimeMillis() > this.expires;
    }

    @Override
    public String getKickMessage() {
        ISettings settings = Essentials.getInstance().getSettings();

        return settings.getTempBanKickMessage().replace("{reason}", this.getReason()).replace("{banner}", this.getBanner()).replace("{time}", DateUtil.getTimeUntil(this.expires).replace("{appeal-message}", Essentials.getInstance().getBanManager().getAppealMessage()));
    }
}
