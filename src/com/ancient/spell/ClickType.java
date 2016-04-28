package com.ancient.spell;

public enum ClickType
{
  LEFT,  RIGHT,  BOTH;
  
  private ClickType() {}
  
  public static ClickType getByName(String s)
  {
    if (s.equalsIgnoreCase("left")) {
      return LEFT;
    }
    if (s.equalsIgnoreCase("right")) {
      return RIGHT;
    }
    if (s.equalsIgnoreCase("both")) {
      return BOTH;
    }
    return null;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancient\spell\ClickType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */