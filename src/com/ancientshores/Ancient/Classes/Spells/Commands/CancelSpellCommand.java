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
