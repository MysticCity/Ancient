package com.ancientshores.AncientRPG.API;

import java.util.UUID;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AncientGainExperienceEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    public final UUID uuid;
    public final int playeroldxp;
    public int gainedxp;
    public boolean cancelled;

    public AncientGainExperienceEvent(int playeroldxp, UUID uuid, int gainedxp) {
        this.playeroldxp = playeroldxp;
        this.gainedxp = gainedxp;
        this.uuid = uuid;
    }

    @Override
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