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


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\SkipCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */