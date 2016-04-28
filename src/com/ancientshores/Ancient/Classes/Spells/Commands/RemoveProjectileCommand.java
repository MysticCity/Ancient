package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileHitEvent;

public class RemoveProjectileCommand
  extends ICommand
{
  @CommandDescription(description="<html>Removes the projectile which hit something, can only be used in ProjectileHitEvent</html>", argnames={}, name="RemoveProjectile", parameters={})
  public RemoveProjectileCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Void };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    if ((ca.getSpellInfo().mEvent instanceof ProjectileHitEvent))
    {
      ((ProjectileHitEvent)ca.getSpellInfo().mEvent).getEntity().remove();
      return true;
    }
    return false;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\RemoveProjectileCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */