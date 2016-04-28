package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import java.util.LinkedList;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;

public class PlayEffectCommand
  extends ICommand
{
  @CommandDescription(description="<html>Play effect at the location</html>", argnames={"location", "effectname"}, name="PlayEffect", parameters={ParameterType.Location, ParameterType.String})
  public PlayEffectCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Location, ParameterType.String };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    if ((ca.getParams().size() == 2) && ((ca.getParams().get(1) instanceof String)) && ((ca.getParams().get(0) instanceof Location[])))
    {
      Location[] loc = (Location[])ca.getParams().get(0);
      String name = (String)ca.getParams().get(1);
      Effect effect = getParticleEffectByName(name);
      if (effect == null) {
        return false;
      }
      if ((loc != null) && (loc.length > 0))
      {
        for (Location l : loc) {
          if (l != null) {
            l.getWorld().playEffect(l, effect, 0);
          }
        }
        return true;
      }
    }
    return false;
  }
  
  private static Effect getParticleEffectByName(String s)
  {
    s = s.replace("_", "").toUpperCase();
    for (Effect e : Effect.values()) {
      if (e.name().replace("_", "").equalsIgnoreCase(s)) {
        return e;
      }
    }
    return null;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\PlayEffectCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */