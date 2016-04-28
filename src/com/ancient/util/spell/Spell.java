package com.ancient.util.spell;

import com.ancient.util.spell.item.SpellItem;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import org.bukkit.configuration.file.YamlConfiguration;

public class Spell
{
  private HashMap<Integer, SpellItem> items;
  
  public Spell(File file)
  {
    ZipFile zip;
    try
    {
      zip = new ZipFile(file);
    }
    catch (ZipException ex)
    {
      ex.printStackTrace();
      return;
    }
    catch (IOException ex)
    {
      ex.printStackTrace();
      return;
    }
    Enumeration<? extends ZipEntry> entries = zip.entries();
    InputStream configStream = null;InputStream spellStream = null;
    while (entries.hasMoreElements())
    {
      ZipEntry entry = (ZipEntry)entries.nextElement();
      try
      {
        if (entry.getName().equalsIgnoreCase("info.yml")) {
          configStream = zip.getInputStream(entry);
        }
        if (entry.getName().equalsIgnoreCase("spell.txt")) {
          spellStream = zip.getInputStream(entry);
        }
      }
      catch (IOException ex)
      {
        ex.printStackTrace();
      }
    }
    assert ((configStream != null) && (spellStream != null));
    
    YamlConfiguration.loadConfiguration(configStream);
    
    SpellItem[] items = SpellParser.parse(spellStream);
  }
  
  private void parseLine(String line)
  {
    line = line.trim();
    if (Character.isLetter(line.charAt(0))) {}
    for (char c : line.toCharArray()) {
      switch (c)
      {
      case '#': 
        
      }
    }
  }
  
  public static void main(String[] args)
  {
    String arg = "   k hjkdkhjfkjhfkjs     ";
    System.out.println(arg);
    arg = arg.trim();
    System.out.println(arg);
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancient\util\spell\Spell.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */