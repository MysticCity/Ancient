package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import java.util.LinkedList;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class ChangeAggroCommand
  extends ICommand
{
  @CommandDescription(description="Changes the aggro of the entity to the new target for atleast the specified time", argnames={"entity", "newtarget", "duration"}, name="ChangeAggro", parameters={ParameterType.Entity, ParameterType.Entity, ParameterType.Number})
  public static final ConcurrentHashMap<UUID, UUID> tauntedEntities = new ConcurrentHashMap();
  
  public ChangeAggroCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Entity, ParameterType.Entity, ParameterType.Number };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    if ((ca.getParams().size() == 3) && 
      ((ca.getParams().get(0) instanceof Entity[])) && ((ca.getParams().get(1) instanceof Entity[])) && ((ca.getParams().get(2) instanceof Number)))
    {
      Entity[] target = (Entity[])ca.getParams().get(0);
      Entity[] newaggro = (Entity[])ca.getParams().get(1);
      if ((newaggro.length == 0) || (!(newaggro[0] instanceof LivingEntity))) {
        return false;
      }
      int time = (int)((Number)ca.getParams().get(2)).doubleValue();
      for (final Entity e : target) {
        if ((e instanceof Creature))
        {
          ((Creature)e).setTarget((LivingEntity)newaggro[0]);
          tauntedEntities.put(e.getUniqueId(), newaggro[0].getUniqueId());
          
          Ancient.plugin.scheduleThreadSafeTask(Ancient.plugin, new Runnable()
          {
            public void run()
            {
              ChangeAggroCommand.tauntedEntities.remove(e.getUniqueId());
            }
          }, Math.round(time / 50));
        }
      }
      return true;
    }
    return false;
  }
}
