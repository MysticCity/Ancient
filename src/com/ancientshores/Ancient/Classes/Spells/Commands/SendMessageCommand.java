package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.HelpList;
import java.util.LinkedList;
import org.bukkit.entity.Player;

public class SendMessageCommand
  extends ICommand
{
  @CommandDescription(description="<html>Sends the message to the specified players</html>", argnames={"player", "message"}, name="SendMessage", parameters={ParameterType.Player, ParameterType.String})
  public SendMessageCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Player, ParameterType.String };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    try
    {
      if (((ca.getParams().get(0) instanceof Player[])) && ((ca.getParams().get(1) instanceof String)))
      {
        Player[] target = (Player[])ca.getParams().get(0);
        String message = (String)ca.getParams().get(1);
        for (Player p : target) {
          if (p != null) {
            p.sendMessage(HelpList.replaceChatColor(message));
          }
        }
        return true;
      }
    }
    catch (IndexOutOfBoundsException ignored) {}
    return false;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\SendMessageCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */