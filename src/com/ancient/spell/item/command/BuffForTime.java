package com.ancient.spell.item.command;

import com.ancient.parameter.Parameter;
import com.ancient.parameter.ParameterType;
import com.ancient.spellmaker.CommandParameterizable;
import org.bukkit.entity.Player;

public class BuffForTime
  extends CommandParameterizable
{
  public BuffForTime(int line)
  {
    super(line, "<html>Buffs the player(s) with the specified buff for the specified time</html>", new Parameter[] { new Parameter(ParameterType.PLAYER, "player(s)", true), new Parameter(ParameterType.STRING, "buff name", false), new Parameter(ParameterType.NUMBER, "time (in seconds)", false) });
  }
  
  public Object[] execute()
    throws Exception
  {
    if (!validValues()) {
      throw new IllegalArgumentException(getClass().getName() + " in line " + this.line + " has parameters of a wrong type.");
    }
    Player[] players = (Player[])this.parameterValues[0];
    
    String name = (String)this.parameterValues[1];
    
    int time = Integer.parseInt((String)this.parameterValues[2]);
    
    return new Object[] { Integer.valueOf(this.line) };
  }
}
