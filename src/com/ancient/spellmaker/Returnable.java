package com.ancient.spellmaker;

import com.ancient.parameter.Parameter;

public abstract interface Returnable<T>
{
  public abstract Parameter getReturnType();
  
  public abstract T getValue();
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancient\spellmaker\Returnable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */