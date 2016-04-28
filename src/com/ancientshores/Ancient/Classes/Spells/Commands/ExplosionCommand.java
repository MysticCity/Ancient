package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.Spell;
import com.ancientshores.Ancient.Listeners.AncientEntityListener;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;

public class ExplosionCommand
  extends ICommand
{
  @CommandDescription(description="<html>Creates an explosion with the specified strength at the location</html>", argnames={"location", "radius"}, name="Explosion", parameters={ParameterType.Location, ParameterType.Number})
  public ExplosionCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Location, ParameterType.Number };
  }
  
  public boolean playCommand(final EffectArgs ca)
  {
    try
    {
      if ((ca.getParams().get(0) instanceof Location[]))
      {
        final Location[] loc = (Location[])ca.getParams().get(0);
        int yield = 4;
        if ((ca.getParams().size() == 2) && ((ca.getParams().get(1) instanceof Number))) {
          yield = (int)((Number)ca.getParams().get(1)).doubleValue();
        }
        final int lol = yield;
        if (loc != null)
        {
          Ancient.plugin.scheduleThreadSafeTask(Ancient.plugin, new Runnable()
          {
            public void run()
            {
              Location l;
              for (l : loc) {
                if (l != null)
                {
                  List<Entity> entityList = l.getWorld().getEntities();
                  TNTPrimed tnt = (TNTPrimed)ca.getCaster().getWorld().spawn(l, TNTPrimed.class);
                  tnt.setYield(lol);
                  tnt.setFuseTicks(0);
                  for (final Entity e : entityList) {
                    if (e.getLocation().distance(l) < 4.0D) {
                      if (!ca.getCaster().equals(e))
                      {
                        AncientEntityListener.scheduledXpList.put(e.getUniqueId(), ca.getCaster().getUniqueId());
                        Ancient.plugin.scheduleThreadSafeTask(Ancient.plugin, new Runnable()
                        {
                          public void run()
                          {
                            AncientEntityListener.scheduledXpList.remove(e.getUniqueId());
                          }
                        }, Math.round(20.0F));
                      }
                    }
                  }
                }
              }
            }
          });
          return true;
        }
      }
      else if (ca.getSpell().active)
      {
        ca.getCaster().sendMessage("No target in range");
      }
    }
    catch (IndexOutOfBoundsException ignored) {}
    return false;
  }
}
