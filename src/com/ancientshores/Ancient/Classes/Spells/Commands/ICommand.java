package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.ParameterType;

public abstract class ICommand
{
  public ParameterType[] paramTypes;
  
  public abstract boolean playCommand(EffectArgs paramEffectArgs);
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\ICommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */