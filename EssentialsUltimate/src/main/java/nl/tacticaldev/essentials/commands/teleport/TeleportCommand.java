package nl.tacticaldev.essentials.commands.teleport;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import essentialsapi.utils.Cooldown;
import essentialsapi.utils.exception.CoreException;
import nl.tacticaldev.essentials.Essentials;
import nl.tacticaldev.essentials.commands.CoreCommand;
import nl.tacticaldev.essentials.database.tables.PlayerTable;
import nl.tacticaldev.essentials.interfaces.IEssentials;
import nl.tacticaldev.essentials.managers.messages.enums.EssentialsMessages;
import nl.tacticaldev.essentials.player.EssentialsOfflinePlayer;
import nl.tacticaldev.essentials.player.EssentialsPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TeleportCommand extends CoreCommand {

    public TeleportCommand(String name, String permission, String info, String usage, boolean sub, boolean allowConsole) {
        super(name, permission, info, usage, sub, allowConsole);
    }

    @Override
    public void init() {

    }

    @Override
    public void execute() throws CoreException {
        IEssentials ess = Essentials.getInstance();

        if (getArgs().length == 0) {
            EssentialsMessages.TELEPORT_ARGS.send(getPlayer());
            return;
        }

        Player target = Bukkit.getPlayer(getArgs()[0]);

        if (target == null) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(getArgs()[0]);

            EssentialsOfflinePlayer essentialsOfflinePlayer = new EssentialsOfflinePlayer(offlinePlayer);
            EssentialsPlayer player = new EssentialsPlayer(getPlayer());

            if (!new PlayerTable().existsPlayer(offlinePlayer.getUniqueId())) {
                EssentialsMessages.PLAYER_NOT_FOUND_IN_DATABASE.send(player.getBase(), getArgs()[0]);
                return;
            }

            World world = Bukkit.getWorld(essentialsOfflinePlayer.getLastLocationWorld());

            Location loc = new Location(world, essentialsOfflinePlayer.getLastLocation("x"), essentialsOfflinePlayer.getLastLocation("y"), essentialsOfflinePlayer.getLastLocation("z"));

            player.teleport(loc);
            EssentialsMessages.TELEPORT_TELEPORTED_TO_OFFLINE.send(player.getBase(), offlinePlayer.getName());
            return;
        }

        EssentialsPlayer player = new EssentialsPlayer(getPlayer());

        player.teleport(target);
        EssentialsMessages.TELEPORT_TELEPORTED_TO_ONLINE.send(player.getBase(), target.getName());
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> suggestions = new ArrayList<>();
            for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
                if (player.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
                    suggestions.add(player.getName());
                }
            }
            return suggestions;
        }
        return new ArrayList<>();
    }
}
