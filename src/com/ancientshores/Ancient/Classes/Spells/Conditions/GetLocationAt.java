package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class GetLocationAt
  extends IArgument
{
  @ArgumentDescription(description="Returns the location in the specified world with the specified coordinates", parameterdescription={"worldname", "xcoord", "ycoord", "zcoord"}, returntype=ParameterType.Location, rparams={ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number})
  public GetLocationAt()
  {
    this.returnType = ParameterType.Location;
    this.requiredTypes = new ParameterType[] { ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number };
    this.name = "getlocationat";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    String wname;
    double x;
    double y;
    double z;
    if ((obj.length == 4) && ((obj[0] instanceof String)) && ((obj[1] instanceof Number)) && ((obj[2] instanceof Number)) && ((obj[3] instanceof Number)))
    {
      wname = (String)obj[0];
      x = ((Number)obj[1]).doubleValue();
      y = ((Number)obj[2]).doubleValue();
      z = ((Number)obj[3]).doubleValue();
      for (World w : Bukkit.getWorlds()) {
        if (w.getName().equalsIgnoreCase(wname))
        {
          Location l = new Location(w, x, y, z);
          return new Location[] { l };
        }
      }
    }
    return null;
  }
}
