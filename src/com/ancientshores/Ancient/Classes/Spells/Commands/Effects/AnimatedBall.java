package com.ancientshores.Ancient.Classes.Spells.Commands.Effects;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.Ancient.Classes.Spells.Commands.ICommand;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.AnimatedBallEffect;
import de.slikey.effectlib.util.ParticleEffect;
import java.util.LinkedList;
import org.bukkit.Location;

public class AnimatedBall
  extends ICommand
{
  @CommandDescription(description="<html>Creates an animated ball at the given location</html>", argnames={"location", "particlename", "particles", "particles per iteration", "size", "x rotation", "y rotation", "z rotation", "x factor", "y factor", "z factor", "period", "iterations"}, name="AnimatedBall", parameters={ParameterType.Location, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number})
  public AnimatedBall()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Location, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    if ((ca.getParams().size() == 13) && ((ca.getParams().get(12) instanceof Number)) && ((ca.getParams().get(11) instanceof Number)) && ((ca.getParams().get(10) instanceof Number)) && ((ca.getParams().get(9) instanceof Number)) && ((ca.getParams().get(8) instanceof Number)) && ((ca.getParams().get(7) instanceof Number)) && ((ca.getParams().get(6) instanceof Number)) && ((ca.getParams().get(5) instanceof Number)) && ((ca.getParams().get(4) instanceof Number)) && ((ca.getParams().get(3) instanceof Number)) && ((ca.getParams().get(2) instanceof Number)) && ((ca.getParams().get(1) instanceof String)) && ((ca.getParams().get(0) instanceof Location[])))
    {
      EffectManager man = new EffectManager(Ancient.effectLib);
      
      Location[] loc = (Location[])ca.getParams().get(0);
      
      ParticleEffect particle = ParticleEffect.fromName((String)ca.getParams().get(1));
      
      int particles = ((Number)ca.getParams().get(2)).intValue();
      int particlesPerIteration = ((Number)ca.getParams().get(3)).intValue();
      
      float size = ((Number)ca.getParams().get(4)).floatValue();
      
      double xRotation = ((Number)ca.getParams().get(5)).doubleValue();
      double yRotation = ((Number)ca.getParams().get(6)).doubleValue();
      double zRotation = ((Number)ca.getParams().get(7)).doubleValue();
      
      float xFactor = ((Number)ca.getParams().get(8)).floatValue();
      float yFactor = ((Number)ca.getParams().get(9)).floatValue();
      float zFactor = ((Number)ca.getParams().get(10)).floatValue();
      
      int period = ((Number)ca.getParams().get(11)).intValue();
      int iterations = ((Number)ca.getParams().get(12)).intValue();
      if ((loc != null) && (loc.length > 0)) {
        for (Location l : loc) {
          if (l != null)
          {
            AnimatedBallEffect e = new AnimatedBallEffect(man);
            
            e.particle = particle;
            
            e.particles = particles;
            e.particlesPerIteration = particlesPerIteration;
            
            e.size = size;
            
            e.xRotation = xRotation;
            e.yRotation = yRotation;
            e.zRotation = zRotation;
            
            e.xFactor = xFactor;
            e.yFactor = yFactor;
            e.zFactor = zFactor;
            
            e.xOffset = (e.yOffset = e.zOffset = 0.0F);
            
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
