package com.ancientshores.Ancient.Race;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.Spell;
import com.ancientshores.Ancient.PlayerData;
import com.ancientshores.Ancient.Race.Commands.RaceHelpCommand;
import com.ancientshores.Ancient.Race.Commands.RaceListCommand;
import com.ancientshores.Ancient.Race.Commands.RaceSpawnCommand;
import com.ancientshores.Ancient.Race.Commands.SetRaceCommand;
import com.ancientshores.Ancient.Race.Commands.SetRaceSpawnCommand;
import com.ancientshores.Ancient.Util.SerializableLocation;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class AncientRace
{
  public final String name;
  public static boolean enabled = true;
  public static final String enabledConfigNode = "Ancient.Race.enabled";
  public static final String setRacePermission = "Ancient.Race.set";
  public static final String listRacePermission = "Ancient.Race.list";
  public static final String adminRacePermission = "Ancient.Race.admin";
  public static final String teleportToSpawnPermission = "Ancient.Race.spawn";
  public static final String configCd = "Race.change cooldown in seconds";
  public static int changeCd = 2;
  public static final String configDefaultRace = "Race.default race";
  public static String defaultRace = "";
  public static HashSet<AncientRace> races = new HashSet();
  public static ConcurrentHashMap<UUID, Long> playersOnCd = new ConcurrentHashMap();
  public String permission = "";
  public String description = "";
  public final String permissionNode = "Race.permission";
  public SerializableLocation spawnLoc = null;
  public final HashSet<Spell> raceSpells = new HashSet();
  public final File root;
  
  public AncientRace(File folder)
  {
    this.name = folder.getName();
    this.root = folder;
    loadSpells(folder);
    loadConfig();
    writeConfig();
  }
  
  public void loadSpells(File f)
  {
    File[] files = f.listFiles();
    if (files != null) {
      for (File spell : files) {
        if (spell.getPath().endsWith(".spell")) {
          this.raceSpells.add(new Spell(spell));
        }
      }
    }
  }
  
  public static void processCommand(CommandSender cs, String[] command)
  {
    if (command.length >= 1)
    {
      if ((cs instanceof Player))
      {
        Player p = (Player)cs;
        if (command[0].equalsIgnoreCase("set")) {
          SetRaceCommand.setRaceCommand(p, command);
        }
        if (command[0].equalsIgnoreCase("help")) {
          RaceHelpCommand.processHelp(p, command);
        } else if (command[0].equalsIgnoreCase("list")) {
          RaceListCommand.showRaces(p, command);
        } else if ((command[0].equalsIgnoreCase("setspawn")) && (command.length == 2)) {
          SetRaceSpawnCommand.setRaceSpawn(p, command[1]);
        } else if (command[0].equalsIgnoreCase("spawn")) {
          RaceSpawnCommand.raceSpawnCommand(p);
        }
      }
    }
    else if ((cs instanceof Player))
    {
      Player p = (Player)cs;
      AncientRace race = getRaceByName(PlayerData.getPlayerData(p.getUniqueId()).getRacename());
      if (race != null) {
        cs.sendMessage("Your race is " + race.name);
      } else {
        cs.sendMessage("You aren't part of a race");
      }
    }
  }
  
  public void loadConfig()
  {
    File config = new File(this.root.getPath() + File.separator + this.name + ".conf");
    if (config.exists())
    {
      YamlConfiguration yc = new YamlConfiguration();
      try
      {
        yc.load(config);
        this.permission = yc.getString("Race.permission", this.permission);
        if (yc.get("Race.spawnx") != null) {
          this.spawnLoc = new SerializableLocation(new Location(Bukkit.getWorld(yc.getString("Race.spawnworld")), yc.getDouble("Race.spawnx"), yc.getDouble("Race.spawny"), yc.getDouble("Race.spawnz")));
        }
        this.description = yc.getString("Race.description", this.description);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
  }
  
  public void writeConfig()
  {
    File config = new File(this.root.getPath() + File.separator + this.name + ".conf");
    YamlConfiguration yc = new YamlConfiguration();
    try
    {
      if (yc.get("Race.permission") == null) {
        yc.set("Race.permission", this.permission);
      }
      if (this.spawnLoc != null)
      {
        yc.set("Race.spawnworld", this.spawnLoc.wName);
        yc.set("Race.spawnx", Double.valueOf(this.spawnLoc.x));
        yc.set("Race.spawny", Double.valueOf(this.spawnLoc.y));
        yc.set("Race.spawnz", Double.valueOf(this.spawnLoc.z));
      }
      Ancient.set(yc, "Race.description", this.description);
      yc.save(config);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public static AncientRace getRaceByName(String name)
  {
    for (AncientRace race : races) {
      if (race.name.equalsIgnoreCase(name)) {
        return race;
      }
    }
    return null;
  }
  
  public static void loadRacesConfig(Ancient instance)
  {
    File newconfig = new File(instance.getDataFolder().getPath() + File.separator + "raceconfig.yml");
    if (newconfig.exists())
    {
      YamlConfiguration yc = new YamlConfiguration();
      try
      {
        yc.load(newconfig);
        enabled = yc.getBoolean("Ancient.Race.enabled", enabled);
        changeCd = yc.getInt("Race.change cooldown in seconds", changeCd);
        defaultRace = yc.getString("Race.default race", defaultRace);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    else
    {
      enabled = instance.getConfig().getBoolean("Ancient.Race.enabled", enabled);
      changeCd = instance.getConfig().getInt("Race.change cooldown in seconds", changeCd);
      defaultRace = instance.getConfig().getString("Race.default race", defaultRace);
    }
  }
  
  public static void writeRacesConfig(Ancient instance)
  {
    File newconfig = new File(instance.getDataFolder().getPath() + File.separator + "raceconfig.yml");
    if (!newconfig.exists()) {
      try
      {
        newconfig.createNewFile();
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    YamlConfiguration yc = new YamlConfiguration();
    yc.set("Ancient.Race.enabled", Boolean.valueOf(enabled));
    yc.set("Race.change cooldown in seconds", Integer.valueOf(changeCd));
    yc.set("Race.default race", defaultRace);
    try
    {
      yc.save(newconfig);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  public static void loadRaces()
  {
    File raceFolder = new File(Ancient.plugin.getDataFolder().getPath() + File.separator + "Races");
    if (!raceFolder.exists()) {
      raceFolder.mkdirs();
    }
    File[] files = raceFolder.listFiles();
    if (files != null) {
      for (File f : files) {
        if (f.isDirectory()) {
          races.add(new AncientRace(f));
        }
      }
    }
    File f = new File(Ancient.plugin.getDataFolder() + File.separator + "Races" + File.separator + "changecds.dat");
    if (f.exists()) {
      try
      {
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line = br.readLine()) != null) {
          playersOnCd.put(UUID.fromString(line.split(";")[0]), Long.getLong(line.split(";")[1]));
        }
        br.close();
        fr.close();
      }
      catch (Exception ignored) {}
    }
  }
}
