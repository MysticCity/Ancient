package com.ancient.spell;

public enum SpellType
{
  ACTIVE,  PASSIVE,  BUFF,  COMMAND;
  
  private SpellType() {}
  
  public static SpellType getByName(String s)
  {
    if (s.equalsIgnoreCase("active")) {
      return ACTIVE;
    }
    if (s.equalsIgnoreCase("passive")) {
      return PASSIVE;
    }
    if (s.equalsIgnoreCase("buff")) {
      return BUFF;
    }
    if (s.equalsIgnoreCase("command")) {
      return COMMAND;
    }
    return null;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancient\spell\SpellType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */