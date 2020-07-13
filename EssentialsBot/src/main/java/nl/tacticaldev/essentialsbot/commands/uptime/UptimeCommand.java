package nl.tacticaldev.essentialsbot.commands.uptime;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import nl.tacticaldev.essentialsbot.interfaces.ICommand;
import nl.tacticaldev.essentialsbot.utils.Constants;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;

public class UptimeCommand implements ICommand {

    @Override
    public void handle(String[] args, GuildMessageReceivedEvent event) {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        long uptime = runtimeMXBean.getUptime();
        long uptimeInSeconds = uptime / 1000;
        long numberOfHours = uptimeInSeconds / (60 * 60);
        long numberOfMinutes = (uptimeInSeconds / 60) - (numberOfHours * 60);
        long numberOfSeconds = uptimeInSeconds % 60;

        event.getChannel().sendMessageFormat(
                "My uptime is `%s hours, %s minutes, %s seconds`",
                numberOfHours, numberOfMinutes, numberOfSeconds
        ).queue();
    }

    @Override
    public String getHelp() {
        return "Shows the current uptime of the bot.";
    }

    @Override
    public String getInvoke() {
        return "uptime";
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
