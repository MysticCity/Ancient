package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import org.bukkit.Location;

public class GetYCoordinate
  extends IArgument
{
  @ArgumentDescription(description="Returns the y coordinate of a location", parameterdescription={"location"}, returntype=ParameterType.Number, rparams={ParameterType.Location})
  public GetYCoordinate()
  {
    this.returnType = ParameterType.Number;
    this.requiredTypes = new ParameterType[] { ParameterType.Location };
    this.name = "getycoordinate";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if ((obj.length == 1) && ((obj[0] instanceof Location[]))) {
      return Double.valueOf(((Location[])(Location[])obj[0])[0].getY());
    }
    return null;
  }
}
