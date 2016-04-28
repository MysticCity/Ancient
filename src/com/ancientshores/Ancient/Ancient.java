package com.ancientshores.Ancient;

import com.ancient.util.PlayerFinder;
import com.ancient.util.UUIDConverter;
import com.ancientshores.Ancient.API.ApiManager;
import com.ancientshores.Ancient.Classes.AncientClass;
import com.ancientshores.Ancient.Classes.AncientClassHelp;
import com.ancientshores.Ancient.Classes.BindingData;
import com.ancientshores.Ancient.Classes.Commands.ClassUnbindCommand;
import com.ancientshores.Ancient.Classes.CooldownTimer;
import com.ancientshores.Ancient.Classes.Spells.Commands.CommandPlayer;
import com.ancientshores.Ancient.Classes.Spells.Conditions.IArgument;
import com.ancientshores.Ancient.Classes.Spells.Parameter;
import com.ancientshores.Ancient.Classes.Spells.Spell;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import com.ancientshores.Ancient.Commands.AncientCommand;
import com.ancientshores.Ancient.Display.Display;
import com.ancientshores.Ancient.Experience.AncientExperience;
import com.ancientshores.Ancient.Experience.SetXpCommand;
import com.ancientshores.Ancient.Guild.AncientGuild;
import com.ancientshores.Ancient.Guild.AncientGuildRanks;
import com.ancientshores.Ancient.Guild.Commands.GuildCommandChat;
import com.ancientshores.Ancient.Guild.Commands.GuildCommandHelp;
import com.ancientshores.Ancient.HP.AncientHP;
import com.ancientshores.Ancient.HP.Armor;
import com.ancientshores.Ancient.HP.CreatureHp;
import com.ancientshores.Ancient.HP.DamageConverter;
import com.ancientshores.Ancient.HP.HPCommand;
import com.ancientshores.Ancient.Listeners.AncientBlockListener;
import com.ancientshores.Ancient.Listeners.AncientEntityListener;
import com.ancientshores.Ancient.Listeners.AncientPlayerListener;
import com.ancientshores.Ancient.Listeners.AncientSpellListener;
import com.ancientshores.Ancient.Listeners.External.MythicMobsListener;
import com.ancientshores.Ancient.Mana.ManaSystem;
import com.ancientshores.Ancient.Party.AncientParty;
import com.ancientshores.Ancient.Party.Commands.PartyCommandChat;
import com.ancientshores.Ancient.Party.Commands.PartyCommandHelp;
import com.ancientshores.Ancient.Permissions.AncientPermission;
import com.ancientshores.Ancient.Race.AncientRace;
import com.ancientshores.Ancient.Spells.Commands.AddSpellFreeZoneCommand;
import com.ancientshores.Ancient.Spells.Commands.SpellBindCommand;
import com.ancientshores.Ancient.Spells.Commands.SpellFreeZoneListener;
import com.ancientshores.Ancient.Spells.Commands.SpellsCommandExecutor;
import com.ancientshores.Ancient.Util.FlatFileConnector;
import com.ancientshores.Ancient.Util.SerializableZone;
import de.slikey.effectlib.EffectLib;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.milkbowl.vault.Metrics;
import net.milkbowl.vault.Vault;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class Ancient
  extends JavaPlugin
{
  public Config config;
  public static Permission permissionHandler;
  public static Ancient plugin;
  public static Economy economy;
  public static final String AdminPermission = "Ancient.admin";
  public static CommandMap commandMap;
  static ApiManager manager;
  public final FlatFileConnector fc = new FlatFileConnector(this);
  public Thread bukkitThread;
  public CommandPlayer ef;
  public static String languagecode;
  public static EffectLib effectLib;
  HashMap<SpellInformationObject, UUID> executingSpells = new HashMap();
  public Logger log;
  public static String partyCommand = "party";
  public static final String partyCommandNode = "Ancient.Commands.party";
  public static String guildCommand = "guild";
  public static final String guildCommandNode = "Ancient.Commands.guild";
  public static String classCommand = "class";
  public static final String classCommandNode = "Ancient.Commands.class";
  public static String levelCommand = "level";
  public static final String levelCommandNode = "Ancient.Commands.level";
  public static String hpCommand = "hp";
  public static final String hpCommandNode = "Ancient.Commands.hp";
  public static String raceCommand = "race";
  public static final String raceCommandNode = "Ancient.Commands.race";
  public static String spellsCommand = "spells";
  public static final String spellsCommandNode = "Ancient.Commands.spells";
  public static String manaCommand = "mana";
  public static final String manaCommandNode = "Ancient.Commands.mana";
  public static String arCommand = "ar";
  public static final String arCommandNode = "Ancient.Commands.ar";
  public static String pcCommand = "pc";
  public static final String pcCommandNode = "Ancient.Commands.pc";
  public static String gcCommand = "gc";
  public static final String gcCommandNode = "Ancient.Commands.gc";
  public static String bindCommand = "bind";
  public static final String bindCommandNode = "Ancient.Commands.bind";
  public static String unbindCommand = "unbind";
  public static final String unbindCommandNode = "Ancient.Commands.unbind";
  public static String ancientCommand = "ancient";
  public static final String ancientCommandNode = "Ancient.Commands.ancient";
  public static String versionString = "";
  static Locale currentLocale;
  public static String brand = "ANCIENT";
  public static String brand2 = "";
  static ResourceBundle messages;
  
  public void onEnable()
  {
    this.log = getLogger();
    this.bukkitThread = Thread.currentThread();
    getDataFolder().mkdir();
    manager = ApiManager.getApiManager();
    plugin = this;
    Plugin vault = getServer().getPluginManager().getPlugin("Vault");
    if ((vault != null) && ((vault instanceof Vault)))
    {
      this.log.info(String.format("[%s] Enabled Version %s", new Object[] { getDescription().getName(), getDescription().getVersion() }));
    }
    else
    {
      this.log.warning(String.format("[%s] Vault was _NOT_ found! Disabling plugin.", new Object[] { getDescription().getName() }));
      getPluginLoader().disablePlugin(this);
    }
    try
    {
      versionString = Bukkit.getServer().getClass().getPackage().getName().substring(Bukkit.getServer().getClass().getPackage().getName().lastIndexOf('.') + 1, Bukkit.getServer().getClass().getPackage().getName().length());
      
      Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
      f.setAccessible(true);
      commandMap = (CommandMap)f.get(Bukkit.getServer());
    }
    catch (Exception ex) {}
    try
    {
      Metrics metrics = new Metrics(this);
      metrics.start();
    }
    catch (IOException ex) {}
    enableSerialization();
    
    UUIDConverter.runConverter(this, new File(plugin.getDataFolder().getPath() + "/players/"));
    
    PlayerFinder.loadAllPlayers(new File(plugin.getDataFolder().getPath() + "/players/"));
    
    setupPermissions();
    
    this.config = new Config(this);
    
    new AncientPlayerListener(this);
    new AncientBlockListener(this);
    new AncientEntityListener(this);
    if ((getServer().getPluginManager().getPlugin("MythicMobs") != null) && (getServer().getPluginManager().getPlugin("MythicMobs").isEnabled())) {
      new MythicMobsListener(this);
    }
    this.config.loadkeys();
    this.config.addDefaults();
    
    Parameter.registerDefaultParameters();
    IArgument.addDefaults();
    com.ancientshores.Ancient.Classes.Spells.Command.putDefaults();
    
    registerCommands();
    try
    {
      Locale l = new Locale(languagecode);
      messages = ResourceBundle.getBundle("AncientMessages", l);
    }
    catch (Exception ex) {}
    setupEconomy();
    saveConfig();
    
    AncientGuild.loadGuilds();
    new AncientSpellListener(this);
    AncientClass.loadClasses();
    AncientRace.loadRaces();
    if (!new File(getDataFolder().getPath() + "/level").exists()) {
      new File(getDataFolder().getPath() + "/level").mkdir();
    }
    if (!new File(getDataFolder().getPath() + "/images").exists()) {
      new File(getDataFolder().getPath() + "/images").mkdir();
    }
    File configFile = new File(getDataFolder().getPath() + "/level/level.conf");
    if (!configFile.exists()) {
      try
      {
        configFile.createNewFile();
        BufferedWriter bw = new BufferedWriter(new FileWriter(configFile));
        bw.write("level \n");
        for (int i = 1; i <= 10; i++) {
          bw.write("" + i + "\n");
        }
        bw.flush();
        bw.close();
      }
      catch (Exception e)
      {
        plugin.getLogger().log(Level.SEVERE, "Ancient: unable to create spell config file " + configFile.getName());
      }
    }
    try
    {
      File f = new File(plugin.getDataFolder().getPath() + File.separator + "spellfreezones");
      f.mkdir();
      File[] files = f.listFiles();
      if (files != null) {
        for (File nf : files) {
          if (nf.getName().endsWith(".sfz")) {
            try
            {
              String name = nf.getName().substring(0, nf.getName().lastIndexOf('.'));
              FileInputStream fis = new FileInputStream(nf);
              ObjectInputStream ois = new ObjectInputStream(fis);
              SerializableZone sz = (SerializableZone)ois.readObject();
              AddSpellFreeZoneCommand.spellfreezones.put(name, sz);
              ois.close();
            }
            catch (Exception ex)
            {
              ex.printStackTrace();
            }
          }
        }
      }
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    for (PlayerData pd : PlayerData.playerData) {
      pd.createMissingObjects();
    }
    PlayerData.startSaveTimer();
    initializeHelpFiles();
    new SpellFreeZoneListener(this);
    new AncientSpellListener(this);
    AncientClass.addPerms();
    CreatureHp.startCleaner();
    for (Player p : Bukkit.getOnlinePlayers())
    {
      PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
      pd.getHpsystem().playerUUID = p.getUniqueId();
      if (DamageConverter.isEnabledInWorld(p.getWorld()))
      {
        pd.getHpsystem().setMaxHP();
        pd.getHpsystem().setHpRegen();
        pd.getHpsystem().setMinecraftHP();
      }
      else
      {
        p.setMaxHealth(20.0D);
      }
      pd.getManasystem().setMaxMana();
      if (AncientExperience.isEnabled()) {
        pd.getXpSystem().addXP(0, false);
      }
    }
    Armor.loadConfig();
    
    effectLib = EffectLib.instance();
    
    Display.loadConfig(this);
    
    Spell.initializeServerSpells();
  }
  
  private void enableSerialization()
  {
    ConfigurationSerialization.registerClass(BindingData.class, "com.ancientshores.AncientRPG.Classes.BindingData");
    ConfigurationSerialization.registerClass(CooldownTimer.class, "com.ancientshores.AncientRPG.Classes.CooldownTimer");
    ConfigurationSerialization.registerClass(AncientExperience.class, "com.ancientshores.AncientRPG.Experience.AncientRPGExperience");
    ConfigurationSerialization.registerClass(AncientHP.class, "com.ancientshores.AncientRPG.HP.AncientRPGHP");
    ConfigurationSerialization.registerClass(ManaSystem.class, "com.ancientshores.AncientRPG.Mana.ManaSystem");
    ConfigurationSerialization.registerClass(AncientPermission.class, "com.ancientshores.AncientRPG.Permissions.AncientPermission");
    ConfigurationSerialization.registerClass(PlayerData.class, "com.ancientshores.AncientRPG.PlayerData");
  }
  
  private void disableSerialization()
  {
    ConfigurationSerialization.unregisterClass(BindingData.class);
    ConfigurationSerialization.unregisterClass(CooldownTimer.class);
    ConfigurationSerialization.unregisterClass(AncientExperience.class);
    ConfigurationSerialization.unregisterClass(AncientHP.class);
    ConfigurationSerialization.unregisterClass(ManaSystem.class);
    ConfigurationSerialization.unregisterClass(AncientPermission.class);
    ConfigurationSerialization.unregisterClass(PlayerData.class);
  }
  
  private void registerCommands()
  {
    try
    {
      Constructor<?> c = Class.forName("org.bukkit.command.PluginCommand").getDeclaredConstructors()[0];
      c.setAccessible(true);
      
      PluginCommand pc = (PluginCommand)c.newInstance(new Object[] { partyCommand, plugin });
      pc.setExecutor(this);
      commandMap.register(partyCommand, pc);
      
      PluginCommand gc = (PluginCommand)c.newInstance(new Object[] { guildCommand, plugin });
      gc.setExecutor(this);
      commandMap.register(guildCommand, gc);
      
      PluginCommand cc = (PluginCommand)c.newInstance(new Object[] { classCommand, plugin });
      cc.setExecutor(this);
      commandMap.register(classCommand, cc);
      
      PluginCommand lc = (PluginCommand)c.newInstance(new Object[] { levelCommand, plugin });
      lc.setExecutor(this);
      commandMap.register(levelCommand, lc);
      
      PluginCommand hc = (PluginCommand)c.newInstance(new Object[] { hpCommand, plugin });
      hc.setExecutor(this);
      commandMap.register(hpCommand, hc);
      
      PluginCommand sc = (PluginCommand)c.newInstance(new Object[] { spellsCommand, plugin });
      sc.setExecutor(new SpellsCommandExecutor());
      commandMap.register(spellsCommand, sc);
      
      PluginCommand mc = (PluginCommand)c.newInstance(new Object[] { manaCommand, plugin });
      mc.setExecutor(this);
      commandMap.register(manaCommand, mc);
      
      PluginCommand arc = (PluginCommand)c.newInstance(new Object[] { arCommand, plugin });
      arc.setExecutor(this);
      commandMap.register(arCommand, arc);
      
      PluginCommand rc = (PluginCommand)c.newInstance(new Object[] { raceCommand, plugin });
      rc.setExecutor(this);
      commandMap.register(raceCommand, rc);
      
      PluginCommand gcc = (PluginCommand)c.newInstance(new Object[] { gcCommand, plugin });
      gcc.setExecutor(this);
      commandMap.register(gcCommand, gcc);
      
      PluginCommand pcc = (PluginCommand)c.newInstance(new Object[] { pcCommand, plugin });
      pcc.setExecutor(this);
      commandMap.register(pcCommand, pcc);
      
      PluginCommand bc = (PluginCommand)c.newInstance(new Object[] { bindCommand, plugin });
      bc.setExecutor(this);
      commandMap.register(bindCommand, bc);
      
      PluginCommand uc = (PluginCommand)c.newInstance(new Object[] { unbindCommand, plugin });
      uc.setExecutor(this);
      commandMap.register(unbindCommand, uc);
      
      PluginCommand ac = (PluginCommand)c.newInstance(new Object[] { ancientCommand, plugin });
      ac.setExecutor(new AncientCommand());
      commandMap.register(ancientCommand, ac);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }
  
  public void onDisable()
  {
    reloadConfig();
    this.config.loadkeys();
    this.config.addDefaults();
    saveConfig();
    PlayerData.writePlayerData();
    
    AncientGuild.writeGuilds();
    for (UUID uuid : AncientSpellListener.disarmList.keySet())
    {
      Player p = Bukkit.getPlayer(uuid);
      if (p.getItemInHand() == null) {
        p.setItemInHand((ItemStack)AncientSpellListener.disarmList.get(p.getUniqueId()));
      } else {
        p.getInventory().addItem(new ItemStack[] { (ItemStack)AncientSpellListener.disarmList.get(p.getUniqueId()) });
      }
    }
    for (Player p : Bukkit.getOnlinePlayers())
    {
      AncientPlayerListener.setVisibleToAll(p);
      AncientPlayerListener.setAllVisible(p);
      AncientHP hp = PlayerData.getPlayerData(p.getUniqueId()).getHpsystem();
      p.setMaxHealth(20.0D);
      p.setHealthScaled(false);
      p.setHealth(hp.health / hp.maxhp * 20.0D);
    }
    for (Map.Entry<String, Integer> entr : Spell.registeredTasks.entrySet()) {
      Bukkit.getScheduler().cancelTask(((Integer)entr.getValue()).intValue());
    }
    Bukkit.getScheduler().cancelTasks(this);
    Spell.registeredTasks.clear();
    Bukkit.getScheduler().cancelTasks(this);
    AncientClass.removePerms();
    PlayerData.playerData = null;
    plugin = null;
    AncientClass.executedSpells = null;
    AncientClass.playersOnCd = null;
    AncientClass.globalSpells = null;
    AncientClass.classList = null;
    AncientRace.races = null;
    AncientRace.playersOnCd = null;
    IArgument.registeredArguments = null;
    for (Map.Entry<String, Integer> entry : Spell.registeredTasks.entrySet()) {
      Bukkit.getScheduler().cancelTask(((Integer)entry.getValue()).intValue());
    }
    Spell.registeredTasks = null;
    Spell.eventListeners = null;
    Parameter.registeredParameters = null;
    com.ancientshores.Ancient.Classes.Spells.Command.registeredCommands = null;
    
    this.log.info("Ancient disabled.");
  }
  
  public int scheduleThreadSafeTask(Plugin p, Runnable r)
  {
    if (Thread.currentThread().equals(this.bukkitThread)) {
      return Bukkit.getScheduler().scheduleSyncDelayedTask(p, r);
    }
    r.run();
    
    return 0;
  }
  
  public int scheduleThreadSafeTask(Plugin p, Runnable r, int delay)
  {
    if ((Thread.currentThread().equals(this.bukkitThread)) && (delay == 0))
    {
      r.run();
      return 0;
    }
    return Bukkit.getScheduler().scheduleSyncDelayedTask(p, r, delay);
  }
  
  public void saveConfig()
  {
    File newconfig = new File(getDataFolder().getPath() + File.separator + "ancientconfig.yml");
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
    
    yc.set("Brand", brand);
    yc.set("Ancient.Commands.party", partyCommand);
    yc.set("Ancient.Commands.guild", guildCommand);
    yc.set("Ancient.Commands.class", classCommand);
    yc.set("Ancient.Commands.level", levelCommand);
    yc.set("Ancient.Commands.hp", hpCommand);
    yc.set("Ancient.Commands.race", raceCommand);
    yc.set("Ancient.Commands.spells", spellsCommand);
    yc.set("Ancient.Commands.mana", manaCommand);
    yc.set("Ancient.Commands.ar", arCommand);
    yc.set("Ancient.Commands.pc", pcCommand);
    yc.set("Ancient.Commands.gc", gcCommand);
    yc.set("Ancient.Commands.bind", bindCommand);
    yc.set("Ancient.Commands.unbind", unbindCommand);
    yc.set("Ancient.Commands.ancient", ancientCommand);
    try
    {
      yc.save(newconfig);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  public void loadConfig(FileConfiguration yc)
  {
    File newconfig = new File(getDataFolder().getPath() + File.separator + "ancientconfig.yml");
    if (newconfig.exists())
    {
      YamlConfiguration yc1 = new YamlConfiguration();
      try
      {
        yc1.load(newconfig);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      brand = yc1.getString("Brand", brand);
      brand2 = ChatColor.GOLD + "[" + brand + "] " + ChatColor.YELLOW;
      partyCommand = yc1.getString("Ancient.Commands.party", partyCommand);
      guildCommand = yc1.getString("Ancient.Commands.guild", guildCommand);
      classCommand = yc1.getString("Ancient.Commands.class", classCommand);
      levelCommand = yc1.getString("Ancient.Commands.level", levelCommand);
      hpCommand = yc1.getString("Ancient.Commands.hp", hpCommand);
      raceCommand = yc1.getString("Ancient.Commands.race", raceCommand);
      spellsCommand = yc1.getString("Ancient.Commands.spells", spellsCommand);
      manaCommand = yc1.getString("Ancient.Commands.mana", manaCommand);
      arCommand = yc1.getString("Ancient.Commands.ar", arCommand);
      pcCommand = yc1.getString("Ancient.Commands.pc", pcCommand);
      gcCommand = yc1.getString("Ancient.Commands.gc", gcCommand);
      bindCommand = yc1.getString("Ancient.Commands.bind", bindCommand);
      unbindCommand = yc1.getString("Ancient.Commands.unbind", unbindCommand);
      ancientCommand = yc1.getString("Ancient.Commands.ancient", ancientCommand);
    }
    else
    {
      brand = yc.getString("Brand", brand);
      brand2 = ChatColor.GOLD + "[" + brand + "] " + ChatColor.YELLOW;
      partyCommand = yc.getString("Ancient.Commands.party", partyCommand);
      guildCommand = yc.getString("Ancient.Commands.guild", guildCommand);
      classCommand = yc.getString("Ancient.Commands.class", classCommand);
      levelCommand = yc.getString("Ancient.Commands.level", levelCommand);
      hpCommand = yc.getString("Ancient.Commands.hp", hpCommand);
      raceCommand = yc.getString("Ancient.Commands.race", raceCommand);
      spellsCommand = yc.getString("Ancient.Commands.spells", spellsCommand);
      manaCommand = yc.getString("Ancient.Commands.mana", manaCommand);
      arCommand = yc.getString("Ancient.Commands.ar", arCommand);
      pcCommand = yc.getString("Ancient.Commands.pc", pcCommand);
      gcCommand = yc.getString("Ancient.Commands.gc", gcCommand);
      bindCommand = yc.getString("Ancient.Commands.bind", bindCommand);
      unbindCommand = yc.getString("Ancient.Commands.unbind", unbindCommand);
      ancientCommand = yc.getString("Ancient.Commands.ancient", ancientCommand);
    }
  }
  
  public void initializeHelpFiles()
  {
    PartyCommandHelp.initHelp();
    GuildCommandHelp.initHelp();
    AncientClassHelp.initHelp();
  }
  
  public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String commandLabel, String[] args)
  {
    String[] nargs = args;
    String commandName = command.getName().toLowerCase();
    if (commandName.equalsIgnoreCase(arCommand))
    {
      if (args.length < 2) {
        return true;
      }
      String[] buffer = new String[nargs.length - 1];
      System.arraycopy(nargs, 1, buffer, 0, nargs.length - 1);
      commandName = nargs[0];
      nargs = buffer;
    }
    if ((commandName.equalsIgnoreCase(levelCommand)) && (args.length > 1) && (args[0].equals("setxp")))
    {
      SetXpCommand.setXp(sender, args);
      return true;
    }
    if (commandName.equalsIgnoreCase(manaCommand))
    {
      ManaSystem.processCommand(sender, nargs);
      return true;
    }
    if ((commandName.equalsIgnoreCase(guildCommand)) && (args.length > 1) && (args[0].equals("setplayersguild")))
    {
      if (((sender instanceof Player)) && (!sender.hasPermission("Ancient.guild.admin"))) {
        return false;
      }
      Player p = Bukkit.getPlayer(args[1]);
      AncientGuild mGuild = AncientGuild.getGuildByName(args[2]);
      if ((p != null) && (mGuild != null)) {
        mGuild.addMember(p.getUniqueId(), AncientGuildRanks.TRIAL);
      } else {
        sender.sendMessage("Player or guild not found");
      }
      return true;
    }
    if ((commandName.equals(levelCommand)) && (AncientExperience.isEnabled()))
    {
      AncientExperience.processCommand(sender, nargs);
      return true;
    }
    if (commandName.equals(classCommand))
    {
      AncientClass.processCommand(sender, nargs);
      return true;
    }
    if ((sender instanceof Player))
    {
      if ((AncientParty.enabled) && (commandName.equals(partyCommand)))
      {
        AncientParty.processCommand(sender, nargs, this);
        return true;
      }
      if ((AncientParty.enabled) && (commandName.equals(pcCommand)) && (args != null))
      {
        nargs = new String[args.length + 1];
        System.arraycopy(args, 0, nargs, 1, args.length);
        nargs[0] = "";
        PartyCommandChat.processChat(sender, nargs);
        return true;
      }
      if ((commandName.equals(guildCommand)) && (AncientGuild.enabled))
      {
        AncientGuild.processCommand(sender, nargs, this);
        return true;
      }
      if ((commandName.equals(gcCommand)) && (AncientGuild.enabled) && (args != null))
      {
        nargs = new String[args.length + 1];
        System.arraycopy(args, 0, nargs, 1, args.length);
        nargs[0] = "";
        GuildCommandChat.processChat(sender, nargs);
        return true;
      }
      if (commandName.equals(bindCommand))
      {
        if (args != null) {
          if (args.length == 2) {
            SpellBindCommand.bindCommand(new String[] { "class", args[1] }, (Player)sender);
          } else if (args.length == 3) {
            SpellBindCommand.bindCommand(new String[] { "class", args[1], args[2] }, (Player)sender);
          } else {
            sender.sendMessage("Correct usage is bind [itemid]");
          }
        }
        return true;
      }
      if (commandName.equals(hpCommand))
      {
        HPCommand.showHP((Player)sender);
        return true;
      }
      if (commandName.equals(unbindCommand))
      {
        if (args != null) {
          if (args.length == 0) {
            ClassUnbindCommand.unbindCommand(new String[] { "unbind" }, (Player)sender);
          } else if (args.length == 1) {
            ClassUnbindCommand.unbindCommand(new String[] { "unbind", args[0] }, (Player)sender);
          } else {
            sender.sendMessage("Correct usage is unbind [itemid]");
          }
        }
        return true;
      }
      if ((commandName.equals(raceCommand)) && (AncientRace.enabled))
      {
        AncientRace.processCommand(sender, args);
        return true;
      }
    }
    else
    {
      System.out.println("Ancient Commands can only be used in-game");
    }
    return false;
  }
  
  public static boolean classExists(String c)
  {
    try
    {
      Class.forName(c);
      return true;
    }
    catch (ClassNotFoundException e) {}
    return false;
  }
  
  public static String convertStringArrayToString(String[] array)
  {
    String returnValue = "";
    for (int i = 0; i < array.length; i++)
    {
      if (!returnValue.equals("")) {
        returnValue = returnValue + " ";
      }
      returnValue = returnValue + array[i];
    }
    return returnValue;
  }
  
  private boolean setupPermissions()
  {
    RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(Permission.class);
    if (permissionProvider != null) {
      permissionHandler = (Permission)permissionProvider.getProvider();
    }
    return permissionHandler != null;
  }
  
  public static boolean hasPermissions(Player a, String b)
  {
    if ((b == null) || (b.equals(""))) {
      return true;
    }
    if ((permissionHandler != null) && (permissionHandler.has(a, b))) {
      return true;
    }
    return (a.hasPermission(b)) || (a.isOp());
  }
  
  public static String getLocalizedString(String code)
  {
    return messages.getString(code);
  }
  
  public static ApiManager getApiManager()
  {
    return manager;
  }
  
  public static boolean iConomyEnabled()
  {
    return (AncientGuild.economyenabled) && (economy != null);
  }
  
  private Boolean setupTowny()
  {
    RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
    if (economyProvider != null) {
      economy = (Economy)economyProvider.getProvider();
    }
    return Boolean.valueOf(economy != null);
  }
  
  public static void set(YamlConfiguration yc, String path, Object obj)
  {
    if (yc.get(path) == null) {
      yc.set(path, obj);
    }
  }
  
  private Boolean setupEconomy()
  {
    RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
    if (economyProvider != null) {
      economy = (Economy)economyProvider.getProvider();
    }
    return Boolean.valueOf(economy != null);
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Ancient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */