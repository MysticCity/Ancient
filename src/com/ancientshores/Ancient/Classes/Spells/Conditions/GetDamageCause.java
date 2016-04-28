package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class GetDamageCause
  extends IArgument
{
  @ArgumentDescription(description="Returns the name of the damage cause", parameterdescription={}, returntype=ParameterType.String, rparams={})
  public GetDamageCause()
  {
    this.returnType = ParameterType.String;
    this.requiredTypes = new ParameterType[0];
    this.name = "getdamagecause";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if ((so.mEvent instanceof EntityDamageEvent))
    {
      EntityDamageEvent event = (EntityDamageEvent)so.mEvent;
      return event.getCause().toString().toLowerCase();
    }
    return null;
  }
}
