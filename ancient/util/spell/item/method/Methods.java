package com.ancientshores.Ancient.Util.spell.item.method;

import com.ancientshores.Ancient.Util.ClassFinder;
import com.ancientshores.Ancient.Util.spell.item.SpellItem;
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
          continue;
        }
         
        
         // V UNREACHABLE V
         
//        continue;
//        if ( !methods.containsKey(name) ) {
//            methods.put(name, c.asSubclass(Method.class));
//        }

            
      }
    }
  }
}
