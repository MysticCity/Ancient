package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import java.util.LinkedList;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class WeakenCommand
  extends ICommand
{
  @CommandDescription(description="<html>Weakens the player for the time with the specified strength</html>", argnames={"entity", "duration", "amplifier"}, name="Weaken", parameters={ParameterType.Entity, ParameterType.Number, ParameterType.Number})
  public WeakenCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Entity, ParameterType.Number, ParameterType.Number };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    if ((ca.getParams().size() == 3) && ((ca.getParams().get(0) instanceof Entity[])) && ((ca.getParams().get(1) instanceof Number)) && ((ca.getParams().get(2) instanceof Number)))
    {
      Entity[] players = (Entity[])ca.getParams().get(0);
      int time = (int)((Number)ca.getParams().get(1)).doubleValue();
      int amplifier = (int)((Number)ca.getParams().get(2)).doubleValue();
      for (Entity p : players) {
        if ((p != null) && ((p instanceof LivingEntity)))
        {
          int t = Math.round(time / 50);
          if (t == 0) {
            t = Integer.MAX_VALUE;
          }
          ((LivingEntity)p).addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, t, amplifier));
        }
      }
      return true;
    }
    return false;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\WeakenCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */