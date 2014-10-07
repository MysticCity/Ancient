package com.ancientshores.AncientRPG.API;

import java.util.UUID;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.ancientshores.AncientRPG.Classes.AncientRPGClass;

public class AncientRPGClassChangeEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    boolean cancelled = false;
    public AncientRPGClass oldclass;
    public AncientRPGClass newclass;
    public UUID uuid;

    public AncientRPGClassChangeEvent(UUID uuid, AncientRPGClass old, AncientRPGClass newc) {
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