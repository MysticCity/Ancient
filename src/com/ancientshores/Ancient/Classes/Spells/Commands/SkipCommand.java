package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.Spell;
import java.util.LinkedList;

public class SkipCommand
  extends ICommand
{
  public SkipCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Number };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    try
    {
      if ((ca.getParams().get(0) instanceof Number))
      {
        int number = ((Number)ca.getParams().get(0)).intValue();
        ca.getSpell().skipCommands(ca.getSpellInfo(), number);
        return true;
      }
    }
    catch (IndexOutOfBoundsException ignored) {}
    return false;
  }
}
