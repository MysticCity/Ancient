package com.ancientshores.Ancient.Classes.Spells.Parameters;

import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.Ancient.Classes.Spells.Conditions.ArgumentInformationObject;
import com.ancientshores.Ancient.Classes.Spells.Conditions.IArgument;
import com.ancientshores.Ancient.Classes.Spells.IParameter;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import org.bukkit.entity.Player;

public class ArgumentParameterWrapper
  implements IParameter
{
  final ArgumentInformationObject arg;
  
  public ArgumentParameterWrapper(ArgumentInformationObject arg)
  {
    this.arg = arg;
  }
  
  public void parseParameter(EffectArgs ea, Player mPlayer, String[] subparam, ParameterType pt)
  {
    IArgument.AutoCast(this.arg.getArgument(ea.getSpellInfo(), mPlayer), pt, ea);
  }
  
  public Object parseParameter(Player mPlayer, String[] subparam, SpellInformationObject so)
  {
    return this.arg.getArgument(so, mPlayer);
  }
  
  public String getName()
  {
    return null;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Parameters\ArgumentParameterWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */