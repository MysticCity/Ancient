package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;

public class CancelSpellCommand
  extends ICommand
{
  @CommandDescription(description="<html>Cancels the currently executing spell</html>", argnames={}, name="CancelSpell", parameters={})
  public CancelSpellCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Void };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    ca.getSpellInfo().finished = true;
    return false;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\CancelSpellCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */