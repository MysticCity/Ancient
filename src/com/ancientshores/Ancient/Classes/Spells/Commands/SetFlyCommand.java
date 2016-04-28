package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import java.util.LinkedList;
import org.bukkit.entity.Player;

public class SetFlyCommand
  extends ICommand
{
  @CommandDescription(description="<html>Sets if the player can fly or not</html>", argnames={"player", "canfly"}, name="SetFly", parameters={ParameterType.Player, ParameterType.Boolean})
  public SetFlyCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Player, ParameterType.Boolean };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    try
    {
      if (((ca.getParams().get(0) instanceof Player[])) && ((ca.getParams().get(1) instanceof Boolean)))
      {
        Player[] players = (Player[])ca.getParams().get(0);
        boolean on = ((Boolean)ca.getParams().get(1)).booleanValue();
        for (Player p : players)
        {
          p.setAllowFlight(on);
          p.setFlying(on);
        }
        return true;
      }
    }
    catch (IndexOutOfBoundsException ignored) {}
    return false;
  }
}
