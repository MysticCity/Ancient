package com.ancient.spell.item.command;

import com.ancient.parameter.Parameter;
import com.ancient.parameter.ParameterType;
import com.ancient.spellmaker.CommandParameterizable;
import com.ancientshores.Ancient.Classes.Spells.Variable;
import java.util.HashMap;
import org.bukkit.entity.Player;

public class AddPlayerVariable
  extends CommandParameterizable
{
  public AddPlayerVariable(int line)
  {
    super(line, "<html>Creates a player variable which is visible to all of the players spells, can be accessed using normal variables.</html>", new Parameter[] { new Parameter(ParameterType.PLAYER, "player(s)", true), new Parameter(ParameterType.STRING, "variable name", false) });
  }
  
  public Object[] execute()
    throws Exception
  {
    if (!validValues()) {
      throw new IllegalArgumentException(getClass().getName() + " in line " + this.line + " has parameters of a wrong type.");
    }
    Player[] players = (Player[])this.parameterValues[0];
    String varName = (String)this.parameterValues[1];
    for (Player p : players)
    {
      if (!Variable.playerVars.containsKey(p.getUniqueId())) {
        Variable.playerVars.put(p.getUniqueId(), new HashMap());
      }
      Variable v = new Variable(varName);
      ((HashMap)Variable.playerVars.get(p.getUniqueId())).put(varName, v);
    }
    return new Object[] { Integer.valueOf(this.line) };
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancient\spell\item\command\AddPlayerVariable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */