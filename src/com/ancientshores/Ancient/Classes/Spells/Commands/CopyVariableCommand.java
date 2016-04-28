package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import com.ancientshores.Ancient.Classes.Spells.Variable;
import java.util.HashMap;
import java.util.LinkedList;

public class CopyVariableCommand
  extends ICommand
{
  @CommandDescription(description="<html>Copies the content of the source to the target</html>", argnames={"variable1", "variable2"}, name="CopyVariable", parameters={ParameterType.String, ParameterType.String})
  public CopyVariableCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.String, ParameterType.String };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    if ((ca.getParams().size() == 2) && 
      ((ca.getParams().get(0) instanceof String)) && ((ca.getParams().get(1) instanceof String)))
    {
      String source = (String)ca.getParams().get(0);
      String target = (String)ca.getParams().get(1);
      Variable v1 = (Variable)ca.getSpellInfo().variables.get(source);
      Variable v2 = (Variable)ca.getSpellInfo().variables.get(target);
      if ((v1 != null) && (v2 != null))
      {
        v2.obj = v1.obj;
        if (Variable.globVars.containsKey(v2.name)) {
          ((Variable)Variable.globVars.get(v2.name)).obj = v2.obj;
        }
      }
      return true;
    }
    return false;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\CopyVariableCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */