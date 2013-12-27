package com.ancientshores.AncientRPG.Listeners.SpellListener;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.AncientRPGClass;
import com.ancientshores.AncientRPG.Classes.Spells.Commands.CommandPlayer;
import com.ancientshores.AncientRPG.Classes.Spells.Spell;
import com.ancientshores.AncientRPG.Listeners.AncientRPGSpellListener;
import com.ancientshores.AncientRPG.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerDropItemEvent;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerDropItemEventListener extends ISpellListener
{
	public PlayerDropItemEventListener(AncientRPG instance)
	{
		super(instance);
		this.eventName = "playerdropitemevent";
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEvent(final PlayerDropItemEvent event)
	{
		if ((event instanceof Cancellable) && event.isCancelled())
			return;
		if (AncientRPGSpellListener.ignoredEvents.contains(event))
			return;
		else
		{
			AncientRPGSpellListener.ignoredEvents.add(event);
			Bukkit.getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, new Runnable()
			{

				@Override
				public void run()
				{
					AncientRPGSpellListener.ignoredEvents.remove(event);
				}
			}, 20);
		}
		Player mPlayer = event.getPlayer();
		PlayerData pd = PlayerData.getPlayerData(mPlayer.getName());
		HashMap<Spell, Player[]> spells = new HashMap<Spell, Player[]>();
		for (Spell p : eventSpells)
		{
			if (AncientRPGClass.spellAvailable(p, pd))
				spells.put(p, new Player[] { mPlayer, mPlayer });
		}
		for (Entry<Spell, ConcurrentHashMap<Player[], Integer>> e : eventBuffs.entrySet())
		{
			for (Player p[] : e.getValue().keySet())
			{
				if (p[0].equals(event.getPlayer()))
				{
					spells.put(e.getKey(), p);
				}
			}
		}
		LinkedList<Entry<Spell, Player[]>> sortedspells = getSortedList(spells);
		for (Entry<Spell, Player[]> sortedspell : sortedspells)
		{
			CommandPlayer.scheduleSpell(sortedspell.getKey(), sortedspell.getValue()[0], event,
					sortedspell.getValue()[1]);
		}
	}
}
