package nl.tacticaldev.essentials.listeners.custom;

import nl.tacticaldev.essentials.player.EssentialsPlayer;
import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class UserWarpEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private EssentialsPlayer user;
    private String warp;
    private boolean cancelled = false;

    public UserWarpEvent(EssentialsPlayer user, String warp) {
        super(!Bukkit.getServer().isPrimaryThread());
        this.user = user;
        this.warp = warp;
    }

    public EssentialsPlayer getUser() {
        return user;
    }

    public String getWarp() {
        return warp;
    }

    public void setWarp(String warp) {
        this.warp = warp;
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
