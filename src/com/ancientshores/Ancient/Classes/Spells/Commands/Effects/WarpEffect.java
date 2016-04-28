package com.ancientshores.Ancient.Classes.Spells.Commands.Effects;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.Ancient.Classes.Spells.Commands.ICommand;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.util.ParticleEffect;
import java.util.LinkedList;
import org.bukkit.Location;

public class WarpEffect
  extends ICommand
{
  @CommandDescription(description="<html>Creates a warp effect at the given location</html>", argnames={"location", "particlename", "circles", "particles per circle", "radius", "grow", "period", "iterations"}, name="WarpEffect", parameters={ParameterType.Location, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number})
  public WarpEffect()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Location, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    if ((ca.getParams().size() == 8) && ((ca.getParams().get(7) instanceof Number)) && ((ca.getParams().get(6) instanceof Number)) && ((ca.getParams().get(5) instanceof Number)) && ((ca.getParams().get(4) instanceof Number)) && ((ca.getParams().get(3) instanceof Number)) && ((ca.getParams().get(2) instanceof Number)) && ((ca.getParams().get(1) instanceof String)) && ((ca.getParams().get(0) instanceof Location[])))
    {
      EffectManager man = new EffectManager(Ancient.effectLib);
      
      Location[] loc = (Location[])ca.getParams().get(0);
      
      ParticleEffect particle = ParticleEffect.fromName((String)ca.getParams().get(1));
      
      int particlesPerCircle = ((Number)ca.getParams().get(2)).intValue();
      
      int circles = ((Number)ca.getParams().get(3)).intValue();
      
      float radius = ((Number)ca.getParams().get(4)).floatValue();
      float grow = ((Number)ca.getParams().get(5)).floatValue();
      
      int period = ((Number)ca.getParams().get(6)).intValue();
      int iterations = ((Number)ca.getParams().get(7)).intValue();
      if ((loc != null) && (loc.length > 0)) {
        for (Location l : loc) {
          if (l != null)
          {
            de.slikey.effectlib.effect.WarpEffect e = new de.slikey.effectlib.effect.WarpEffect(man);
            
            e.particle = particle;
            
            e.particles = particlesPerCircle;
            
            e.rings = circles;
            
            e.radius = radius;
            e.grow = grow;
            
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
