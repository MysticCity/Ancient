package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class IsInWater
  extends IArgument
{
  @ArgumentDescription(description="Returns true if the location is in water", parameterdescription={"location"}, returntype=ParameterType.Boolean, rparams={ParameterType.Location})
  public IsInWater()
  {
    this.returnType = ParameterType.Boolean;
    this.requiredTypes = new ParameterType[] { ParameterType.Location };
    this.name = "isinwater";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if ((obj.length == 1) && ((obj[0] instanceof Location[])) && (((Location[])obj[0]).length > 0))
    {
      Location l = ((Location[])(Location[])obj[0])[0];
      if (l != null) {
        return Boolean.valueOf((l.getBlock().getType() == Material.WATER) || (l.getBlock().getType() == Material.STATIONARY_WATER));
      }
    }
    return Boolean.valueOf(false);
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Conditions\IsInWater.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */