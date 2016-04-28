package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import java.util.LinkedList;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class LaunchFirework
  extends ICommand
{
  @CommandDescription(description="<html>Launches a firework at the location with the effect type and the color (must be specified in 'r, g, b')</html>", argnames={"location", "effectname", "color", "flickering", "trail"}, name="LaunchFirework", parameters={ParameterType.Location, ParameterType.String, ParameterType.String, ParameterType.Boolean, ParameterType.Boolean})
  public LaunchFirework()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Location, ParameterType.String, ParameterType.String, ParameterType.Boolean, ParameterType.Boolean };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    if ((ca.getParams().size() == 5) && ((ca.getParams().get(1) instanceof String)) && ((ca.getParams().get(2) instanceof String)) && ((ca.getParams().get(3) instanceof Boolean)) && ((ca.getParams().get(4) instanceof Boolean)) && ((ca.getParams().get(0) instanceof Location[])))
    {
      Location[] locs = (Location[])ca.getParams().get(0);
      String name = (String)ca.getParams().get(1);
      String colorname = (String)ca.getParams().get(2);
      boolean flickering = ((Boolean)ca.getParams().get(3)).booleanValue();
      boolean trail = ((Boolean)ca.getParams().get(4)).booleanValue();
      Color c = getColorByString(colorname);
      FireworkEffect.Type effect = getEntityEffectByName(name);
      if (effect == null) {
        return false;
      }
      if ((locs != null) && (locs.length > 0) && (locs[0] != null))
      {
        for (Location l : locs) {
          if (l != null)
          {
            Firework fw = (Firework)l.getWorld().spawnEntity(l, EntityType.FIREWORK);
            FireworkMeta fwm = fw.getFireworkMeta();
            FireworkEffect.Builder builder = FireworkEffect.builder();
            builder.with(effect);
            builder.flicker(flickering);
            builder.trail(trail);
            builder.withColor(c);
            fwm.setPower(2);
            fwm.addEffect(builder.build());
            fw.setFireworkMeta(fwm);
          }
        }
        return true;
      }
    }
    return false;
  }
  
  public static FireworkEffect.Type getEntityEffectByName(String s)
  {
    s = s.replace("_", "");
    for (FireworkEffect.Type e : FireworkEffect.Type.values())
    {
      String ename = e.name().replace("_", "");
      if (ename.equalsIgnoreCase(s)) {
        return e;
      }
    }
    return null;
  }
  
  public static Color getColorByString(String s)
  {
    try
    {
      String[] args = s.split(",");
      int r = Integer.parseInt(args[0].trim());
      int g = Integer.parseInt(args[1].trim());
      int b = Integer.parseInt(args[2].trim());
      return Color.fromBGR(b, g, r);
    }
    catch (Exception e) {}
    return null;
  }
}
