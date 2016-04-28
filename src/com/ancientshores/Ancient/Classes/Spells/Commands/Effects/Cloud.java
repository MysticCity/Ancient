package com.ancientshores.Ancient.Classes.Spells.Commands.Effects;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.Ancient.Classes.Spells.Commands.ICommand;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.CloudEffect;
import de.slikey.effectlib.util.ParticleEffect;
import java.util.LinkedList;
import org.bukkit.Location;

public class Cloud
  extends ICommand
{
  @CommandDescription(description="<html>Creates a cloud at the given location</html>", argnames={"location", "cloud particlename", "rain particlename", "cloud size", "rain particle radius", "period", "iterations"}, name="Cloud", parameters={ParameterType.Location, ParameterType.String, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number})
  public Cloud()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Location, ParameterType.String, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    if ((ca.getParams().size() == 7) && ((ca.getParams().get(6) instanceof Number)) && ((ca.getParams().get(5) instanceof Number)) && ((ca.getParams().get(4) instanceof Number)) && ((ca.getParams().get(3) instanceof Number)) && ((ca.getParams().get(2) instanceof String)) && ((ca.getParams().get(1) instanceof String)) && ((ca.getParams().get(0) instanceof Location[])))
    {
      EffectManager man = new EffectManager(Ancient.effectLib);
      
      Location[] loc = (Location[])ca.getParams().get(0);
      
      ParticleEffect cloudParticle = ParticleEffect.fromName((String)ca.getParams().get(1));
      ParticleEffect rainParticle = ParticleEffect.fromName((String)ca.getParams().get(2));
      
      float cloudSize = ((Number)ca.getParams().get(3)).floatValue();
      float rainParticleRadius = ((Number)ca.getParams().get(4)).floatValue();
      
      int period = ((Number)ca.getParams().get(5)).intValue();
      int iterations = ((Number)ca.getParams().get(6)).intValue();
      if ((loc != null) && (loc.length > 0)) {
        for (Location l : loc) {
          if (l != null)
          {
            CloudEffect e = new CloudEffect(man);
            e.cloudParticle = cloudParticle;
            e.mainParticle = rainParticle;
            
            e.cloudSize = cloudSize;
            e.particleRadius = rainParticleRadius;
            
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
