package com.ancientshores.Ancient.Display;

public enum Bar
{
  XP("xpbar"),  BOSS("bossbar"),  NONE("none");
  
  private String label;
  
  private Bar(String label)
  {
    this.label = label;
  }
  
  public static Bar getByName(String name)
  {
    for (Bar b : ) {
      if (b.label.equalsIgnoreCase(name)) {
        return b;
      }
    }
    return NONE;
  }
  
  public String toString()
  {
    return this.label.toUpperCase();
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Display\Bar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */