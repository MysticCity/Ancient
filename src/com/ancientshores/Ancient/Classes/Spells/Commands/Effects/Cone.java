package com.ancientshores.Ancient.Classes.Spells.Commands.Effects;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.Ancient.Classes.Spells.Commands.ICommand;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.ConeEffect;
import de.slikey.effectlib.util.ParticleEffect;
import java.util.LinkedList;
import org.bukkit.Location;

public class Cone
  extends ICommand
{
  @CommandDescription(description="<html>Creates a cone at the given location</html>", argnames={"location", "particlename", "cone-particles per iteration", "particles per cone", "rotation", "angular velocity", "length grow per iteration", "radius grow per iteration", "randomize", "period", "iterations"}, name="Cone", parameters={ParameterType.Location, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Boolean, ParameterType.Number, ParameterType.Number})
  public Cone()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Location, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Boolean, ParameterType.Number, ParameterType.Number };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    if ((ca.getParams().size() == 11) && ((ca.getParams().get(10) instanceof Number)) && ((ca.getParams().get(9) instanceof Number)) && ((ca.getParams().get(8) instanceof Boolean)) && ((ca.getParams().get(7) instanceof Number)) && ((ca.getParams().get(6) instanceof Number)) && ((ca.getParams().get(5) instanceof Number)) && ((ca.getParams().get(4) instanceof Number)) && ((ca.getParams().get(3) instanceof Number)) && ((ca.getParams().get(2) instanceof Number)) && ((ca.getParams().get(1) instanceof String)) && ((ca.getParams().get(0) instanceof Location[])))
    {
      EffectManager man = new EffectManager(Ancient.effectLib);
      
      Location[] loc = (Location[])ca.getParams().get(0);
      
      ParticleEffect particle = ParticleEffect.fromName((String)ca.getParams().get(1));
      
      int particles = ((Number)ca.getParams().get(2)).intValue();
      int particlesCone = ((Number)ca.getParams().get(3)).intValue();
      
      double rotation = ((Number)ca.getParams().get(4)).doubleValue();
      double angularVelocity = ((Number)ca.getParams().get(5)).doubleValue();
      float lengthGrow = ((Number)ca.getParams().get(6)).floatValue();
      float radiusGrow = ((Number)ca.getParams().get(7)).floatValue();
      
      boolean randomize = ((Boolean)ca.getParams().get(8)).booleanValue();
      
      int period = ((Number)ca.getParams().get(9)).intValue();
      int iterations = ((Number)ca.getParams().get(10)).intValue();
      if ((loc != null) && (loc.length > 0)) {
        for (Location l : loc) {
          if (l != null)
          {
            ConeEffect e = new ConeEffect(man);
            
            e.particle = particle;
            e.particles = particles;
            e.particlesCone = particlesCone;
            
            e.rotation = rotation;
            e.angularVelocity = angularVelocity;
            
            e.lengthGrow = lengthGrow;
            e.radiusGrow = radiusGrow;
            
            e.randomize = randomize;
            
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


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\Effects\Cone.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */