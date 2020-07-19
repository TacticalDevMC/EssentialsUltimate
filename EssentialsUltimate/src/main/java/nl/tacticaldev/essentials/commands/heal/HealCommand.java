package nl.tacticaldev.essentials.commands.heal;

import essentialsapi.utils.exception.CoreException;
import nl.tacticaldev.essentials.commands.CoreCommand;
import nl.tacticaldev.essentials.managers.messages.enums.EssentialsMessages;
import nl.tacticaldev.essentials.player.EssentialsPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class HealCommand extends CoreCommand {

    public HealCommand(String name, String permission, String info, String usage, boolean sub, boolean allowConsole) {
        super(name, permission, info, usage, sub, allowConsole);
    }

    @Override
    public void init() {

    }

    @Override
    public void execute() throws CoreException {

        switch (getArgs().length) {
            case 0:
                if (getSender() instanceof ConsoleCommandSender) {
                    EssentialsMessages.HEAL_ARGS.send(getSender());
                    return;
                }

                Player player = (Player) getSender();
                EssentialsPlayer base = new EssentialsPlayer(player);

                base.heal(20);
                base.feed(20);
                EssentialsMessages.HEAL_PLAYER_HEALED.send(player);
                break;
            case 1:
                Player target = Bukkit.getPlayer(getArgs()[0]);
                EssentialsPlayer baseTarget = new EssentialsPlayer(target);

                if (target == null) {
                    EssentialsMessages.PLAYER_ERROR.send(getSender(), getArgs()[0]);
                    return;
                }

                baseTarget.heal(20);
                baseTarget.feed(20);
                EssentialsMessages.HEAL_TARGET_HEALED.send(getSender(), target.getName());
            default:
                break;
        }

    }
}
