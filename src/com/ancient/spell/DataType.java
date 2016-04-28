package com.ancient.spell;

import com.ancient.spellmaker.Returnable;

public abstract class DataType<T>
  extends SpellItem
  implements Returnable<T>
{
  public DataType(int line, String description)
  {
    super(line, description);
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancient\spell\DataType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */