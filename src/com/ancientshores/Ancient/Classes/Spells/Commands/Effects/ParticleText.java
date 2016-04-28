package com.ancientshores.Ancient.Classes.Spells.Commands.Effects;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.Ancient.Classes.Spells.Commands.ICommand;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.TextEffect;
import de.slikey.effectlib.util.ParticleEffect;
import java.awt.Font;
import java.util.LinkedList;
import org.bukkit.Location;

public class ParticleText
  extends ICommand
{
  @CommandDescription(description="<html>Creates a text consisting of particles at the given location</html>", argnames={"location", "particlename", "text", "invert", "only show every x. pixel", "only show every y. pixel", "size", "font", "period", "iterations"}, name="ParticleText", parameters={ParameterType.Location, ParameterType.String, ParameterType.String, ParameterType.Boolean, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.String, ParameterType.Number, ParameterType.Number})
  public ParticleText()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Location, ParameterType.String, ParameterType.String, ParameterType.Boolean, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.String, ParameterType.Number, ParameterType.Number };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    if ((ca.getParams().size() == 10) && ((ca.getParams().get(9) instanceof Number)) && ((ca.getParams().get(8) instanceof Number)) && ((ca.getParams().get(7) instanceof String)) && ((ca.getParams().get(6) instanceof Number)) && ((ca.getParams().get(5) instanceof Number)) && ((ca.getParams().get(4) instanceof Number)) && ((ca.getParams().get(3) instanceof Boolean)) && ((ca.getParams().get(2) instanceof String)) && ((ca.getParams().get(1) instanceof String)) && ((ca.getParams().get(0) instanceof Location[])))
    {
      EffectManager man = new EffectManager(Ancient.effectLib);
      
      Location[] loc = (Location[])ca.getParams().get(0);
      
      ParticleEffect particle = ParticleEffect.fromName((String)ca.getParams().get(1));
      
      String text = (String)ca.getParams().get(2);
      
      boolean invert = ((Boolean)ca.getParams().get(3)).booleanValue();
      
      int stepX = ((Number)ca.getParams().get(4)).intValue();
      int stepY = ((Number)ca.getParams().get(5)).intValue();
      
      float size = ((Number)ca.getParams().get(6)).floatValue();
      
      Font font = Font.decode((String)ca.getParams().get(7));
      
      int period = ((Number)ca.getParams().get(8)).intValue();
      int iterations = ((Number)ca.getParams().get(9)).intValue();
      if ((loc != null) && (loc.length > 0)) {
        for (Location l : loc) {
          if (l != null)
          {
            TextEffect e = new TextEffect(man);
            
            e.particle = particle;
            
            e.text = text;
            
            e.invert = invert;
            
            e.stepX = stepX;
            e.stepY = stepY;
            
            e.size = size;
            
            e.font = font;
            
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


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\Effects\ParticleText.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */