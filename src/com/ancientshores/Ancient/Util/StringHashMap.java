package com.ancientshores.Ancient.Util;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class StringHashMap<T>
  extends ConcurrentHashMap<String, T>
{
  private static final long serialVersionUID = 1L;
  
  public boolean containsKey(Object c)
  {
    if (!(c instanceof String)) {
      return false;
    }
    for (Map.Entry<String, T> entry : entrySet()) {
      if (((String)entry.getKey()).equalsIgnoreCase((String)c)) {
        return true;
      }
    }
    return false;
  }
  
  public T get(Object c)
  {
    if (!(c instanceof String)) {
      return null;
    }
    for (Map.Entry<String, T> entry : entrySet()) {
      if (((String)entry.getKey()).equalsIgnoreCase((String)c)) {
        return (T)entry.getValue();
      }
    }
    return null;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Util\StringHashMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */