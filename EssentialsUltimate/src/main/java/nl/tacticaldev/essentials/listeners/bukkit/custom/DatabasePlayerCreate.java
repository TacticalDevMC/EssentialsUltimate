package nl.tacticaldev.essentials.listeners.bukkit.custom;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import essentialsapi.interfaces.IDatabase;
import nl.tacticaldev.essentials.database.tables.PlayerTable;
import nl.tacticaldev.essentials.interfaces.IPlayerTable;
import nl.tacticaldev.essentials.listeners.custom.DatabaseEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class DatabasePlayerCreate implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onDatabase(DatabaseEvent event) {
        Player player = event.getPlayer();
        IDatabase database = event.getDatabase();

        IPlayerTable iPlayerTable = new PlayerTable();

        if (!iPlayerTable.existsPlayer(player.getUniqueId())) {
            iPlayerTable.insertPlayer(player);
        }
    }
}
