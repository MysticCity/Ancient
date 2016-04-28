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
