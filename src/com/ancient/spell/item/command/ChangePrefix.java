package com.ancient.spell.item.command;

import com.ancient.parameter.Parameter;
import com.ancient.parameter.ParameterType;
import com.ancient.spellmaker.CommandParameterizable;
import com.ancientshores.Ancient.HelpList;
import org.bukkit.entity.Player;

public class ChangePrefix
  extends CommandParameterizable
{
  public ChangePrefix(int line)
  {
    super(line, "<html>Adds a prefix to the players name.</html>", new Parameter[] { new Parameter(ParameterType.PLAYER, "player(s)", true), new Parameter(ParameterType.STRING, "prefix (blank to remove prefix)", false) });
  }
  
  public Object[] execute()
    throws Exception
  {
    if (!validValues()) {
      throw new IllegalArgumentException(getClass().getName() + " in line " + this.line + " has parameters of a wrong type.");
    }
    Player[] players = (Player[])this.parameterValues[0];
    
    String prefix = HelpList.replaceChatColor((String)this.parameterValues[1]);
    
    return new Object[] { Integer.valueOf(this.line) };
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancient\spell\item\command\ChangePrefix.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */