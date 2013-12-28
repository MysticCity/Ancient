package com.ancientshores.AncientRPG.API;

import com.ancientshores.AncientRPG.Party.AncientRPGParty;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AncientRPGPartyLeaveEvent extends Event implements Cancellable {
    final HandlerList hl = new HandlerList();
    boolean cancelled;
    private Player mPlayer;
    private AncientRPGParty mParty;

    public AncientRPGPartyLeaveEvent(Player mPlayer, AncientRPGParty mParty) {
        this.mParty = mParty;
        this.mPlayer = mPlayer;
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

    public Player getPlayer() {
        return mPlayer;
    }

    public AncientRPGParty getParty() {
        return mParty;
    }
}