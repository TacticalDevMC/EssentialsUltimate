package nl.tacticaldev.essentials.commands.warps;

import essentialsapi.utils.essentialsutils.NumberUtil;
import essentialsapi.utils.essentialsutils.StringUtil;
import essentialsapi.utils.exception.NoChargeException;
import nl.tacticaldev.essentials.Essentials;
import nl.tacticaldev.essentials.commands.CoreCommand;
import nl.tacticaldev.essentials.interfaces.IEssentials;
import nl.tacticaldev.essentials.managers.messages.enums.EssentialsMessages;
import nl.tacticaldev.essentials.managers.warp.interfaces.IWarps;
import nl.tacticaldev.essentials.player.EssentialsPlayer;
import nl.tacticaldev.essentials.settings.interfaces.ISettings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class WarpCommand extends CoreCommand {

    public WarpCommand(String name, String permission, String info, String usage, boolean sub, boolean allowConsole) {
        super(name, permission, info, usage, sub, allowConsole);
    }

    @Override
    public void init() {

    }

    private static final int WARPS_PER_PAGE = 20;

    @Override
    public void execute() throws Exception {
        EssentialsPlayer user = new EssentialsPlayer(getPlayer());
        if (getArgs().length == 0 || getArgs()[0].matches("[0-9]+")) {
            if (!user.isAuthorized("essentials.warp.list")) {
                EssentialsMessages.WARP_NO_LIST_PERMS.send(getPlayer());
                return;
            }
            warpList(user.getBase(), getArgs(), user);
            throw new NoChargeException();
        }

        if (getArgs().length > 0) {
            EssentialsPlayer otherUser = null;
            if (getArgs().length == 2 && (user.isAuthorized("essentials.warp.otherplayers") || user.isAuthorized("essentials.warp.others"))) {
                otherUser = new EssentialsPlayer(getArgs()[1]);
                warpUser(user, otherUser, getArgs()[0], getName());
                return;
            }
            warpUser(user, user, getArgs()[0], getName());
        }
    }

    private void warpList(final CommandSender sender, final String[] args, final EssentialsPlayer user) throws Exception {
        final IWarps warps = ess.getWarps();
        final List<String> warpNameList = getAvailableWarpsFor(user);

        if (warpNameList.isEmpty()) {
            EssentialsMessages.WARP_NO_WARPS_DEFINED.send(getPlayer());
            return;
        }

        int page = 1;
        if (getArgs().length > 0 && NumberUtil.isInt(getArgs()[0])) {
            page = Integer.parseInt(getArgs()[0]);
        }

        final int maxPages = (int) Math.ceil(warpNameList.size() / (double) WARPS_PER_PAGE);

        if (page > maxPages) {
            page = maxPages;
        }

        final int warpPage = (page - 1) * WARPS_PER_PAGE;
        final String warpList = StringUtil.joinList(warpNameList.subList(warpPage, warpPage + Math.min(warpNameList.size() - warpPage, WARPS_PER_PAGE)));

        if (warpNameList.size() > WARPS_PER_PAGE) {
            EssentialsMessages.WARP_WARPS_COUNT.send(getPlayer(), warpNameList.size(), page, maxPages);
            EssentialsMessages.WARP_WARPS_LIST.send(getPlayer(), warpList);
        } else {
            EssentialsMessages.WARP_WARPS.send(getPlayer(), warpList);
        }
    }

    private void warpUser(final EssentialsPlayer owner, final EssentialsPlayer user, final String name, final String commandLabel) {
        if (settings.getPerWarpPermission() && !owner.isAuthorized("essentials.warps." + name)) {
            EssentialsMessages.WARP_NO_WARP_PERMISSION.send(owner.getBase());
        }

        owner.warp(user, name, getNewExceptionFuture(user.getBase(), commandLabel));
    }

    private List<String> getAvailableWarpsFor(final EssentialsPlayer user) {
        if (settings.getPerWarpPermission() && user != null) {
            return ess.getWarps().getList().stream()
                    .filter(warpName -> user.isAuthorized("essentials.warps." + warpName))
                    .collect(Collectors.toList());
        }

        return new ArrayList<>(ess.getWarps().getList());
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        EssentialsPlayer user = new EssentialsPlayer(sender.getName());

        if (args.length == 1 && user.isAuthorized("essentials.warp.list")) {
            return getAvailableWarpsFor(user);
        } else if (args.length == 2 && (user.isAuthorized("essentials.warp.otherplayers") || user.isAuthorized("essentials.warp.others"))) {
            //TODO: Remove 'otherplayers' permission.
            return getPlayers(args, 1);
        }
        return new ArrayList<>();
    }
}
