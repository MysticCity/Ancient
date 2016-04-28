package com.ancient.spell.item.command;

import com.ancient.parameter.Parameter;
import com.ancient.parameter.ParameterType;
import com.ancient.spellmaker.CommandParameterizable;
import com.ancientshores.Ancient.Classes.Spells.Variable;
import java.util.HashMap;

public class AddGlobalVariable
  extends CommandParameterizable
{
  public AddGlobalVariable(int line)
  {
    super(line, "<html>Creates a global variable which is visible to all spells, can be accessed using normal variables.</html>", new Parameter[] { new Parameter(ParameterType.STRING, "variable name", false) });
  }
  
  public Object[] execute()
    throws Exception
  {
    if (!validValues()) {
      throw new IllegalArgumentException(getClass().getName() + " in line " + this.line + " has parameters of a wrong type.");
    }
    String varName = (String)this.parameterValues[0];
    
    Variable v = new Variable(varName);
    Variable.globVars.put(varName, v);
    
    return new Object[] { Integer.valueOf(this.line) };
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancient\spell\item\command\AddGlobalVariable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */