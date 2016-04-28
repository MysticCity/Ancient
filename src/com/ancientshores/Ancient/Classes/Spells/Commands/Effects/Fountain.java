package com.ancientshores.Ancient.Classes.Spells.Commands.Effects;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.Ancient.Classes.Spells.Commands.ICommand;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.FountainEffect;
import de.slikey.effectlib.util.ParticleEffect;
import java.util.LinkedList;
import org.bukkit.Location;

public class Fountain
  extends ICommand
{
  @CommandDescription(description="<html>Creates a fountain at the given location</html>", argnames={"location", "particlename", "strands", "particles per strand", "particles per iteration in spout", "radius", "radius of spout", "height", "height of spout", "rotation", "period", "iterations"}, name="Fountain", parameters={ParameterType.Location, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number})
  public Fountain()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Location, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    if ((ca.getParams().size() == 12) && ((ca.getParams().get(11) instanceof Number)) && ((ca.getParams().get(10) instanceof Number)) && ((ca.getParams().get(9) instanceof Number)) && ((ca.getParams().get(8) instanceof Number)) && ((ca.getParams().get(7) instanceof Number)) && ((ca.getParams().get(6) instanceof Number)) && ((ca.getParams().get(5) instanceof Number)) && ((ca.getParams().get(4) instanceof Number)) && ((ca.getParams().get(3) instanceof Number)) && ((ca.getParams().get(2) instanceof Number)) && ((ca.getParams().get(1) instanceof String)) && ((ca.getParams().get(0) instanceof Location[])))
    {
      EffectManager man = new EffectManager(Ancient.effectLib);
      
      Location[] loc = (Location[])ca.getParams().get(0);
      
      ParticleEffect particle = ParticleEffect.fromName((String)ca.getParams().get(1));
      
      int strands = ((Number)ca.getParams().get(2)).intValue();
      int particlesPerStrand = ((Number)ca.getParams().get(3)).intValue();
      int particlesPerIterationInSpout = ((Number)ca.getParams().get(4)).intValue();
      
      float radius = ((Number)ca.getParams().get(5)).floatValue();
      float radiusSpout = ((Number)ca.getParams().get(6)).floatValue();
      
      float height = ((Number)ca.getParams().get(7)).floatValue();
      float heightSpout = ((Number)ca.getParams().get(8)).floatValue();
      
      double rotation = ((Number)ca.getParams().get(9)).doubleValue();
      
      int period = ((Number)ca.getParams().get(10)).intValue();
      int iterations = ((Number)ca.getParams().get(11)).intValue();
      if ((loc != null) && (loc.length > 0)) {
        for (Location l : loc) {
          if (l != null)
          {
            FountainEffect e = new FountainEffect(man);
            
            e.particle = particle;
            
            e.strands = strands;
            e.particlesStrand = particlesPerStrand;
            e.particlesSpout = particlesPerIterationInSpout;
            
            e.radius = radius;
            e.radiusSpout = radiusSpout;
            
            e.height = height;
            e.heightSpout = heightSpout;
            
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
