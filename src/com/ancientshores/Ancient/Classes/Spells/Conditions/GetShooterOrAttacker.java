package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import com.ancientshores.Ancient.Util.GlobalMethods;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class GetShooterOrAttacker
  extends IArgument
{
  @ArgumentDescription(description="Returns the damager, for example if a player hits you with an arrow the player is returned.", parameterdescription={}, returntype=ParameterType.String, rparams={})
  public GetShooterOrAttacker()
  {
    this.returnType = ParameterType.String;
    this.requiredTypes = new ParameterType[0];
    this.name = "getshooterorattacker";
  }
  
  public Object getArgument(Object[] mPlayer, SpellInformationObject so)
  {
    if ((so.mEvent instanceof EntityDamageByEntityEvent))
    {
      EntityDamageByEntityEvent damageEvent = (EntityDamageByEntityEvent)so.mEvent;
      if ((damageEvent.getDamager() instanceof Projectile)) {
        return GlobalMethods.getStringByEntity((Entity)((Projectile)damageEvent.getDamager()).getShooter());
      }
      return GlobalMethods.getStringByEntity(damageEvent.getDamager());
    }
    return "";
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Conditions\GetShooterOrAttacker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */