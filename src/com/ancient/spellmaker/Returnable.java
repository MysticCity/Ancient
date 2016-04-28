package com.ancient.spellmaker;

import com.ancient.parameter.Parameter;

public abstract interface Returnable<T>
{
  public abstract Parameter getReturnType();
  
  public abstract T getValue();
}
