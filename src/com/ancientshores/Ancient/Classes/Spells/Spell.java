package com.ancientshores.Ancient.Classes.Spells;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.AncientClass;
import com.ancientshores.Ancient.Experience.AncientExperience;
import com.ancientshores.Ancient.Listeners.AncientSpellListener;
import com.ancientshores.Ancient.Listeners.SpellListener.ISpellListener;
import com.ancientshores.Ancient.Listeners.SpellListener.PlayerBedEnterEventListener;
import com.ancientshores.Ancient.Listeners.SpellListener.PlayerBedLeaveEventListener;
import com.ancientshores.Ancient.Listeners.SpellListener.PlayerChatEventListener;
import com.ancientshores.Ancient.Listeners.SpellListener.PlayerDropItemEventListener;
import com.ancientshores.Ancient.Listeners.SpellListener.PlayerEggThrowEventListener;
import com.ancientshores.Ancient.Listeners.SpellListener.PlayerKickEventListener;
import com.ancientshores.Ancient.Listeners.SpellListener.PlayerPickupItemEventListener;
import com.ancientshores.Ancient.Listeners.SpellListener.PlayerPortalEventListener;
import com.ancientshores.Ancient.Listeners.SpellListener.PlayerTeleportEventListener;
import com.ancientshores.Ancient.Listeners.SpellListener.PlayerToggleSneakEventListener;
import com.ancientshores.Ancient.PlayerData;
import com.ancientshores.Ancient.Spells.Commands.AddSpellFreeZoneCommand;
import com.ancientshores.Ancient.Util.FlatFileConnector;
import com.ancientshores.Ancient.Util.SerializableZone;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.scheduler.BukkitScheduler;

