package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.Spell;
import com.ancientshores.Ancient.Listeners.AncientEntityListener;
import java.util.Collection;
import java.util.LinkedList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class StunCommand
  extends ICommand
{
  @CommandDescription(description="<html>Stuns the player for the specified amount of time</html>", argnames={"entity", "duration"}, name="Stun", parameters={ParameterType.Entity, ParameterType.Number})
  public StunCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Entity, ParameterType.Number };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    try
    {
      if (((ca.getParams().get(0) instanceof Entity[])) && ((ca.getParams().get(1) instanceof Number)))
      {
        Entity[] target = (Entity[])ca.getParams().get(0);
        int time = (int)((Number)ca.getParams().get(1)).doubleValue();
        if ((target != null) && (target.length > 0) && ((target[0] instanceof Entity)))
        {
          for (final Entity e : target) {
            if ((e != null) && ((e instanceof LivingEntity)))
            {
              AncientEntityListener.stun(e);
              final Location l = e.getLocation();
              final int id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Ancient.plugin, new Runnable()
              {
                public void run()
                {
                  if (!AncientEntityListener.StunList.contains(e.getUniqueId())) {
                    return;
                  }
                  e.teleport(l);
                }
              }, 1L, 1L);
              
              Bukkit.getScheduler().scheduleSyncDelayedTask(Ancient.plugin, new Runnable()
              {
                public void run()
                {
                  Bukkit.getScheduler().cancelTask(id);
                  AncientEntityListener.unstun(e);
                }
              }, Math.round(time / 50));
            }
          }
          return true;
        }
        if (ca.getSpell().active) {
          ca.getCaster().sendMessage("Target not found");
        }
      }
    }
    catch (IndexOutOfBoundsException ignored) {}
    return false;
  }
}
