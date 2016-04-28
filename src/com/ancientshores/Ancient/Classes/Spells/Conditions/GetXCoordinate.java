package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import org.bukkit.Location;

public class GetXCoordinate
  extends IArgument
{
  @ArgumentDescription(description="Returns the x coordinate of a location", parameterdescription={"location"}, returntype=ParameterType.Number, rparams={ParameterType.Location})
  public GetXCoordinate()
  {
    this.returnType = ParameterType.Number;
    this.requiredTypes = new ParameterType[] { ParameterType.Location };
    this.name = "getxcoordinate";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if ((obj.length == 1) && ((obj[0] instanceof Location[]))) {
      return Double.valueOf(((Location[])(Location[])obj[0])[0].getX());
    }
    return null;
  }
}
