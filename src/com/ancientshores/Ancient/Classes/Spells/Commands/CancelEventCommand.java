package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import org.bukkit.event.Cancellable;

public class CancelEventCommand
  extends ICommand
{
  @CommandDescription(description="<html>Cancels the event, can only be used in a passive spell</html>", argnames={}, name="CancelEvent", parameters={})
  public CancelEventCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Void };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    if ((ca.getSpellInfo().mEvent instanceof Cancellable)) {
      ((Cancellable)ca.getSpellInfo().mEvent).setCancelled(true);
    }
    return true;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\CancelEventCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */