package nl.tacticaldev.essentials.commands.punishments;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import essentialsapi.utils.Utils;
import essentialsapi.utils.exception.CoreException;
import nl.tacticaldev.essentials.Essentials;
import nl.tacticaldev.essentials.commands.CoreCommand;
import nl.tacticaldev.essentials.managers.messages.enums.EssentialsMessages;
import nl.tacticaldev.essentials.managers.punishments.ban.Ban;
import nl.tacticaldev.essentials.managers.punishments.ban.ipban.IPBan;
import nl.tacticaldev.essentials.managers.punishments.ban.ipban.TempIPBan;
import nl.tacticaldev.essentials.managers.punishments.ban.tempban.TempBan;
import nl.tacticaldev.essentials.player.EssentialsPlayer;
import nl.tacticaldev.essentials.settings.interfaces.ISettings;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BanCommand extends CoreCommand {

    public BanCommand(String name, String permission, String info, String usage, boolean sub, boolean allowConsole) {
        super(name, permission, info, usage, sub, allowConsole);
    }

    @Override
    public void init() {

    }

    @Override
    public void execute() throws CoreException {
        if (getArgs().length <= 2) {
            user.sendMessage(EssentialsMessages.BAN_ARGS);
            return;
        }

        final boolean silent = Utils.isSilent(getArgs());
        String name = getArgs()[0];

        if (name.isEmpty()) {
            user.sendMessage(EssentialsMessages.BAN_NO_PLAYER_GIVEN);
            return;
        }

        final String reason = Utils.buildReason(getArgs());
        final String banner = Utils.getName(getSender());
        final String message = settings.getPlayerBannedAnnouncement().replace("{banner}", banner).replace("{name}", name).replace("{reason}", reason);
        if (!(Utils.isIP(name))) {
            name = ess.getBanManager().match(name);
            if (name == null) {
                name = getArgs()[0];
            }
            final Ban ban = ess.getBanManager().getBan(name);
            EssentialsPlayer base = new EssentialsPlayer(name);

            if (ban != null && !(ban instanceof TempBan) && base.isBanned()) {
                user.sendMessage(EssentialsMessages.BAN_PLAYER_ALREADY_BANNED, base.getBase().getName());
                return;
            }

            ess.getBanManager().ban(name, reason, banner);
            base.updateTotalBans(+1);
        } else {
            final IPBan ipban = Essentials.getInstance().getBanManager().getIPBan(name);
            if (ipban != null && !(ipban instanceof TempIPBan)) {
                user.sendMessage(EssentialsMessages.IPBAN_IP_ALREADY_BANNED, name);
                return;
            }
            ess.getBanManager().ipban(name, reason, banner);
        }
        ess.getBanManager().announce(message, silent, getSender());
        ess.getBanManager().addHistory(name, banner, message);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> suggestions = new ArrayList<>();
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
                    suggestions.add(player.getName());
                }
            }
            return suggestions;
        } else if (args.length == 2) {
            return getSuggestions(args[1], "[-s]");
        } else if (args.length == 3) {
            return getSuggestions(args[2], "<reason>");
        }
        return new ArrayList<>();
    }
}
