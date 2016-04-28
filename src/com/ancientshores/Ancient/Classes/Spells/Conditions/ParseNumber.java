package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;

public class ParseNumber
  extends IArgument
{
  @ArgumentDescription(description="Parses a string and tries to convert it into a number", parameterdescription={"numberasstring"}, returntype=ParameterType.Number, rparams={ParameterType.String})
  public ParseNumber()
  {
    this.returnType = ParameterType.Number;
    this.requiredTypes = new ParameterType[] { ParameterType.String };
    this.name = "parsenumber";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if (!(obj[0] instanceof String)) {
      return Integer.valueOf(0);
    }
    String s = (String)obj[0];
    try
    {
      return Double.valueOf(Double.parseDouble(s));
    }
    catch (Exception ignored) {}
    return Integer.valueOf(-1);
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Conditions\ParseNumber.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */