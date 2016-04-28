package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

public class ThrowSmallFireballCommand
  extends ICommand
{
  @CommandDescription(description="The caster throws a asmall fireball", argnames={}, name="ThrowSmallFireball", parameters={})
  public ThrowSmallFireballCommand()
  {
    this.paramTypes = new ParameterType[0];
  }
  
  public boolean playCommand(final EffectArgs ca)
  {
    Ancient.plugin.scheduleThreadSafeTask(Ancient.plugin, new Runnable()
    {
      public void run()
      {
        Location loc = ca.getCaster().getLocation().add(ca.getCaster().getLocation().getDirection().normalize().multiply(2));
        final SmallFireball f = (SmallFireball)ca.getCaster().getWorld().spawn(loc.add(new Location(ca.getCaster().getWorld(), 0.0D, 1.0D, 0.0D)), SmallFireball.class);
        f.setShooter(ca.getCaster());
        f.setVelocity(ca.getCaster().getLocation().getDirection().normalize().multiply(3));
        Bukkit.getScheduler().scheduleSyncDelayedTask(Ancient.plugin, new Runnable()
        {
          public void run()
          {
            f.remove();
          }
        }, 250L);
      }
    });
    return true;
  }
}
