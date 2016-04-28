package com.ancientshores.Ancient.Classes.Spells.Commands.Effects;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.Ancient.Classes.Spells.Commands.ICommand;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.CircleEffect;
import de.slikey.effectlib.util.ParticleEffect;
import java.util.LinkedList;
import org.bukkit.Location;

public class Circle
  extends ICommand
{
  @CommandDescription(description="<html>Creates a (rotating) circle at the given location</html>", argnames={"location", "particlename", "particles", "radius", "period", "iterations", "x rotation", "y rotation", "z rotation", "rotating", "angular velocity x", "angular velocity y", "angular velocity z"}, name="Circle", parameters={ParameterType.Location, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Boolean, ParameterType.Number, ParameterType.Number, ParameterType.Number})
  public Circle()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Location, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Boolean, ParameterType.Number, ParameterType.Number, ParameterType.Number };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    if ((ca.getParams().size() == 13) && ((ca.getParams().get(12) instanceof Number)) && ((ca.getParams().get(11) instanceof Number)) && ((ca.getParams().get(10) instanceof Number)) && ((ca.getParams().get(9) instanceof Boolean)) && ((ca.getParams().get(8) instanceof Number)) && ((ca.getParams().get(7) instanceof Number)) && ((ca.getParams().get(6) instanceof Number)) && ((ca.getParams().get(5) instanceof Number)) && ((ca.getParams().get(4) instanceof Number)) && ((ca.getParams().get(3) instanceof Number)) && ((ca.getParams().get(2) instanceof Number)) && ((ca.getParams().get(1) instanceof String)) && ((ca.getParams().get(0) instanceof Location[])))
    {
      EffectManager man = new EffectManager(Ancient.effectLib);
      
      Location[] loc = (Location[])ca.getParams().get(0);
      
      ParticleEffect particle = ParticleEffect.fromName((String)ca.getParams().get(1));
      
      int particles = ((Number)ca.getParams().get(2)).intValue();
      
      float radius = ((Number)ca.getParams().get(3)).floatValue();
      
      int period = ((Number)ca.getParams().get(4)).intValue();
      int iterations = ((Number)ca.getParams().get(5)).intValue();
      
      double xRotation = ((Number)ca.getParams().get(6)).doubleValue();
      double yRotation = ((Number)ca.getParams().get(7)).doubleValue();
      double zRotation = ((Number)ca.getParams().get(8)).doubleValue();
      
      boolean rotating = ((Boolean)ca.getParams().get(9)).booleanValue();
      
      double angularVelocityX = ((Number)ca.getParams().get(10)).doubleValue();
      double angularVelocityY = ((Number)ca.getParams().get(11)).doubleValue();
      double angularVelocityZ = ((Number)ca.getParams().get(12)).doubleValue();
      if ((loc != null) && (loc.length > 0)) {
        for (Location l : loc) {
          if (l != null)
          {
            CircleEffect e = new CircleEffect(man);
            
            e.particle = particle;
            e.particles = particles;
            e.radius = radius;
            
            e.period = period;
            e.iterations = iterations;
            
            e.xRotation = xRotation;
            e.yRotation = yRotation;
            e.zRotation = zRotation;
            
            e.enableRotation = rotating;
            
            e.angularVelocityX = angularVelocityX;
            e.angularVelocityY = angularVelocityY;
            e.angularVelocityZ = angularVelocityZ;
            
            l = l.clone();
            l.setZ(l.getZ());
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


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\Effects\Circle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */