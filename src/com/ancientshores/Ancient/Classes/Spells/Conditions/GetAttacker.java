package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import com.ancientshores.Ancient.Util.GlobalMethods;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class GetAttacker
  extends IArgument
{
  @ArgumentDescription(description="Returns the type of the attacker", parameterdescription={}, returntype=ParameterType.String, rparams={})
  public GetAttacker()
  {
    this.returnType = ParameterType.String;
    this.requiredTypes = new ParameterType[0];
    this.name = "getattacker";
  }
  
  public Object getArgument(Object[] mPlayer, SpellInformationObject so)
  {
    if ((so.mEvent instanceof EntityDamageByEntityEvent)) {
      return GlobalMethods.getStringByEntity(((EntityDamageByEntityEvent)so.mEvent).getDamager());
    }
    return "";
  }
}
