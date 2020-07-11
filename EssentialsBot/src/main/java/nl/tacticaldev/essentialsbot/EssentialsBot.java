package nl.tacticaldev.essentialsbot;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import nl.tacticaldev.essentialsbot.commands.base.CommandManager;
import nl.tacticaldev.essentialsbot.utils.Listener;
import nl.tacticaldev.essentialsbot.utils.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.io.IOException;
import java.util.Random;

public class EssentialsBot extends ListenerAdapter {

    private static JDA jda;
    public Guild guild;
    private static final Random random = new Random();

    private EssentialsBot() throws IOException {
        CommandManager commandManager = new CommandManager(random);
        Listener listener = new Listener(commandManager);
        Logger logger = LoggerFactory.getLogger(EssentialsBot.class);

        try {
            logger.info("Booting");
            jda = new JDABuilder(Token.TOKEN)
                    .setChunkingFilter(ChunkingFilter.NONE)
                    .addEventListeners(listener)
                    .build();
            jda.getPresence().setStatus(OnlineStatus.DO_NOT_DISTURB);
            jda.getPresence().setActivity(Activity.listening("Working from EssentialsUltimate"));
            logger.info("Running");
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        new EssentialsBot();
    }

    public JDA getJda() {
        return jda;
    }

    public static Color getRandomColor() {
        float r = random.nextFloat();
        float g = random.nextFloat();
        float b = random.nextFloat();

        return new Color(r, g, b);
    }
}
