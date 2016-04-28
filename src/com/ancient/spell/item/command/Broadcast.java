package com.ancient.spell.item.command;

import com.ancient.parameter.Parameter;
import com.ancient.parameter.ParameterType;
import com.ancient.spellmaker.CommandParameterizable;
import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.HelpList;
import org.bukkit.Server;

public class Broadcast
  extends CommandParameterizable
{
  public Broadcast(int line)
  {
    super(line, "<html>Broadcasts the given message</html>", new Parameter[] { new Parameter(ParameterType.STRING, "message", false) });
  }
  
  public Object[] execute()
    throws Exception
  {
    if (!validValues()) {
      throw new IllegalArgumentException(getClass().getName() + " in line " + this.line + " has parameters of a wrong type.");
    }
    String message = HelpList.replaceChatColor((String)this.parameterValues[0]);
    
    Ancient.plugin.getServer().broadcastMessage(message);
    
    return new Object[] { Integer.valueOf(this.line) };
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancient\spell\item\command\Broadcast.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */