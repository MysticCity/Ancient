package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import java.util.LinkedList;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.WitherSkull;

public class ShootWitherSkull
  extends ICommand
{
  @CommandDescription(description="<html>The entity shoots a witherskull</html>", argnames={"shooter"}, name="ShootWitherSkull", parameters={ParameterType.Entity})
  public ShootWitherSkull()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Entity };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    try
    {
      if ((ca.getParams().get(0) instanceof Entity[]))
      {
        Entity[] target = (Entity[])ca.getParams().get(0);
        if ((target != null) && (target.length > 0))
        {
          for (Entity targetPlayer : target) {
            if ((targetPlayer != null) && ((targetPlayer instanceof LivingEntity)))
            {
              WitherSkull a = (WitherSkull)((LivingEntity)targetPlayer).launchProjectile(WitherSkull.class);
              a.setShooter((LivingEntity)targetPlayer);
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
