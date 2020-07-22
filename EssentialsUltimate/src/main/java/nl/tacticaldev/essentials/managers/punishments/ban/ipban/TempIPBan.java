package nl.tacticaldev.essentials.managers.punishments.ban.ipban;

import essentialsapi.utils.essentialsutils.DateUtil;
import nl.tacticaldev.essentials.Essentials;
import nl.tacticaldev.essentials.interfaces.IEssentials;
import nl.tacticaldev.essentials.managers.punishments.BanManager;
import nl.tacticaldev.essentials.managers.punishments.punishment.Temporary;
import nl.tacticaldev.essentials.settings.interfaces.ISettings;

import static essentialsapi.utils.Utils.replaceColor;

public class TempIPBan extends IPBan implements Temporary {

    private long expires;

    public TempIPBan(final String ip, final String reason, final String banner, final long created, final long expires) {
        super(ip, reason, banner, created);
        this.expires = expires;
    }

    public long getExpires() {
        return this.expires;
    }

    public boolean hasExpired() {
        return System.currentTimeMillis() > this.expires;
    }

    @Override
    public String getKickMessage() {
        IEssentials ess = Essentials.getInstance();
        ISettings settings = ess.getSettings();
        BanManager banManager = ess.getBanManager();

        return replaceColor(settings.getTempIPBanKickMessage().replace("{reason}", this.getReason()).replace("{banner}", this.getBanner()).replace("{time}", DateUtil.getTimeUntil(this.expires)).replace("{appeal-message}", banManager.getAppealMessage()));
    }
}
