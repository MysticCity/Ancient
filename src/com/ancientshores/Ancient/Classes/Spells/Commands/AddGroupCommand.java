package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import java.util.LinkedList;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.entity.Player;

public class AddGroupCommand
  extends ICommand
{
  @CommandDescription(description="<html>Sets the player to the permission group with the specified name</html>", argnames={"the player", "the group"}, name="AddGroup", parameters={ParameterType.Player, ParameterType.String})
  public AddGroupCommand()
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
        String node = (String)ca.getParams().get(1);
        if (node != null)
        {
          for (Player p : players) {
            if (p != null) {
              try
              {
                Ancient.permissionHandler.playerAddGroup(p, node);
              }
              catch (Exception ignored) {}
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


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\AddGroupCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */