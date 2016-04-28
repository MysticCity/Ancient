package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import com.ancientshores.Ancient.Classes.Spells.Variable;
import java.util.HashMap;
import java.util.LinkedList;
import org.bukkit.entity.Player;

public class AddPlayerVariableCommand
  extends ICommand
{
  @CommandDescription(description="<html>Creates a player variable which is visible to all of the players spells, can be accessed using normal variables.</html>", argnames={"player", "varname"}, name="AddPlayerVariable", parameters={ParameterType.Player, ParameterType.String})
  public AddPlayerVariableCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Player, ParameterType.String };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    if ((ca.getParams().size() == 2) && 
      ((ca.getParams().get(0) instanceof Player[])) && ((ca.getParams().get(1) instanceof String)))
    {
      Player[] players = (Player[])ca.getParams().get(0);
      String name = (String)ca.getParams().get(1);
      for (Player p : players)
      {
        Variable v = new Variable(name);
        if (!Variable.playerVars.containsKey(p.getUniqueId())) {
          Variable.playerVars.put(p.getUniqueId(), new HashMap());
        }
        ((HashMap)Variable.playerVars.get(p.getUniqueId())).put(name, v);
        ca.getSpellInfo().variables.put(v.name.toLowerCase(), v);
      }
      return true;
    }
    return false;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\AddPlayerVariableCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */