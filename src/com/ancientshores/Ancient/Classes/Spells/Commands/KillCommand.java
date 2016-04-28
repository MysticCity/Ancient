package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Listeners.AncientEntityListener;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.plugin.PluginManager;

public class KillCommand
  extends ICommand
{
  @CommandDescription(description="<html>Kills the target</html>", argnames={"entity"}, name="Kill", parameters={ParameterType.Entity})
  public KillCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Entity };
  }
  
  public boolean playCommand(final EffectArgs ca)
  {
    if ((ca.getParams().size() == 1) && 
      ((ca.getParams().get(0) instanceof Entity[])))
    {
      final Entity[] entities = (Entity[])ca.getParams().get(0);
      Ancient.plugin.scheduleThreadSafeTask(Ancient.plugin, new Runnable()
      {
        public void run()
        {
          for (Entity e : entities) {
            if ((e != null) && ((e instanceof LivingEntity)))
            {
              LivingEntity ent = (LivingEntity)e;
              AncientEntityListener.scheduledXpList.put(e.getUniqueId(), ca.getCaster().getUniqueId());
              EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(ca.getCaster(), ent, EntityDamageEvent.DamageCause.CUSTOM, Double.MAX_VALUE);
              Bukkit.getServer().getPluginManager().callEvent(event);
              AncientEntityListener.scheduledXpList.remove(e.getUniqueId());
              if ((event.isCancelled()) || (event.getDamage() == 0.0D)) {
                return;
              }
              ent.setHealth(0.0D);
            }
          }
        }
      });
    }
    return false;
  }
}
