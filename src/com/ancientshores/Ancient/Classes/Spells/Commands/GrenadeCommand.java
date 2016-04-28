package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Listeners.AncientEntityListener;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

public class GrenadeCommand
  extends ICommand
{
  @CommandDescription(description="<html>The caster throws tnt which explodes after the time</html>", argnames={"time"}, name="Grenade", parameters={ParameterType.Number})
  public GrenadeCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Number };
  }
  
  public boolean playCommand(final EffectArgs ca)
  {
    if (ca.getParams().size() == 1)
    {
      if ((ca.getParams().get(0) instanceof Number))
      {
        int time = (int)((Number)ca.getParams().get(0)).doubleValue();
        Location spawnloc = ca.getCaster().getLocation().add(0.0D, 2.0D, 0.0D);
        final TNTPrimed grenade = (TNTPrimed)ca.getCaster().getWorld().spawn(spawnloc, TNTPrimed.class);
        Vector throwv = ca.getCaster().getLocation().getDirection().normalize();
        grenade.setVelocity(throwv);
        int ticks = Math.round(time / 50);
        if (ticks == 0) {
          ticks = 1;
        }
        grenade.setFuseTicks(ticks);
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Ancient.plugin, new Runnable()
        {
          public void run()
          {
            List<Entity> entityList = grenade.getLocation().getWorld().getEntities();
            for (final Entity e : entityList) {
              if (e.getLocation().distance(grenade.getLocation()) < 4.0D) {
                if (!ca.getCaster().equals(e))
                {
                  AncientEntityListener.scheduledXpList.put(e.getUniqueId(), ca.getCaster().getUniqueId());
                  Ancient.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Ancient.plugin, new Runnable()
                  {
                    public void run()
                    {
                      AncientEntityListener.scheduledXpList.remove(e.getUniqueId());
                    }
                  }, Math.round(2.0F));
                }
              }
            }
          }
        }, ticks);
      }
      return true;
    }
    return false;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\GrenadeCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */