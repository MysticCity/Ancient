package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import java.util.LinkedList;
import org.bukkit.entity.Entity;

public class IgniteCommand
  extends ICommand
{
  @CommandDescription(description="<html>Ignites the target for the specified time</html>", argnames={"entity", "duration"}, name="Ignite", parameters={ParameterType.Entity, ParameterType.Number})
  public IgniteCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Entity, ParameterType.Number };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    try
    {
      if (((ca.getParams().get(0) instanceof Entity[])) && ((ca.getParams().get(1) instanceof Number)))
      {
        Entity[] target = (Entity[])ca.getParams().get(0);
        int time = (int)((Number)ca.getParams().get(1)).doubleValue();
        if ((target != null) && (target.length > 0))
        {
          for (Entity targetPlayer : target) {
            if (targetPlayer != null) {
              targetPlayer.setFireTicks(Math.round(time / 50));
            }
          }
          return true;
        }
      }
    }
    catch (IndexOutOfBoundsException ignored) {}
    return false;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\IgniteCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */