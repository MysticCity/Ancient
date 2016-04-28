package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import java.util.LinkedList;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SpeedupCommand
  extends ICommand
{
  @CommandDescription(description="<html>Speeds up the player for the specified amount of time</html>", argnames={"entity", "duration"}, name="Speedup", parameters={ParameterType.Entity, ParameterType.Number})
  public SpeedupCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Entity, ParameterType.Number };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    if ((ca.getParams().size() >= 2) && 
      ((ca.getParams().get(0) instanceof Entity[])) && ((ca.getParams().get(1) instanceof Number)))
    {
      Entity[] entities = (Entity[])ca.getParams().get(0);
      int time = (int)((Number)ca.getParams().get(1)).doubleValue();
      int amplifier = 2;
      if ((ca.getParams().size() == 3) && ((ca.getParams().get(2) instanceof Number))) {
        amplifier = (int)((Number)ca.getParams().get(2)).doubleValue();
      }
      for (Entity e : entities) {
        if ((e != null) && ((e instanceof LivingEntity)))
        {
          int t = Math.round(time / 50);
          if (t == 0) {
            t = Integer.MAX_VALUE;
          }
          ((LivingEntity)e).addPotionEffect(new PotionEffect(PotionEffectType.SPEED, t, amplifier));
        }
      }
      return true;
    }
    return false;
  }
}
