package nl.tacticaldev.essentials.commands.teleport;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

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

//        if (getArgs()[0].contains("X:") && getArgs()[1].contains("Y:") && getArgs()[2].contains("Z:")) {
//            String[] xSplit = getArgs()[0].split("X:");
//            String[] ySplit = getArgs()[1].split("Y:");
//            String[] zSplit = getArgs()[2].split("Z:");
//
//            int x = Integer.parseInt(xSplit[0]);
//            int y = Integer.parseInt(ySplit[0]);
//            int z = Integer.parseInt(zSplit[0]);
//
//            EssentialsPlayer base = new EssentialsPlayer(getPlayer());
//
//            base.teleportLocation(x, y, z);
//            base.sendMessage("You have been teleported to the location: X: " + x + " Y: " + y + " Z: " + z);
//            return;
//        }

        Player target = Bukkit.getPlayer(getArgs()[0]);

        if (target == null) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(getArgs()[0]);

            EssentialsOfflinePlayer essentialsOfflinePlayer = new EssentialsOfflinePlayer(offlinePlayer);

            if (!new PlayerTable().existsPlayer(offlinePlayer.getUniqueId())) {
                user.sendMessage(EssentialsMessages.PLAYER_NOT_FOUND_IN_DATABASE, getArgs()[0]);
                return;
            }

            user.teleport(essentialsOfflinePlayer.getLastLocation());
            user.sendMessage(EssentialsMessages.TELEPORT_TELEPORTED_TO_OFFLINE, offlinePlayer.getName());
            return;
        }

        user.teleport(target);
        user.sendMessage(EssentialsMessages.TELEPORT_TELEPORTED_TO_ONLINE, target.getName());
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
