package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

public class GetLightLevel
  extends IArgument
{
  @ArgumentDescription(description="Returns the light level at the specified location", parameterdescription={"location"}, returntype=ParameterType.Number, rparams={ParameterType.Location})
  public GetLightLevel()
  {
    this.returnType = ParameterType.Number;
    this.requiredTypes = new ParameterType[] { ParameterType.Location };
    this.name = "getlightlevel";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if ((obj[0] instanceof Location[]))
    {
      Location loc = ((Location[])(Location[])obj[0])[0];
      if ((loc != null) && (loc.getWorld() != null) && (loc.getWorld().getBlockAt(loc) != null)) {
        return Integer.valueOf(loc.getWorld().getBlockAt(loc).getLightLevel());
      }
    }
    return Integer.valueOf(0);
  }
}
