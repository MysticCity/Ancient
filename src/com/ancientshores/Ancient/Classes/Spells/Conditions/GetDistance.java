package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import org.bukkit.Location;

public class GetDistance
  extends IArgument
{
  @ArgumentDescription(description="Returns the distance between two locations in the same world", parameterdescription={"location1", "location2"}, returntype=ParameterType.Number, rparams={ParameterType.Location, ParameterType.Location})
  public GetDistance()
  {
    this.returnType = ParameterType.Number;
    this.requiredTypes = new ParameterType[] { ParameterType.Location, ParameterType.Location };
    this.name = "getdistance";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if ((obj.length != 2) || (!(obj[0] instanceof Location[])) || (!(obj[1] instanceof Location[]))) {
      return Integer.valueOf(Integer.MAX_VALUE);
    }
    Location l1 = ((Location[])(Location[])obj[0])[0];
    Location l2 = ((Location[])(Location[])obj[1])[0];
    return Double.valueOf(l1.distance(l2));
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Conditions\GetDistance.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */