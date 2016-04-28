package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.HelpList;
import java.util.LinkedList;
import org.bukkit.entity.Player;

public class AddPrefixCommand
  extends ICommand
{
  @CommandDescription(description="<html>Adds a prefix to the players name if it doesn't already exist</html>", argnames={"player", "prefix"}, name="AddPrefix", parameters={ParameterType.Player, ParameterType.String})
  public AddPrefixCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Player, ParameterType.String };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    try
    {
      if (((ca.getParams().get(0) instanceof Player[])) && ((ca.getParams().get(1) instanceof String)))
      {
        Player[] players = (Player[])ca.getParams().get(0);
        String node = HelpList.replaceChatColor((String)ca.getParams().get(1));
        if (node != null)
        {
          for (Player p : players) {
            if (p != null) {
              if (!p.getDisplayName().startsWith(node)) {
                p.setDisplayName(node + p.getDisplayName());
              }
            }
          }
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


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\AddPrefixCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */