package nl.tacticaldev.essentials.commands.punishments;

import essentialsapi.utils.Utils;
import essentialsapi.utils.exception.CoreException;
import nl.tacticaldev.essentials.Essentials;
import nl.tacticaldev.essentials.commands.CoreCommand;
import nl.tacticaldev.essentials.interfaces.IEssentials;
import nl.tacticaldev.essentials.managers.messages.enums.EssentialsMessages;
import nl.tacticaldev.essentials.managers.punishments.ban.ipban.IPBan;
import nl.tacticaldev.essentials.managers.punishments.ban.ipban.TempIPBan;
import nl.tacticaldev.essentials.player.EssentialsPlayerIP;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class IPBanCommand extends CoreCommand {

    public IPBanCommand(String name, String permission, String info, String usage, boolean sub, boolean allowConsole) {
        super(name, permission, info, usage, sub, allowConsole);
    }

    @Override
    public void init() {

    }

    @Override
    public void execute() throws CoreException {
        final boolean silent = Utils.isSilent(getArgs());
        final String reason = Utils.buildReason(getArgs());
        final String banner = Utils.getName(getSender());
        if (getArgs().length <= 0) {
            user.sendMessage(EssentialsMessages.IPBAN_ARSG);
            return;
        }

        String name = getArgs()[0];

        if (name.isEmpty()) {
            user.sendMessage(EssentialsMessages.PLAYER_ERROR, getArgs()[0]);
            return;
        }

        String ip;
        IEssentials ess = Essentials.getInstance();

        if (!Utils.isIP(name)) {
            name = Essentials.getInstance().getBanManager().match(name);
            if (name == null) {
                name = getArgs()[0];
            }
            ip = Essentials.getInstance().getBanManager().getIP(name);
            if (ip == null) {
                user.sendMessage(EssentialsMessages.IPBAN_NO_IP_RECORD_FOUND, name);
                return;
            }

            ess.getBanManager().ban(name, reason, banner);
        } else {
            ip = name;
        }
        final IPBan ban = ess.getBanManager().getIPBan(ip);
        EssentialsPlayerIP playerIP = new EssentialsPlayerIP(ip);

        if (ban != null && !(ban instanceof TempIPBan) && playerIP.isBanned(ip)) {
            user.sendMessage(EssentialsMessages.IPBAN_IP_ALREADY_BANNED, ip);
            return;
        }

        ess.getBanManager().ipban(ip, reason, banner);
        final String message = Utils.replaceColor(ess.getSettings().getPlayerIpBannedAnnouncement().replace("{banner}", banner).replace("{name}", name).replace("{reason}", reason).replace("{ip}", ip));

        ess.getBanManager().announce(message, silent, getSender());
        ess.getBanManager().addHistory(name, banner, message);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return getSuggestions(args[0], "<player|IP>");
        } else if (args.length == 2) {
            return getSuggestions(args[1], "[-s]");
        } else if (args.length == 3) {
            return getSuggestions(args[2], "<reason>");
        }
        return new ArrayList<>();
    }
}
