package nl.tacticaldev.essentials.commands.spawn;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import essentialsapi.utils.exception.CoreException;
import essentialsapi.utils.logger.Logger;
import nl.tacticaldev.essentials.Essentials;
import nl.tacticaldev.essentials.commands.CoreCommand;
import nl.tacticaldev.essentials.managers.messages.enums.EssentialsMessages;
import nl.tacticaldev.essentials.managers.spawn.Spawns;

public class DeleteSpawnCommand extends CoreCommand {

    public DeleteSpawnCommand(String name, String permission, String info, String usage, boolean sub, boolean allowConsole) {
        super(name, permission, info, usage, sub, allowConsole);
    }

    @Override
    public void init() {

    }

    @Override
    public void execute() throws CoreException {
        if (getArgs().length == 0) {
            user.sendMessage(EssentialsMessages.DELETESPAWN_ARGS);
            return;
        }

        Spawns spawns = ess.getSpawns();

        if (spawns.getSpawn(getArgs()[0]) == null) {
            user.sendMessage(EssentialsMessages.DELETESPAWN_SPAWN_NOT_FOUND, getArgs()[0]);
            return;
        }

        spawns.removeSpawn(getArgs()[0]);
        user.sendMessage(EssentialsMessages.DELETESPAWN_SPAWN_DELETED, getArgs()[0]);
    }
}
