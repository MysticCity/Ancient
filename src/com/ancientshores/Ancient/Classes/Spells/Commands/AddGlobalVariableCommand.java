package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import com.ancientshores.Ancient.Classes.Spells.Variable;
import java.util.HashMap;
import java.util.LinkedList;

public class AddGlobalVariableCommand
  extends ICommand
{
  @CommandDescription(description="<html>Creates a global variable which is visible to all spells, can be accessed using normal variables.</html>", argnames={"varname"}, name="AddGlobalVariable", parameters={ParameterType.String})
  public AddGlobalVariableCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.String };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    if ((ca.getParams().size() == 1) && 
      ((ca.getParams().get(0) instanceof String)))
    {
      String name = (String)ca.getParams().get(0);
      if (!Variable.globVars.containsKey(name))
      {
        Variable v = new Variable(name);
        Variable.globVars.put(name, v);
        ca.getSpellInfo().variables.put(name, v);
      }
      return true;
    }
    return false;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\AddGlobalVariableCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */