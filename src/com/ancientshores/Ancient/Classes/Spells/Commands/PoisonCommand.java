package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import java.util.LinkedList;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PoisonCommand
  extends ICommand
{
  @CommandDescription(description="<html>Poisons the target for the specified amount of time</html>", argnames={"entity", "duration", "amplifier"}, name="Poison", parameters={ParameterType.Entity, ParameterType.Number, ParameterType.Number})
  public PoisonCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Entity, ParameterType.Number, ParameterType.Number };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    try
    {
      if ((ca.getParams().size() == 3) && ((ca.getParams().get(0) instanceof Entity[])) && ((ca.getParams().get(1) instanceof Number)) && ((ca.getParams().get(2) instanceof Number)))
      {
        Entity[] entities = (Entity[])ca.getParams().getFirst();
        int time = (int)((Number)ca.getParams().get(1)).doubleValue();
        int amplifier = (int)((Number)ca.getParams().get(2)).doubleValue();
        for (Entity e : entities)
        {
          if (!(e instanceof LivingEntity)) {
            break;
          }
          int t = Math.round(time / 50);
          if (t == 0) {
            t = Integer.MAX_VALUE;
          }
          ((LivingEntity)e).addPotionEffect(new PotionEffect(PotionEffectType.POISON, t, amplifier));
        }
        return true;
      }
    }
    catch (IndexOutOfBoundsException ignored) {}
    return false;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\PoisonCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */