package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Listeners.AncientEntityListener;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class DirectDamageCommand
  extends ICommand
{
  public DirectDamageCommand()
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
        float damage = ((Number)ca.getParams().get(1)).floatValue();
        if ((target != null) && (target.length > 0) && ((target[0] instanceof Entity)))
        {
          for (final Entity targetPlayer : target) {
            if ((targetPlayer != null) && ((targetPlayer instanceof LivingEntity)))
            {
              com.ancientshores.Ancient.Listeners.AncientPlayerListener.damageignored = true;
              AncientEntityListener.ignoreNextHpEvent = true;
              
              ((LivingEntity)targetPlayer).damage(Math.round(damage), ca.getCaster());
              
              com.ancientshores.Ancient.Listeners.AncientPlayerListener.damageignored = false;
              AncientEntityListener.ignoreNextHpEvent = false;
              
              AncientEntityListener.scheduledXpList.put(targetPlayer.getUniqueId(), ca.getCaster().getUniqueId());
              Ancient.plugin.scheduleThreadSafeTask(Ancient.plugin, new Runnable()
              {
                public void run()
                {
                  AncientEntityListener.scheduledXpList.remove(targetPlayer.getUniqueId());
                }
              }, Math.round(20.0F));
            }
          }
          return true;
        }
      }
    }
    catch (Exception ignored) {}
    return false;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\DirectDamageCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */