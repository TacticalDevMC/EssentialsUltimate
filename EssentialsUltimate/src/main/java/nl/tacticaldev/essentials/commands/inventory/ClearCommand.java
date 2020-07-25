package nl.tacticaldev.essentials.commands.inventory;

import nl.tacticaldev.essentials.commands.CoreCommand;
import nl.tacticaldev.essentials.managers.messages.enums.EssentialsMessages;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ClearCommand extends CoreCommand {

    public ClearCommand(String name, String permission, String info, String usage, boolean sub, boolean allowConsole) {
        super(name, permission, info, usage, sub, allowConsole);
    }

    @Override
    public void init() {

    }

    @Override
    public void execute() throws Exception {
        switch (getArgs().length) {
            case 0:
                if (getSender() instanceof ConsoleCommandSender) {
                    EssentialsMessages.CLEAR_ARGS.send(getSender());
                    return;
                }

                user.getInventory().clear();
                user.sendMessage(EssentialsMessages.CLEAR_INVENTORY_CLEARED);
                break;
            default:
                break;
        }
    }
}
