package com.ancientshores.Ancient.Classes.Spells.Commands.Effects;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.Ancient.Classes.Spells.Commands.ICommand;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.HelixEffect;
import de.slikey.effectlib.util.ParticleEffect;
import java.util.LinkedList;
import org.bukkit.Location;

public class Helix
  extends ICommand
{
  @CommandDescription(description="<html>Creates a helix at the given location</html>", argnames={"location", "particlename", "strands", "particles per strand", "radius", "curve", "rotation", "period", "iterations"}, name="Helix", parameters={ParameterType.Location, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number})
  public Helix()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Location, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    if ((ca.getParams().size() == 9) && ((ca.getParams().get(8) instanceof Number)) && ((ca.getParams().get(7) instanceof Number)) && ((ca.getParams().get(6) instanceof Number)) && ((ca.getParams().get(5) instanceof Number)) && ((ca.getParams().get(4) instanceof Number)) && ((ca.getParams().get(3) instanceof Number)) && ((ca.getParams().get(2) instanceof Number)) && ((ca.getParams().get(1) instanceof String)) && ((ca.getParams().get(0) instanceof Location[])))
    {
      EffectManager man = new EffectManager(Ancient.effectLib);
      
      Location[] loc = (Location[])ca.getParams().get(0);
      
      ParticleEffect particle = ParticleEffect.fromName((String)ca.getParams().get(1));
      
      int strands = ((Number)ca.getParams().get(2)).intValue();
      int particlesPerStrand = ((Number)ca.getParams().get(3)).intValue();
      
      float radius = ((Number)ca.getParams().get(4)).floatValue();
      float curve = ((Number)ca.getParams().get(5)).floatValue();
      
      double rotation = ((Number)ca.getParams().get(6)).doubleValue();
      
      int period = ((Number)ca.getParams().get(7)).intValue();
      int iterations = ((Number)ca.getParams().get(8)).intValue();
      if ((loc != null) && (loc.length > 0)) {
        for (Location l : loc) {
          if (l != null)
          {
            HelixEffect e = new HelixEffect(man);
            
            e.particle = particle;
            
            e.strands = strands;
            e.particles = particlesPerStrand;
            
            e.radius = radius;
            e.curve = curve;
            
            e.rotation = rotation;
            
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
