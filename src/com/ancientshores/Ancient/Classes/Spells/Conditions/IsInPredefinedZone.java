package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class IsInPredefinedZone
  extends IArgument
{
  @ArgumentDescription(description="Returns true if the given location is in the defined bounds of the zone", parameterdescription={"location", "world", "x1", "y1", "z1", "x2", "y2", "z2"}, returntype=ParameterType.Number, rparams={ParameterType.Location, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number})
  public IsInPredefinedZone()
  {
    this.returnType = ParameterType.Number;
    this.requiredTypes = new ParameterType[] { ParameterType.Location, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number };
    this.name = "isinpredefinedzone";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if ((obj.length == 8) && ((obj[0] instanceof Location[])) && ((obj[1] instanceof String)) && ((obj[2] instanceof Number)) && ((obj[3] instanceof Number)) && ((obj[4] instanceof Number)) && ((obj[5] instanceof Number)) && ((obj[6] instanceof Number)) && ((obj[7] instanceof Number)))
    {
      Location target = ((Location[])(Location[])obj[0])[0];
      if ((Bukkit.getWorld((String)obj[1]) == null) || (Bukkit.getWorld((String)obj[1]) != target.getWorld())) {
        return Boolean.valueOf(false);
      }
      Location start = new Location(Bukkit.getWorld((String)obj[1]), (int)((Number)obj[2]).doubleValue(), (int)((Number)obj[3]).doubleValue(), (int)((Number)obj[4]).doubleValue());
      Location end = new Location(Bukkit.getWorld((String)obj[1]), (int)((Number)obj[5]).doubleValue(), (int)((Number)obj[6]).doubleValue(), (int)((Number)obj[7]).doubleValue());
      int startx = start.getBlockX();
      int endx = end.getBlockX();
      if (startx > endx)
      {
        int buffer = startx;
        startx = endx;
        endx = buffer;
      }
      int starty = start.getBlockY();
      int endy = end.getBlockY();
      if (starty > endy)
      {
        int buffer = starty;
        starty = endy;
        endy = buffer;
      }
      int startz = start.getBlockZ();
      int endz = end.getBlockZ();
      if (startz > endz)
      {
        int buffer = startz;
        startz = endz;
        endz = buffer;
      }
      start = new Location(start.getWorld(), startx, starty, startz);
      end = new Location(start.getWorld(), endx, endy, endz);
      boolean s = (target.getX() > start.getX()) && (target.getY() > start.getY()) && (target.getZ() > start.getZ());
      boolean e = (target.getX() < end.getX()) && (target.getY() < end.getY()) && (target.getZ() < end.getZ());
      return Boolean.valueOf((s) && (e));
    }
    return Boolean.valueOf(false);
  }
}
