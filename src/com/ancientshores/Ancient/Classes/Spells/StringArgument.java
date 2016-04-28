package com.ancientshores.Ancient.Classes.Spells;

import com.ancientshores.Ancient.Classes.Spells.Conditions.IArgument;
import java.util.HashMap;

public class StringArgument
  extends IArgument
{
  final String s;
  
  public StringArgument(String value)
  {
    this.returnType = ParameterType.Void;
    this.s = value;
    this.name = "stringargument";
  }
  
  public Object getArgument(Object[] mPlayer, SpellInformationObject so)
  {
    if (so.variables.containsKey(this.s.toLowerCase())) {
      return ((Variable)so.variables.get(this.s.toLowerCase())).getVariableObject();
    }
    if ((this.s.trim().startsWith("\"")) && (this.s.trim().endsWith("\""))) {
      return this.s.trim().substring(this.s.trim().indexOf('"') + 1, this.s.trim().length() - 1);
    }
    return this.s.trim();
  }
}
