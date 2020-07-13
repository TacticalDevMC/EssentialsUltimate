package nl.tacticaldev.essentialsbot.commands.essentials;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import nl.tacticaldev.essentialsbot.EssentialsBot;
import nl.tacticaldev.essentialsbot.interfaces.ICommand;
import nl.tacticaldev.essentialsbot.utils.Constants;

import java.awt.*;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

public class EssentialsCommand implements ICommand {

    @Override
    public void handle(String[] args, GuildMessageReceivedEvent event) {
        if (args.length == 1) {
            event.getChannel().sendMessage(getUsage()).queue();
            return;
        }

        String prefix = Constants.PREFIX;

        switch (args[1]) {
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
                        .append("Bekijk een bepaalde command: `" + prefix + getInvoke() + " command <command>`")
                        .append("\n\n")
                        .append("Bekijk alle (default)permissions: `" + prefix + getInvoke() + " permissions`")
                        .append("\n");

                event.getChannel().sendMessage(builder.build()).queue();
                break;
            case "command":
                if (args.length == 2) {
                    event.getChannel().sendMessage("Gebruik: `" + prefix + getInvoke() + " command <command>`").queue();
                    return;
                }

                switch (args[2]) {
                    case "seen":
                        sendSeen(event);
                        break;
                    case "teleport":
                        sendTeleport(event);
                        break;
                    case "teleportoffline":
                        sendTeleportOffline(event);
                        break;
                    default:
                        EmbedBuilder commandNotFoundEmbed = new EmbedBuilder().setTitle("Command niet gevonden");

                        commandNotFoundEmbed.setColor(Color.RED);
                        event.getChannel().sendMessage(commandNotFoundEmbed.build()).queue();
                }
                break;
            case "commands":
                if (args.length == 2) {
                    sendCommands(0, event);
                    return;
                }
                break;
            default:
                EmbedBuilder commandNotFoundEmbed = new EmbedBuilder().setTitle("SubCommand niet gevonden");

                commandNotFoundEmbed.setColor(Color.RED);
                event.getChannel().sendMessage(commandNotFoundEmbed.build()).queue();
        }

    }

    private void sendCommands(Integer page, GuildMessageReceivedEvent event) {
        if (page == 0) {
            EmbedBuilder builder = new EmbedBuilder().setTitle("Commands of Essentials");

            builder.setColor(EssentialsBot.getRandomColor());
            builder.setFooter("{EssentialsUltimate} - 1.0.1-BETA", null);
            builder.setTimestamp(Instant.now());

            StringBuilder descriptionBuilderSeen = builder.getDescriptionBuilder();

            descriptionBuilderSeen
                    .append("Admin:")
                    .append("\n")
                    .append("- `/seen <player>`")
                    .append("\n\n")
                    .append("Teleport:")
                    .append("\n")
                    .append("- `/teleport <player, location(x,y,z)>`")
                    .append("\n")
                    .append("- `/teleportoffline <offlineplayer>`");

            event.getChannel().sendMessage(builder.build()).queue();
        }
    }

    private void sendSeen(GuildMessageReceivedEvent event) {
        EmbedBuilder builderSeen = new EmbedBuilder().setTitle("Command: `Seen`");

        builderSeen.setColor(EssentialsBot.getRandomColor());
        builderSeen.setFooter("{EssentialsUltimate} - 1.0.1-BETA", null);
        builderSeen.setTimestamp(Instant.now());

        StringBuilder descriptionBuilderSeen = builderSeen.getDescriptionBuilder();

        descriptionBuilderSeen
                .append("Usage: `/seen <player>`.")
                .append("\n\n")
                .append("Aliases: `Geen`")
                .append("\n\n")
                .append("Description: `See who's last seen on the server`")
                .append("\n\n")
                .append("Category: `Admin`")
                .append("\n\n")
                .append("Permission: `essentials.seen`");

        event.getChannel().sendMessage(builderSeen.build()).queue();
    }

    private void sendTeleport(GuildMessageReceivedEvent event) {
        EmbedBuilder builder = new EmbedBuilder().setTitle("Command information");

        builder.setColor(EssentialsBot.getRandomColor());
        builder.setFooter("{EssentialsUltimate} - 1.0.1-BETA", null);
        builder.setTimestamp(Instant.now());

        builder.addField("teleport", "```Usage: /teleport <player, location(x, y, z)>\n\nDescription: Teleport to a player or to a location.\n\nAliases: tp```", false);
        builder.addField("teleportoffline", "```Usage: /teleportoffline <offlineplayer>\n\nDescription: Teleport to a offline's player's location.\n\nAliases: tpoffline, tpoffl```", false);

//        StringBuilder descriptionBuilder = builder.getDescriptionBuilder();

//        descriptionBuilderSeen
//                .append("Usage: `/teleport <player, location(x,y,z)>`.")
//                .append("\n\n")
//                .append("Aliases: `tp`")
//                .append("\n\n")
//                .append("Description: `Teleport to a player or to a location`")
//                .append("\n\n")
//                .append("Category: `Teleport`")
//                .append("\n\n")
//                .append("Permission: `essentials.teleport`");


        event.getChannel().sendMessage(builder.build()).queue();
    }

    private void sendTeleportOffline(GuildMessageReceivedEvent event) {
        EmbedBuilder builder = new EmbedBuilder().setTitle("Command: `TeleportOffline`");

        builder.setColor(EssentialsBot.getRandomColor());
        builder.setFooter("{EssentialsUltimate} - 1.0.1-BETA", null);
        builder.setTimestamp(Instant.now());

        StringBuilder descriptionBuilderSeen = builder.getDescriptionBuilder();

        descriptionBuilderSeen
                .append("Usage: `/teleportoffline <offlineplayer>`.")
                .append("\n\n")
                .append("Aliases: `tpoffline, tpoffl`")
                .append("\n\n")
                .append("Description: `Teleport to a offline's player's location`")
                .append("\n\n")
                .append("Category: `Teleport`")
                .append("\n\n")
                .append("Permission: `essentials.teleport.offline`");

        event.getChannel().sendMessage(builder.build()).queue();
    }

    @Override
    public String getHelp() {
        return "Bot main command";
    }

    @Override
    public String getInvoke() {
        return "ess";
    }

    @Override
    public String getUsage() {
        return "" + Constants.PREFIX + getInvoke() + " <help, commands, command, permissions, wiki, github>";
    }

    @Override
    public List<String> getAliases() {
        return null;
    }
}
