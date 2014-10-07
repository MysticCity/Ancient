package com.ancientshores.AncientRPG.API;

import java.util.UUID;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.ancientshores.AncientRPG.Party.AncientRPGParty;

public class AncientRPGPartyLeaveEvent extends Event implements Cancellable {
    final HandlerList hl = new HandlerList();
    boolean cancelled;
    private UUID uuid;
    private AncientRPGParty mParty;

    public AncientRPGPartyLeaveEvent(UUID uuidPlayerLeaving, AncientRPGParty mParty) {
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

    public AncientRPGParty getParty() {
        return mParty;
    }
}