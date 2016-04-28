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
