package com.ancientshores.Ancient.Listeners.External;

import net.elseland.xikage.MythicMobs.API.Bukkit.Events.MythicMobSpawnEvent;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.HP.CreatureHp;

public class MythicMobsListener implements Listener {

	public MythicMobsListener(Ancient plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	@EventHandler(priority = EventPriority.MONITOR)
	public void spawnOfMythicMob(MythicMobSpawnEvent event) {
		// Only apply Ancient health if event isn't cancelled and CreatureHp of Ancient are enabled in the world where mob is spawning
		if (event.isCancelled() || !CreatureHp.isEnabledInWorld(event.getLocation().getWorld())) return;
		
		// Calculate how many hp the mob will have when spawned
		double hp = event.getMobType().getHealth();
		if (event.getMobLevel() > 1)
			hp += event.getMobType().getHealth() * (event.getMobLevel() - 1);
		
		// Create a new CreatureHp for that mob
		CreatureHp.getCreatureHpByEntity(event.getLivingEntity(), hp);
	}
}
