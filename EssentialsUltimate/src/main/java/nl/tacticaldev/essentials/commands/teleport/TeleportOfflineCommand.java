package nl.tacticaldev.essentials.commands.teleport;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import essentialsapi.utils.exception.CoreException;
import nl.tacticaldev.essentials.commands.CoreCommand;
import nl.tacticaldev.essentials.database.tables.PlayerTable;
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

public class TeleportOfflineCommand extends CoreCommand {

    public TeleportOfflineCommand(String name, String permission, String info, String usage, boolean sub, boolean allowConsole) {
        super(name, permission, info, usage, sub, allowConsole);
    }

    @Override
    public void init() {

    }

    @Override
    public void execute() throws CoreException {
        if (getArgs().length == 0) {
            EssentialsMessages.TELEPORT_OFFLINE_ARGS.send(getPlayer());
            return;
        }

        Player target = Bukkit.getPlayer(getArgs()[0]);

        if (target != null) {
            user.sendMessage(EssentialsMessages.TELEPORT_OFFLINE_PLAYER_IS_ONLINE);
            return;
        }

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(getArgs()[0]);

        EssentialsOfflinePlayer baseOffline = new EssentialsOfflinePlayer(offlinePlayer);

        if (!new PlayerTable().existsPlayer(baseOffline.getBase().getUniqueId())) {
            user.sendMessage(EssentialsMessages.PLAYER_NOT_FOUND_IN_DATABASE, getArgs()[0]);
            return;
        }

        user.teleport(baseOffline.getLastLocation());
        user.sendMessage(EssentialsMessages.TELEPORT_OFFLINE_TELEPORTED, baseOffline.getBase().getName());
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
