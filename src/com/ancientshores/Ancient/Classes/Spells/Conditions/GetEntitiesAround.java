package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import org.bukkit.Location;

public class GetEntitiesAround
  extends IArgument
{
  public GetEntitiesAround()
  {
    this.returnType = ParameterType.String;
    this.requiredTypes = new ParameterType[] { ParameterType.Location, ParameterType.Number, ParameterType.Number };
    this.name = "getentitiesaround";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if ((obj.length == 3) && (isValidArgument(obj[0], new Location[0].getClass())) && ((obj[1] instanceof Number)) && ((obj[2] instanceof Number)))
    {
      Location l = ((Location[])(Location[])obj[0])[0];
      int range = ((Number)obj[1]).intValue();
      int amount = ((Number)obj[2]).intValue();
      return so.getNearestEntities(l, range, amount);
    }
    return null;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Conditions\GetEntitiesAround.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */