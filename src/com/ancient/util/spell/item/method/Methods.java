package com.ancient.util.spell.item.method;

import com.ancient.util.ClassFinder;
import com.ancient.util.spell.item.SpellItem;
import java.util.HashMap;
import java.util.Map;

public class Methods
{
  private static Map<String, Class<? extends Method>> methods;
  
  public static SpellItem call(String text)
  {
    return null;
  }
  
  private static void loadMethods(String packageName)
  {
    if (methods == null) {
      methods = new HashMap();
    }
    for (Class<?> c : ClassFinder.find(packageName)) {
      if (Method.class.isAssignableFrom(c))
      {
        String name;
        try
        {
          name = ((Method)c.newInstance()).getName();
        }
        catch (InstantiationException ex)
        {
          ex.printStackTrace();
          continue;
        }
        catch (IllegalAccessException ex)
        {
          ex.printStackTrace();
        }
        continue;
        if (!methods.containsKey(name)) {
          methods.put(name, c.asSubclass(Method.class));
        }
      }
    }
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancient\util\spell\item\method\Methods.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */