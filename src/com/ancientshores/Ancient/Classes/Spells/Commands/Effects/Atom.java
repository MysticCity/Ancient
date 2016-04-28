package com.ancientshores.Ancient.Classes.Spells.Commands.Effects;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.Ancient.Classes.Spells.Commands.ICommand;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.AtomEffect;
import de.slikey.effectlib.util.ParticleEffect;
import java.util.LinkedList;
import org.bukkit.Location;

public class Atom
  extends ICommand
{
  @CommandDescription(description="<html>Creates an animated ball at the given location</html>", argnames={"location", "particlename nucleus", "particlename orbital", "radius", "radiusNucleus", "particels nucleus", "particels orbital", "orbitals", "rotation", "angular velocity", "period", "iterations"}, name="Atom", parameters={ParameterType.Location, ParameterType.String, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number})
  public Atom()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Location, ParameterType.String, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    if ((ca.getParams().size() == 12) && ((ca.getParams().get(11) instanceof Number)) && ((ca.getParams().get(10) instanceof Number)) && ((ca.getParams().get(9) instanceof Number)) && ((ca.getParams().get(8) instanceof Number)) && ((ca.getParams().get(7) instanceof Number)) && ((ca.getParams().get(6) instanceof Number)) && ((ca.getParams().get(5) instanceof Number)) && ((ca.getParams().get(4) instanceof Number)) && ((ca.getParams().get(3) instanceof Number)) && ((ca.getParams().get(2) instanceof String)) && ((ca.getParams().get(1) instanceof String)) && ((ca.getParams().get(0) instanceof Location[])))
    {
      EffectManager man = new EffectManager(Ancient.effectLib);
      
      Location[] loc = (Location[])ca.getParams().get(0);
      
      ParticleEffect particleNucleus = ParticleEffect.fromName((String)ca.getParams().get(1));
      ParticleEffect particleOrbital = ParticleEffect.fromName((String)ca.getParams().get(2));
      int radius = ((Number)ca.getParams().get(3)).intValue();
      float radiusNucleus = ((Number)ca.getParams().get(4)).floatValue();
      int particlesNucleus = ((Number)ca.getParams().get(5)).intValue();
      int particlesOrbital = ((Number)ca.getParams().get(6)).intValue();
      int orbitals = ((Number)ca.getParams().get(7)).intValue();
      double rotation = ((Number)ca.getParams().get(8)).doubleValue();
      double angularVelocity = ((Number)ca.getParams().get(9)).doubleValue();
      int period = ((Number)ca.getParams().get(10)).intValue();
      int iterations = ((Number)ca.getParams().get(11)).intValue();
      if ((loc != null) && (loc.length > 0)) {
        for (Location l : loc) {
          if (l != null)
          {
            AtomEffect e = new AtomEffect(man);
            e.particleNucleus = particleNucleus;
            e.particleOrbital = particleOrbital;
            e.radius = radius;
            e.radiusNucleus = radiusNucleus;
            e.particlesNucleus = particlesNucleus;
            e.particlesOrbital = particlesOrbital;
            e.orbitals = orbitals;
            e.rotation = rotation;
            e.angularVelocity = angularVelocity;
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


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\Effects\Atom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */