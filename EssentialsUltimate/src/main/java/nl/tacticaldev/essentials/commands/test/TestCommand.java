package nl.tacticaldev.essentials.commands.test;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import essentialsapi.utils.exception.CoreException;
import nl.tacticaldev.essentials.Essentials;
import nl.tacticaldev.essentials.commands.CoreCommand;
import nl.tacticaldev.essentials.perm.impl.IPermissionsHandler;
import nl.tacticaldev.essentials.player.EssentialsPlayer;

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

        player.sendMessage("LastLocationWorld: " + player.getLastLocationWorld());
        player.sendMessage("LastLocationX: " + player.getLastLocation("x"));
        player.sendMessage("LastLocationY: " + player.getLastLocation("y"));
        player.sendMessage("LastLocationZ: " + player.getLastLocation("z"));
        player.sendMessage("LastLocationYaw: " + player.getLastLocation("yaw"));
        player.sendMessage("LastLocationPitch: " + player.getLastLocation("pitch"));
    }
}
