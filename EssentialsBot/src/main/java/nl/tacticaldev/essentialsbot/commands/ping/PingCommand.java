package nl.tacticaldev.essentialsbot.commands.ping;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import nl.tacticaldev.essentialsbot.interfaces.ICommand;
import nl.tacticaldev.essentialsbot.utils.Constants;

import java.util.List;

public class PingCommand implements ICommand {

    @Override
    public void handle(String[] args, GuildMessageReceivedEvent event) {
        event.getChannel().sendMessage("Pong!").queue((message) ->
                message.editMessageFormat("Ping is %sms", event.getJDA().getGatewayPing()).queue()
        );
    }

    @Override
    public String getHelp() {
        return "Pong!";
    }

    @Override
    public String getInvoke() {
        return "ping";
    }

    @Override
    public String getUsage() {
        return "" + Constants.PREFIX + getInvoke();
    }

    @Override
    public List<String> getAliases() {
        return null;
    }
}