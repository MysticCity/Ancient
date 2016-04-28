package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import java.util.LinkedList;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.entity.Player;

public class AddPermissionCommand
  extends ICommand
{
  @CommandDescription(description="<html>Adds the specified permission to the player</html>", argnames={"player", "permission"}, name="AddPermission", parameters={ParameterType.Player, ParameterType.String})
  public AddPermissionCommand()
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
              Ancient.permissionHandler.playerAdd(p, node);
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


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\AddPermissionCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */