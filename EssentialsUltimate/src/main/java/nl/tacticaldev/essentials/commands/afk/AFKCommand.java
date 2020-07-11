package nl.tacticaldev.essentials.commands.afk;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import essentialsapi.utils.exception.CoreException;
import nl.tacticaldev.essentials.commands.CoreCommand;
import nl.tacticaldev.essentials.listeners.custom.AFKChangeEvent;
import nl.tacticaldev.essentials.player.EssentialsPlayer;
import org.bukkit.Bukkit;

public class AFKCommand extends CoreCommand {

    public AFKCommand(String name, String permission, String info, String usage, boolean sub, boolean allowConsole) {
        super(name, permission, info, usage, sub, allowConsole);
    }

    @Override
    public void init() {

    }

    @Override
    public void execute() throws CoreException {
        EssentialsPlayer base = new EssentialsPlayer(getPlayer());

        AFKChangeEvent afkChangeEvent = new AFKChangeEvent(getPlayer());

        Bukkit.getPluginManager().callEvent(afkChangeEvent);

        if (base.isAfk()) {
            base.updateAfk(true);
            getPlayer().sendMessage("U bent nu afk");
        } else if(!base.isAfk()){
            base.updateAfk(false);
            getPlayer().sendMessage("U bent nu niet meer afk");
        }

    }
}
