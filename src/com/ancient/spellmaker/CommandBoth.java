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
