package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import java.util.LinkedList;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

public class SetVelocityCommand
  extends ICommand
{
  @CommandDescription(description="<html>Sets the velo of the entity</html>", argnames={"entity", "forward", "sideward", "upward"}, name="SetVelocity", parameters={ParameterType.Entity, ParameterType.Number, ParameterType.Number, ParameterType.Number})
  public SetVelocityCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Entity, ParameterType.Number, ParameterType.Number, ParameterType.Number };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    if ((ca.getParams().size() == 4) && 
      ((ca.getParams().get(0) instanceof Entity[])) && ((ca.getParams().get(1) instanceof Number)) && ((ca.getParams().get(2) instanceof Number)) && ((ca.getParams().get(3) instanceof Number)))
    {
      Entity[] target = (Entity[])ca.getParams().get(0);
      double forward = ((Number)ca.getParams().get(1)).doubleValue();
      double sideward = ((Number)ca.getParams().get(2)).doubleValue();
      double upward = ((Number)ca.getParams().get(3)).doubleValue();
      if ((target != null) && (target.length > 0))
      {
        for (Entity targetPlayer : target) {
          if ((targetPlayer != null) && ((targetPlayer instanceof LivingEntity)))
          {
            Vector v = targetPlayer.getLocation().getDirection();
            double z;
            double x;
            double z;
            if (Math.abs(v.getX()) > Math.abs(v.getZ()))
            {
              double x = forward * v.getX();
              z = sideward * v.getZ();
            }
            else
            {
              x = sideward * v.getX();
              z = forward * v.getZ();
            }
            targetPlayer.setVelocity(new Vector(x, upward, z));
          }
        }
        return true;
      }
    }
    return false;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\SetVelocityCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */