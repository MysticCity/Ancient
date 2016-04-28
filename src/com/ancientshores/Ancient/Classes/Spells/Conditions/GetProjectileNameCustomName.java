package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import com.ancientshores.Ancient.Listeners.AncientPlayerListener;
import java.util.HashMap;
import org.apache.commons.lang.WordUtils;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileHitEvent;

public class GetProjectileNameCustomName
  extends IArgument
{
  @ArgumentDescription(description="Returns the name of the projectile with the custom set name, only usable in projectilehitevent", parameterdescription={}, returntype=ParameterType.String, rparams={})
  public GetProjectileNameCustomName()
  {
    this.returnType = ParameterType.String;
    this.requiredTypes = new ParameterType[0];
    this.name = "getprojectilename:customname";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if ((so.mEvent instanceof ProjectileHitEvent))
    {
      ProjectileHitEvent pEvent = (ProjectileHitEvent)so.mEvent;
      String name = WordUtils.capitalizeFully(pEvent.getEntity().getType().toString().replaceAll("_", " ")).replaceAll(" ", "");
      if (AncientPlayerListener.thrownProjectiles.containsKey(pEvent.getEntity())) {
        name = name + ":" + (String)AncientPlayerListener.thrownProjectiles.get(pEvent.getEntity());
      }
      return name;
    }
    return null;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Conditions\GetProjectileNameCustomName.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */