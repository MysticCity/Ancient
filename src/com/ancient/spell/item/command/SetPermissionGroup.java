package com.ancient.spell.item.command;

import com.ancient.parameter.Parameter;
import com.ancient.parameter.ParameterType;
import com.ancient.spellmaker.CommandParameterizable;
import com.ancientshores.Ancient.Ancient;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.entity.Player;

public class SetPermissionGroup
  extends CommandParameterizable
{
  public SetPermissionGroup(int line)
  {
    super(line, "<html>Sets the player(s) to the permission group with the specified name</html>", new Parameter[] { new Parameter(ParameterType.PLAYER, "Player(s)", true), new Parameter(ParameterType.STRING, "Group name", false) });
  }
  
  public Object[] execute()
    throws Exception
  {
    if (!validValues()) {
      throw new IllegalArgumentException(getClass().getName() + " in line " + this.line + " has parameters of a wrong type.");
    }
    Player[] players = (Player[])this.parameterValues[0];
    String groupName = (String)this.parameterValues[1];
    for (Player p : players) {
      Ancient.permissionHandler.playerAddGroup(p, groupName);
    }
    return new Object[] { Integer.valueOf(this.line) };
  }
}
