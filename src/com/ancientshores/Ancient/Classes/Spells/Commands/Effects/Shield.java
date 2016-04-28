package com.ancientshores.Ancient.Classes.Spells.Commands.Effects;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.Ancient.Classes.Spells.Commands.ICommand;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.ShieldEffect;
import de.slikey.effectlib.util.ParticleEffect;
import java.util.LinkedList;
import org.bukkit.Location;

public class Shield
  extends ICommand
{
  @CommandDescription(description="<html>Creates a particle shield at the given location</html>", argnames={"location", "particlename", "radius", "particles", "full sphere(true) or half sphere(false)", "period", "iterations"}, name="Shield", parameters={ParameterType.Location, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Boolean, ParameterType.Number, ParameterType.Number})
  public Shield()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Location, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Boolean, ParameterType.Number, ParameterType.Number };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    if ((ca.getParams().size() == 7) && ((ca.getParams().get(6) instanceof Number)) && ((ca.getParams().get(5) instanceof Number)) && ((ca.getParams().get(4) instanceof Boolean)) && ((ca.getParams().get(3) instanceof Number)) && ((ca.getParams().get(2) instanceof Number)) && ((ca.getParams().get(1) instanceof String)) && ((ca.getParams().get(0) instanceof Location[])))
    {
      EffectManager man = new EffectManager(Ancient.effectLib);
      
      Location[] loc = (Location[])ca.getParams().get(0);
      
      ParticleEffect particle = ParticleEffect.fromName((String)ca.getParams().get(1));
      
      int radius = ((Number)ca.getParams().get(2)).intValue();
      int particles = ((Number)ca.getParams().get(3)).intValue();
      
      boolean isSphere = ((Boolean)ca.getParams().get(4)).booleanValue();
      
      int period = ((Number)ca.getParams().get(5)).intValue();
      int iterations = ((Number)ca.getParams().get(6)).intValue();
      if ((loc != null) && (loc.length > 0)) {
        for (Location l : loc) {
          if (l != null)
          {
            ShieldEffect e = new ShieldEffect(man);
            
            e.particle = particle;
            
            e.radius = radius;
            e.particles = particles;
            
            e.sphere = isSphere;
            
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
