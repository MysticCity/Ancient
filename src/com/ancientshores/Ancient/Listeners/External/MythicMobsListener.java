package com.ancientshores.Ancient.Listeners.External;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.HP.CreatureHp;
import net.elseland.xikage.MythicMobs.API.Events.MythicMobSpawnEvent;
import net.elseland.xikage.MythicMobs.Mobs.MythicMob;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

public class MythicMobsListener
  implements Listener
{
  public MythicMobsListener(Ancient plugin)
  {
    plugin.getServer().getPluginManager().registerEvents(this, plugin);
  }
  
  @EventHandler(priority=EventPriority.MONITOR)
  public void spawnOfMythicMob(MythicMobSpawnEvent event)
  {
    if ((event.isCancelled()) || (!CreatureHp.isEnabledInWorld(event.getLocation().getWorld()))) {
      return;
    }
    double hp = event.getMobType().getHealth();
    if (event.getMobLevel() > 1) {
      hp += event.getMobType().lvlModHealth * (event.getMobLevel() - 1);
    }
    CreatureHp.getCreatureHpByEntity(event.getLivingEntity(), hp);
  }
}
