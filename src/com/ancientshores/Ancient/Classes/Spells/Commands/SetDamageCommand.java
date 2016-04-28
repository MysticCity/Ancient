package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import java.util.LinkedList;
import org.bukkit.event.entity.EntityDamageEvent;

public class SetDamageCommand
  extends ICommand
{
  @CommandDescription(description="<html>Sets the damage of a damage event (attackevent/damageevent/damagebyentityevent)</html>", argnames={"amount"}, name="SetDamage", parameters={ParameterType.Number})
  public SetDamageCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Number };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    try
    {
      if ((ca.getParams().get(0) instanceof Number))
      {
        int damage = (int)((Number)ca.getParams().get(0)).doubleValue();
        if ((ca.getSpellInfo().mEvent instanceof EntityDamageEvent)) {
          ((EntityDamageEvent)ca.getSpellInfo().mEvent).setDamage(damage);
        }
        return true;
      }
    }
    catch (IndexOutOfBoundsException ignored) {}
    return false;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\SetDamageCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */