package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import org.bukkit.Location;
import org.bukkit.World;

public class GetDayTime
  extends IArgument
{
  @ArgumentDescription(description="Returns the time at the specified location", parameterdescription={"world"}, returntype=ParameterType.Number, rparams={ParameterType.Location})
  public GetDayTime()
  {
    this.returnType = ParameterType.Number;
    this.requiredTypes = new ParameterType[] { ParameterType.Location };
    this.name = "getdaytime";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if (!(obj[0] instanceof Location[])) {
      return Integer.valueOf(0);
    }
    Location l = ((Location[])(Location[])obj[0])[0];
    return Long.valueOf(l.getWorld().getTime());
  }
}
