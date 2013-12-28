package com.ancientshores.AncientRPG.API;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AncientLevelupEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    public int newlevel;
    public final Player p;
    public int xp;
    public int addedxp;
    public boolean cancelled;

    public AncientLevelupEvent(int newlevel, Player p, int xp, int addedxp) {
        this.newlevel = newlevel;
        this.p = p;
        this.xp = xp;
        this.addedxp = addedxp;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean arg0) {
        cancelled = arg0;
    }
}