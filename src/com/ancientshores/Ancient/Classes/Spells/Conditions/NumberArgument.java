package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;

public class NumberArgument
  extends IArgument
{
  double i;
  
  public NumberArgument(String s)
  {
    this.name = "number";
    this.returnType = ParameterType.Void;
    try
    {
      this.i = Double.parseDouble(s.trim());
    }
    catch (Exception e)
    {
      this.i = 0.0D;
    }
  }
  
  public Object getArgument(Object[] mPlayer, SpellInformationObject so)
  {
    return Double.valueOf(this.i);
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Conditions\NumberArgument.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */