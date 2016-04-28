package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;

public class BooleanArgument
  extends IArgument
{
  final boolean b;
  
  public BooleanArgument(Boolean b)
  {
    this.b = b.booleanValue();
  }
  
  public Object getArgument(Object[] mPlayer, SpellInformationObject so)
  {
    return Boolean.valueOf(this.b);
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Conditions\BooleanArgument.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */