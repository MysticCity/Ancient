package com.ancient.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class UUIDConverter
{
  private static Map<String, UUID> converted;
  private static JavaPlugin pl;
  private static File folder;
  
  public static void runConverter(JavaPlugin plugin, File folderToConvert)
  {
    converted = new HashMap();
    pl = plugin;
    folder = folderToConvert;
    convert();
    
    inform();
  }
  
  private static void inform()
  {
    if (!pl.isEnabled())
    {
      pl.getLogger().info("Converted 0 files.");
      pl.getLogger().warning("The convertation was canceled because an error occoured.");
      pl.getLogger().warning("The plugin cannot be used when these files are not converted.");
      pl.getLogger().warning("Ancient now disables...");
      return;
    }
    if (converted.size() == 0)
    {
      pl.getLogger().info("Converted 0 files.");
      pl.getLogger().info("Whether your files are uptodate, or you don't have any player files.");
    }
    else if (converted.size() == 1)
    {
      pl.getLogger().info("Converted 1 file.");
    }
    else
    {
      pl.getLogger().info(String.format("Converted %d files.", new Object[] { Integer.valueOf(converted.size()) }));
    }
    pl.getLogger().info("All files that where in the folder \"plugins/Ancient/players\" should be converted.");
    pl.getLogger().info("If you find any unconverted files just reload the plugin/server and report that issue.");
    pl.getLogger().info("All player names were converted into UUID's.");
    pl.getLogger().info("The plugin now is ready for future use!");
  }
  
  private static void convert()
  {
    if ((folder == null) || (!folder.exists()) || (!folder.isDirectory())) {
      return;
    }
    for (File f : folder.listFiles()) {
      if (f.isFile()) {
        if (f.getName().endsWith(".yml"))
        {
          String name = f.getName().replaceAll(".yml", "");
          try
          {
            UUID uuid = UUID.fromString(name);
          }
          catch (IllegalArgumentException ex)
          {
            try
            {
              UUID uuid;
              YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
            }
            catch (IllegalArgumentException e)
            {
              FileConfiguration config;
              pl.getLogger().warning(String.format("Could not change %s's player configuration to the new UUID system. Please reload the server and try again if you want to use Ancient!", new Object[] { name }));
              pl.getPluginLoader().disablePlugin(pl);
              e.printStackTrace();
            }
            File dat = new File(f.getPath().replaceAll(f.getName(), name + ".dat"));
            f.renameTo(new File(f.getPath().replaceAll(f.getName(), converted.get(name) + ".yml")));
            dat.renameTo(new File(dat.getPath().replaceAll(dat.getName(), converted.get(name) + ".dat")));
          }
        }
      }
    }
  }
  
  public static UUID getUUID(String name)
  {
    if (converted.containsKey(name)) {
      return (UUID)converted.get(name);
    }
    UUID uuid = null;
    for (char c : name.toCharArray()) {
      if (((c < 'a') || (c > 'z')) && ((c < 'A') || (c > 'Z')) && ((c < '0') || (c > '9')) && (c < '_'))
      {
        uuid = new UUID(0L, 0L);
        
        converted.put(name, uuid);
        return uuid;
      }
    }
    InputStream is = null;
    try
    {
      is = new URL("https://api.mojang.com/users/profiles/minecraft/" + name).openStream();
    }
    catch (IOException ex)
    {
      ex.printStackTrace();
    }
    if (is != null)
    {
      Scanner scanner = new Scanner(is, "UTF-8");
      if (!scanner.hasNext())
      {
        uuid = new UUID(0L, 0L);
      }
      else
      {
        String jsonString = scanner.next();
        
        JSONParser jsonParser = new JSONParser();
        JSONObject json = null;
        try
        {
          json = (JSONObject)jsonParser.parse(jsonString);
        }
        catch (ParseException ex)
        {
          ex.printStackTrace();
        }
        String uuidDashed = (String)json.get("id");
        uuidDashed = uuidDashed.substring(0, 8) + "-" + uuidDashed.substring(8, 12) + "-" + uuidDashed.substring(12, 16) + "-" + uuidDashed.substring(16, 20) + "-" + uuidDashed.substring(20, uuidDashed.length());
        
        uuid = UUID.fromString(uuidDashed);
      }
      scanner.close();
      try
      {
        is.close();
      }
      catch (IOException ex)
      {
        ex.printStackTrace();
      }
    }
    converted.put(name, uuid);
    return uuid;
  }
  
  public static String getName(UUID uuid)
  {
    String name = "";
    InputStream is = null;
    if ((uuid.getMostSignificantBits() == 0L) && (uuid.getLeastSignificantBits() == 0L)) {
      return null;
    }
    try
    {
      is = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.toString().replaceAll("-", "")).openStream();
      if (is != null)
      {
        Scanner scanner = new Scanner(is, "UTF-8");
        if (!scanner.hasNext())
        {
          name = "";
        }
        else
        {
          String jsonString = scanner.next();
          JSONParser jsonParser = new JSONParser();
          JSONObject json = null;
          
          json = (JSONObject)jsonParser.parse(jsonString);
          
          name = (String)json.get("name");
        }
        scanner.close();
        
        is.close();
      }
    }
    catch (Exception ex)
    {
      pl.getLogger().warning("Unable to get the name of the player with the uuid " + uuid + ". Maybe to many reqeusts?");
    }
    return name;
  }
}
