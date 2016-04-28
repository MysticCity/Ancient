package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import org.bukkit.Location;
import org.bukkit.World;

public class GetWorld
  extends IArgument
{
  @ArgumentDescription(description="Returns the name of the world of the specified location", parameterdescription={"location"}, returntype=ParameterType.String, rparams={ParameterType.Location})
  public GetWorld()
  {
    this.returnType = ParameterType.String;
    this.requiredTypes = new ParameterType[] { ParameterType.Location };
    this.name = "getworld";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if (!(obj[0] instanceof Location[])) {
      return null;
    }
    if (((Location[])obj[0]).length < 0) {
      return null;
    }
    if (((Location[])(Location[])obj[0])[0] == null) {
      return null;
    }
    Location l = ((Location[])(Location[])obj[0])[0];
    return l.getWorld().getName();
  }
}
