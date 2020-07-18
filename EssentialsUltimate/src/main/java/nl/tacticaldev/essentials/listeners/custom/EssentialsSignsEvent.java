package nl.tacticaldev.essentials.listeners.custom;
// Plugin made by TacticalDev
// Do not copy this plugin, and use it in you're own plugins.
// This plugin belong to Joran (TacticalDev) Discord: Joran#7925

import nl.tacticaldev.essentials.player.EssentialsPlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockEvent;

import javax.annotation.Nullable;

public class EssentialsSignsEvent extends BlockEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancel = false;
    private final String[] lines;

    private EssentialsPlayer essentialsPlayer;

    public EssentialsSignsEvent(final Block theBlock, Player thePlayer, final String[] theLines) {
        super(theBlock);
        this.essentialsPlayer = new EssentialsPlayer(thePlayer);
        this.lines = theLines;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public EssentialsPlayer getPlayer() {
        return essentialsPlayer;
    }

    public String[] getLines() {
        return lines;
    }

    /**
     * Gets a single line of text from the sign involved in this event.
     *
     * @param index index of the line to get
     * @return the String containing the line of text associated with the
     * provided index
     * @throws IndexOutOfBoundsException thrown when the provided index is {@literal > 3
     *                                   or < 0}
     */
    @Nullable
    public String getLine(int index) throws IndexOutOfBoundsException {
        return lines[index];
    }

    /**
     * Sets a single line for the sign involved in this event
     *
     * @param index index of the line to set
     * @param line  text to set
     * @throws IndexOutOfBoundsException thrown when the provided index is {@literal > 3
     *                                   or < 0}
     */
    public void setLine(int index, @Nullable String line) throws IndexOutOfBoundsException {
        lines[index] = line;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }


    /**
     * Gets the cancellation state of this event. A cancelled event will not
     * be executed in the server, but will still pass to other plugins
     *
     * @return true if this event is cancelled
     */
    @Override
    public boolean isCancelled() {
        return cancel;
    }

    /**
     * Sets the cancellation state of this event. A cancelled event will not
     * be executed in the server, but will still pass to other plugins.
     *
     * @param cancel true if you wish to cancel this event
     */
    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
}
