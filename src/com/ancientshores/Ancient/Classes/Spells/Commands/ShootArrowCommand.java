package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import java.util.LinkedList;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

public class ShootArrowCommand
  extends ICommand
{
  @CommandDescription(description="<html>The entity shoots an arrow with the specified velocity</html>", argnames={"entity", "velocity"}, name="ShootArrow", parameters={ParameterType.Entity, ParameterType.Number})
  public ShootArrowCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Entity, ParameterType.Number };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    try
    {
      if ((ca.getParams().get(0) instanceof Entity[]))
      {
        Entity[] target = (Entity[])ca.getParams().get(0);
        double velo = 2.0D;
        if ((ca.getParams().size() > 1) && ((ca.getParams().get(1) instanceof Number))) {
          velo = ((Number)ca.getParams().get(1)).doubleValue();
        }
        if ((target != null) && (target.length > 0))
        {
          for (Entity targetPlayer : target) {
            if ((targetPlayer != null) && ((targetPlayer instanceof LivingEntity)))
            {
              Arrow a = (Arrow)((LivingEntity)targetPlayer).launchProjectile(Arrow.class);
              a.setVelocity(a.getVelocity().multiply(velo));
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


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\ShootArrowCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */