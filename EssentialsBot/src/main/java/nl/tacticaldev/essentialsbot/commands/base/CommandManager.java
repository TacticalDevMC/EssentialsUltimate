package nl.tacticaldev.essentialsbot.commands.base;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import nl.tacticaldev.essentialsbot.commands.uptime.UptimeCommand;
import nl.tacticaldev.essentialsbot.commands.essentials.EssentialsCommand;
import nl.tacticaldev.essentialsbot.commands.help.HelpCommand;
import nl.tacticaldev.essentialsbot.commands.owner.EvalCommand;
import nl.tacticaldev.essentialsbot.commands.ping.PingCommand;
import nl.tacticaldev.essentialsbot.interfaces.ICommand;
import nl.tacticaldev.essentialsbot.utils.Constants;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.regex.Pattern;

public class CommandManager {

    private final Map<String, ICommand> commands = new HashMap<>();

    public CommandManager(Random random) {
        System.out.println("Loading commands");

        addCommand(new PingCommand());
        addCommand(new HelpCommand(this));
        addCommand(new UptimeCommand());

        addCommand(new EssentialsCommand());

        addCommand(new EvalCommand());
    }

    private void addCommand(ICommand command) {
        if (!commands.containsKey(command.getInvoke())) {
            commands.put(command.getInvoke(), command);
        }
    }

    public Collection<ICommand> getCommands() {
        return commands.values();
    }

    public ICommand getCommand(@NotNull String name) {
        return commands.get(name);
    }

    public void handleCommand(GuildMessageReceivedEvent event) {
        final String prefix = Constants.PREFIXES.get(event.getGuild().getIdLong());

        final String[] args = event.getMessage().getContentRaw().replaceFirst(
                "(?i)" + Pattern.quote(prefix), "").split("\\s+");
        final String invoke = args[0].toLowerCase();

        if (commands.containsKey(invoke)) {

            event.getChannel().sendTyping().queue();
            commands.get(invoke).handle(args, event);
        }
    }
}