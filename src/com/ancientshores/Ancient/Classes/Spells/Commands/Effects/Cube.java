package com.ancientshores.Ancient.Classes.Spells.Commands.Effects;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.Ancient.Classes.Spells.Commands.ICommand;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.CubeEffect;
import de.slikey.effectlib.util.ParticleEffect;
import java.util.LinkedList;
import org.bukkit.Location;

public class Cube
  extends ICommand
{
  @CommandDescription(description="<html>Creates a cube at the given location</html>", argnames={"location", "particlename", "particles", "edge length", "enable rotation", "angular velocity x", "angular velocity y", "angular velocity z", "outline only", "period", "iterations"}, name="Cube", parameters={ParameterType.Location, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Boolean, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Boolean, ParameterType.Number, ParameterType.Number})
  public Cube()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Location, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Boolean, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Boolean, ParameterType.Number, ParameterType.Number };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    if ((ca.getParams().size() == 11) && ((ca.getParams().get(10) instanceof Number)) && ((ca.getParams().get(9) instanceof Number)) && ((ca.getParams().get(8) instanceof Boolean)) && ((ca.getParams().get(7) instanceof Number)) && ((ca.getParams().get(6) instanceof Number)) && ((ca.getParams().get(5) instanceof Number)) && ((ca.getParams().get(4) instanceof Boolean)) && ((ca.getParams().get(3) instanceof Number)) && ((ca.getParams().get(2) instanceof Number)) && ((ca.getParams().get(1) instanceof String)) && ((ca.getParams().get(0) instanceof Location[])))
    {
      EffectManager man = new EffectManager(Ancient.effectLib);
      
      Location[] loc = (Location[])ca.getParams().get(0);
      
      ParticleEffect particle = ParticleEffect.fromName((String)ca.getParams().get(1));
      
      int particles = ((Number)ca.getParams().get(2)).intValue();
      
      float edgeLength = ((Number)ca.getParams().get(3)).floatValue();
      
      boolean enableRotation = ((Boolean)ca.getParams().get(4)).booleanValue();
      
      double angularVelocityX = ((Number)ca.getParams().get(5)).doubleValue();
      double angularVelocityY = ((Number)ca.getParams().get(6)).doubleValue();
      double angularVelocityZ = ((Number)ca.getParams().get(7)).doubleValue();
      
      boolean outlineOnly = ((Boolean)ca.getParams().get(8)).booleanValue();
      
      int period = ((Number)ca.getParams().get(9)).intValue();
      int iterations = ((Number)ca.getParams().get(10)).intValue();
      if ((loc != null) && (loc.length > 0)) {
        for (Location l : loc) {
          if (l != null)
          {
            CubeEffect e = new CubeEffect(man);
            
            e.particle = particle;
            e.particles = particles;
            
            e.edgeLength = edgeLength;
            
            e.enableRotation = enableRotation;
            e.angularVelocityX = angularVelocityX;
            e.angularVelocityY = angularVelocityY;
            e.angularVelocityZ = angularVelocityZ;
            
            e.outlineOnly = outlineOnly;
            
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
