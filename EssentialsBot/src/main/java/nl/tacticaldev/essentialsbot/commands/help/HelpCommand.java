package nl.tacticaldev.essentialsbot.commands.help;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import nl.tacticaldev.essentialsbot.EssentialsBot;
import nl.tacticaldev.essentialsbot.commands.base.CommandManager;
import nl.tacticaldev.essentialsbot.interfaces.ICommand;
import nl.tacticaldev.essentialsbot.utils.Constants;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

public class HelpCommand implements ICommand {

    private final CommandManager manager;

    public HelpCommand(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(String[] args, GuildMessageReceivedEvent event) {

        if (args.length == 0) {
            generateAndSendEmbed(event);
            return;
        }

        String joined = args[0];

        ICommand command = manager.getCommand(joined);

        if (command == null) {
            event.getChannel().sendMessage("The command `" + joined + "` does not exist\n" +
                    "Use `" + Constants.PREFIX + getInvoke() + "` for a list of commands").queue();
            return;
        }

        EmbedBuilder builder = new EmbedBuilder().setTitle("Command help for " + command.getInvoke());

        builder.setColor(EssentialsBot.getRandomColor());
        builder.setFooter("{EssentialsUltimate} - 1.0.1-BETA", null);
        builder.setTimestamp(Instant.now());

        StringBuilder descriptionBuilder = builder.getDescriptionBuilder();

        if (command.getAliases() == null) {
            descriptionBuilder
                    .append("Help: ").append(command.getHelp()).append("\n\n")
                    .append("Usage: ").append(command.getUsage()).append("\n\n")
                    .append("Aliases: ").append(Arrays.asList("Geen").toString().replace("[", "").replace("]", "")).append("\n\n");
        } else {
            descriptionBuilder
                    .append("Help: ").append(command.getHelp()).append("\n\n")
                    .append("Usage: ").append(command.getUsage()).append("\n\n")
                    .append("Aliases: ").append(command.getAliases().toString().replace("[", "").replace("]", "")).append("\n\n");
        }


//        String message = "Command help for `" + command.getInvoke() + "`\n" + command.getHelp();

        event.getChannel().sendMessage(builder.build()).queue();
    }

    private void generateAndSendEmbed(GuildMessageReceivedEvent event) {

        EmbedBuilder builder = new EmbedBuilder().setTitle("A list of all my commands:");

        builder.setColor(EssentialsBot.getRandomColor());
        builder.setFooter("{EssentialsUltimate} - 1.0.1-BETA", null);
        builder.setTimestamp(Instant.now());

        StringBuilder descriptionBuilder = builder.getDescriptionBuilder();

        manager.getCommands().forEach(
                (command) -> descriptionBuilder.append('`').append(command.getInvoke()).append("`\n")
        );

        event.getChannel().sendMessage(builder.build()).queue();
    }

    @Override
    public String getHelp() {
        return "Shows a list of all the commands.";
    }

    @Override
    public String getInvoke() {
        return "help";
    }

    @Override
    public String getUsage() {
        return "" + Constants.PREFIX + getInvoke() + " [command]";
    }
}