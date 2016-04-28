package com.ancientshores.Ancient.Classes.Spells;

import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;
import org.bukkit.entity.Player;

public abstract interface IParameter
{
  public abstract void parseParameter(EffectArgs paramEffectArgs, Player paramPlayer, String[] paramArrayOfString, ParameterType paramParameterType);
  
  public abstract Object parseParameter(Player paramPlayer, String[] paramArrayOfString, SpellInformationObject paramSpellInformationObject);
  
  public abstract String getName();
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\IParameter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */