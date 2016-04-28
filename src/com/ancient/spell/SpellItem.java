package com.ancient.spell;

public abstract class SpellItem
  implements SpellSection
{
  protected String description;
  protected int line;
  protected Spell spell;
  
  public SpellItem(int line, String description)
  {
    this.spell = null;
    this.line = line;
    this.description = description;
  }
  
  public String getDescription()
  {
    return this.description;
  }
  
  public int getLine()
  {
    return this.line;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancient\spell\SpellItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */