package nl.tacticaldev.essentialsbot.commands.owner;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import groovy.lang.GroovyShell;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import nl.tacticaldev.essentialsbot.interfaces.ICommand;
import nl.tacticaldev.essentialsbot.utils.Constants;

import java.util.Arrays;
import java.util.List;

public class EvalCommand implements ICommand {

    private final GroovyShell engine;
    private final String imports;

    public EvalCommand() {
        this.engine = new GroovyShell();
        this.imports = "import java.io.*\n" +
                "import java.lang.*\n" +
                "import java.util.*\n" +
                "import java.util.concurrent.*\n" +
                "import net.dv8tion.jda.core.*\n" +
                "import net.dv8tion.jda.core.entities.*\n" +
                "import net.dv8tion.jda.core.entities.impl.*\n" +
                "import net.dv8tion.jda.core.managers.*\n" +
                "import net.dv8tion.jda.core.managers.impl.*\n" +
                "import net.dv8tion.jda.core.utils.*\n";
    }

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if (event.getAuthor().getIdLong() != Constants.OWNER) {
            return;
        }

        if (args.isEmpty()) {
            event.getChannel().sendMessage("Missing arguments").queue();
            return;
        }

        try {
            engine.setProperty("args", args);
            engine.setProperty("event", event);
            engine.setProperty("message", event.getMessage());
            engine.setProperty("channel", event.getChannel());
            engine.setProperty("jda", event.getJDA());
            engine.setProperty("guild", event.getGuild());
            engine.setProperty("member", event.getMember());

            String script = imports + event.getMessage().getContentRaw().split("\\s+", 2)[1];
            Object out = engine.evaluate(script);

            event.getChannel().sendMessage(out == null ? "Executed without error" : out.toString()).queue();
        } catch (Exception e) {
            event.getChannel().sendMessage(e.getMessage()).queue();
        }
    }

    @Override
    public String getHelp() {
        return "Takes groovy code and evaluates it";
    }

    @Override
    public String getInvoke() {
        return "eval";
    }

    @Override
    public String getUsage() {
        return "" + Constants.PREFIX + getInvoke() + " <code to evaluate>";
    }

    @Override
    public List<String> getAliases() {
        return null;
    }
}
