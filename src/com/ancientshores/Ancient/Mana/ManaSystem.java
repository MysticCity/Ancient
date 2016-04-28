package com.ancientshores.Ancient.Mana;

import com.ancient.util.UUIDConverter;
import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.AncientClass;
import com.ancientshores.Ancient.Display.Display;
import com.ancientshores.Ancient.Experience.AncientExperience;
import com.ancientshores.Ancient.PlayerData;
import com.ancientshores.Ancient.Util.LinkedStringHashMap;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitScheduler;

public class ManaSystem
  implements ConfigurationSerializable
{
  public static float defaultManaRegInterval = 3.0F;
  public static int defaultMana = 1000;
  public static int defaultReg = 20;
  public static boolean enabled = true;
  public int maxmana;
  public int curmana;
  public float manareginterval = defaultManaRegInterval;
  public int manareg = defaultMana;
  private UUID playeruuid;
  public int task;
  
  public ManaSystem(UUID playeruuid, int maxmana)
  {
    this.playeruuid = playeruuid;
    this.maxmana = maxmana;
    this.curmana = maxmana;
  }
  
  public ManaSystem(Map<String, Object> map)
  {
    this.curmana = ((Integer)map.get("mana")).intValue();
    this.maxmana = ((Integer)map.get("maxmana")).intValue();
    double d = ((Double)map.get("manareginterval")).doubleValue();
    this.manareginterval = ((float)d);
    this.manareg = ((Integer)map.get("manareg")).intValue();
    if (map.containsKey("uuid")) {
      this.playeruuid = UUID.fromString((String)map.get("uuid"));
    } else {
      this.playeruuid = UUIDConverter.getUUID((String)map.get("playername"));
    }
  }
  
  public Map<String, Object> serialize()
  {
    HashMap<String, Object> map = new HashMap();
    map.put("maxmana", Integer.valueOf(this.maxmana));
    map.put("manareg", Integer.valueOf(this.manareg));
    map.put("manareginterval", Float.valueOf(this.manareginterval));
    map.put("mana", Integer.valueOf(this.curmana));
    map.put("uuid", this.playeruuid.toString());
    
    return map;
  }
  
  public void startRegenTimer()
  {
    stopRegenTimer();
    this.manareginterval = defaultManaRegInterval;
    this.task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Ancient.plugin, new Runnable()
    {
      public void run()
      {
        Player p = Bukkit.getPlayer(ManaSystem.this.playeruuid);
        if (p == null)
        {
          ManaSystem.this.stopRegenTimer();
          return;
        }
        if (p.isDead()) {
          return;
        }
        ManaSystem.addManaByUUID(ManaSystem.this.playeruuid, ManaSystem.this.manareg);
      }
    }, Math.round(this.manareginterval * 20.0F), Math.round(this.manareginterval * 20.0F));
  }
  
  public int getMaxmana()
  {
    return this.maxmana;
  }
  
  public int getCurrentMana()
  {
    return this.curmana;
  }
  
  public float getManareginterval()
  {
    return this.manareginterval;
  }
  
  public int getManareg()
  {
    return this.manareg;
  }
  
  public UUID getPlayerUUID()
  {
    return this.playeruuid;
  }
  
  public static void addManaByUUID(UUID uuid, int amount)
  {
    PlayerData pd = PlayerData.getPlayerData(uuid);
    pd.getManasystem().curmana += amount;
    if (pd.getManasystem().maxmana < pd.getManasystem().curmana) {
      pd.getManasystem().curmana = pd.getManasystem().maxmana;
    }
    if (0 > pd.getManasystem().curmana) {
      pd.getManasystem().curmana = 0;
    }
    Display.updateMana(pd);
  }
  
  public static void removeManaByUUID(UUID uuid, int amount)
  {
    PlayerData pd = PlayerData.getPlayerData(uuid);
    pd.getManasystem().curmana -= amount;
    if (pd.getManasystem().maxmana < pd.getManasystem().curmana) {
      pd.getManasystem().curmana = pd.getManasystem().maxmana;
    }
    if (0 > pd.getManasystem().curmana) {
      pd.getManasystem().curmana = 0;
    }
    Display.updateMana(pd);
  }
  
  public static void loadConfig(Ancient plugin)
  {
    File newconfig = new File(plugin.getDataFolder().getPath() + File.separator + "manaconfig.yml");
    if (newconfig.exists())
    {
      YamlConfiguration yc = new YamlConfiguration();
      try
      {
        yc.load(newconfig);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      defaultMana = yc.getInt("Mana.default mana", defaultMana);
      defaultReg = yc.getInt("Mana.default manareg", defaultReg);
      defaultManaRegInterval = (float)yc.getDouble("Mana.manareg interval", defaultManaRegInterval);
    }
  }
  
  public static void writeConfig(Ancient plugin)
  {
    File newconfig = new File(plugin.getDataFolder().getPath() + File.separator + "manaconfig.yml");
    if (!newconfig.exists()) {
      try
      {
        newconfig.createNewFile();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
    YamlConfiguration yc = new YamlConfiguration();
    yc.set("Mana.default mana", Integer.valueOf(defaultMana));
    yc.set("Mana.default manareg", Integer.valueOf(defaultReg));
    yc.set("Mana.manareg interval", Float.valueOf(defaultManaRegInterval));
    try
    {
      yc.save(newconfig);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  public static void setManaByUUID(UUID uuid, int amount)
  {
    PlayerData pd = PlayerData.getPlayerData(uuid);
    pd.getManasystem().curmana = amount;
    if (pd.getManasystem().maxmana < pd.getManasystem().curmana) {
      pd.getManasystem().curmana = pd.getManasystem().maxmana;
    }
    if (0 > pd.getManasystem().curmana) {
      pd.getManasystem().curmana = 0;
    }
    Display.updateMana(pd);
  }
  
  public void stopRegenTimer()
  {
    Bukkit.getScheduler().cancelTask(this.task);
  }
  
  public void setMaxMana()
  {
    PlayerData pd = PlayerData.getPlayerData(this.playeruuid);
    AncientClass mClass = (AncientClass)AncientClass.classList.get(pd.getClassName().toLowerCase());
    if (mClass != null)
    {
      OfflinePlayer p = Bukkit.getOfflinePlayer(this.playeruuid);
      if ((p.isOnline()) && (AncientExperience.isWorldEnabled(p.getPlayer().getWorld())))
      {
        this.maxmana = ((Integer)mClass.manalevel.get(Integer.valueOf(pd.getXpSystem().level))).intValue();
        this.manareg = ((Integer)mClass.manareglevel.get(Integer.valueOf(pd.getXpSystem().level))).intValue();
      }
      else
      {
        this.maxmana = mClass.defaultmana;
        this.manareg = mClass.manareg;
      }
    }
    else
    {
      this.maxmana = defaultMana;
      this.manareg = defaultReg;
    }
    if (this.curmana > this.maxmana) {
      this.curmana = this.maxmana;
    }
  }
  
  public static void processCommand(CommandSender sender, String[] args)
  {
    if (args.length == 0) {
      ManaCommand.processManaCommand(sender);
    }
  }
  
  @EventHandler
  public void onPlayerConnect(PlayerJoinEvent event)
  {
    if (this.playeruuid.compareTo(event.getPlayer().getUniqueId()) == 0)
    {
      setMaxMana();
      startRegenTimer();
    }
  }
  
  @EventHandler
  public void onPlayerDisconnect(PlayerQuitEvent event)
  {
    if (this.playeruuid.compareTo(event.getPlayer().getUniqueId()) == 0) {
      stopRegenTimer();
    }
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Mana\ManaSystem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */