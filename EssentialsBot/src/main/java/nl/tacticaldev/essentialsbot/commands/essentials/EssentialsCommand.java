package nl.tacticaldev.essentialsbot.commands.essentials;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import nl.tacticaldev.essentialsbot.EssentialsBot;
import nl.tacticaldev.essentialsbot.interfaces.ICommand;
import nl.tacticaldev.essentialsbot.utils.Constants;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

public class EssentialsCommand implements ICommand {

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if (args.size() == 0) {
            event.getChannel().sendMessage(getUsage()).queue();
            return;
        }

        String prefix = Constants.PREFIX;

        switch (String.join("", args.subList(0, args.size()))) {
            case "help":
                EmbedBuilder builder = new EmbedBuilder().setTitle("Help of essentials");

                builder.setColor(EssentialsBot.getRandomColor());
                builder.setFooter("{EssentialsUltimate} - 1.0.1-BETA", null);
                builder.setTimestamp(Instant.now());

                StringBuilder descriptionBuilder = builder.getDescriptionBuilder();


                descriptionBuilder
                        .append("Dit is de essentials help lijst.")
                        .append("\n\n")
                        .append("Bekijk alle comands: `" + prefix + getInvoke() + " commands`")
                        .append("\n\n")
                        .append("Bekijk een bepaalde command: `" + prefix + getInvoke() + "command <command>`")
                        .append("\n\n")
                        .append("Bekijk alle (default)permissions: `" + prefix + getInvoke() + " permissions`")
                        .append("\n")
                        .append("LETOP! Als je een permission veranderd in de `permissions.yml` file, wordt dit niet weergegeven in de permissions lijst.");

                event.getChannel().sendMessage(builder.build()).queue();
                break;
            case "command":
                if (args.size() == 1) {
                    event.getChannel().sendMessage("Gebruik: `" + prefix + getInvoke() + " command <command>`").queue();
                    return;
                }

                switch (String.join(" ", args.subList(1, args.size()))) {
                    case "seen":
                        EmbedBuilder builderSeen = new EmbedBuilder().setTitle("Help of essentials");

                        builderSeen.setColor(EssentialsBot.getRandomColor());
                        builderSeen.setFooter("{EssentialsUltimate} - 1.0.1-BETA", null);
                        builderSeen.setTimestamp(Instant.now());

                        StringBuilder descriptionBuilderSeen = builderSeen.getDescriptionBuilder();

                        descriptionBuilderSeen
                                .append("Seen command")
                                .append("\n\n")
                                .append("Usage: `/seen <player>`.")
                                .append("\n\n")
                                .append("Aliases: `Geen`")
                                .append("\n\n")
                                .append("Description: `See who's last seen on the server`")
                                .append("\n")
                                .append("Category: `Admin`");

                        event.getChannel().sendMessage(builderSeen.build()).queue();
                        break;
                    default:
                        event.getChannel().sendMessage("Command niet gevonden!").queue();
                }
                break;
            default:
                event.getChannel().sendMessage("Subcommand niet gevonden!").queue();
        }

    }

    @Override
    public String getHelp() {
        return "Bot main command";
    }

    @Override
    public String getInvoke() {
        return "essentials";
    }

    @Override
    public String getUsage() {
        return "" + Constants.PREFIX + getInvoke() + " <help, commands, command, permissions, wiki, github>";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("ess");
    }
}
