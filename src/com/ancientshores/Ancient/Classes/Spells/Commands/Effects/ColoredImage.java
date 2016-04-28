package com.ancientshores.Ancient.Classes.Spells.Commands.Effects;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.Ancient.Classes.Spells.Commands.ICommand;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.ColoredImageEffect;
import de.slikey.effectlib.effect.ColoredImageEffect.Plane;
import de.slikey.effectlib.util.ParticleEffect;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import org.bukkit.Location;

public class ColoredImage
  extends ICommand
{
  @CommandDescription(description="<html>Shows a colored image at the given location</html>", argnames={"location", "particlename", "file name", "show every x pixel", "show every y pixel", "size", "enable rotation", "rotation axis", "angular velocity x", "angular velocity y", "angular velocity z", "period", "iterations"}, name="ColoredImage", parameters={ParameterType.Location, ParameterType.String, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Boolean, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number})
  public ColoredImage()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Location, ParameterType.String, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Boolean, ParameterType.String, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number, ParameterType.Number };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    if ((ca.getParams().size() == 13) && ((ca.getParams().get(12) instanceof Number)) && ((ca.getParams().get(11) instanceof Number)) && ((ca.getParams().get(10) instanceof Number)) && ((ca.getParams().get(9) instanceof Number)) && ((ca.getParams().get(8) instanceof Number)) && ((ca.getParams().get(7) instanceof String)) && ((ca.getParams().get(6) instanceof Boolean)) && ((ca.getParams().get(5) instanceof Number)) && ((ca.getParams().get(4) instanceof Number)) && ((ca.getParams().get(3) instanceof Number)) && ((ca.getParams().get(2) instanceof String)) && ((ca.getParams().get(1) instanceof String)) && ((ca.getParams().get(0) instanceof Location[])))
    {
      EffectManager man = new EffectManager(Ancient.effectLib);
      
      Location[] loc = (Location[])ca.getParams().get(0);
      
      ParticleEffect particle = ParticleEffect.fromName((String)ca.getParams().get(1));
      
      File file = new File(Ancient.plugin.getDataFolder().getPath() + "/images/" + (String)ca.getParams().get(2));
      
      int stepX = ((Number)ca.getParams().get(3)).intValue();
      int stepY = ((Number)ca.getParams().get(4)).intValue();
      float size = ((Number)ca.getParams().get(5)).floatValue();
      
      boolean enableRotation = ((Boolean)ca.getParams().get(6)).booleanValue();
      String rotationPlane = (String)ca.getParams().get(7);
      
      double angularVelocityX = ((Number)ca.getParams().get(8)).doubleValue();
      double angularVelocityY = ((Number)ca.getParams().get(9)).doubleValue();
      double angularVelocityZ = ((Number)ca.getParams().get(10)).doubleValue();
      
      int period = ((Number)ca.getParams().get(11)).intValue();
      int iterations = ((Number)ca.getParams().get(12)).intValue();
      try
      {
        if ((loc != null) && (loc.length > 0)) {
          for (Location l : loc) {
            if (l != null)
            {
              ColoredImageEffect e = new ColoredImageEffect(man);
              
              e.loadFile(file);
              
              e.particle = particle;
              
              e.stepX = stepX;
              e.stepY = stepY;
              
              e.size = size;
              
              e.enableRotation = enableRotation;
              e.plane = ColoredImageEffect.Plane.valueOf(rotationPlane);
              
              e.angularVelocityX = angularVelocityX;
              e.angularVelocityY = angularVelocityY;
              e.angularVelocityZ = angularVelocityZ;
              
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
      catch (IOException ex)
      {
        ex.printStackTrace();
      }
    }
    return false;
  }
}
