package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import java.util.LinkedList;

public class WaitCommand
  extends ICommand
{
  @CommandDescription(description="<html>Delays the execution of the spell for the specified amount of time in millaseconds.</html>", argnames={"duration"}, name="Wait", parameters={ParameterType.Number})
  public WaitCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Number };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    try
    {
      if ((ca.getParams().get(0) instanceof Number))
      {
        ca.getSpellInfo().waittime += (int)((Number)ca.getParams().get(0)).doubleValue();
        return true;
      }
    }
    catch (IndexOutOfBoundsException ignored) {}
    return false;
  }
}
