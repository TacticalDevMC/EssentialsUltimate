package nl.tacticaldev.essentials.listeners.custom;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import essentialsapi.interfaces.IDatabase;
import nl.tacticaldev.essentials.database.EssentialsDatabase;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DatabaseEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private IDatabase database;

    public DatabaseEvent(Player player) {
        this.player = player;
        this.database = EssentialsDatabase.getInstance();
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public Player getPlayer() {
        return player;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public IDatabase getDatabase() {
        return database;
    }
}
