package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import org.apache.commons.lang.WordUtils;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileHitEvent;

public class GetProjectileName
  extends IArgument
{
  @ArgumentDescription(description="Returns the name of the projectile, only usable in projectilehitevent", parameterdescription={}, returntype=ParameterType.String, rparams={})
  public GetProjectileName()
  {
    this.returnType = ParameterType.String;
    this.requiredTypes = new ParameterType[0];
    this.name = "getprojectilename";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if ((so.mEvent instanceof ProjectileHitEvent))
    {
      ProjectileHitEvent pEvent = (ProjectileHitEvent)so.mEvent;
      return WordUtils.capitalizeFully(pEvent.getEntity().getType().toString().replaceAll("_", " ")).replaceAll(" ", "");
    }
    return null;
  }
}
