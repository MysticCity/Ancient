package com.ancient.spellmaker;

import com.ancient.parameter.Parameter;

public abstract class CommandBoth<T>
  extends Parameterizable
  implements Returnable<T>
{
  public CommandBoth(int line, String description, Parameter[] parameter)
  {
    super(line, description, parameter);
  }
  
  public abstract Parameter getReturnType();
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancient\spellmaker\CommandBoth.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */