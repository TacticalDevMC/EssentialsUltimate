package nl.tacticaldev.essentials.commands.punishments;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import essentialsapi.utils.Utils;
import essentialsapi.utils.essentialsutils.DateUtil;
import essentialsapi.utils.exception.CoreException;
import nl.tacticaldev.essentials.Essentials;
import nl.tacticaldev.essentials.commands.CoreCommand;
import nl.tacticaldev.essentials.managers.messages.enums.EssentialsMessages;
import nl.tacticaldev.essentials.managers.punishments.ban.Ban;
import nl.tacticaldev.essentials.managers.punishments.ban.tempban.TempBan;
import nl.tacticaldev.essentials.player.EssentialsPlayer;
import nl.tacticaldev.essentials.settings.interfaces.ISettings;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TempBanCommand extends CoreCommand {

    public TempBanCommand(String name, String permission, String info, String usage, boolean sub, boolean allowConsole) {
        super(name, permission, info, usage, sub, allowConsole);
    }

    @Override
    public void init() {

    }

    @Override
    public void execute() throws CoreException {
        if (getArgs().length <= 4) {
            EssentialsMessages.TEMPBAN_ARGS.send(getSender());
            return;
        }

        final boolean silent = Utils.isSilent(getArgs());
        String name = getArgs()[0];

        if (name.isEmpty()) {
            EssentialsMessages.TEMPBAN_NO_PLAYER_GIVEN.send(getSender());
            return;
        }

        long expires = DateUtil.getTime(getArgs());
        if (expires <= 0L) {
            EssentialsMessages.TEMPBAN_ARGS.send(getSender());
            return;
        }

        expires += System.currentTimeMillis();
        long tempbanTime;
        try {
            tempbanTime = Essentials.getInstance().getSettings().getMaxTempbanTime();
        } catch (Exception e) {
            tempbanTime = 604800L;
        }

        long compare = tempbanTime;
        tempbanTime = tempbanTime * 1000;

        tempbanTime += System.currentTimeMillis();

        if (compare != 0 && expires > tempbanTime) {
            EssentialsMessages.TEMPBAN_BAN_TIME_TO_LONG.send(getSender(), DateUtil.getTimeUntil(tempbanTime));
            expires = tempbanTime;
        }

        final String reason = Utils.buildReason(getArgs());
        final String banner = Utils.getName(getSender());
        if (!Utils.isIP(name)) {
            name = Essentials.getInstance().getBanManager().match(name);
            if (name == null) {
                name = getArgs()[0];
            }

            final Ban ban = Essentials.getInstance().getBanManager().getBan(name);
            if (ban != null) {
                if (!(ban instanceof TempBan)) {
                    EssentialsMessages.TEMPBAN_TEMPBAN_SHORTER_THAN_LAST.send(getSender());
                    return;
                }

                final TempBan tBan = (TempBan) ban;

                if (tBan.getExpires() > expires) {
                    EssentialsMessages.TEMPBAN_TEMPBAN_SHORTER_THAN_LAST.send(getSender());
                    return;
                }

                Essentials.getInstance().getBanManager().unban(name);
                EssentialsPlayer base = new EssentialsPlayer(name);
                base.updateBanned(false);
            }

            EssentialsPlayer base = new EssentialsPlayer(name);
            Essentials.getInstance().getBanManager().tempban(name, reason, banner, expires);
            base.updateTotalBans(+1);
        } else {
            // TODO: Add ipTempBan
        }

        ISettings settings = Essentials.getInstance().getSettings();
        final String message = settings.getPlayerTempBannedAnnouncement().replace("{banner}", banner).replace("{name}", name).replace("{reason}", reason).replace("{time}", DateUtil.getTimeUntil(expires).replace("{appeal-message}", Essentials.getInstance().getBanManager().getAppealMessage()));

        Essentials.getInstance().getBanManager().announce(message, silent, getSender());
        Essentials.getInstance().getBanManager().addHistory(name, banner, message);
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
            return getSuggestions(args[1], "<time>");
        } else if (args.length == 3) {
            return getSuggestions(args[2], "<timeform>");
        } else if (args.length == 4) {
            return getSuggestions(args[3], "[-s]");
        } else if (args.length == 5) {
            return getSuggestions(args[4], "<reason>");
        }
        return new ArrayList<>();
    }
}
