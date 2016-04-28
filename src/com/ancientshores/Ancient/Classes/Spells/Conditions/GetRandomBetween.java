package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;

public class GetRandomBetween
  extends IArgument
{
  @ArgumentDescription(description="Returns a random number between 2 numbers", parameterdescription={"minimum", "maximum"}, returntype=ParameterType.Number, rparams={ParameterType.Number, ParameterType.Number})
  public GetRandomBetween()
  {
    this.returnType = ParameterType.Number;
    this.requiredTypes = new ParameterType[] { ParameterType.Number, ParameterType.Number };
    this.name = "getrandombetween";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if ((obj.length == 2) && ((obj[0] instanceof Number)) && ((obj[1] instanceof Number)))
    {
      int min = ((Number)obj[0]).intValue();
      int max = ((Number)obj[1]).intValue();
      int delta = max - min;
      return Integer.valueOf((int)(Math.random() * delta + min));
    }
    return null;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Conditions\GetRandomBetween.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */