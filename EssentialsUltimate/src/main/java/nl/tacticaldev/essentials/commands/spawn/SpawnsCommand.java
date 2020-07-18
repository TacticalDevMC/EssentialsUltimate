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

import java.util.ArrayList;
import java.util.List;

public class SpawnsCommand extends CoreCommand {

    public SpawnsCommand(String name, String permission, String info, String usage, boolean sub, boolean allowConsole) {
        super(name, permission, info, usage, sub, allowConsole);
    }

    @Override
    public void init() {

    }

    @Override
    public void execute() throws CoreException {
        Spawns spawns = Essentials.getInstance().getSpawns();
        final List<String> spawnNameList = getAvailableSpawns();

        if (spawnNameList.isEmpty()) {
            EssentialsMessages.SPAWNS_SPAWN_NOT_FOUND.send(getPlayer());
            return;
        }

        if (spawns.getCount() == 0) {
            EssentialsMessages.SPAWNS_SPAWN_NOT_FOUND.send(getPlayer());
            return;
        }

        EssentialsMessages.SPAWNS_LIST_FORMAT.send(getPlayer(), spawns.getCount(), getAvailableSpawns().toString().replace("[", "").replace("]", ""));
    }

    private List<String> getAvailableSpawns() {
        IEssentials ess = Essentials.getInstance();

        return new ArrayList<>(ess.getSpawns().getList());
    }
}
