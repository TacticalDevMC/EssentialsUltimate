package nl.tacticaldev.essentials.commands.spawn;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import essentialsapi.utils.exception.CoreException;
import nl.tacticaldev.essentials.Essentials;
import nl.tacticaldev.essentials.commands.CoreCommand;
import nl.tacticaldev.essentials.settings.interfaces.ISettings;
import nl.tacticaldev.essentials.managers.messages.enums.EssentialsMessages;
import nl.tacticaldev.essentials.managers.spawn.Spawns;
import nl.tacticaldev.essentials.player.EssentialsPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.Objects;

public class SpawnCommand extends CoreCommand {

    public SpawnCommand(String name, String permission, String info, String usage, boolean sub, boolean allowConsole) {
        super(name, permission, info, usage, sub, allowConsole);
    }

    @Override
    public void init() {

    }

    @Override
    public void execute() throws CoreException {
        EssentialsPlayer base = new EssentialsPlayer(getPlayer());
        Spawns spawns = ess.getSpawns();

        if (getArgs().length == 0) {
            if (settings.getDefaultSpawn().equals("none")) {
                Location worldSpawn = Objects.requireNonNull(Bukkit.getWorld(base.getBase().getWorld().getName())).getSpawnLocation();

                base.teleport(worldSpawn);
            } else if (!settings.getDefaultSpawn().equals("none")) {
                if (spawns.getSpawn(settings.getDefaultSpawn()) == null) {
                    user.sendMessage(EssentialsMessages.SPAWN_DEFAULT_SPAWN_CAN_NOT_BE_FOUND);
                    return;
                }

                user.spawn(getArgs()[0], getNewExceptionFuture(user.getBase(), getName()));
            }
            return;
        }

        if (spawns.getSpawn(getArgs()[0]) == null) {
            user.sendMessage(EssentialsMessages.SPAWN_SPAWN_NOT_EXISTS, getArgs()[0]);
            return;
        }

        user.spawn(getArgs()[0], getNewExceptionFuture(user.getBase(), getName()));
    }
}
