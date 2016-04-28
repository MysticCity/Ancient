package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import org.bukkit.Location;

public class GetAmountOf
  extends IArgument
{
  @ArgumentDescription(description="Returns the amount of elements in the collection", parameterdescription={"collection"}, returntype=ParameterType.Number, rparams={ParameterType.Location})
  public GetAmountOf()
  {
    this.returnType = ParameterType.Number;
    this.requiredTypes = new ParameterType[] { ParameterType.Location };
    this.name = "getamountof";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if ((obj.length == 1) && ((obj[0] instanceof Location[])))
    {
      Location[] locs = (Location[])obj[0];
      int am = 0;
      for (Location loc : locs) {
        if (loc != null) {
          am++;
        }
      }
      return Integer.valueOf(am);
    }
    return Integer.valueOf(0);
  }
}
