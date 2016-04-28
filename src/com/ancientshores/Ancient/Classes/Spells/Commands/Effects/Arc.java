package com.ancientshores.Ancient.Classes.Spells.Commands.Effects;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.Ancient.Classes.Spells.Commands.ICommand;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.ArcEffect;
import de.slikey.effectlib.util.ParticleEffect;
import java.util.LinkedList;
import org.bukkit.Location;

public class Arc
  extends ICommand
{
  @CommandDescription(description="<html>Creates an arc from the first to the second location</html>", argnames={"location from", "location to", "particlename", "particles", "height", "period", "iterations"}, name="Arc", parameters={ParameterType.Location, ParameterType.Location, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number})
  public Arc()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Location, ParameterType.Location, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    if ((ca.getParams().size() == 7) && ((ca.getParams().get(6) instanceof Number)) && ((ca.getParams().get(5) instanceof Number)) && ((ca.getParams().get(4) instanceof Number)) && ((ca.getParams().get(3) instanceof Number)) && ((ca.getParams().get(2) instanceof String)) && ((ca.getParams().get(1) instanceof Location[])) && ((ca.getParams().get(0) instanceof Location[])))
    {
      EffectManager man = new EffectManager(Ancient.effectLib);
      
      Location[] loc1 = (Location[])ca.getParams().get(0);
      Location[] loc2 = (Location[])ca.getParams().get(1);
      
      ParticleEffect particle = ParticleEffect.fromName((String)ca.getParams().get(2));
      
      int particles = ((Number)ca.getParams().get(3)).intValue();
      float height = ((Number)ca.getParams().get(4)).floatValue();
      
      int period = ((Number)ca.getParams().get(5)).intValue();
      int iterations = ((Number)ca.getParams().get(6)).intValue();
      if ((loc1 != null) && (loc2 != null) && (loc1.length != 0) && (loc2.length != 0))
      {
        ArcEffect e = new ArcEffect(man);
        
        e.particle = particle;
        
        e.particles = particles;
        e.height = height;
        
        e.period = period;
        e.iterations = iterations;
        
        e.setLocation(loc1[0]);
        e.setTarget(loc2[0]);
        
        e.start();
      }
      man.disposeOnTermination();
      return true;
    }
    return false;
  }
}
