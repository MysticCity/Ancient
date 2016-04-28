package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.ParameterType;

public abstract class ICommand
{
  public ParameterType[] paramTypes;
  
  public abstract boolean playCommand(EffectArgs paramEffectArgs);
}
