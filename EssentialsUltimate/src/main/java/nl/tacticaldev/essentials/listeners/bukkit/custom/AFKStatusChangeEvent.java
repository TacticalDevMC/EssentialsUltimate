package nl.tacticaldev.essentials.listeners.bukkit.custom;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import nl.tacticaldev.essentials.Essentials;
import nl.tacticaldev.essentials.interfaces.ISettings;
import nl.tacticaldev.essentials.listeners.custom.AFKChangeEvent;
import nl.tacticaldev.essentials.player.EssentialsPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AFKStatusChangeEvent implements Listener {


    @EventHandler
    public void onAFKChange(AFKChangeEvent event) {
        EssentialsPlayer essentialsPlayer = event.getEssentialsPlayer();
        ISettings settings = Essentials.getInstance().getSettings();

        if (essentialsPlayer.isAfk()) {
            essentialsPlayer.getBase().setPlayerListName(essentialsPlayer.getBase().getName());
        } else if (essentialsPlayer.isAfk()) {
            if (!settings.getAfkListName().equals("none")) {
                essentialsPlayer.setAFKNickname();
            }

            if (essentialsPlayer.isAuthorized("essentials.afk.auto")) {
                if (settings.getAutoAfkKick() != -1) {
                    Essentials.getInstance().runTaskLaterAsynchronously(new Runnable() {
                        @Override
                        public void run() {
                            essentialsPlayer.getBase().kickPlayer("You where been AFK kicked!");
                        }
                    }, settings.getAutoAfkKick());
                }
            }
        }
    }
}
