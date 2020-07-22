package nl.tacticaldev.essentials.listeners.bukkit.players;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import nl.tacticaldev.essentials.Essentials;
import nl.tacticaldev.essentials.managers.punishments.ban.Ban;
import nl.tacticaldev.essentials.player.EssentialsPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static essentialsapi.utils.Utils.replaceColor;

public class PlayerConnectBanListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        EssentialsPlayer base = new EssentialsPlayer(player);

        final Ban ban = Essentials.getInstance().getBanManager().getBan(base.getBase().getName());

        if (ban != null && base.isBanned()) {
            Essentials.getInstance().getBanManager().kick(base.getBase().getName(), replaceColor(ban.getKickMessage()));
        }
    }
}
