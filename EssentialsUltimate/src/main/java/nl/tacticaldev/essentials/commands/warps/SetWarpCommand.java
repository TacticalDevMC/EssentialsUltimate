package nl.tacticaldev.essentials.commands.warps;

import essentialsapi.utils.essentialsutils.NumberUtil;
import essentialsapi.utils.essentialsutils.StringUtil;
import essentialsapi.utils.exception.CoreException;
import essentialsapi.utils.exception.NotEnoughArgumentsException;
import essentialsapi.utils.logger.Logger;
import nl.tacticaldev.essentials.Essentials;
import nl.tacticaldev.essentials.commands.CoreCommand;
import nl.tacticaldev.essentials.interfaces.IEssentials;
import nl.tacticaldev.essentials.managers.messages.enums.EssentialsMessages;
import nl.tacticaldev.essentials.managers.warp.exceptions.InvalidWorldException;
import nl.tacticaldev.essentials.managers.warp.exceptions.WarpNotFoundException;
import nl.tacticaldev.essentials.managers.warp.interfaces.IWarps;
import nl.tacticaldev.essentials.player.EssentialsPlayer;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class SetWarpCommand extends CoreCommand {

    public SetWarpCommand(String name, String permission, String info, String usage, boolean sub, boolean allowConsole) {
        super(name, permission, info, usage, sub, allowConsole);
    }

    @Override
    public void init() {

    }

    @Override
    public void execute() throws Exception {
        if (getArgs().length < 1) {
            throw new NotEnoughArgumentsException();
        }

        if (NumberUtil.isInt(getArgs()[0]) || getArgs()[0].isEmpty()) {
            EssentialsMessages.SETWARP_INVALID_WARP_NAME.send(getPlayer(), getArgs()[0]);
            return;
        }

        EssentialsPlayer base = new EssentialsPlayer(getPlayer());
        IEssentials ess = Essentials.getInstance();

        final Location loc = base.getLocation();
        final IWarps warps = ess.getWarps();
        Location warpLoc = null;

        try {
            warpLoc = warps.getWarp(getArgs()[0]);
        } catch (WarpNotFoundException | InvalidWorldException ignored) {

        }

        if (warpLoc == null || base.isAuthorized("essentials.warp.overwrite." + StringUtil.safeString(getArgs()[0]))) {
            warps.setWarp(base, getArgs()[0], loc);
        } else {
            throw new Exception("Warp overwrite.");
        }

        EssentialsMessages.SETWARP_WARP_SET.send(getPlayer(), getArgs()[0]);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return getSuggestions(args[0], "<name>");
        }
        return new ArrayList<>();
    }
}
