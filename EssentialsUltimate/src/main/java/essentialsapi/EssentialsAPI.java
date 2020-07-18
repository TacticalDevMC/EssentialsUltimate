package essentialsapi;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import nl.tacticaldev.essentials.commands.CommandModule;
import nl.tacticaldev.essentials.database.EssentialsDatabase;
import nl.tacticaldev.essentials.database.sql.SQLManager;
import nl.tacticaldev.essentials.interfaces.IEssentials;
import essentialsapi.interfaces.IAPI;
import essentialsapi.interfaces.IDatabase;
import essentialsapi.utils.menu.MenuManager;
import essentialsapi.utils.menu.item.InventoryCheck;
import nl.tacticaldev.essentials.listeners.bukkit.custom.DatabasePlayerCreate;
import nl.tacticaldev.essentials.listeners.bukkit.custom.SpawnSignCreateEvent;
import nl.tacticaldev.essentials.listeners.bukkit.players.PlayerBlockBreakListener;
import nl.tacticaldev.essentials.listeners.bukkit.players.PlayerConnectBanListener;
import nl.tacticaldev.essentials.listeners.bukkit.players.PlayerConnectionListener;
import nl.tacticaldev.essentials.listeners.bukkit.players.PlayerInteractSignListener;
import nl.tacticaldev.essentials.listeners.bukkit.players.signs.SignChangeListener;
import org.bukkit.plugin.PluginManager;

public class EssentialsAPI implements IAPI {

    private IEssentials ess;

    public EssentialsAPI(IEssentials ess) {
        this.ess = ess;
    }

    @Override
    public IDatabase getDatabase() {
        return EssentialsDatabase.getInstance();
    }

    @Override
    public void loadListeners(IEssentials ess) {
        PluginManager pm = ess.getServer().getPluginManager();

        // MENUS
        pm.registerEvents(new InventoryCheck(), ess);
        pm.registerEvents(new MenuManager(), ess);

        // PLAYERS
        pm.registerEvents(new PlayerConnectionListener(), ess);
        pm.registerEvents(new PlayerBlockBreakListener(), ess);
        pm.registerEvents(new PlayerConnectBanListener(), ess);

        // CUSTOM
        pm.registerEvents(new DatabasePlayerCreate(), ess);

        if (ess.getSettings().isUsingEssentialsSigns()) {
            pm.registerEvents(new SignChangeListener(), ess);
            pm.registerEvents(new PlayerInteractSignListener(), ess);

            if (ess.getSettings().getEnabledSigns().contains("spawn")) {
                pm.registerEvents(new SpawnSignCreateEvent(), ess);
            }
        }
    }

    @Override
    public void loadCommands() {
        CommandModule commandModule = new CommandModule();

        commandModule.loadTestCommands();
        commandModule.loadTeleportCommands();
        commandModule.loadDefaultCommands();
        commandModule.loadPunishmentsCommands();
    }

    @Override
    public void createTables() {
        SQLManager sqlManager = new SQLManager(getDatabase());

        sqlManager.createTablePlayers();
        sqlManager.createTableBans();
        sqlManager.createTableHistory();
    }
}
