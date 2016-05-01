package com.ancientshores.Ancient.Util.spell.item;

import com.ancientshores.Ancient.Util.spell.data.Data;

public abstract interface Returning<T extends Data>
  extends SpellItem
{
  public abstract T getReturn();
}
