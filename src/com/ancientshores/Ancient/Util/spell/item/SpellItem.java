package com.ancientshores.Ancient.Util.spell.item;

import com.ancientshores.Ancient.Util.spell.ExecutionReturn;
import com.ancientshores.Ancient.Util.spell.data.Data;

public abstract interface SpellItem
{
  public abstract ExecutionReturn excecute(Data... paramVarArgs);
}
