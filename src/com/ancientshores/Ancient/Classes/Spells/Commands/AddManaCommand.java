package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Mana.ManaSystem;
import java.util.LinkedList;
import org.bukkit.entity.Player;

public class AddManaCommand
  extends ICommand
{
  @CommandDescription(description="<html>Adds the specified amount of mana to the players mana system</html>", argnames={"the player", "amount"}, name="AddMana", parameters={ParameterType.Player, ParameterType.Number})
  public AddManaCommand()
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
          ManaSystem.addManaByUUID(p.getUniqueId(), (int)((Number)ca.getParams().get(1)).doubleValue());
        }
      }
      return true;
    }
    return false;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\AddManaCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */