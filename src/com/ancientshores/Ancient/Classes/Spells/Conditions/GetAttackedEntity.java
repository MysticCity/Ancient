package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import com.ancientshores.Ancient.Util.GlobalMethods;
import org.bukkit.event.entity.EntityDamageEvent;

public class GetAttackedEntity
  extends IArgument
{
  @ArgumentDescription(description="Returns the type of the attacked entity", parameterdescription={}, returntype=ParameterType.Entity, rparams={})
  public GetAttackedEntity()
  {
    this.returnType = ParameterType.Entity;
    this.requiredTypes = new ParameterType[0];
    this.name = "getattackedentity";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if ((so.mEvent instanceof EntityDamageEvent)) {
      return GlobalMethods.getStringByEntity(((EntityDamageEvent)so.mEvent).getEntity());
    }
    return "";
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Conditions\GetAttackedEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */