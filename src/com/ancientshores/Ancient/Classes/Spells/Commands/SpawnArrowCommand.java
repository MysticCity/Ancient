package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import java.util.LinkedList;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

public class SpawnArrowCommand
  extends ICommand
{
  @CommandDescription(description="<html>Spawns an arrow at the location with the velocity</html>", argnames={"location", "forward", "sideward", "upward"}, name="SpawnArrow", parameters={ParameterType.Location, ParameterType.Number, ParameterType.Number, ParameterType.Number})
  public SpawnArrowCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Location, ParameterType.Number, ParameterType.Number, ParameterType.Number };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    if ((ca.getParams().size() == 4) && ((ca.getParams().get(0) instanceof Location[])) && ((ca.getParams().get(1) instanceof Number)) && ((ca.getParams().get(2) instanceof Number)) && ((ca.getParams().get(3) instanceof Number)))
    {
      Location[] locs = (Location[])ca.getParams().get(0);
      double forward = ((Number)ca.getParams().get(1)).doubleValue();
      double sideward = ((Number)ca.getParams().get(2)).doubleValue();
      double upward = ((Number)ca.getParams().get(3)).doubleValue();
      
      double y = upward;
      for (Location l : locs)
      {
        Vector v = l.getDirection();
        double z;
        double x;
        double z;
        if (Math.abs(v.getX()) > Math.abs(v.getZ()))
        {
          double x = forward * v.getX();
          z = sideward * v.getZ();
        }
        else
        {
          x = sideward * v.getX();
          z = forward * v.getZ();
        }
        Vector vs = new Vector(x, y, z);
        float speed = (float)vs.length();
        l.getWorld().spawnArrow(l, vs, speed, 12.0F);
      }
    }
    return false;
  }
}
