package com.ancientshores.Ancient.Classes.Spells;

import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;
import org.bukkit.entity.Player;

public abstract interface IParameter
{
  public abstract void parseParameter(EffectArgs paramEffectArgs, Player paramPlayer, String[] paramArrayOfString, ParameterType paramParameterType);
  
  public abstract Object parseParameter(Player paramPlayer, String[] paramArrayOfString, SpellInformationObject paramSpellInformationObject);
  
  public abstract String getName();
}
