package com.ancient.util.spell.item;

import com.ancient.util.spell.data.Data;

public abstract interface Returning<T extends Data>
  extends SpellItem
{
  public abstract T getReturn();
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancient\util\spell\item\Returning.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */