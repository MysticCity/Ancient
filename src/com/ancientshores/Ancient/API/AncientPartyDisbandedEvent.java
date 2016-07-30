package com.ancientshores.Ancient.API;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.ancientshores.Ancient.Party.AncientParty;

public class AncientPartyDisbandedEvent extends Event implements Cancellable {
    final HandlerList hl = new HandlerList();
    boolean cancelled;
    private final AncientParty mParty;

    public AncientPartyDisbandedEvent(AncientParty mParty) {
        this.mParty = mParty;
    }

    @Override
    public HandlerList getHandlers() {
        return hl;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean arg0) {
        cancelled = arg0;
    }

    public AncientParty getParty() {
        return mParty;
    }
}