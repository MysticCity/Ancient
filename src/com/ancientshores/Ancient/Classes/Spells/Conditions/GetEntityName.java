package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import com.ancientshores.Ancient.Util.GlobalMethods;
import org.bukkit.entity.Entity;

public class GetEntityName
  extends IArgument
{
  @ArgumentDescription(description="Returns the name of the entity", parameterdescription={"entity"}, returntype=ParameterType.String, rparams={ParameterType.Entity})
  public GetEntityName()
  {
    this.returnType = ParameterType.String;
    this.requiredTypes = new ParameterType[] { ParameterType.Entity };
    this.name = "getentityname";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if ((obj.length == 1) && ((obj[0] instanceof Entity[]))) {
      return GlobalMethods.getStringByEntity(((Entity[])(Entity[])obj[0])[0]);
    }
    return "";
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Conditions\GetEntityName.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */