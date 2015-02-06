package com.ancientshores.AncientRPG.Listeners.External;

import net.elseland.xikage.MythicMobs.API.Events.MythicMobSpawnEvent;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.HP.CreatureHp;

public class MythicMobsListener implements Listener {

	public MythicMobsListener(AncientRPG plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	@EventHandler(priority = EventPriority.MONITOR)
	public void spawnOfMythicMob(MythicMobSpawnEvent event) {
		// Only apply AncientRPG health if event isn't cancelled and CreatureHp of ARPG are enabled in the world where mob is spawning
		if (event.isCancelled() || !CreatureHp.isEnabledInWorld(event.getLocation().getWorld())) return;
		
		// Calculate how many hp the mob will have when spawned
		double hp = event.getMobType().getHealth();
		if (event.getMobLevel() > 1)
			hp += event.getMobType().lvlModHealth * (event.getMobLevel() - 1);
		
		// Create a new CreatureHp for that mob
		CreatureHp.getCreatureHpByEntity(event.getLivingEntity(), hp);
	}
}
