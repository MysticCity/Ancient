package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import java.util.LinkedList;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageModifyCommand
  extends ICommand
{
  @CommandDescription(description="<html>Modifies the damage of the current damage event</html>", argnames={"modifier"}, name="DamageModify", parameters={ParameterType.Number})
  public DamageModifyCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Number };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    if ((ca.getParams().size() == 1) && 
      ((ca.getParams().get(0) instanceof Number)))
    {
      int percent = (int)((Number)ca.getParams().get(0)).doubleValue();
      if ((ca.getSpellInfo().mEvent instanceof EntityDamageEvent))
      {
        EntityDamageEvent event = (EntityDamageEvent)ca.getSpellInfo().mEvent;
        event.setDamage(Math.round(event.getDamage() * percent / 100.0D));
        if (event.getDamage() == 0.0D) {
          event.setCancelled(true);
        }
        return true;
      }
    }
    return false;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\DamageModifyCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */