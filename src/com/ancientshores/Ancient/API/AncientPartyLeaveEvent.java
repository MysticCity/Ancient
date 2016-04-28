package com.ancientshores.Ancient.API;

import java.util.UUID;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.ancientshores.Ancient.Party.AncientParty;

public class AncientPartyLeaveEvent extends Event implements Cancellable {
    final HandlerList hl = new HandlerList();
    boolean cancelled;
    private UUID uuid;
    private AncientParty mParty;

    public AncientPartyLeaveEvent(UUID uuidPlayerLeaving, AncientParty mParty) {
        this.mParty = mParty;
        this.uuid = uuidPlayerLeaving;
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

    public UUID getUUID() {
        return uuid;
    }

    public AncientParty getParty() {
        return mParty;
    }
}