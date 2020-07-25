package nl.tacticaldev.essentials.commands.heal;

import essentialsapi.utils.Cooldown;
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
                    user.sendMessage(EssentialsMessages.HEAL_ARGS);
                    return;
                }

                Player player = (Player) getSender();

                if (Cooldown.isInCooldown(player.getUniqueId(), "healfeed")) {
                    user.sendMessage(EssentialsMessages.IN_COOLDOWN, "heal", Cooldown.getTimeLeft(player.getUniqueId(), "healfeed"));
                    return;
                }

                Cooldown c = new Cooldown(player.getUniqueId(), "healfeed", settings.getHealFeedCooldown());

                c.start();

                user.heal(20);
                user.feed(20);
                user.sendMessage(EssentialsMessages.HEAL_PLAYER_HEALED);
                break;
            case 1:
                Player target = Bukkit.getPlayer(getArgs()[0]);
                EssentialsPlayer baseTarget = new EssentialsPlayer(target);

                if (getSender() instanceof ConsoleCommandSender) {
                    // TODO: Heal from console
                    return;
                }

                if (Cooldown.isInCooldown(getPlayer().getUniqueId(), "healfeed")) {
                    user.sendMessage(EssentialsMessages.IN_COOLDOWN, "heal", Cooldown.getTimeLeft(getPlayer().getUniqueId(), "healfeed"));
                    return;
                }

                Cooldown c1 = new Cooldown(getPlayer().getUniqueId(), "healfeed", settings.getHealFeedCooldown());

                c1.start();

                if (target == null) {
                    user.sendMessage(EssentialsMessages.PLAYER_ERROR, getArgs()[0]);
                    return;
                }

                baseTarget.heal(20);
                baseTarget.feed(20);
                user.sendMessage(EssentialsMessages.HEAL_TARGET_HEALED, target.getName());
            default:
                break;
        }

    }
}
