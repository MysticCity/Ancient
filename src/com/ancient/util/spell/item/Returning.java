package com.ancient.util.spell.item;

import com.ancient.util.spell.data.Data;

public abstract interface Returning<T extends Data>
  extends SpellItem
{
  public abstract T getReturn();
}
