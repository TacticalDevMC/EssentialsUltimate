package nl.tacticaldev.essentials.commands;

import nl.tacticaldev.essentials.commands.afk.AFKCommand;
import nl.tacticaldev.essentials.commands.bukkit.PluginsCommand;
import nl.tacticaldev.essentials.commands.essentials.EssentialsCommand;
import nl.tacticaldev.essentials.commands.heal.HealCommand;
import nl.tacticaldev.essentials.commands.inventory.ClearCommand;
import nl.tacticaldev.essentials.commands.lang.LangCommand;
import nl.tacticaldev.essentials.commands.punishments.BanCommand;
import nl.tacticaldev.essentials.commands.punishments.IPBanCommand;
import nl.tacticaldev.essentials.commands.punishments.TempBanCommand;
import nl.tacticaldev.essentials.commands.spawn.DeleteSpawnCommand;
import nl.tacticaldev.essentials.commands.spawn.SetSpawnCommand;
import nl.tacticaldev.essentials.commands.spawn.SpawnCommand;
import nl.tacticaldev.essentials.commands.spawn.SpawnsCommand;
import nl.tacticaldev.essentials.commands.teleport.TeleportCommand;
import nl.tacticaldev.essentials.commands.teleport.TeleportOfflineCommand;
import nl.tacticaldev.essentials.commands.test.TestCommand;
import nl.tacticaldev.essentials.commands.warps.SetWarpCommand;
import nl.tacticaldev.essentials.commands.warps.WarpCommand;

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
        new BanCommand("ban", "essentials.ban", "Ban a player", "<player:IP> [-s] <reason>", false, true).registerCommand();
        new TempBanCommand("tempban", "essentials.tempban", "TempBan a player", "<player:IP> <time> <timeform> [-s] <reason>", false, true).registerCommand();
        new IPBanCommand("ipban", "essentials.ipban", "Ip ban a player", "<player|IP> [s] <reason>", false, true).registerCommand();
    }

    public void loadTeleportCommands() {
        new TeleportCommand("teleport", "essentials.teleport", "Teleport to a player or to a location", "<player>", false, false).registerCommand();
        new TeleportOfflineCommand("teleportoffline", "essentials.teleport.offline", "Teleport to a offline's player's location", "<offlineplayer>", false, false).registerCommand();
    }

    public void loadMainCommands() {
        new EssentialsCommand("essentials", "essentials.command", "Essentials command", "<reload>", true, false).registerCommand();
        new LangCommand("language", "essentials.lang", "Lang command", "<menu>", true, false).registerCommand();
    }

    public void loadDefaultCommands() {
        // afk
//        new AFKCommand("afk", "essentials.afk", "Afk command", "", false, false).registerCommand();

        // spawn
        new SetSpawnCommand("setspawn", "essentials.setspawn", "Setspawn command", "<name>", false, false).registerCommand();
        new SpawnsCommand("spawns", "essentials.spawns", "List all the spawns", "", false, false).registerCommand();
        new SpawnCommand("spawn", "essentials.spawn", "Teleport to a spawn", "[name]", false, false).registerCommand();
        new DeleteSpawnCommand("deletespawn", "essentials.deletespawn", "Delete a spawn", "<name>", false, false).registerCommand();

        // heal/feed
        new HealCommand("heal", "essentials.heal", "Heal a player", "[player]", false, true).registerCommand();

        // inventory
        new ClearCommand("clear", "essentials.clear", "Clear the inventory of a player", "[player]", false, true).registerCommand();

        // warp
//        new SetWarpCommand("setwarp", "essentials.setwarp", "Set a warp", "<name>", false, false).registerCommand();
//        new WarpCommand("warp", "essentials.warp", "Teleport to a warp", "<name>", false, false).registerCommand();
    }

    public void loadPlayerCommands() {
    }

    public void loadBukkitCommands() {
//        new PluginsCommand("plugins", "essentialsultimate.command.plugins", "Plugins command", "", false, false).registerCommand();
    }
}
