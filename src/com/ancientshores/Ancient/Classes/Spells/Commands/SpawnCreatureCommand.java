package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.HP.CreatureHp;
import java.util.LinkedList;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class SpawnCreatureCommand
  extends ICommand
{
  @CommandDescription(description="<html>Spawns creatures at the location with the given health and of the specified type</html>", argnames={"location", "creaturename", "health", "amount"}, name="SpawnCreature", parameters={ParameterType.Location, ParameterType.String, ParameterType.Number, ParameterType.Number})
  public SpawnCreatureCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Location, ParameterType.String, ParameterType.Number, ParameterType.Number };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    try
    {
      if (((ca.getParams().get(0) instanceof Location[])) && ((ca.getParams().get(1) instanceof String)) && ((ca.getParams().get(2) instanceof Number)) && ((ca.getParams().get(3) instanceof Number)))
      {
        final Location[] loca = (Location[])ca.getParams().get(0);
        String cName = (String)ca.getParams().get(1);
        final int health = (int)((Number)ca.getParams().get(2)).doubleValue();
        final int amount = (int)((Number)ca.getParams().get(3)).doubleValue();
        if ((loca != null) && (cName != null))
        {
          final EntityType e = SummonCommand.getCreatureTypeOfString(cName);
          if (e != null)
          {
            Ancient.plugin.scheduleThreadSafeTask(Ancient.plugin, new Runnable()
            {
              public void run()
              {
                for (Location loc : loca) {
                  if (loc != null) {
                    for (int i = 0; i < amount; i++)
                    {
                      Entity mob = loc.getWorld().spawnEntity(loc, e);
                      if (((mob instanceof Creature)) && (CreatureHp.isEnabledInWorld(mob.getWorld())))
                      {
                        ((Creature)mob).setMaxHealth(health);
                        ((Creature)mob).setHealth(health);
                      }
                    }
                  }
                }
              }
            });
            return true;
          }
        }
      }
    }
    catch (IndexOutOfBoundsException ignored) {}
    return false;
  }
}
