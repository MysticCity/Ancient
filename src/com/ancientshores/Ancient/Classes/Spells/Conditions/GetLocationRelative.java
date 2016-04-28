package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class GetLocationRelative
  extends IArgument
{
  public GetLocationRelative()
  {
    this.returnType = ParameterType.Number;
    this.requiredTypes = new ParameterType[] { ParameterType.Location, ParameterType.Number, ParameterType.Number, ParameterType.Number };
    this.name = "getrelative";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if (obj.length < 4) {
      return null;
    }
    if (!(obj[0] instanceof Location[])) {
      return null;
    }
    Location l = ((Location[])(Location[])obj[0])[0];
    double forward = ((Number)obj[1]).doubleValue();
    double sideward = ((Number)obj[2]).doubleValue();
    double upward = ((Number)obj[3]).doubleValue();
    
    double y = upward;
    
    Vector v = l.getDirection();
    double z;
    double z;
    double x;
    if (Math.abs(v.getX()) > Math.abs(v.getZ()))
    {
      double x;
      double x;
      if (v.getX() > 0.0D) {
        x = forward;
      } else {
        x = -forward;
      }
      double z;
      if (v.getZ() > 0.0D) {
        z = sideward;
      } else {
        z = -sideward;
      }
    }
    else
    {
      double z;
      if (v.getZ() > 0.0D) {
        z = forward;
      } else {
        z = -forward;
      }
      double x;
      if (v.getX() > 0.0D) {
        x = sideward;
      } else {
        x = -sideward;
      }
    }
    return new Location[] { l.add(x, y, z) };
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Conditions\GetLocationRelative.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */