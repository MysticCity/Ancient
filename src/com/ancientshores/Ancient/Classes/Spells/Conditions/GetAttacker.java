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


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Conditions\GetAttacker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */