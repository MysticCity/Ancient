package com.ancientshores.Ancient.Util;

import com.ancientshores.Ancient.Ancient;
import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;

public class YamlFileConnector
  implements FileConnector
{
  final Ancient instance;
  
  public YamlFileConnector(Ancient instance)
  {
    this.instance = instance;
  }
  
  public String getElementOfFile(String spellName, String rowName)
  {
    FileConfiguration c = Ancient.plugin.getConfig();
    return c.getString(spellName + "." + rowName);
  }
  
  public double getDoubleOfFile(String spellName, String rowName, File f)
  {
    return Double.parseDouble(getElementOfFile(spellName, rowName));
  }
  
  public int getIntOfFile(String spellName, String rowName, File f)
  {
    return Integer.parseInt(getElementOfFile(spellName, rowName));
  }
  
  public boolean getBooleanOfFile(String spellName, String rowName, File f)
  {
    return Boolean.parseBoolean(getElementOfFile(spellName, rowName));
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Util\YamlFileConnector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */