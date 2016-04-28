package com.ancientshores.Ancient.Classes.Spells.Commands.Effects;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.Ancient.Classes.Spells.Commands.ICommand;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.MusicEffect;
import java.util.LinkedList;
import org.bukkit.Location;

public class MusicNotes
  extends ICommand
{
  @CommandDescription(description="<html>Spawns music notes at the given location</html>", argnames={"location", "radials per step", "radius", "period", "iterations"}, name="MusicNotes", parameters={ParameterType.Location, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number})
  public MusicNotes()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Location, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    if ((ca.getParams().size() == 5) && ((ca.getParams().get(4) instanceof Number)) && ((ca.getParams().get(3) instanceof Number)) && ((ca.getParams().get(2) instanceof Number)) && ((ca.getParams().get(1) instanceof Number)) && ((ca.getParams().get(0) instanceof Location[])))
    {
      EffectManager man = new EffectManager(Ancient.effectLib);
      
      Location[] loc = (Location[])ca.getParams().get(0);
      
      double radialsPerStep = ((Number)ca.getParams().get(1)).doubleValue();
      
      float radius = ((Number)ca.getParams().get(2)).floatValue();
      
      int period = ((Number)ca.getParams().get(3)).intValue();
      int iterations = ((Number)ca.getParams().get(4)).intValue();
      if ((loc != null) && (loc.length > 0)) {
        for (Location l : loc) {
          if (l != null)
          {
            MusicEffect e = new MusicEffect(man);
            
            e.radialsPerStep = radialsPerStep;
            
            e.radius = radius;
            
            e.period = period;
            e.iterations = iterations;
            
            e.setLocation(l);
            e.start();
          }
        }
      }
      man.disposeOnTermination();
      return true;
    }
    return false;
  }
}
