package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import java.util.LinkedList;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.entity.Player;

public class RemoveGroupCommand
  extends ICommand
{
  @CommandDescription(description="<html>Removes the permission group from the player</html>", argnames={"player", "group"}, name="RemoveGroup", parameters={ParameterType.Player, ParameterType.String})
  public RemoveGroupCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Player, ParameterType.String };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    if ((ca.getParams().size() == 2) && ((ca.getParams().get(0) instanceof Player[])) && ((ca.getParams().get(1) instanceof String)))
    {
      Player[] players = (Player[])ca.getParams().get(0);
      String node = (String)ca.getParams().get(1);
      if (node != null)
      {
        for (Player p : players) {
          if (p != null) {
            try
            {
              Ancient.permissionHandler.playerRemoveGroup(p, node);
            }
            catch (Exception ignored) {}
          }
        }
        return true;
      }
    }
    return false;
  }
}
