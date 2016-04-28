package com.ancientshores.Ancient.Classes.Spells.Commands.Effects;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.Ancient.Classes.Spells.Commands.ICommand;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.HeartEffect;
import de.slikey.effectlib.util.ParticleEffect;
import java.util.LinkedList;
import org.bukkit.Location;

public class Heart
  extends ICommand
{
  @CommandDescription(description="<html>Creates a heart at the given location</html>", argnames={"location", "particlename", "particles", "x rotation", "y rotation", "z rotation", "x factor", "y factor", "factor of inner spike (the \\/)", "compress factor along y axis", "compilation"}, name="Heart", parameters={ParameterType.Location, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number})
  public Heart()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Location, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    if ((ca.getParams().size() == 11) && ((ca.getParams().get(10) instanceof Number)) && ((ca.getParams().get(9) instanceof Number)) && ((ca.getParams().get(8) instanceof Number)) && ((ca.getParams().get(7) instanceof Number)) && ((ca.getParams().get(6) instanceof Number)) && ((ca.getParams().get(5) instanceof Number)) && ((ca.getParams().get(4) instanceof Number)) && ((ca.getParams().get(3) instanceof Number)) && ((ca.getParams().get(2) instanceof Number)) && ((ca.getParams().get(1) instanceof String)) && ((ca.getParams().get(0) instanceof Location[])))
    {
      EffectManager man = new EffectManager(Ancient.effectLib);
      
      Location[] loc = (Location[])ca.getParams().get(0);
      
      ParticleEffect particle = ParticleEffect.fromName((String)ca.getParams().get(1));
      
      int particles = ((Number)ca.getParams().get(2)).intValue();
      
      double xRotation = ((Number)ca.getParams().get(3)).doubleValue();
      double yRotation = ((Number)ca.getParams().get(4)).doubleValue();
      double zRotation = ((Number)ca.getParams().get(5)).doubleValue();
      
      double xFactor = ((Number)ca.getParams().get(6)).doubleValue();
      double yFactor = ((Number)ca.getParams().get(7)).doubleValue();
      
      double factorInnerSpike = ((Number)ca.getParams().get(8)).doubleValue();
      double totalCompressFactorY = ((Number)ca.getParams().get(9)).doubleValue();
      
      float compilation = ((Number)ca.getParams().get(10)).floatValue();
      if ((loc != null) && (loc.length > 0)) {
        for (Location l : loc) {
          if (l != null)
          {
            HeartEffect e = new HeartEffect(man);
            
            e.particle = particle;
            e.particles = particles;
            
            e.xRotation = xRotation;
            e.yRotation = yRotation;
            e.zRotation = zRotation;
            
            e.xFactor = xFactor;
            e.yFactor = yFactor;
            
            e.factorInnerSpike = factorInnerSpike;
            e.compressYFactorTotal = totalCompressFactorY;
            
            e.compilaction = compilation;
            
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


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\Effects\Heart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */