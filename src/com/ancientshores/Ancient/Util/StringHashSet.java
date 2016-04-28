package com.ancientshores.Ancient.Util;

import java.util.HashSet;

public class StringHashSet
  extends HashSet<String>
{
  private static final long serialVersionUID = 1L;
  
  public boolean contains(Object c)
  {
    if (!(c instanceof String)) {
      return false;
    }
    for (String entry : this) {
      if (entry.equalsIgnoreCase((String)c)) {
        return true;
      }
    }
    return false;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Util\StringHashSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */