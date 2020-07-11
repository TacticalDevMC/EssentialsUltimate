package nl.tacticaldev.essentialsbot.interfaces;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Arrays;
import java.util.List;

public interface ICommand {

    void handle(List<String> args, GuildMessageReceivedEvent event);

    String getHelp();

    String getInvoke();

    String getUsage();

    default List<String> getAliases() {
        return Arrays.asList();
    }

}