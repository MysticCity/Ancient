package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import java.util.LinkedList;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;

public class SmokeCommand
  extends ICommand
{
  @CommandDescription(description="<html>Creates smoke particles at the location</html>", argnames={"location"}, name="Smoke", parameters={ParameterType.Location})
  public SmokeCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Location };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    try
    {
      if ((ca.getParams().get(0) instanceof Location[]))
      {
        final Location[] loc = (Location[])ca.getParams().get(0);
        if ((loc != null) && (loc.length > 0) && (loc[0] != null))
        {
          Ancient.plugin.scheduleThreadSafeTask(Ancient.plugin, new Runnable()
          {
            public void run()
            {
              for (Location l : loc) {
                if (l != null) {
                  for (int i = 0; i < 20; i++) {
                    l.getWorld().playEffect(l, Effect.SMOKE, 1);
                  }
                }
              }
            }
          });
          return true;
        }
      }
    }
    catch (IndexOutOfBoundsException ignored) {}
    return false;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\SmokeCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */