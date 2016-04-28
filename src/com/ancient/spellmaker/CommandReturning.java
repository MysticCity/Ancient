package com.ancient.spellmaker;

import com.ancient.parameter.Parameter;

public abstract class CommandReturning<T>
  implements Returnable<T>
{
  public abstract Parameter getReturnType();
  
  public abstract T execute();
}
