package com.ancientshores.Ancient.Classes.Spells.Commands.Effects;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.Ancient.Classes.Spells.Commands.ICommand;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.TornadoEffect;
import de.slikey.effectlib.util.ParticleEffect;
import java.util.LinkedList;
import org.bukkit.Location;

public class Tornado
  extends ICommand
{
  @CommandDescription(description="<html>Creates a tornado at the given location</html>", argnames={"location", "tornado particlename", "cloud particlename", "cloud size", "y offset", "height of tornado", "max tornado radius", "show cloud", "show tornado", "distance between rows", "period", "iterations"}, name="Tornado", parameters={ParameterType.Location, ParameterType.String, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Boolean, ParameterType.Boolean, ParameterType.Number, ParameterType.Number, ParameterType.Number})
  public Tornado()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Location, ParameterType.String, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Boolean, ParameterType.Boolean, ParameterType.Number, ParameterType.Number, ParameterType.Number };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    if ((ca.getParams().size() == 12) && ((ca.getParams().get(11) instanceof Number)) && ((ca.getParams().get(10) instanceof Number)) && ((ca.getParams().get(9) instanceof Number)) && ((ca.getParams().get(8) instanceof Boolean)) && ((ca.getParams().get(7) instanceof Boolean)) && ((ca.getParams().get(6) instanceof Number)) && ((ca.getParams().get(5) instanceof Number)) && ((ca.getParams().get(4) instanceof Number)) && ((ca.getParams().get(3) instanceof Number)) && ((ca.getParams().get(2) instanceof String)) && ((ca.getParams().get(1) instanceof String)) && ((ca.getParams().get(0) instanceof Location[])))
    {
      EffectManager man = new EffectManager(Ancient.effectLib);
      
      Location[] loc = (Location[])ca.getParams().get(0);
      
      ParticleEffect tornadoParticle = ParticleEffect.fromName((String)ca.getParams().get(1));
      ParticleEffect cloudParticle = ParticleEffect.fromName((String)ca.getParams().get(2));
      
      float cloudSize = ((Number)ca.getParams().get(3)).floatValue();
      
      double yOffset = ((Number)ca.getParams().get(4)).doubleValue();
      
      float tornadoHeight = ((Number)ca.getParams().get(5)).floatValue();
      float maxTornadoRadius = ((Number)ca.getParams().get(6)).floatValue();
      
      boolean showCloud = ((Boolean)ca.getParams().get(7)).booleanValue();
      boolean showTornado = ((Boolean)ca.getParams().get(8)).booleanValue();
      
      double rowDistance = ((Number)ca.getParams().get(9)).doubleValue();
      
      int period = ((Number)ca.getParams().get(10)).intValue();
      int iterations = ((Number)ca.getParams().get(11)).intValue();
      if ((loc != null) && (loc.length > 0)) {
        for (Location l : loc) {
          if (l != null)
          {
            TornadoEffect e = new TornadoEffect(man);
            
            e.tornadoParticle = tornadoParticle;
            e.cloudParticle = cloudParticle;
            
            e.cloudSize = cloudSize;
            
            e.yOffset = yOffset;
            
            e.tornadoHeight = tornadoHeight;
            e.maxTornadoRadius = maxTornadoRadius;
            
            e.showCloud = showCloud;
            e.showTornado = showTornado;
            
            e.distance = rowDistance;
            
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


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\Effects\Tornado.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */