package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import java.util.LinkedList;
import org.bukkit.entity.Player;

public class SetFoodLevelCommand
  extends ICommand
{
  @CommandDescription(description="<html>Sets the foodlevel of the player</html>", argnames={"player", "amount"}, name="SetFoodLevel", parameters={ParameterType.Player, ParameterType.Number})
  public SetFoodLevelCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Player, ParameterType.Number };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    if ((ca.getParams().size() == 2) && 
      ((ca.getParams().get(0) instanceof Player[])) && ((ca.getParams().get(1) instanceof Number)))
    {
      for (Player p : (Player[])ca.getParams().get(0)) {
        if (p != null) {
          p.setFoodLevel((int)((Number)ca.getParams().get(1)).doubleValue());
        }
      }
      return true;
    }
    return false;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\SetFoodLevelCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */