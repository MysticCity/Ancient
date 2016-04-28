package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import org.bukkit.entity.Player;

public class ArgumentInformationObject
{
  public ArgumentParameter[] parameters;
  public IArgument argument;
  
  public Object getArgument(SpellInformationObject so, Player mPlayer)
  {
    if ((this.parameters != null) && (this.parameters.length > 0) && (this.argument.requiredTypes != null) && (this.argument.requiredTypes.length > 0))
    {
      Object[] arguments = new Object[this.parameters.length];
      for (int i = 0; i < this.parameters.length; i++) {
        arguments[i] = this.parameters[i].parseParameter(this.argument.requiredTypes[i], so, mPlayer, so.mSpell);
      }
      if (arguments.length == 0) {
        return this.argument.getArgument(new Object[] { mPlayer }, so);
      }
      return this.argument.getArgument(arguments, so);
    }
    return this.argument.getArgument(new Object[] { { mPlayer } }, so);
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Conditions\ArgumentInformationObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */