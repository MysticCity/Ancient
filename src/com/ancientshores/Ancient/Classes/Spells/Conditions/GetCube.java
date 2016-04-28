package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import java.util.HashSet;
import org.bukkit.Location;

public class GetCube
  extends IArgument
{
  @ArgumentDescription(description="<html>returns the outline of a cube with the specified radius at the given locaiton<br>Parameter 1: the middle point of the cube<br>Parameter 2: the radius of the cube</html>", parameterdescription={"location", "radius"}, returntype=ParameterType.Location, rparams={ParameterType.Location, ParameterType.Number})
  public GetCube()
  {
    this.name = "getCube";
    this.returnType = ParameterType.Location;
    this.requiredTypes = new ParameterType[] { ParameterType.Location, ParameterType.Number };
  }
  
  public Object getArgument(Object[] params, SpellInformationObject so)
  {
    if (params.length < 2) {
      return null;
    }
    if (!(params[0] instanceof Location[])) {
      return null;
    }
    if (!(params[1] instanceof Number)) {
      return null;
    }
    int rad = (int)((Number)params[1]).doubleValue();
    HashSet<Location> list = new HashSet();
    Location l = ((Location[])(Location[])params[0])[0];
    for (int i = -rad; i <= rad; i++) {
      for (int y = -rad; y <= rad; y++)
      {
        list.add(l.clone().add(-rad, y, i));
        list.add(l.clone().add(rad, y, i));
        list.add(l.clone().add(y, -rad, i));
        list.add(l.clone().add(y, rad, i));
        list.add(l.clone().add(i, y, -rad));
        list.add(l.clone().add(i, y, rad));
      }
    }
    return list.toArray(new Location[list.size()]);
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Conditions\GetCube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */