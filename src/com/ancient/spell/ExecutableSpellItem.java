package com.ancient.spell;

public abstract class ExecutableSpellItem
  extends SpellItem
{
  public ExecutableSpellItem(int line, String description)
  {
    super(line, description);
  }
  
  public abstract Object[] execute()
    throws Exception;
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancient\spell\ExecutableSpellItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */