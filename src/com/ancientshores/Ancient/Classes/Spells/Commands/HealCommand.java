package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.HP.CreatureHp;
import com.ancientshores.Ancient.HP.DamageConverter;
import java.util.LinkedList;
import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.plugin.PluginManager;

public class HealCommand
  extends ICommand
{
  @CommandDescription(description="<html>Heals the target the specified amount of health</html>", argnames={"entity", "amount"}, name="Heal", parameters={ParameterType.Entity, ParameterType.Number})
  public HealCommand()
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
        double heal = ((Number)ca.getParams().get(1)).doubleValue();
        if ((target != null) && (target.length > 0))
        {
          for (Entity targetPlayer : target) {
            if ((targetPlayer != null) && ((targetPlayer instanceof LivingEntity))) {
              if (((targetPlayer instanceof Player)) && (DamageConverter.isEnabledInWorld(ca.getCaster().getWorld())))
              {
                Player p = (Player)targetPlayer;
                EntityRegainHealthEvent e = new EntityRegainHealthEvent(targetPlayer, heal, EntityRegainHealthEvent.RegainReason.CUSTOM);
                Bukkit.getPluginManager().callEvent(e);
                if (!e.isCancelled()) {
                  if (p.getHealth() + e.getAmount() > p.getMaxHealth()) {
                    p.setHealth(p.getMaxHealth());
                  } else if (p.getHealth() + e.getAmount() > p.getMaxHealth()) {
                    p.setHealth(0.0D);
                  } else {
                    p.setHealth(p.getHealth() + e.getAmount());
                  }
                }
              }
              else if (((targetPlayer instanceof Creature)) && (CreatureHp.isEnabledInWorld(targetPlayer.getWorld())))
              {
                EntityRegainHealthEvent e = new EntityRegainHealthEvent(targetPlayer, heal, EntityRegainHealthEvent.RegainReason.CUSTOM);
                Bukkit.getPluginManager().callEvent(e);
                if (!e.isCancelled())
                {
                  double newhealth = ((Creature)targetPlayer).getHealth() + heal;
                  if (newhealth > ((Creature)targetPlayer).getMaxHealth()) {
                    newhealth = ((Creature)targetPlayer).getMaxHealth();
                  }
                  ((Creature)targetPlayer).setHealth(newhealth);
                }
              }
              else
              {
                EntityRegainHealthEvent e = new EntityRegainHealthEvent(targetPlayer, heal, EntityRegainHealthEvent.RegainReason.CUSTOM);
                Bukkit.getPluginManager().callEvent(e);
                if (!e.isCancelled())
                {
                  double health = ((LivingEntity)targetPlayer).getHealth() + e.getAmount();
                  if (health < 0.0D)
                  {
                    health = 0.0D;
                    ((LivingEntity)targetPlayer).setHealth(0.0D);
                    targetPlayer.playEffect(EntityEffect.DEATH);
                  }
                  if (health > ((LivingEntity)targetPlayer).getMaxHealth()) {
                    health = ((LivingEntity)targetPlayer).getMaxHealth();
                  }
                  ((LivingEntity)targetPlayer).setHealth(health);
                }
              }
            }
          }
          return true;
        }
      }
    }
    catch (IndexOutOfBoundsException ignored) {}
    return false;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\HealCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */