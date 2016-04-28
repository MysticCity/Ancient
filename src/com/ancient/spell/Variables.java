package com.ancient.spell;

import java.util.HashMap;

public class Variables
{
  private static HashMap<String, Variable> variables = new HashMap();
  
  public static void addVariable(String name, Variable variable)
  {
    if ((variables.containsKey(name)) || (variables.containsValue(variable))) {
      return;
    }
    variables.put(name, variable);
  }
  
  public static Object getValue(String variableName)
  {
    if (!variables.containsKey(variableName)) {
      return null;
    }
    return variables.get(variableName);
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancient\spell\Variables.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */