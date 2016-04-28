package com.ancientshores.Ancient.Classes.Spells.Commands.Effects;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.Ancient.Classes.Spells.Commands.ICommand;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.DonutEffect;
import de.slikey.effectlib.util.ParticleEffect;
import java.util.LinkedList;
import org.bukkit.Location;

public class Donut
  extends ICommand
{
  @CommandDescription(description="<html>Creates a donut at the given location</html>", argnames={"location", "particlename", "particles per circle", "circles", "radius donut", "radius tube", "x rotation", "y rotation", "z rotation", "period", "iterations"}, name="Donut", parameters={ParameterType.Location, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number})
  public Donut()
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
      
      int particlesPerCircle = ((Number)ca.getParams().get(2)).intValue();
      int circles = ((Number)ca.getParams().get(3)).intValue();
      
      float radiusDonut = ((Number)ca.getParams().get(4)).floatValue();
      float radiusTube = ((Number)ca.getParams().get(5)).floatValue();
      
      double xRotation = ((Number)ca.getParams().get(6)).doubleValue();
      double yRotation = ((Number)ca.getParams().get(7)).doubleValue();
      double zRotation = ((Number)ca.getParams().get(8)).doubleValue();
      
      int period = ((Number)ca.getParams().get(9)).intValue();
      int iterations = ((Number)ca.getParams().get(10)).intValue();
      if ((loc != null) && (loc.length > 0)) {
        for (Location l : loc) {
          if (l != null)
          {
            DonutEffect e = new DonutEffect(man);
            
            e.particle = particle;
            
            e.particlesCircle = particlesPerCircle;
            e.circles = circles;
            
            e.radiusDonut = radiusDonut;
            e.radiusTube = radiusTube;
            
            e.xRotation = xRotation;
            e.yRotation = yRotation;
            e.zRotation = zRotation;
            
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
