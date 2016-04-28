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
