package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;

public class GetTimeMillis
  extends IArgument
{
  @ArgumentDescription(description="Returns the system time in milliseconds", parameterdescription={}, returntype=ParameterType.Number, rparams={})
  public GetTimeMillis()
  {
    this.returnType = ParameterType.Number;
    this.requiredTypes = new ParameterType[0];
    this.name = "gettimemillis";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    return Double.valueOf(System.currentTimeMillis());
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Conditions\GetTimeMillis.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */