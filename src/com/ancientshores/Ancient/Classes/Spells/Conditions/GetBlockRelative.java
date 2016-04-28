package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

public class GetBlockRelative
  extends IArgument
{
  @ArgumentDescription(description="Returns the block relative to the player.", parameterdescription={"location", "forward", "sideward", "upward"}, returntype=ParameterType.Number, rparams={ParameterType.Location, ParameterType.Number, ParameterType.Number, ParameterType.Number})
  public GetBlockRelative()
  {
    this.returnType = ParameterType.Number;
    this.requiredTypes = new ParameterType[] { ParameterType.Location, ParameterType.Number, ParameterType.Number, ParameterType.Number };
    this.name = "getBlockRelative";
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
    int forward = (int)((Number)obj[1]).doubleValue();
    int sideward = (int)((Number)obj[2]).doubleValue();
    int upward = (int)((Number)obj[3]).doubleValue();
    
    int y = upward;
    
    Block b = l.getBlock();
    Vector v = l.getDirection();
    int z;
    int z;
    int x;
    if (Math.abs(v.getX()) > Math.abs(v.getZ()))
    {
      int x;
      int x;
      if (v.getX() > 0.0D) {
        x = forward;
      } else {
        x = -forward;
      }
      int z;
      if (v.getZ() > 0.0D) {
        z = sideward;
      } else {
        z = -sideward;
      }
    }
    else
    {
      int z;
      if (v.getZ() > 0.0D) {
        z = forward;
      } else {
        z = -forward;
      }
      int x;
      if (v.getX() > 0.0D) {
        x = sideward;
      } else {
        x = -sideward;
      }
    }
    return new Location[] { b.getRelative(x, y, z).getLocation() };
  }
}
