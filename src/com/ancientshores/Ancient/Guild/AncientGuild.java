package com.ancientshores.Ancient.Guild;

import com.ancient.util.PlayerFinder;
import com.ancient.util.UUIDConverter;
import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Guild.Commands.GuildCommandAccept;
import com.ancientshores.Ancient.Guild.Commands.GuildCommandAdmin;
import com.ancientshores.Ancient.Guild.Commands.GuildCommandChat;
import com.ancientshores.Ancient.Guild.Commands.GuildCommandCreate;
import com.ancientshores.Ancient.Guild.Commands.GuildCommandDeposit;
import com.ancientshores.Ancient.Guild.Commands.GuildCommandDisband;
import com.ancientshores.Ancient.Guild.Commands.GuildCommandGrant;
import com.ancientshores.Ancient.Guild.Commands.GuildCommandHelp;
import com.ancientshores.Ancient.Guild.Commands.GuildCommandInfo;
import com.ancientshores.Ancient.Guild.Commands.GuildCommandInvite;
import com.ancientshores.Ancient.Guild.Commands.GuildCommandKick;
import com.ancientshores.Ancient.Guild.Commands.GuildCommandLeave;
import com.ancientshores.Ancient.Guild.Commands.GuildCommandList;
import com.ancientshores.Ancient.Guild.Commands.GuildCommandMemberlist;
import com.ancientshores.Ancient.Guild.Commands.GuildCommandMoney;
import com.ancientshores.Ancient.Guild.Commands.GuildCommandMotd;
import com.ancientshores.Ancient.Guild.Commands.GuildCommandOnline;
import com.ancientshores.Ancient.Guild.Commands.GuildCommandReject;
import com.ancientshores.Ancient.Guild.Commands.GuildCommandRemoveMotd;
import com.ancientshores.Ancient.Guild.Commands.GuildCommandRemoveTag;
import com.ancientshores.Ancient.Guild.Commands.GuildCommandSetMotd;
import com.ancientshores.Ancient.Guild.Commands.GuildCommandSetTag;
import com.ancientshores.Ancient.Guild.Commands.GuildCommandToggle;
import com.ancientshores.Ancient.Guild.Commands.GuildCommandToggleFriendlyFire;
import com.ancientshores.Ancient.Guild.Commands.GuildCommandWithdraw;
import com.ancientshores.Ancient.Guild.Commands.GuildSetSpawnCommand;
import com.ancientshores.Ancient.HelpList;
import com.ancientshores.Ancient.Util.SerializableLocation;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class AncientGuild
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String guildName;
  public UUID gLeader;
  public String motd;
  public String accountName;
  public String tag;
  public boolean friendlyFire = false;
  public SerializableLocation spawnLocation;
  public static final ConcurrentHashMap<UUID, AncientGuild> invites = new ConcurrentHashMap();
  public HashMap<UUID, AncientGuildRanks> gMember;
  public static Collection<AncientGuild> guilds = Collections.newSetFromMap(new ConcurrentHashMap());
  public static final String gNodeCreate = "Ancient.guild.create";
  public static final String gNodeJoin = "Ancient.guild.join";
  public static final String gNodeAdmin = "Ancient.guild.admin";
  protected static final String gConfigIconomyEnabled = "guild.Iconomyenabled";
  protected static final String gConfigSize = "guild.size";
  protected static final String gConfigEnabled = "guild.guild enabled";
  protected static final String gConfigCost = "guild.cost";
  protected static final String gConfigCanToggleff = "guild.Can toggle ff";
  protected static final String gConfigSpawnEnabled = "guild.guildspawn enabled";
  protected static final String gConfigTagEnabled = "guild.guildtag enabled";
  public static int maxPlayers = 15;
  public static boolean enabled = true;
  public static boolean tagenabled = true;
  public static boolean canToggleff = true;
  public static boolean economyenabled = true;
  public static double cost = 300.0D;
  public static boolean spawnEnabled = false;
  
  public AncientGuild(String name, UUID leaderuuid)
  {
    this.guildName = name;
    this.motd = "";
    if (Ancient.iConomyEnabled())
    {
      this.accountName = ("[g]" + this.guildName.replace(' ', '_'));
      Ancient.economy.createBank(this.accountName, PlayerFinder.getPlayerName(leaderuuid));
    }
    this.gLeader = leaderuuid;
    this.gMember = new HashMap();
    this.gMember.put(leaderuuid, AncientGuildRanks.LEADER);
    guilds.add(this);
    System.out.println("Created a new guild -- Name: _" + name + "_");
    writeGuild(this);
  }
  
  public AncientGuild() {}
  
  public HashMap<UUID, AncientGuildRanks> getGuildMembers()
  {
    return this.gMember;
  }
  
  public void sendMessage(String message, Player sender)
  {
    for (UUID uuid : this.gMember.keySet())
    {
      Player p = Bukkit.getPlayer(uuid);
      if (p != null) {
        p.sendMessage(ChatColor.GREEN + "<Guild>" + AncientGuildRanks.getChatColorByRank((AncientGuildRanks)this.gMember.get(sender.getUniqueId())) + sender.getName() + ChatColor.GREEN + ": " + message);
      }
    }
  }
  
  public void broadcastMessage(String message)
  {
    for (UUID uuid : this.gMember.keySet())
    {
      Player p = Bukkit.getPlayer(uuid);
      if (p != null) {
        p.sendMessage(message);
      }
    }
  }
  
  public void addMember(UUID uuid, AncientGuildRanks rank)
  {
    this.gMember.put(uuid, rank);
    writeGuild(this);
  }
  
  public void grantRights(UUID uuid, AncientGuildRanks rank)
  {
    this.gMember.put(uuid, rank);
    writeGuild(this);
  }
  
  public void kickMember(UUID uuid)
  {
    AncientGuildRanks gr = (AncientGuildRanks)this.gMember.get(uuid);
    this.gMember.remove(uuid);
    OfflinePlayer p = Bukkit.getOfflinePlayer(uuid);
    broadcastMessage(Ancient.brand2 + AncientGuildRanks.getChatColorByRank(gr) + p.getName() + ChatColor.GREEN + " was kicked out of the guild");
    if (p.isOnline()) {
      p.getPlayer().sendMessage(Ancient.brand2 + "You were kicked out of your guild");
    }
  }
  
  public void giveNextLeader()
  {
    Set<UUID> uuids = this.gMember.keySet();
    for (UUID uuid : uuids) {
      if (this.gMember.get(uuid) == AncientGuildRanks.CO_LEADER)
      {
        this.gLeader = uuid;
        this.gMember.put(uuid, AncientGuildRanks.LEADER);
        if (Ancient.iConomyEnabled())
        {
          double balance = Ancient.economy.bankBalance(this.accountName).amount;
          Ancient.economy.deleteBank(this.accountName);
          Ancient.economy.createBank(this.accountName, Bukkit.getOfflinePlayer(this.gLeader).getName());
          Ancient.economy.bankDeposit(this.accountName, balance);
        }
        broadcastMessage(Ancient.brand2 + ChatColor.DARK_RED + Bukkit.getPlayer(this.gLeader) + " is the new Leader of the guild.");
        
        writeGuild(this);
        return;
      }
    }
    broadcastMessage(Ancient.brand2 + ChatColor.GREEN + " this guild has been disbanded because no one of the members can be a Leader.");
    if (Ancient.iConomyEnabled())
    {
      double balance = Ancient.economy.bankBalance(this.accountName).amount;
      Ancient.economy.depositPlayer(PlayerFinder.getPlayerName(this.gLeader), balance);
    }
    guilds.remove(this);
    writeGuild(this);
  }
  
  public void disband(boolean admin)
  {
    if (admin) {
      for (UUID uuid : this.gMember.keySet())
      {
        Player p = Bukkit.getPlayer(uuid);
        if (p != null) {
          p.sendMessage(Ancient.brand2 + ChatColor.GREEN + "Your guild has been disbanded by an admin.");
        }
      }
    } else {
      for (UUID uuid : this.gMember.keySet())
      {
        Player p = Bukkit.getPlayer(uuid);
        if (p != null) {
          p.sendMessage(Ancient.brand2 + ChatColor.GREEN + "Your guild has been disbanded by the leader.");
        }
      }
    }
    if (Ancient.iConomyEnabled())
    {
      double balance = Ancient.economy.bankBalance(this.accountName).amount;
      Ancient.economy.depositPlayer(PlayerFinder.getPlayerName(this.gLeader), balance);
    }
    deleteGuild(this);
  }
  
  public String getGuildName()
  {
    return this.guildName;
  }
  
  public static void processCommand(CommandSender sender, String[] args, Ancient main)
  {
    if (args.length == 0)
    {
      GuildCommandHelp.processHelp(sender, args);
      return;
    }
    if (args[0].equals("create")) {
      GuildCommandCreate.processCreate(sender, args);
    } else if (args[0].equals("invite")) {
      GuildCommandInvite.processInvite(sender, args);
    } else if (args[0].equals("accept")) {
      GuildCommandAccept.processAccept(sender);
    } else if (args[0].equals("reject")) {
      GuildCommandReject.processReject(sender);
    } else if (args[0].equals("motd")) {
      GuildCommandMotd.processMotd(sender);
    } else if (args[0].equals("setmotd")) {
      GuildCommandSetMotd.processSetMOTD(sender, args);
    } else if (args[0].equals("removemotd")) {
      GuildCommandRemoveMotd.processRemoveMOTD(sender, args);
    } else if (args[0].equals("chat")) {
      GuildCommandChat.processChat(sender, args);
    } else if (args[0].equals("grant")) {
      GuildCommandGrant.processGrant(sender, args);
    } else if (args[0].equals("online")) {
      GuildCommandOnline.processOnline(sender);
    } else if (args[0].equals("list")) {
      GuildCommandList.processList(sender, args);
    } else if (args[0].equals("kick")) {
      GuildCommandKick.processKick(sender, args);
    } else if (args[0].equals("leave")) {
      GuildCommandLeave.processLeave(sender);
    } else if (args[0].equals("setguildspawn")) {
      GuildSetSpawnCommand.setGuildSpawnCommand(sender);
    } else if (args[0].equals("memberlist")) {
      GuildCommandMemberlist.processMemberList(sender);
    } else if (args[0].equals("disband")) {
      GuildCommandDisband.processDisband(sender);
    } else if (args[0].equals("toggle")) {
      GuildCommandToggle.processToggle(sender);
    } else if (args[0].equals("toggleff")) {
      GuildCommandToggleFriendlyFire.processFF(sender);
    } else if (args[0].equals("info")) {
      GuildCommandInfo.processInfo(sender);
    } else if (args[0].equals("admin")) {
      GuildCommandAdmin.processAdmin(sender, args);
    } else if (args[0].equals("help")) {
      GuildCommandHelp.processHelp(sender, args);
    } else if (args[0].equals("settag")) {
      GuildCommandSetTag.processSetTag(sender, args);
    } else if (args[0].equals("removetag")) {
      GuildCommandRemoveTag.processRemoveTag(sender, args);
    } else if ((economyenabled) && (Ancient.economy != null)) {
      if (args[0].equals("deposit")) {
        GuildCommandDeposit.processDeposit(sender, args);
      } else if (args[0].equals("withdraw")) {
        GuildCommandWithdraw.processWithdraw(sender, args);
      } else if (args[0].equals("money")) {
        GuildCommandMoney.processMoney(sender);
      }
    }
  }
  
  public void setTag(String tag)
  {
    String finalTag = HelpList.replaceChatColor(tag) + "&r ";
    finalTag = ChatColor.translateAlternateColorCodes('&', finalTag);
    if (tag.equals("")) {
      finalTag = "";
    }
    if (this.tag == null) {
      this.tag = "";
    }
    for (UUID uuid : this.gMember.keySet())
    {
      Player p = Bukkit.getPlayer(uuid);
      if (p != null)
      {
        String newName = p.getDisplayName();
        newName = newName.replace(this.tag, "");
        newName = finalTag + newName;
        p.setDisplayName(newName);
      }
    }
    this.tag = finalTag;
  }
  
  public static void processJoin(final PlayerJoinEvent event)
  {
    Player p = event.getPlayer();
    AncientGuild guild = getPlayersGuild(p.getUniqueId());
    if (guild != null)
    {
      guild.broadcastMessage(ChatColor.GREEN + "<Guild>:" + AncientGuildRanks.getChatColorByRank((AncientGuildRanks)guild.getGuildMembers().get(p.getUniqueId())) + p.getName() + ChatColor.GREEN + " is now online.");
      writeGuild(guild);
      new Timer().schedule(new TimerTask()
      {
        public void run()
        {
          if ((this.val$guild.motd != null) && (!this.val$guild.motd.equals(""))) {
            event.getPlayer().sendMessage(ChatColor.GREEN + "<Guild> MOTD: " + this.val$guild.motd);
          }
        }
      }, 3000L);
      
      guild.setTag(guild.tag);
    }
  }
  
  public static void processQuit(PlayerQuitEvent event)
  {
    AncientGuild mGuild = getPlayersGuild(event.getPlayer().getUniqueId());
    if (mGuild != null)
    {
      mGuild.broadcastMessage(ChatColor.GREEN + "<Guild>:" + AncientGuildRanks.getChatColorByRank((AncientGuildRanks)mGuild.getGuildMembers().get(event.getPlayer().getUniqueId())) + event.getPlayer().getName() + ChatColor.GREEN + " is now offline.");
      writeGuild(mGuild);
    }
  }
  
  public static void writeGuilds()
  {
    File basepath = new File(Ancient.plugin.getDataFolder().getPath() + File.separator + "Guilds");
    if (!basepath.exists()) {
      basepath.mkdir();
    }
    for (AncientGuild guild : guilds)
    {
      File f = new File(basepath.getPath() + File.separator + guild.guildName + ".guild");
      if (f.exists())
      {
        f.renameTo(new File(f.getName() + "old"));
        f.delete();
      }
      YamlConfiguration guildConfig = new YamlConfiguration();
      guildConfig.set("Guild.Name", guild.guildName);
      guildConfig.set("Guild.Accountname", guild.accountName);
      guildConfig.set("Guild.Leader", guild.gLeader.toString());
      guildConfig.set("Guild.FF", Boolean.valueOf(guild.friendlyFire));
      guildConfig.set("Guild.Motd", guild.motd);
      guildConfig.set("Guild.tag", guild.tag);
      if (guild.spawnLocation != null)
      {
        guildConfig.set("Guild.spawnworld", guild.spawnLocation.wName);
        guildConfig.set("Guild.spawnx", Double.valueOf(guild.spawnLocation.x));
        guildConfig.set("Guild.spawny", Double.valueOf(guild.spawnLocation.y));
        guildConfig.set("Guild.spawnz", Double.valueOf(guild.spawnLocation.z));
      }
      int i = 0;
      for (UUID uuid : guild.gMember.keySet())
      {
        guildConfig.set("Guild.Members." + i, uuid.toString() + ":" + AncientGuildRanks.toString((AncientGuildRanks)guild.gMember.get(uuid)));
        i++;
      }
      try
      {
        guildConfig.save(f);
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
  }
  
  public static void deleteGuild(AncientGuild guild)
  {
    guilds.remove(guild);
    File f = new File(Ancient.plugin.getDataFolder().getPath() + File.separator + File.separator + File.separator + "Guilds" + File.separator + guild.guildName + ".guild");
    if (f.exists())
    {
      File deleteFolder = new File(Ancient.plugin.getDataFolder().getPath() + File.separator + File.separator + File.separator + "Guilds" + File.separator + "deleted");
      if (!deleteFolder.exists()) {
        deleteFolder.mkdir();
      }
      f.renameTo(new File(Ancient.plugin.getDataFolder().getPath() + File.separator + "Guilds" + File.separator + "deleted" + File.separator + guild.guildName + ".guild"));
      f.delete();
    }
  }
  
  public static void writeGuild(AncientGuild guild)
  {
    File f = new File(Ancient.plugin.getDataFolder().getPath() + File.separator + "Guilds" + File.separator + guild.guildName + ".guild");
    if (f.exists())
    {
      f.renameTo(new File(f.getName() + "old"));
      f.delete();
    }
    YamlConfiguration guildConfig = new YamlConfiguration();
    guildConfig.set("Guild.Name", guild.guildName);
    guildConfig.set("Guild.Accountname", guild.accountName);
    guildConfig.set("Guild.Leader", guild.gLeader.toString());
    guildConfig.set("Guild.FF", Boolean.valueOf(guild.friendlyFire));
    guildConfig.set("Guild.Motd", guild.motd);
    guildConfig.set("Guild.tag", guild.tag);
    if (guild.spawnLocation != null)
    {
      guildConfig.set("Guild.spawnworld", guild.spawnLocation.wName);
      guildConfig.set("Guild.spawnx", Double.valueOf(guild.spawnLocation.x));
      guildConfig.set("Guild.spawny", Double.valueOf(guild.spawnLocation.y));
      guildConfig.set("Guild.spawnz", Double.valueOf(guild.spawnLocation.z));
    }
    int i = 0;
    for (UUID uuid : guild.gMember.keySet())
    {
      guildConfig.set("Guild.Members." + i, uuid.toString() + ":" + AncientGuildRanks.toString((AncientGuildRanks)guild.gMember.get(uuid)));
      i++;
    }
    try
    {
      guildConfig.save(f);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  public static void loadGuilds()
  {
    File basepath = new File(Ancient.plugin.getDataFolder().getPath() + File.separator + "Guilds");
    if (!basepath.exists()) {
      basepath.mkdir();
    }
    guilds = new HashSet();
    
    File[] guildFiles = basepath.listFiles();
    if (guildFiles != null) {
      for (File f : guildFiles) {
        if ((!f.isDirectory()) && (f.getName().endsWith(".guild")))
        {
          YamlConfiguration guildConfig = new YamlConfiguration();
          AncientGuild guild = new AncientGuild();
          try
          {
            guildConfig.load(f);
          }
          catch (Exception e)
          {
            e.printStackTrace();
            Ancient.plugin.getLogger().log(Level.SEVERE, "Failed to load guild " + f.getName().replaceAll("\\.guild", ""));
            continue;
          }
          guild.guildName = guildConfig.getString("Guild.Name");
          guild.accountName = guildConfig.getString("Guild.Accountname");
          try
          {
            guild.gLeader = UUID.fromString(guildConfig.getString("Guild.Leader"));
          }
          catch (IllegalArgumentException e)
          {
            guild.gLeader = UUIDConverter.getUUID(guildConfig.getString("Guild.Leader"));
          }
          guild.motd = guildConfig.getString("Guild.Motd");
          guild.friendlyFire = guildConfig.getBoolean("Guild.FF");
          guild.tag = guildConfig.getString("Guild.tag", guild.tag);
          if (guildConfig.get("Guild.spawnworld") != null) {
            guild.spawnLocation = new SerializableLocation(new Location(Bukkit.getWorld(guildConfig.getString("Guild.spawnworld")), guildConfig.getDouble("Guild.spawnx"), guildConfig.getDouble("Guild.spawny"), guildConfig.getDouble("Guild.spawnz")));
          }
          if (!canToggleff) {
            guild.friendlyFire = true;
          }
          guild.gMember = new HashMap();
          int i = 0;
          String s = guildConfig.getString("Guild.Members." + i);
          while ((s != null) && (!s.equals("")))
          {
            String[] regex = s.split(":");
            if (regex.length != 2) {
              break;
            }
            UUID uuid = null;
            try
            {
              uuid = UUID.fromString(regex[0].trim());
            }
            catch (IllegalArgumentException e)
            {
              uuid = UUIDConverter.getUUID(regex[0].trim());
            }
            guild.gMember.put(uuid, AncientGuildRanks.getGuildRankByString(regex[1].trim()));
            
            i++;
            s = guildConfig.getString("Guild.Members." + i);
          }
          guilds.add(guild);
        }
      }
    }
  }
  
  public static void writeConfig(Ancient plugin)
  {
    File newconfig = new File(plugin.getDataFolder().getPath() + File.separator + "guildconfig.yml");
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
    YamlConfiguration guildConfig = new YamlConfiguration();
    guildConfig.set("guild.guild enabled", Boolean.valueOf(enabled));
    guildConfig.set("guild.size", Integer.valueOf(maxPlayers));
    guildConfig.set("guild.Can toggle ff", Boolean.valueOf(canToggleff));
    guildConfig.set("guild.Iconomyenabled", Boolean.valueOf(economyenabled));
    guildConfig.set("guild.cost", Double.valueOf(cost));
    guildConfig.set("guild.guildspawn enabled", Boolean.valueOf(spawnEnabled));
    guildConfig.set("guild.guildtag enabled", Boolean.valueOf(tagenabled));
    try
    {
      guildConfig.save(newconfig);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  public static void loadConfig(Ancient plugin)
  {
    File newconfig = new File(plugin.getDataFolder().getPath() + File.separator + "guildconfig.yml");
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
      enabled = yc.getBoolean("guild.guild enabled", enabled);
      maxPlayers = yc.getInt("guild.size", maxPlayers);
      canToggleff = yc.getBoolean("guild.Can toggle ff", canToggleff);
      economyenabled = yc.getBoolean("guild.Iconomyenabled", economyenabled);
      cost = yc.getDouble("guild.cost", cost);
      spawnEnabled = yc.getBoolean("guild.guildspawn enabled", spawnEnabled);
      tagenabled = yc.getBoolean("guild.guildtag enabled", tagenabled);
    }
    else
    {
      enabled = plugin.getConfig().getBoolean("guild.guild enabled", enabled);
      maxPlayers = plugin.getConfig().getInt("guild.size", maxPlayers);
      canToggleff = plugin.getConfig().getBoolean("guild.Can toggle ff", canToggleff);
      economyenabled = plugin.getConfig().getBoolean("guild.Iconomyenabled", economyenabled);
      cost = plugin.getConfig().getDouble("guild.cost", cost);
      spawnEnabled = plugin.getConfig().getBoolean("guild.guildspawn enabled", spawnEnabled);
      tagenabled = plugin.getConfig().getBoolean("guild.guildtag enabled", tagenabled);
    }
  }
  
  public static boolean guildExists(String guildname)
  {
    for (AncientGuild g : guilds) {
      if (g.guildName.equalsIgnoreCase(guildname)) {
        return true;
      }
    }
    return false;
  }
  
  public static AncientGuild getGuildByName(String guildname)
  {
    for (AncientGuild g : guilds) {
      if (g.guildName.equalsIgnoreCase(guildname)) {
        return g;
      }
    }
    return null;
  }
  
  public static AncientGuild getPlayersGuild(UUID uuid)
  {
    for (AncientGuild g : guilds) {
      if (g.gMember.containsKey(uuid)) {
        return g;
      }
    }
    return null;
  }
}