public class Spell
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public static CommandMap commandMap = null;
  public static HashSet<String> disabledWorlds = new HashSet();
  public static final String worldnode = "Spells.disabled worlds";
  public static HashMap<String, Integer> registeredTasks = new HashMap();
  public static ConcurrentHashMap<String, ISpellListener> eventListeners = new ConcurrentHashMap();
  ICodeSection mainsection;
  public String name;
  public String caseSensitiveName;
  public boolean active;
  public boolean serverspell;
  
  static
  {
    try
    {
      Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
      f.setAccessible(true);
      commandMap = (CommandMap)f.get(Bukkit.getServer());
      eventListeners.put("playerbedenterevent", new PlayerBedEnterEventListener(Ancient.plugin));
      eventListeners.put("playerbedleaveevent", new PlayerBedLeaveEventListener(Ancient.plugin));
      eventListeners.put("playerchatevent", new PlayerChatEventListener(Ancient.plugin));
      eventListeners.put("playerdropitemevent", new PlayerDropItemEventListener(Ancient.plugin));
      eventListeners.put("playerkickevent", new PlayerKickEventListener(Ancient.plugin));
      eventListeners.put("playerpickupitemevent", new PlayerPickupItemEventListener(Ancient.plugin));
      eventListeners.put("playerportalevent", new PlayerPortalEventListener(Ancient.plugin));
      eventListeners.put("playerteleportevent", new PlayerTeleportEventListener(Ancient.plugin));
      eventListeners.put("playertogglesneakevent", new PlayerToggleSneakEventListener(Ancient.plugin));
      eventListeners.put("playereggthrowevent", new PlayerEggThrowEventListener(Ancient.plugin));
    }
    catch (SecurityException ignored) {}catch (Exception ignored) {}
  }
  
  public final HashSet<String> variables = new HashSet();
  public int minlevel = 0;
  public int priority = 5;
  public String buffEvent;
  final File newConfigFile;
  String permission;
  public boolean hidden = false;
  public boolean leftclickonly = false;
  public boolean rightclickonly = false;
  public String description = "";
  public String shortdescription = "";
  public boolean buff;
  public int repeattime = 0;
  public boolean ignorespellfreezones = false;
  
  public Spell(File f)
  {
    String configName = f.getName().split("\\.(?=[^\\.]+$)")[0] + ".conf";
    String newConfigName = f.getName().split("\\.(?=[^\\.]+$)")[0] + ".cfg";
    long before = System.currentTimeMillis();
    parseFile(f);
    long after = System.currentTimeMillis();
    File configFile = new File(f.getParent() + File.separator + configName);
    this.newConfigFile = new File(f.getParent() + File.separator + newConfigName);
    if (!this.newConfigFile.exists()) {
      try
      {
        this.newConfigFile.createNewFile();
        YamlConfiguration yc = new YamlConfiguration();
        yc.set(this.name + ".description", "");
        Ancient.set(yc, this.name + ".hidden", Boolean.valueOf(false));
        yc.save(this.newConfigFile);
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
    YamlConfiguration yc1 = new YamlConfiguration();
    try
    {
      yc1.load(this.newConfigFile);
      Ancient.set(yc1, this.name + ".description", "");
      this.description = yc1.getString(this.name + ".description", "");
      Ancient.set(yc1, this.name + ".shortdescription", "");
      this.shortdescription = yc1.getString(this.name + ".shortdescription", "");
      Ancient.set(yc1, this.name + ".ignorespellfreezones", Boolean.valueOf(false));
      this.ignorespellfreezones = yc1.getBoolean(this.name + ".ignorespellfreezones", false);
      Ancient.set(yc1, this.name + ".hidden", Boolean.valueOf(false));
      this.hidden = yc1.getBoolean(this.name + ".hidden", false);
      Ancient.set(yc1, this.name + ".leftclickonly", Boolean.valueOf(false));
      this.leftclickonly = yc1.getBoolean(this.name + ".leftclickonly", false);
      Ancient.set(yc1, this.name + ".rightclickonly", Boolean.valueOf(false));
      this.rightclickonly = yc1.getBoolean(this.name + ".rightclickonly", false);
      yc1.save(this.newConfigFile);
    }
    catch (Exception e2)
    {
      e2.printStackTrace();
    }
    if (configFile.exists()) {
      try
      {
        HashMap<String, Integer[]> map = Ancient.plugin.fc.getValues(configFile);
        YamlConfiguration yc = new YamlConfiguration();
        try
        {
          yc.load(this.newConfigFile);
        }
        catch (Exception e1)
        {
          e1.printStackTrace();
        }
        for (Map.Entry<String, Integer[]> entry : map.entrySet())
        {
          Integer[] vals = (Integer[])entry.getValue();
          for (int i = 0; i < vals.length; i++) {
            yc.set(this.name + ".variables." + (String)entry.getKey() + ".level" + (i + 1), vals[i]);
          }
        }
        try
        {
          yc.save(this.newConfigFile);
        }
        catch (IOException e)
        {
          e.printStackTrace();
        }
        configFile.delete();
      }
      catch (Exception e)
      {
        Ancient.plugin.getLogger().log(Level.SEVERE, "Error converting conf file of " + this.name + " spell to cfg file.");
      }
    }
    Bukkit.getLogger().log(Level.INFO, "Compiled spell " + this.name + " took " + (after - before) + " milliseconds");
  }
  
  public void skipCommands(SpellInformationObject so, int count)
  {
    so.skipedCommands += count;
  }
  
  private void parseFile(File f)
  {
    try
    {
      int lineNumber = 1;
      BufferedReader bf = new BufferedReader(new FileReader(f));
      String line = bf.readLine();
      while ((line != null) && (line.startsWith("//")))
      {
        line = bf.readLine();
        lineNumber++;
      }
      this.name = line;
      line = bf.readLine();
      lineNumber++;
      while ((line != null) && (line.startsWith("//")))
      {
        line = bf.readLine();
        lineNumber++;
      }
      if (line != null) {
        if (line.toLowerCase().startsWith("passive"))
        {
          this.active = false;
          if (line.contains(":"))
          {
            String[] args = line.split(Pattern.quote(":"));
            try
            {
              this.priority = Integer.parseInt(args[1]);
            }
            catch (Exception ignored) {}
          }
          line = bf.readLine();
          lineNumber++;
          while ((line.startsWith("//")) && (line != null))
          {
            line = bf.readLine();
            lineNumber++;
          }
          if (line != null)
          {
            line = line.trim();
            attachToEvent(line);
          }
        }
        else if (line.toLowerCase().startsWith("active"))
        {
          this.active = true;
          if (line.contains(":"))
          {
            String[] args = line.split(Pattern.quote(":"));
            try
            {
              this.priority = Integer.parseInt(args[1]);
            }
            catch (Exception ignored) {}
          }
        }
        else if (line.toLowerCase().startsWith("buff"))
        {
          this.active = false;
          this.buff = true;
          if (line.contains(":"))
          {
            String[] args = line.split(Pattern.quote(":"));
            try
            {
              this.priority = Integer.parseInt(args[1]);
            }
            catch (Exception ignored) {}
          }
          line = bf.readLine();
          lineNumber++;
          while ((line.startsWith("//")) && (line != null))
          {
            line = bf.readLine();
            lineNumber++;
          }
          line = line.trim();
          if (line.contains(":"))
          {
            String[] args = line.split(Pattern.quote(":"));
            line = args[0];
            try
            {
              this.priority = Integer.parseInt(args[1]);
            }
            catch (Exception ignored) {}
          }
          this.buffEvent = line.trim().toLowerCase();
        }
        else
        {
          Ancient.plugin.getLogger().log(Level.SEVERE, "Failed to load Spell " + this.name + " in line " + lineNumber + " forgot active/passive/buff declaration?");
          bf.close();
          return;
        }
      }
      line = bf.readLine();
      lineNumber++;
      while ((line != null) && (line.startsWith("//")))
      {
        line = bf.readLine();
        lineNumber++;
      }
      if (line.startsWith("variables")) {
        try
        {
          String vars = line.split(":")[1].trim();
          for (String s : vars.split(",")) {
            this.variables.add(s.trim().toLowerCase());
          }
        }
        catch (Exception e)
        {
          Ancient.plugin.getLogger().log(Level.SEVERE, "Failed to load Spell " + this.name + " in line " + lineNumber + " variable declaration in wrong line?");
          bf.close();
          return;
        }
      }
      while ((line != null) && (line.startsWith("//")))
      {
        line = bf.readLine();
        lineNumber++;
      }
      if (line.toLowerCase().startsWith("minlevel:"))
      {
        String[] minlevelS = line.split(":");
        if (minlevelS.length == 2)
        {
          try
          {
            this.minlevel = Integer.parseInt(minlevelS[1].trim());
          }
          catch (Exception e)
          {
            Ancient.plugin.getLogger().log(Level.SEVERE, "Error parsing spell: " + this.name);
            Ancient.plugin.getLogger().log(Level.SEVERE, "Error in line " + lineNumber + ": " + line);
            Ancient.plugin.getLogger().log(Level.SEVERE, "Could not parse the minlevel of the spell");
          }
        }
        else
        {
          Ancient.plugin.getLogger().log(Level.SEVERE, "Error parsing spell: " + this.name);
          Ancient.plugin.getLogger().log(Level.SEVERE, "Error in line " + lineNumber + ": " + line);
          Ancient.plugin.getLogger().log(Level.SEVERE, "Could not parse the minlevel of the spell");
        }
        line = bf.readLine();
        lineNumber++;
      }
      while ((line.startsWith("//")) && (line != null))
      {
        line = bf.readLine();
        lineNumber++;
      }
      if (line.toLowerCase().startsWith("permission:"))
      {
        String[] perms = line.split(":");
        if (perms.length == 2)
        {
          this.permission = perms[1].trim();
        }
        else
        {
          Ancient.plugin.getLogger().log(Level.SEVERE, "Error parsing spell: " + this.name);
          Ancient.plugin.getLogger().log(Level.SEVERE, "Error in line " + lineNumber + ": " + line);
          Ancient.plugin.getLogger().log(Level.SEVERE, "Could not parse the permission of the spell");
        }
        line = bf.readLine();
        lineNumber++;
        while ((line.startsWith("//")) && (line != null))
        {
          line = bf.readLine();
          lineNumber++;
        }
      }
      this.mainsection = new CodeSection(line, bf, lineNumber, this, null);
      bf.close();
    }
    catch (FileNotFoundException e)
    {
      Ancient.plugin.log.log(Level.SEVERE, "Wasn't able to load a spell. Reason: File not found!");
    }
    catch (Exception e)
    {
      Ancient.plugin.log.log(Level.SEVERE, "Wasn't able to load a spell. Reason: Error parsing file!");
    }
  }
  
  public void attachToEvent(String s)
  {
    if (s == null) {
      Ancient.plugin.getLogger().log(Level.WARNING, "Ancient: No event found to attach to in spell " + this.name);
    }
    if (s.equalsIgnoreCase("damageevent"))
    {
      AncientSpellListener.damageEventSpells.add(this);
    }
    else if (s.equalsIgnoreCase("damagebyentityevent"))
    {
      AncientSpellListener.damageByEntityEventSpells.add(this);
    }
    else if (s.equalsIgnoreCase("attackevent"))
    {
      AncientSpellListener.attackEventSpells.add(this);
    }
    else if (s.equalsIgnoreCase("changeblockevent"))
    {
      AncientSpellListener.ChangeBlockEventSpells.add(this);
    }
    else if (s.equalsIgnoreCase("joinevent"))
    {
      AncientSpellListener.joinEventSpells.add(this);
    }
    else if (s.equalsIgnoreCase("interactevent"))
    {
      AncientSpellListener.interactEventSpells.add(this);
    }
    else if (s.equalsIgnoreCase("chatevent"))
    {
      AncientSpellListener.chatEventSpells.add(this);
    }
    else if (s.equalsIgnoreCase("regenevent"))
    {
      AncientSpellListener.regenEventSpells.add(this);
    }
    else if (s.equalsIgnoreCase("moveevent"))
    {
      AncientSpellListener.moveEventSpells.add(this);
    }
    else if (s.equalsIgnoreCase("levelupevent"))
    {
      AncientSpellListener.LevelupEventSpells.add(this);
    }
    else if (s.equalsIgnoreCase("killentityevent"))
    {
      AncientSpellListener.killEntityEventSpells.add(this);
    }
    else if (s.equalsIgnoreCase("playerdeathevent"))
    {
      AncientSpellListener.playerDeathSpells.add(this);
    }
    else if (s.equalsIgnoreCase("projectilehitevent"))
    {
      AncientSpellListener.ProjectileHitEventSpells.add(this);
    }
    else if (s.equalsIgnoreCase("usebedevent"))
    {
      AncientSpellListener.UseBedEventSpells.add(this);
    }
    else if (s.equalsIgnoreCase("classchangeevent"))
    {
      AncientSpellListener.classChangeEventSpells.add(this);
    }
    else if (s.startsWith("chatcommand"))
    {
      String[] split = s.split(Pattern.quote(":"));
      if (split.length != 2) {
        Bukkit.getLogger().log(Level.SEVERE, "Spell " + this.name + ": error in line: " + s);
      } else if (commandMap == null) {
        Bukkit.getLogger().log(Level.SEVERE, "Wasn't able to register Commands.");
      } else {
        try
        {
          Constructor<?> c = Class.forName("org.bukkit.command.PluginCommand").getDeclaredConstructors()[0];
          c.setAccessible(true);
          PluginCommand pc = (PluginCommand)c.newInstance(new Object[] { split[1], Ancient.plugin });
          final Spell mSpell = this;
          pc.setExecutor(new CommandExecutor()
          {
            public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
            {
              if ((sender instanceof Player))
              {
                Player p = (Player)sender;
                for (Map.Entry<String, SerializableZone> sz : AddSpellFreeZoneCommand.spellfreezones.entrySet()) {
                  if ((((SerializableZone)sz.getValue()).isInZone(p.getLocation())) && (!p.hasPermission("Ancient.spells.ignorespellfreezones"))) {
                    return true;
                  }
                }
                if (AncientClass.spellAvailable(mSpell.name, PlayerData.getPlayerData(p.getUniqueId()))) {
                  mSpell.execute(p, p, null, args);
                }
              }
              return true;
            }
          });
          commandMap.register(split[1], pc);
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
    }
    else if (s.startsWith("repetitive"))
    {
      String[] split = s.split(Pattern.quote(":"));
      if (split.length != 2)
      {
        Bukkit.getLogger().log(Level.SEVERE, "Spell " + this.name + ": error in line: " + s);
      }
      else
      {
        int time;
        try
        {
          time = Math.round(Integer.parseInt(split[1].trim()) / 50);
        }
        catch (Exception e)
        {
          Bukkit.getLogger().log(Level.SEVERE, "Spell " + this.name + ": error in line: " + s + " 2nd argumend needs to be an integer");
          return;
        }
        if (registeredTasks.containsKey(this.name)) {
          Bukkit.getScheduler().cancelTask(((Integer)registeredTasks.get(this.name)).intValue());
        }
        registeredTasks.put(this.name, Integer.valueOf(Bukkit.getScheduler().scheduleSyncRepeatingTask(Ancient.plugin, new Runnable()
        {
          public void run()
          {
            if (Spell.this.serverspell) {
              Spell.this.executeServerSpell();
            } else {
              for (Player p : Bukkit.getOnlinePlayers()) {
                if (AncientClass.spellAvailable(Spell.this.name, PlayerData.getPlayerData(p.getUniqueId())))
                {
                  for (Map.Entry<String, SerializableZone> sz : AddSpellFreeZoneCommand.spellfreezones.entrySet()) {
                    if ((((SerializableZone)sz.getValue()).isInZone(p.getLocation())) && (!p.hasPermission("Ancient.spells.ignorespellfreezones"))) {
                      return;
                    }
                  }
                  Spell.this.execute(p.getUniqueId(), p.getUniqueId(), null);
                }
              }
            }
          }
        }, time, time)));
      }
    }
    else
    {
      for (Map.Entry<String, ISpellListener> entry : eventListeners.entrySet()) {
        if (((String)entry.getKey()).equalsIgnoreCase(s))
        {
          ((ISpellListener)entry.getValue()).eventSpells.add(this);
          return;
        }
      }
      Ancient.plugin.getLogger().log(Level.WARNING, "Ancient: Event " + s + " not found in spell " + this.name);
    }
  }
  
  public static void initializeServerSpells()
  {
    File serv = new File(Ancient.plugin.getDataFolder().getPath() + File.separator + "serverspells");
    serv.mkdir();
    File[] files = serv.listFiles();
    if (files != null) {
      for (File f : files) {
        if (f.getName().endsWith(".spell"))
        {
          Spell s = new Spell(f);
          s.serverspell = true;
        }
      }
    }
  }
  
  public int attachToEventAsBuff(Player p, Player buffcaster)
  {
    if ((this.permission != null) && (!this.permission.equals("")) && (!buffcaster.hasPermission(this.permission))) {
      return Integer.MAX_VALUE;
    }
    if (this.buffEvent == null)
    {
      Ancient.plugin.getLogger().log(Level.WARNING, "Ancient: No event found to attach to in spell " + this.name);
      return Integer.MAX_VALUE;
    }
    Player[] arr = { p, buffcaster };
    if (this.buffEvent.equalsIgnoreCase("damageevent")) {
      return AncientSpellListener.attachBuffToEvent(this, AncientSpellListener.damageEventBuffs, arr);
    }
    if (this.buffEvent.equalsIgnoreCase("damagebyentityevent")) {
      return AncientSpellListener.attachBuffToEvent(this, AncientSpellListener.damageByEntityEventBuffs, arr);
    }
    if (this.buffEvent.equalsIgnoreCase("attackevent")) {
      return AncientSpellListener.attachBuffToEvent(this, AncientSpellListener.attackEventBuffs, arr);
    }
    if (this.buffEvent.equalsIgnoreCase("changeblockevent")) {
      return AncientSpellListener.attachBuffToEvent(this, AncientSpellListener.ChangeBlockEventBuffs, arr);
    }
    if (this.buffEvent.equalsIgnoreCase("joinevent")) {
      return AncientSpellListener.attachBuffToEvent(this, AncientSpellListener.joinEventBuffs, arr);
    }
    if (this.buffEvent.equalsIgnoreCase("interactevent")) {
      return AncientSpellListener.attachBuffToEvent(this, AncientSpellListener.interactEventBuffs, arr);
    }
    if (this.buffEvent.equalsIgnoreCase("chatevent")) {
      return AncientSpellListener.attachBuffToEvent(this, AncientSpellListener.chatEventBuffs, arr);
    }
    if (this.buffEvent.equalsIgnoreCase("regenevent")) {
      return AncientSpellListener.attachBuffToEvent(this, AncientSpellListener.regenEventBuffs, arr);
    }
    if (this.buffEvent.equalsIgnoreCase("moveevent")) {
      return AncientSpellListener.attachBuffToEvent(this, AncientSpellListener.moveEventBuffs, arr);
    }
    if (this.buffEvent.equalsIgnoreCase("levelupevent")) {
      return AncientSpellListener.attachBuffToEvent(this, AncientSpellListener.LevelupEventBuffs, arr);
    }
    if (this.buffEvent.equalsIgnoreCase("playerdeathevent")) {
      return AncientSpellListener.attachBuffToEvent(this, AncientSpellListener.playerDeathBuffs, arr);
    }
    if (this.buffEvent.equalsIgnoreCase("killentityevent")) {
      return AncientSpellListener.attachBuffToEvent(this, AncientSpellListener.killEntityEventBuffs, arr);
    }
    if (this.buffEvent.equalsIgnoreCase("projectilehitevent")) {
      return AncientSpellListener.attachBuffToEvent(this, AncientSpellListener.ProjectileHitEventBuffs, arr);
    }
    if (this.buffEvent.equalsIgnoreCase("usebedevent")) {
      return AncientSpellListener.attachBuffToEvent(this, AncientSpellListener.UseBedEventBuffs, arr);
    }
    if (this.buffEvent.equalsIgnoreCase("classchangeevent")) {
      return AncientSpellListener.attachBuffToEvent(this, AncientSpellListener.classChangeEventBuffs, arr);
    }
    for (Map.Entry<String, ISpellListener> entry : eventListeners.entrySet()) {
      if (((String)entry.getKey()).equalsIgnoreCase(this.buffEvent)) {
        return ((ISpellListener)entry.getValue()).attachBuffToEvent(this, arr);
      }
    }
    Ancient.plugin.getLogger().log(Level.WARNING, "Ancient: Event " + this.buffEvent + " not found in spell " + this.name);
    return Integer.MAX_VALUE;
  }
  
  public void detachBuffOfEvent(Player p, Player buffcaster, int id)
  {
    if (this.buffEvent == null) {
      Ancient.plugin.getLogger().log(Level.WARNING, "Ancient: No event found to attach to in spell " + this.name);
    }
    Player[] arr = { p, buffcaster };
    if (this.buffEvent.equalsIgnoreCase("damageevent"))
    {
      AncientSpellListener.detachBuffToEvent(this, AncientSpellListener.damageEventBuffs, arr, id);
    }
    else if (this.buffEvent.equalsIgnoreCase("damagebyentityevent"))
    {
      AncientSpellListener.detachBuffToEvent(this, AncientSpellListener.damageByEntityEventBuffs, arr, id);
    }
    else if (this.buffEvent.equalsIgnoreCase("attackevent"))
    {
      AncientSpellListener.detachBuffToEvent(this, AncientSpellListener.attackEventBuffs, arr, id);
    }
    else if (this.buffEvent.equalsIgnoreCase("changeblockevent"))
    {
      AncientSpellListener.detachBuffToEvent(this, AncientSpellListener.ChangeBlockEventBuffs, arr, id);
    }
    else if (this.buffEvent.equalsIgnoreCase("joinevent"))
    {
      AncientSpellListener.detachBuffToEvent(this, AncientSpellListener.joinEventBuffs, arr, id);
    }
    else if (this.buffEvent.equalsIgnoreCase("interactevent"))
    {
      AncientSpellListener.detachBuffToEvent(this, AncientSpellListener.interactEventBuffs, arr, id);
    }
    else if (this.buffEvent.equalsIgnoreCase("chatevent"))
    {
      AncientSpellListener.detachBuffToEvent(this, AncientSpellListener.chatEventBuffs, arr, id);
    }
    else if (this.buffEvent.equalsIgnoreCase("regenevent"))
    {
      AncientSpellListener.detachBuffToEvent(this, AncientSpellListener.regenEventBuffs, arr, id);
    }
    else if (this.buffEvent.equalsIgnoreCase("moveevent"))
    {
      AncientSpellListener.detachBuffToEvent(this, AncientSpellListener.moveEventBuffs, arr, id);
    }
    else if (this.buffEvent.equalsIgnoreCase("levelupevent"))
    {
      AncientSpellListener.detachBuffToEvent(this, AncientSpellListener.LevelupEventBuffs, arr, id);
    }
    else if (this.buffEvent.equalsIgnoreCase("playerdeathevent"))
    {
      AncientSpellListener.detachBuffToEvent(this, AncientSpellListener.playerDeathBuffs, arr, id);
    }
    else if (this.buffEvent.equalsIgnoreCase("killentityevent"))
    {
      AncientSpellListener.detachBuffToEvent(this, AncientSpellListener.killEntityEventBuffs, arr, id);
    }
    else if (this.buffEvent.equalsIgnoreCase("projectilehitevent"))
    {
      AncientSpellListener.detachBuffToEvent(this, AncientSpellListener.ProjectileHitEventBuffs, arr, id);
    }
    else if (this.buffEvent.equalsIgnoreCase("usebedevent"))
    {
      AncientSpellListener.detachBuffToEvent(this, AncientSpellListener.UseBedEventBuffs, arr, id);
    }
    else if (this.buffEvent.equalsIgnoreCase("classchangeevent"))
    {
      AncientSpellListener.detachBuffToEvent(this, AncientSpellListener.classChangeEventBuffs, arr, id);
    }
    else
    {
      for (Map.Entry<String, ISpellListener> entry : eventListeners.entrySet()) {
        if (((String)entry.getKey()).equalsIgnoreCase(this.buffEvent))
        {
          ((ISpellListener)entry.getValue()).detachBuffOfEvent(this, arr, id);
          return;
        }
      }
      Ancient.plugin.getLogger().log(Level.WARNING, "Ancient: Event " + this.buffEvent + " not found in spell " + this.name);
    }
  }
  
  public void executeServerSpell()
  {
    SpellInformationObject so = new SpellInformationObject();
    so.variables.putAll(Variable.globVars);
    so.mSpell = this;
    if (this.mainsection != null) {
      try
      {
        this.mainsection.executeCommand(null, so);
      }
      catch (Exception ignored) {}
    }
  }
  
  public void execute(Player p, Player buffcaster, Event e, String[] args)
  {
    if (disabledWorlds.contains(p.getWorld().getName().toLowerCase()))
    {
      buffcaster.sendMessage("This spell is not enabled in this world");
      return;
    }
    if ((this.permission != null) && (!this.permission.equals("")) && (!buffcaster.hasPermission(this.permission)))
    {
      if (this.active) {
        buffcaster.sendMessage("You don't have permission to cast this spell");
      }
      return;
    }
    if ((AncientExperience.isWorldEnabled(buffcaster.getWorld())) && 
      (PlayerData.getPlayerData(buffcaster.getUniqueId()).getXpSystem().level < this.minlevel))
    {
      if (this.active) {
        buffcaster.sendMessage("You need to be atleast level " + this.minlevel + " to cast this spell");
      }
      return;
    }
    for (Map.Entry<String, SerializableZone> sz : AddSpellFreeZoneCommand.spellfreezones.entrySet()) {
      if ((((SerializableZone)sz.getValue()).isInZone(p.getLocation())) && (!p.hasPermission("Ancient.spells.ignorespellfreezones")))
      {
        if (this.active) {
          p.sendMessage("You can't use a spell in this zone");
        }
        return;
      }
    }
    SpellInformationObject so = new SpellInformationObject();
    so.chatmessage = args;
    so.variables.putAll(Variable.globVars);
    if (Variable.playerVars.containsKey(buffcaster.getUniqueId())) {
      so.variables.putAll((Map)Variable.playerVars.get(buffcaster.getUniqueId()));
    }
    if (e != null) {
      so.mEvent = e;
    }
    AncientClass.executedSpells.put(so, p.getUniqueId());
    so.buffcaster = buffcaster;
    so.mSpell = this;
    if (this.mainsection != null) {
      this.mainsection.executeCommand(p, so);
    }
  }
  
  public void execute(UUID uuidPlayer, UUID uuidBuffcaster, Event e)
  {
    Player p = Bukkit.getPlayer(uuidPlayer);
    Player buffcaster = Bukkit.getPlayer(uuidBuffcaster);
    if ((p == null) || (buffcaster == null)) {
      return;
    }
    if (disabledWorlds.contains(p.getWorld().getName().toLowerCase()))
    {
      buffcaster.sendMessage("This spell is not enabled in this world");
      return;
    }
    if ((this.permission != null) && (!this.permission.equals("")) && (!buffcaster.hasPermission(this.permission)))
    {
      if (this.active) {
        buffcaster.sendMessage("You don't have permission to cast this spell");
      }
      return;
    }
    if ((AncientExperience.isWorldEnabled(buffcaster.getWorld())) && 
      (PlayerData.getPlayerData(buffcaster.getUniqueId()).getXpSystem().level < this.minlevel))
    {
      if (this.active) {
        buffcaster.sendMessage("You need to be atleast level " + this.minlevel + " to cast this spell");
      }
      return;
    }
    for (Map.Entry<String, SerializableZone> sz : AddSpellFreeZoneCommand.spellfreezones.entrySet()) {
      if ((((SerializableZone)sz.getValue()).isInZone(p.getLocation())) && (!p.hasPermission("Ancient.spells.ignorespellfreezones")))
      {
        if (this.active) {
          p.sendMessage("You can't use a spell in this zone");
        }
        return;
      }
    }
    SpellInformationObject so = new SpellInformationObject();
    so.variables.putAll(Variable.globVars);
    if (Variable.playerVars.containsKey(buffcaster.getUniqueId())) {
      so.variables.putAll((Map)Variable.playerVars.get(buffcaster.getUniqueId()));
    }
    if (e != null) {
      so.mEvent = e;
    }
    AncientClass.executedSpells.put(so, p.getUniqueId());
    so.buffcaster = buffcaster;
    so.mSpell = this;
    if (this.mainsection != null) {
      this.mainsection.executeCommand(p, so);
    }
  }
  
  public static void loadConfig(FileConfiguration yc)
  {
    disabledWorlds = new HashSet();
    String s = yc.getString("Spells.disabled worlds", "");
    s = s.trim();
    String[] splits = s.split(Pattern.quote(","));
    for (String sn : splits) {
      disabledWorlds.add(sn.trim().toLowerCase());
    }
  }
  
  public static void writeConfig(FileConfiguration yc)
  {
    String s = "";
    for (String sn : disabledWorlds) {
      s = s + sn + ", ";
    }
    s = s.trim();
    if (s.endsWith(",")) {
      s = s.substring(0, s.length() - 1);
    }
    yc.set("Spells.disabled worlds", s.trim());
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Spell.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */