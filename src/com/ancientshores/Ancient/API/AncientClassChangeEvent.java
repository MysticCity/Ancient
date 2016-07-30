package com.ancientshores.Ancient.API;

import java.util.UUID;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.ancientshores.Ancient.Classes.AncientClass;

public class AncientClassChangeEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    boolean cancelled = false;
    public AncientClass oldclass;
    public AncientClass newclass;
    public UUID uuid;

    public AncientClassChangeEvent(UUID uuid, AncientClass old, AncientClass newc) {
        this.uuid = uuid;
        oldclass = old;
        newclass = newc;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean arg0) {
        cancelled = arg0;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}