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

public class LightningCommand
  extends ICommand
{
  @CommandDescription(description="<html>Lightning will strike at the location</html>", argnames={"location"}, name="Lightning", parameters={ParameterType.Location})
  public LightningCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Location };
  }
  
  public boolean playCommand(final EffectArgs ca)
  {
    try
    {
      if ((ca.getParams().get(0) != null) && ((ca.getParams().get(0) instanceof Location[])) && (((Location[])ca.getParams().get(0)).length > 0) && (ca.getParams().get(0) != null))
      {
        final Location[] loc = (Location[])ca.getParams().getFirst();
        Ancient.plugin.scheduleThreadSafeTask(Ancient.plugin, new Runnable()
        {
          public void run()
          {
            Location l;
            for (l : loc) {
              if (l != null)
              {
                List<Entity> entityList = l.getWorld().getEntities();
                ca.getCaster().getWorld().strikeLightning(l);
                for (final Entity e : entityList) {
                  if (e.getLocation().distance(l) < 3.0D) {
                    if (!ca.getCaster().equals(e))
                    {
                      AncientEntityListener.scheduledXpList.put(e.getUniqueId(), ca.getCaster().getUniqueId());
                      Ancient.plugin.scheduleThreadSafeTask(Ancient.plugin, new Runnable()
                      {
                        public void run()
                        {
                          AncientEntityListener.scheduledXpList.remove(e.getUniqueId());
                        }
                      }, 20);
                    }
                  }
                }
              }
            }
          }
        });
        return true;
      }
      if (ca.getSpell().active) {
        ca.getCaster().sendMessage("No target in range");
      }
    }
    catch (IndexOutOfBoundsException ignored) {}
    return false;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\LightningCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */