package com.ancientshores.Ancient;

import com.ancient.util.UUIDConverter;
import com.ancientshores.Ancient.Classes.AncientClass;
import com.ancientshores.Ancient.Classes.BindingData;
import com.ancientshores.Ancient.Classes.CooldownTimer;
import com.ancientshores.Ancient.Experience.AncientExperience;
import com.ancientshores.Ancient.HP.AncientHP;
import com.ancientshores.Ancient.HP.DamageConverter;
import com.ancientshores.Ancient.Mana.ManaSystem;
import com.ancientshores.Ancient.Race.AncientRace;
import com.ancientshores.Ancient.Util.LinkedStringHashMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.scheduler.BukkitScheduler;

public class PlayerData
  implements Serializable, ConfigurationSerializable
{
  private static final long serialVersionUID = 1L;
  public static HashSet<PlayerData> playerData = new HashSet();
  private UUID player;
  public int[] data;
  private AncientHP hpsystem;
  private String className;
  private AncientExperience xpSystem;
  public long lastFireDamage;
  public long lastCactusDamage;
  public long lastLavaDamage;
  public long lastFireDamageSpell;
  public long lastCactusDamageSpell;
  public long lastLavaDamageSpell;
  private HashSet<CooldownTimer> cooldownTimer = new HashSet();
  private HashMap<String, Integer> classLevels;
  private HashMap<BindingData, String> bindings = new HashMap();
  private HashMap<Integer, String> slotbinds = new HashMap();
  private String stance;
  private String racename;
  private final HashMap<String, Object> objects = new HashMap();
  private ManaSystem manasystem;
  
  public Map<String, Object> serialize()
  {
    HashMap<String, Object> map = new HashMap();
    map.put("playeruuid", this.player.toString());
    map.put("hpsystem", this.hpsystem);
    map.put("className", this.className);
    map.put("xpsystem", this.xpSystem);
    
    map.put("classlevels", this.classLevels);
    map.put("stance", this.stance);
    map.put("racename", this.racename);
    map.put("manasystem", this.manasystem);
    
    return map;
  }
  
  public PlayerData(Map<String, Object> map)
  {
    if (map.containsKey("playeruuid")) {
      this.player = UUID.fromString((String)map.get("playeruuid"));
    } else {
      this.player = UUIDConverter.getUUID((String)map.get("player"));
    }
    this.hpsystem = ((AncientHP)map.get("hpsystem"));
    this.className = ((String)map.get("className"));
    this.xpSystem = ((AncientExperience)map.get("xpsystem"));
    
    this.classLevels = ((HashMap)map.get("classlevels"));
    this.stance = ((String)map.get("stance"));
    this.racename = ((String)map.get("racename"));
    this.manasystem = ((ManaSystem)map.get("manasystem"));
  }
  
  public PlayerData(UUID playeruuid)
  {
    this.classLevels = new HashMap();
    this.player = playeruuid;
    
    this.hpsystem = new AncientHP(DamageConverter.getStandardHP(), playeruuid);
    
    this.className = AncientClass.standardclassName;
    this.cooldownTimer = new HashSet();
    
    this.xpSystem = new AncientExperience(playeruuid);
    
    this.manasystem = new ManaSystem(playeruuid, ManaSystem.defaultMana);
    this.racename = AncientRace.defaultRace;
  }
  
  public void finalize()
  {
    try
    {
      super.finalize();
    }
    catch (Throwable ignored) {}
    dispose();
  }
  
  public void dispose()
  {
    getManasystem().stopRegenTimer();
    getHpsystem().stopRegenTimer();
  }
  
  public void initialize()
  {
    getManasystem().setMaxMana();
    getManasystem().startRegenTimer();
    getHpsystem().setMaxHP();
    getHpsystem().setHpRegen();
    getHpsystem().startRegenTimer();
  }
  
  public void createMissingObjects()
  {
    if (this.className == null)
    {
      this.className = AncientClass.standardclassName.toLowerCase();
      this.cooldownTimer = new HashSet();
    }
    if (this.classLevels == null) {
      this.classLevels = new HashMap();
    }
    if (this.hpsystem == null) {
      this.hpsystem = new AncientHP(DamageConverter.getStandardHP(), this.player);
    }
    AncientClass mClass = (AncientClass)AncientClass.classList.get(this.className.toLowerCase());
    if (mClass != null) {
      this.hpsystem.maxhp = mClass.hp;
    }
    if (this.xpSystem == null) {
      this.xpSystem = new AncientExperience(this.player);
    }
    if (getManasystem() == null) {
      this.manasystem = new ManaSystem(this.player, ManaSystem.defaultMana);
    }
    if (this.cooldownTimer == null) {
      this.cooldownTimer = new HashSet();
    }
    for (PlayerData pd : playerData)
    {
      if (pd.xpSystem != null) {
        pd.xpSystem.addXP(0, false);
      }
      if (pd.getBindings() == null) {
        pd.bindings = new HashMap();
      }
    }
    HashMap<BindingData, String> newbinds = new HashMap();
    for (Map.Entry<BindingData, String> entr : getBindings().entrySet()) {
      newbinds.put(new BindingData(((BindingData)entr.getKey()).id, ((BindingData)entr.getKey()).data), entr.getValue());
    }
    this.bindings = newbinds;
  }
  
  public static void startSaveTimer()
  {
    Ancient.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(Ancient.plugin, new Runnable()
    {
      public void run() {}
    }, 1200L, 1200L);
  }
  
  public boolean isCastReady(String name)
  {
    for (CooldownTimer ct : this.cooldownTimer) {
      if (ct.cooldownname.equals(name)) {
        return ct.ready;
      }
    }
    return true;
  }
  
  public void startTimer(String name)
  {
    for (CooldownTimer ct : this.cooldownTimer) {
      if (ct.cooldownname.equals(name))
      {
        ct.startTimer();
        return;
      }
    }
  }
  
  public void addTimer(String name, int time)
  {
    CooldownTimer cd = new CooldownTimer(time, name);
    if (this.cooldownTimer.contains(cd)) {
      this.cooldownTimer.remove(cd);
    }
    this.cooldownTimer.add(cd);
  }
  
  public long getRemainingTime(String name)
  {
    for (CooldownTimer ct : this.cooldownTimer) {
      if (ct.cooldownname.equals(name)) {
        return ct.time;
      }
    }
    return 0L;
  }
  
  public boolean containsTimer(String name)
  {
    for (CooldownTimer ct : this.cooldownTimer) {
      if (ct.cooldownname.equals(name)) {
        return true;
      }
    }
    return false;
  }
  
  public UUID getPlayer()
  {
    return this.player;
  }
  
  public static PlayerData getPlayerData(UUID uuid)
  {
    PlayerData pd = playerHasPlayerData(uuid);
    if (pd != null) {
      return pd;
    }
    pd = new PlayerData(uuid);
    playerData.add(pd);
    pd.initialize();
    return pd;
  }
  
  public static PlayerData playerHasPlayerData(UUID uuid)
  {
    for (PlayerData pd : playerData) {
      if ((pd.player != null) && (pd.player.compareTo(uuid) == 0)) {
        return pd;
      }
    }
    File folder = new File(Ancient.plugin.getDataFolder().getPath() + File.separator + "players");
    File f = new File(folder.getPath() + File.separator + uuid + ".yml");
    if (f.exists())
    {
      YamlConfiguration yc = new YamlConfiguration();
      File input = new File(folder.getPath() + File.separator + uuid + ".dat");
      try
      {
        yc.load(f);
        PlayerData pd = (PlayerData)yc.get("PlayerData");
        
        ObjectInputStream ois = null;
        try
        {
          ois = new ObjectInputStream(new FileInputStream(input));
          pd.cooldownTimer = ((HashSet)ois.readObject());
          pd.bindings = ((HashMap)ois.readObject());
          pd.slotbinds = ((HashMap)ois.readObject());
          if (ois != null) {
            try
            {
              ois.close();
            }
            catch (Exception ignored) {}
          }
          playerData.add(pd);
        }
        catch (Exception e)
        {
          Bukkit.getLogger().log(Level.SEVERE, "Cooldown and binding data corrupt, replacing them");
        }
        finally
        {
          if (ois != null) {
            try
            {
              ois.close();
            }
            catch (Exception ignored) {}
          }
        }
        pd.createMissingObjects();
        pd.initialize();
        for (CooldownTimer cd : pd.cooldownTimer)
        {
          cd.enabled = false;
          cd.startTimer();
        }
        return pd;
      }
      catch (Exception e)
      {
        e.printStackTrace();
        Bukkit.getLogger().log(Level.SEVERE, "Error in playerdata of player: " + uuid);
      }
    }
    return null;
  }
  
  public void save()
  {
    File folder = new File(Ancient.plugin.getDataFolder().getPath() + File.separator + "players");
    if (!folder.exists()) {
      folder.mkdir();
    }
    File output = new File(folder.getPath() + File.separator + this.player + ".yml");
    File output2 = new File(folder.getPath() + File.separator + this.player + ".dat");
    ObjectOutputStream oos = null;
    try
    {
      oos = new ObjectOutputStream(new FileOutputStream(output2));
      oos.writeObject(this.cooldownTimer);
      oos.writeObject(this.bindings);
      oos.writeObject(this.slotbinds);
      if (oos != null) {
        try
        {
          oos.close();
        }
        catch (Exception ignored) {}
      }
      yc = new YamlConfiguration();
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    finally
    {
      if (oos != null) {
        try
        {
          oos.close();
        }
        catch (Exception ignored) {}
      }
    }
    YamlConfiguration yc;
    yc.set("PlayerData", this);
    try
    {
      yc.save(output);
    }
    catch (IOException e)
    {
      e.printStackTrace();
      Bukkit.getLogger().log(Level.SEVERE, "Error while saving playerdata of player " + this.player);
    }
  }
  
  public static void writePlayerData()
  {
    for (PlayerData pd : playerData) {
      pd.save();
    }
  }
  
  public static boolean checkForOldAndConvertPlayerData()
  {
    File f = new File(Ancient.plugin.getDataFolder().getPath() + File.separator + "player.dat");
    if (f.exists())
    {
      try
      {
        FileInputStream fis = new FileInputStream(f);
        
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object input = ois.readObject();
        HashSet<PlayerData> oldset = (HashSet)input;
        fis.close();
        ois.close();
        playerData.addAll(oldset);
      }
      catch (FileNotFoundException e)
      {
        e.printStackTrace();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
      catch (ClassNotFoundException e)
      {
        e.printStackTrace();
      }
      String s = "oldplayer";
      File newFile = new File(Ancient.plugin.getDataFolder().getPath() + File.separator + s + ".dat");
      while (newFile.exists())
      {
        s = s + 1;
        newFile = new File(Ancient.plugin.getDataFolder().getPath() + File.separator + s + ".dat");
      }
      f.renameTo(newFile);
      return true;
    }
    return false;
  }
  
  public AncientHP getHpsystem()
  {
    return this.hpsystem;
  }
  
  public void setHpsystem(AncientHP hpsystem)
  {
    this.hpsystem = hpsystem;
    save();
  }
  
  public String getClassName()
  {
    return this.className;
  }
  
  public void setClassName(String className)
  {
    this.className = className;
    save();
  }
  
  public AncientExperience getXpSystem()
  {
    return this.xpSystem;
  }
  
  public void setXpSystem(AncientExperience xpSystem)
  {
    this.xpSystem = xpSystem;
    save();
  }
  
  public HashSet<CooldownTimer> getCooldownTimer()
  {
    return this.cooldownTimer;
  }
  
  public void setCooldownTimer(HashSet<CooldownTimer> cooldownTimer)
  {
    this.cooldownTimer = cooldownTimer;
    save();
  }
  
  public HashMap<String, Integer> getClassLevels()
  {
    return this.classLevels;
  }
  
  public void setClassLevels(HashMap<String, Integer> classLevels)
  {
    this.classLevels = classLevels;
    save();
  }
  
  public HashMap<BindingData, String> getBindings()
  {
    return this.bindings;
  }
  
  public void setBindings(HashMap<BindingData, String> bindings)
  {
    this.bindings = bindings;
    save();
  }
  
  public String getStance()
  {
    return this.stance;
  }
  
  public void setStance(String stance)
  {
    this.stance = stance;
    save();
  }
  
  public String getRacename()
  {
    return this.racename;
  }
  
  public void setRacename(String racename)
  {
    this.racename = racename;
    save();
  }
  
  public void setPlayer(UUID player)
  {
    this.player = player;
    save();
  }
  
  public Object getKeyValue(String key)
  {
    return this.objects.get(key);
  }
  
  public void addKey(String key, Object o)
  {
    this.objects.put(key, o);
  }
  
  public ManaSystem getManasystem()
  {
    return this.manasystem;
  }
  
  public HashMap<Integer, String> getSlotbinds()
  {
    return this.slotbinds;
  }
}
