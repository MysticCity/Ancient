package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import java.util.LinkedList;
import org.bukkit.Location;
import org.bukkit.World;

public class SetWeatherCommand
  extends ICommand
{
  @CommandDescription(description="<html>Changes the weather in the world for atleast the specified time</html>", argnames={"world", "duration", "raining", "thundering"}, name="SetWeather", parameters={ParameterType.Location, ParameterType.Number, ParameterType.Boolean, ParameterType.Boolean})
  public SetWeatherCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Location, ParameterType.Number, ParameterType.Boolean, ParameterType.Boolean };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    if ((ca.getParams().size() == 4) && ((ca.getParams().get(0) instanceof Location[])) && ((ca.getParams().get(1) instanceof Number)) && ((ca.getParams().get(2) instanceof Boolean)) && ((ca.getParams().get(3) instanceof Boolean)))
    {
      Location[] locs = (Location[])ca.getParams().get(0);
      boolean raining = ((Boolean)ca.getParams().get(2)).booleanValue();
      boolean thundering = ((Boolean)ca.getParams().get(3)).booleanValue();
      int time = (int)((Number)ca.getParams().get(1)).doubleValue();
      for (Location l : locs) {
        if (l != null)
        {
          int t = Math.round(time / 50);
          if (t == 0) {
            t = Integer.MAX_VALUE;
          }
          l.getWorld().setWeatherDuration(t);
          l.getWorld().setThunderDuration(t);
          l.getWorld().setThundering(thundering);
          l.getWorld().setStorm(raining);
          l.getWorld().setWeatherDuration(t);
          l.getWorld().setThunderDuration(t);
        }
      }
      return true;
    }
    return false;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\SetWeatherCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */