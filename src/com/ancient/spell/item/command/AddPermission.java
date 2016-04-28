package com.ancient.spell.item.command;

import com.ancient.parameter.Parameter;
import com.ancient.parameter.ParameterType;
import com.ancient.spellmaker.CommandParameterizable;
import com.ancientshores.Ancient.Ancient;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.entity.Player;

public class AddPermission
  extends CommandParameterizable
{
  public AddPermission(int line)
  {
    super(line, "<html>Adds the specified permission to the player(s)</html>", new Parameter[] { new Parameter(ParameterType.PLAYER, "player(s)", true), new Parameter(ParameterType.STRING, "permission node", false) });
  }
  
  public Object[] execute()
    throws Exception
  {
    if (!validValues()) {
      throw new IllegalArgumentException(getClass().getName() + " in line " + this.line + " has parameters of a wrong type.");
    }
    Player[] players = (Player[])this.parameterValues[0];
    String permissionNode = (String)this.parameterValues[1];
    for (Player p : players) {
      Ancient.permissionHandler.playerAdd(p, permissionNode);
    }
    return new Object[] { Integer.valueOf(this.line) };
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancient\spell\item\command\AddPermission.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */