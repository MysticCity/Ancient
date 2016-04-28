package com.ancient.spell;

import com.ancientshores.Ancient.Ancient;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class SpellItems
{
  private static HashMap<String, String> items;
  
  static
  {
    String packageName = "com.ancient.spell.item";
    
    packageName = packageName.replaceAll("\\.", "/");
    try
    {
      JarInputStream jarFile = new JarInputStream(new FileInputStream(Ancient.plugin.getDataFolder().getPath() + ".jar"));
      JarEntry jarEntry;
      while ((jarEntry = jarFile.getNextJarEntry()) != null) {
        if ((jarEntry.getName().startsWith(packageName)) && (jarEntry.getName().endsWith(".class")))
        {
          String className = jarEntry.getName().replaceAll("/", "\\.");
          items.put(className.split("\\.")[(className.split("/").length - 1)], className);
        }
      }
      jarFile.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public static String getFullName(String s)
  {
    if (items.containsKey(s)) {
      return (String)items.get(s);
    }
    return null;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancient\spell\SpellItems.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */