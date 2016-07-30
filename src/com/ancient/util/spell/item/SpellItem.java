package com.ancient.util.spell.item;

import com.ancient.util.spell.ExecutionReturn;
import com.ancient.util.spell.data.Data;

public abstract interface SpellItem
{
  public abstract ExecutionReturn excecute(Data... paramVarArgs);
}
