package nl.tacticaldev.essentials.commands;

import nl.tacticaldev.essentials.commands.afk.AFKCommand;
import nl.tacticaldev.essentials.commands.teleport.TeleportCommand;
import nl.tacticaldev.essentials.commands.teleport.TeleportOfflineCommand;
import nl.tacticaldev.essentials.commands.test.TestCommand;

import java.util.ArrayList;


public class CommandModule {

    private static CommandModule instance;
    private ArrayList<CoreCommand> command = new ArrayList<CoreCommand>();

    public static CommandModule getInstance() {
        return instance;
    }

    public static void setInstance(CommandModule instance) {
        CommandModule.instance = instance;
    }

    public ArrayList<CoreCommand> getCommand() {
        return command;
    }

    public CommandModule() {
        setInstance(this);
    }

    public void loadTestCommands() {
        new TestCommand("test", "TacticalDev", "Test command", "", false, false).registerCommand();
    }

    public void loadPunishmentsCommands() {
    }

    public void loadTeleportCommands() {
        new TeleportCommand("teleport", "essentials.teleport", "Teleport to a player or to a location", "<player>", false, false).registerCommand();
        new TeleportOfflineCommand("teleportoffline", "essentials.teleport.offline", "Teleport to a offline's player's location","<offlineplayer>", false, false).registerCommand();
    }

    public void loadMainCommands() {
    }

    public void loadDefaultCommands() {
//        new AFKCommand("afk", "essentials.afk", "Afk command", "", false, false).registerCommand();
    }

    public void loadPlayerCommands() {
    }

    public void loadBukkitCommands() {
        //new PluginsCommand("plugins", "essentialsultimate.command.plugins", "Plugins command", "", false, false).registerCommand();
    }
}