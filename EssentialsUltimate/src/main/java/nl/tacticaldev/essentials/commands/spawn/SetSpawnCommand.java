package nl.tacticaldev.essentials.commands.spawn;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import essentialsapi.utils.exception.CoreException;
import nl.tacticaldev.essentials.Essentials;
import nl.tacticaldev.essentials.commands.CoreCommand;
import nl.tacticaldev.essentials.interfaces.IEssentials;
import nl.tacticaldev.essentials.managers.messages.enums.EssentialsMessages;
import nl.tacticaldev.essentials.managers.spawn.Spawns;
import nl.tacticaldev.essentials.player.EssentialsPlayer;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class SetSpawnCommand extends CoreCommand {

    public SetSpawnCommand(String name, String permission, String info, String usage, boolean sub, boolean allowConsole) {
        super(name, permission, info, usage, sub, allowConsole);
    }

    @Override
    public void init() {

    }

    @Override
    public void execute() throws CoreException {
        if (getArgs().length == 0) {
            user.sendMessage(EssentialsMessages.SETSPAWN_ARGS);
            return;
        }

        EssentialsPlayer base = new EssentialsPlayer(getPlayer());
        Spawns spawns = ess.getSpawns();

        String name = getArgs()[0];

        if (spawns.getSpawn(name) != null) {
            user.sendMessage(EssentialsMessages.SETSPAWN_SPAWN_ALREADY_EXISTS, name);
            return;
        }

        World world = base.getWorld();
        int x = base.getBlockX();
        int y = base.getBlockY();
        int z = base.getBlockZ();
        float yaw = base.getYaw();
        float pitch = base.getPitch();

        spawns.addSpawn(name, world, x, y, z, yaw, pitch);
        user.sendMessage(EssentialsMessages.SETSPAWN_SPAWNSET, name);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return getSuggestions(args[0], "<name>");
        }
        return new ArrayList<>();
    }
}
