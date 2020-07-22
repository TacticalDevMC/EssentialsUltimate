package nl.tacticaldev.essentials.managers.punishments.ban.ipban;

import nl.tacticaldev.essentials.Essentials;
import nl.tacticaldev.essentials.interfaces.IEssentials;
import nl.tacticaldev.essentials.managers.punishments.BanManager;
import nl.tacticaldev.essentials.managers.punishments.ban.Ban;
import nl.tacticaldev.essentials.settings.interfaces.ISettings;

import static essentialsapi.utils.Utils.replaceColor;

public class IPBan extends Ban {

    public IPBan(final String ip, final String reason, final String banner, final long created) {
        super(ip, reason, banner, created);
    }

    @Override
    public String getKickMessage() {
        IEssentials ess = Essentials.getInstance();
        ISettings settings = ess.getSettings();
        BanManager banManager = ess.getBanManager();

        return replaceColor(settings.getIPBanKickMessage().replace("{reason}", this.getReason()).replace("{banner}", this.getBanner()).replace("{appeal-message}", banManager.getAppealMessage()));
    }
}
