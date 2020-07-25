package nl.tacticaldev.essentials.listeners.custom;

import nl.tacticaldev.essentials.player.EssentialsPlayer;
import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class UserSpawnEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private EssentialsPlayer user;
    private String spawn;
    private boolean cancelled = false;

    public UserSpawnEvent(EssentialsPlayer user, String spawn) {
        super(!Bukkit.getServer().isPrimaryThread());
        this.user = user;
        this.spawn = spawn;
    }

    public EssentialsPlayer getUser() {
        return user;
    }

    public String getSpawn() {
        return spawn;
    }

    public void setSpawn(String spawn) {
        this.spawn = spawn;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
