package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.HP.CreatureHp;
import com.ancientshores.Ancient.Listeners.AncientEntityListener;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class DamageCommand
  extends ICommand
{
  @CommandDescription(description="<html>Damages the targeted entity with the specified damage</html>", argnames={"entity", "damage"}, name="Damage", parameters={ParameterType.Entity, ParameterType.Number})
  public DamageCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Entity, ParameterType.Number };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    try
    {
      if (((ca.getParams().get(0) instanceof Entity[])) && ((ca.getParams().get(1) instanceof Number)))
      {
        Entity[] targets = (Entity[])ca.getParams().get(0);
        double damage = ((Number)ca.getParams().get(1)).floatValue();
        if ((targets != null) && (targets.length > 0) && ((targets[0] instanceof Entity)))
        {
          for (final Entity target : targets) {
            if ((target != null) && ((target instanceof LivingEntity)))
            {
              CreatureHp.getCreatureHpByEntity((LivingEntity)target);
              
              com.ancientshores.Ancient.Listeners.AncientPlayerListener.damageignored = true;
              AncientEntityListener.ignoreNextHpEvent = true;
              ((LivingEntity)target).damage(damage, ca.getCaster());
              com.ancientshores.Ancient.Listeners.AncientPlayerListener.damageignored = false;
              AncientEntityListener.ignoreNextHpEvent = false;
              
              AncientEntityListener.scheduledXpList.put(target.getUniqueId(), ca.getCaster().getUniqueId());
              Ancient.plugin.scheduleThreadSafeTask(Ancient.plugin, new Runnable()
              {
                public void run()
                {
                  AncientEntityListener.scheduledXpList.remove(target.getUniqueId());
                }
              }, Math.round(20.0F));
            }
          }
          return true;
        }
      }
    }
    catch (IndexOutOfBoundsException ignored) {}catch (Exception ex)
    {
      ex.printStackTrace();
    }
    return false;
  }
}
