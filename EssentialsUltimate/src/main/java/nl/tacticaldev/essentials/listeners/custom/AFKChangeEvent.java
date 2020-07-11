package nl.tacticaldev.essentials.listeners.custom;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import nl.tacticaldev.essentials.player.EssentialsPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AFKChangeEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private EssentialsPlayer essentialsPlayer;

    public AFKChangeEvent(Player player) {
        this.essentialsPlayer = new EssentialsPlayer(player);
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public EssentialsPlayer getEssentialsPlayer() {
        return essentialsPlayer;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    private static Cause cause;

    public static void setCause(Cause cause) {
        AFKChangeEvent.cause = cause;
    }

    public static Cause getCause() {
        return cause;
    }

    public enum Cause {
        ACTIVITY,
        MOVE,
        INTERACT,
        COMMAND,
        JOIN,
        QUIT,
        UNKNOWN
    }
}
