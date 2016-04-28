package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.Spell;
import java.util.LinkedList;
import java.util.Random;

public class ChanceCommand
  extends ICommand
{
  static final Random r = new Random();
  
  public ChanceCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Number, ParameterType.Number };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    try
    {
      if ((!(ca.getParams().get(0) instanceof Number)) || (!(ca.getParams().get(1) instanceof Number))) {
        return false;
      }
      int chance = (int)((Number)ca.getParams().get(0)).doubleValue();
      int skipcommands = (int)((Number)ca.getParams().get(1)).doubleValue();
      if (r.nextInt(100) >= chance) {
        ca.getSpell().skipCommands(ca.getSpellInfo(), skipcommands);
      }
      return true;
    }
    catch (IndexOutOfBoundsException e) {}
    return false;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\ChanceCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */