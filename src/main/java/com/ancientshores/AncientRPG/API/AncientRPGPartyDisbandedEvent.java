package com.ancientshores.AncientRPG.API;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.ancientshores.AncientRPG.Party.AncientRPGParty;

public class AncientRPGPartyDisbandedEvent extends Event implements Cancellable
{
	final HandlerList hl = new HandlerList();
	boolean cancelled;
	private final AncientRPGParty mParty;

	public AncientRPGPartyDisbandedEvent(AncientRPGParty mParty)
	{
		this.mParty = mParty;
	}

	@Override
	public HandlerList getHandlers()
	{
		return hl;
	}

	@Override
	public boolean isCancelled()
	{
		return cancelled;
	}

	@Override
	public void setCancelled(boolean arg0)
	{
		cancelled = arg0;
	}

	public AncientRPGParty getParty()
	{
		return mParty;
	}
}
