package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import java.util.LinkedList;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class ThrowEnderpearlCommand
  extends ICommand
{
  @CommandDescription(description="<html>The shooter throws an enderpearl in the direction he is looking at<br> Parameter 1: the player who throws the egg</html>", argnames={"entity"}, name="ThrowEnderpearl", parameters={ParameterType.Entity})
  public ThrowEnderpearlCommand()
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
              EnderPearl e = (EnderPearl)((LivingEntity)targetPlayer).launchProjectile(EnderPearl.class);
              e.setShooter((LivingEntity)targetPlayer);
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


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\ThrowEnderpearlCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */