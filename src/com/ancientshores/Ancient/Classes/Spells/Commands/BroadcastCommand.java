package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.HelpList;
import java.util.LinkedList;
import org.bukkit.Server;

public class BroadcastCommand
  extends ICommand
{
  @CommandDescription(description="<html>Broadcasts the message</html>", argnames={"message"}, name="Broadcast", parameters={ParameterType.String})
  public BroadcastCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.String };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    try
    {
      if ((ca.getParams().get(0) instanceof String))
      {
        final String message = (String)ca.getParams().get(0);
        if (message != null)
        {
          Ancient.plugin.scheduleThreadSafeTask(Ancient.plugin, new Runnable()
          {
            public void run()
            {
              Ancient.plugin.getServer().broadcastMessage(HelpList.replaceChatColor(message));
            }
          });
          return true;
        }
      }
    }
    catch (IndexOutOfBoundsException e)
    {
      return false;
    }
    return false;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\BroadcastCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */