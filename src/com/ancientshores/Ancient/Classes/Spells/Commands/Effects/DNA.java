package com.ancientshores.Ancient.Classes.Spells.Commands.Effects;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.Ancient.Classes.Spells.Commands.ICommand;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.DnaEffect;
import de.slikey.effectlib.util.ParticleEffect;
import java.util.LinkedList;
import org.bukkit.Location;

public class DNA
  extends ICommand
{
  @CommandDescription(description="<html>Creates a dna helix at the given location</html>", argnames={"location", "helix particlename", "base 1 particlename", "base 2 particlename", "radial turns", "radius", "particles helix per iteration", "particles per base", "length", "grow", "base interval", "period", "iteration"}, name="DNA", parameters={ParameterType.Location, ParameterType.String, ParameterType.String, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number})
  public DNA()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Location, ParameterType.String, ParameterType.String, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    if ((ca.getParams().size() == 13) && ((ca.getParams().get(12) instanceof Number)) && ((ca.getParams().get(11) instanceof Number)) && ((ca.getParams().get(10) instanceof Number)) && ((ca.getParams().get(9) instanceof Number)) && ((ca.getParams().get(8) instanceof Number)) && ((ca.getParams().get(7) instanceof Number)) && ((ca.getParams().get(6) instanceof Number)) && ((ca.getParams().get(5) instanceof Number)) && ((ca.getParams().get(4) instanceof Number)) && ((ca.getParams().get(3) instanceof String)) && ((ca.getParams().get(2) instanceof String)) && ((ca.getParams().get(1) instanceof String)) && ((ca.getParams().get(0) instanceof Location[])))
    {
      EffectManager man = new EffectManager(Ancient.effectLib);
      
      Location[] loc = (Location[])ca.getParams().get(0);
      
      ParticleEffect particleHelix = ParticleEffect.fromName((String)ca.getParams().get(1));
      ParticleEffect particleBase1 = ParticleEffect.fromName((String)ca.getParams().get(2));
      ParticleEffect particleBase2 = ParticleEffect.fromName((String)ca.getParams().get(3));
      
      double radials = ((Number)ca.getParams().get(4)).doubleValue();
      float radius = ((Number)ca.getParams().get(5)).floatValue();
      
      int particlesHelix = ((Number)ca.getParams().get(6)).intValue();
      int particlesBase = ((Number)ca.getParams().get(7)).intValue();
      
      float length = ((Number)ca.getParams().get(8)).floatValue();
      float grow = ((Number)ca.getParams().get(9)).floatValue();
      
      float baseInterval = ((Number)ca.getParams().get(10)).floatValue();
      
      int period = ((Number)ca.getParams().get(11)).intValue();
      int iterations = ((Number)ca.getParams().get(12)).intValue();
      if ((loc != null) && (loc.length > 0)) {
        for (Location l : loc) {
          if (l != null)
          {
            DnaEffect e = new DnaEffect(man);
            
            e.particleHelix = particleHelix;
            e.particleBase1 = particleBase1;
            e.particleBase2 = particleBase2;
            
            e.radials = radials;
            e.radius = radius;
            
            e.particlesHelix = particlesHelix;
            e.particlesBase = particlesBase;
            
            e.length = length;
            e.grow = grow;
            
            e.baseInterval = baseInterval;
            
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


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\Effects\DNA.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */