package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import org.bukkit.Location;
import org.bukkit.World;

public class GetTime
  extends IArgument
{
  @ArgumentDescription(description="Returns the time of the world", parameterdescription={"world"}, returntype=ParameterType.Number, rparams={ParameterType.Location})
  public GetTime()
  {
    this.returnType = ParameterType.Number;
    this.requiredTypes = new ParameterType[] { ParameterType.Location };
    this.name = "gettime";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if ((obj.length > 0) && ((obj[0] instanceof Location[])))
    {
      Location[] l = (Location[])obj[0];
      if (l.length == 0) {
        return null;
      }
      return Long.valueOf(l[0].getWorld().getTime());
    }
    return null;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Conditions\GetTime.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */