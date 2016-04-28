package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;

public class WaterBreathingCommand
  extends ICommand
  implements Runnable, Listener
{
  public static final ConcurrentHashMap<UUID, Integer> waterbreathingPlayers = new ConcurrentHashMap();
  
  @CommandDescription(description="<html>Enables water breathing for the specified amount of time</html>", argnames={"entity", "duration"}, name="WaterBreathing", parameters={ParameterType.Entity, ParameterType.Number})
  public WaterBreathingCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Entity, ParameterType.Number };
    try
    {
      Bukkit.getScheduler().scheduleSyncRepeatingTask(Ancient.plugin, this, 1L, 1L);
      Ancient.plugin.getServer().getPluginManager().registerEvents(this, Ancient.plugin);
    }
    catch (Exception ignored) {}
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    if ((ca.getParams().size() == 2) && ((ca.getParams().get(0) instanceof Entity[])) && ((ca.getParams().get(1) instanceof Number)))
    {
      Entity[] players = (Entity[])ca.getParams().get(0);
      int time = (int)((Number)ca.getParams().get(1)).doubleValue();
      for (Entity e : players) {
        if ((e != null) && ((e instanceof LivingEntity)))
        {
          int t = Math.round(time / 50);
          if (t == 0) {
            t = Integer.MAX_VALUE;
          }
          if ((waterbreathingPlayers.contains(e.getUniqueId())) && 
            (((Integer)waterbreathingPlayers.get(e.getUniqueId())).intValue() > time)) {
            return true;
          }
          waterbreathingPlayers.put(e.getUniqueId(), Integer.valueOf(t));
        }
      }
      return true;
    }
    return false;
  }
  
  @EventHandler(priority=EventPriority.LOWEST)
  public void onWaterDamage(EntityDamageEvent event)
  {
    if ((event.getCause().equals(EntityDamageEvent.DamageCause.DROWNING)) && (waterbreathingPlayers.containsKey(event.getEntity().getUniqueId()))) {
      event.setCancelled(true);
    }
  }
  
  public void run()
  {
    HashSet<UUID> removeplayers = new HashSet();
    for (UUID uuid : waterbreathingPlayers.keySet())
    {
      int newamount = ((Integer)waterbreathingPlayers.get(uuid)).intValue() - 1;
      waterbreathingPlayers.put(uuid, Integer.valueOf(newamount));
      if (newamount <= 0) {
        removeplayers.add(uuid);
      }
    }
    for (UUID uuid : removeplayers) {
      waterbreathingPlayers.remove(uuid);
    }
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\WaterBreathingCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */