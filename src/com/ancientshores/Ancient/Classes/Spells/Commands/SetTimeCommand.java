package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import java.util.LinkedList;
import org.bukkit.Location;
import org.bukkit.World;

public class SetTimeCommand
  extends ICommand
{
  @CommandDescription(description="<html>Sets the time of the world</html>", argnames={"world", "time"}, name="SetTime", parameters={ParameterType.Location, ParameterType.Number})
  public SetTimeCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Location, ParameterType.Number };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    if ((ca.getParams().size() == 2) && 
      ((ca.getParams().get(0) instanceof Location[])) && ((ca.getParams().get(1) instanceof Number)))
    {
      for (Location e : (Location[])ca.getParams().get(0)) {
        if (e != null) {
          e.getWorld().setTime(((Number)ca.getParams().get(1)).doubleValue());
        }
      }
      return true;
    }
    return false;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\SetTimeCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */