package nl.tacticaldev.essentials.commands.test;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import essentialsapi.utils.exception.CoreException;
import nl.tacticaldev.essentials.Essentials;
import nl.tacticaldev.essentials.commands.CoreCommand;
import nl.tacticaldev.essentials.interfaces.IEssentials;
import nl.tacticaldev.essentials.perm.impl.IPermissionsHandler;
import nl.tacticaldev.essentials.player.EssentialsPlayer;
import org.bukkit.ChatColor;

public class TestCommand extends CoreCommand {

    public TestCommand(String name, String permission, String info, String usage, boolean sub, boolean allowConsole) {
        super(name, permission, info, usage, sub, allowConsole);
    }

    @Override
    public void init() {

    }

    @Override
    public void execute() throws CoreException {
        IPermissionsHandler permissionsHandler = Essentials.getInstance().getPermissionsHandler();

        EssentialsPlayer player = new EssentialsPlayer(getPlayer());
        IEssentials ess = Essentials.getInstance();

        switch (getArgs()[0]) {
            case "create":

                ess.getSpawns().addSpawn(getArgs()[1], player.getWorld(), player.getBlockX(), player.getBlockY(), player.getBlockZ(), player.getYaw(), player.getPitch());

                player.sendMessage("Spawn named " + getArgs()[1] + " created!");
                break;
            case "get":
                player.sendMessage(ess.getSpawns().getSpawnLocation(getArgs()[1]));
                break;
            default:
                break;
        }
    }
}
