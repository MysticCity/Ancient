package com.ancientshores.Ancient.Classes.Commands;

import com.ancientshores.Ancient.API.AncientClassChangeEvent;
import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.AncientClass;
import com.ancientshores.Ancient.Classes.BindingData;
import com.ancientshores.Ancient.Experience.AncientExperience;
import com.ancientshores.Ancient.HP.AncientHP;
import com.ancientshores.Ancient.Mana.ManaSystem;
import com.ancientshores.Ancient.PlayerData;
import com.ancientshores.Ancient.Race.AncientRace;
import com.ancientshores.Ancient.Util.LinkedStringHashMap;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

public class ClassSetCommand
{
  public static void setCommand(Object[] args, CommandSender sender)
  {
    if (args.length == 1)
    {
      sender.sendMessage(Ancient.brand2 + "Not enough arguments");
      return;
    }
    if (!(sender instanceof Player)) {
      return;
    }
    Player player = (Player)sender;
    if (AncientClass.playersOnCd.containsKey(player.getUniqueId()))
    {
      long t = System.currentTimeMillis();
      long div = t - ((Long)AncientClass.playersOnCd.get(player.getUniqueId())).longValue();
      if (div < AncientClass.changeCd * 1000)
      {
        sender.sendMessage(Ancient.brand2 + "The class change cooldown hasn't expired yet");
        long timeleft = ((Long)AncientClass.playersOnCd.get(player.getUniqueId())).longValue() + AncientClass.changeCd * 1000 - System.currentTimeMillis();
        int minutes = (int)(timeleft / 1000.0D / 60.0D + 1.0D);
        sender.sendMessage("You have to wait another " + minutes + " minutes.");
        return;
      }
    }
    PlayerData pd = PlayerData.getPlayerData(player.getUniqueId());
    
    AncientClass oldclass = (AncientClass)AncientClass.classList.get(pd.getClassName().toLowerCase());
    AncientClass c = (AncientClass)AncientClass.classList.get(((String)args[1]).toLowerCase());
    if ((args.length == 3) && (senderHasAdminPermissions(sender)))
    {
      Player pl = Ancient.plugin.getServer().getPlayer((String)args[1]);
      if (pl != null)
      {
        pd = PlayerData.getPlayerData(player.getUniqueId());
        player = pl;
        c = (AncientClass)AncientClass.classList.get(((String)args[2]).toLowerCase());
      }
      else
      {
        sender.sendMessage(Ancient.brand2 + "Player not found");
        return;
      }
    }
    if (c != null)
    {
      if ((c.preclass != null) && (!c.preclass.equals("")) && ((pd.getClassName() == null) || (!c.preclass.toLowerCase().equals(pd.getClassName().toLowerCase()))))
      {
        sender.sendMessage(Ancient.brand2 + "You need to be a " + c.preclass + " to join this class");
        return;
      }
      setClass(c, oldclass, player, sender);
    }
    else
    {
      sender.sendMessage(Ancient.brand2 + "This class does not exist (typo?)");
    }
  }
  
  public static void setCommandConsole(Object[] args)
  {
    Player player = Ancient.plugin.getServer().getPlayer(UUID.fromString((String)args[1]));
    PlayerData pd;
    if (player != null)
    {
      pd = PlayerData.getPlayerData(player.getUniqueId());
    }
    else
    {
      Bukkit.getLogger().log(Level.WARNING, "Player not found"); return;
    }
    PlayerData pd;
    AncientClass oldclass = (AncientClass)AncientClass.classList.get(pd.getClassName().toLowerCase());
    AncientClass c = (AncientClass)AncientClass.classList.get(((String)args[2]).toLowerCase());
    if (c != null) {
      setClass(c, oldclass, player, null);
    } else {
      Bukkit.getLogger().log(Level.WARNING, "This class does not exist (typo?)");
    }
  }
  
  public static boolean canSetClass(AncientClass newClass, Player p)
  {
    PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
    if (!newClass.isWorldEnabled(p.getWorld())) {
      return false;
    }
    if ((AncientExperience.isWorldEnabled(p.getWorld())) && 
      (pd.getXpSystem().level < newClass.minlevel))
    {
      p.sendMessage(Ancient.brand2 + "You need to be level " + newClass.minlevel + " to join this class");
      return false;
    }
    if ((newClass.preclass != null) && (!newClass.preclass.equals("")) && (!newClass.preclass.toLowerCase().equals(pd.getClassName().toLowerCase()))) {
      return false;
    }
    AncientRace race = AncientRace.getRaceByName(PlayerData.getPlayerData(p.getUniqueId()).getRacename());
    if ((newClass.requiredraces.size() >= 0) && (race != null) && (!newClass.requiredraces.contains(race.name.toLowerCase()))) {
      return false;
    }
    if ((newClass.permissionNode != null) && (!newClass.permissionNode.equalsIgnoreCase("")) && (!Ancient.hasPermissions(p, newClass.permissionNode))) {
      return false;
    }
    return Ancient.hasPermissions(p, "Ancient.classes.set");
  }
  
  public static boolean senderHasAdminPermissions(CommandSender cs)
  {
    return ((cs instanceof ConsoleCommandSender)) || ((cs instanceof RemoteConsoleCommandSender)) || (((cs instanceof Player)) && (Ancient.hasPermissions((Player)cs, "Ancient.classes.admin")));
  }
  
  public static boolean senderHasPermissions(CommandSender cs, String perm)
  {
    return ((cs instanceof ConsoleCommandSender)) || ((cs instanceof RemoteConsoleCommandSender)) || (((cs instanceof Player)) && (Ancient.hasPermissions((Player)cs, perm)));
  }
  
  public static void setClass(AncientClass newClass, AncientClass oldClass, Player p, CommandSender sender)
  {
    if (p == null) {
      return;
    }
    PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
    if (newClass != null)
    {
      if ((sender == p) && (AncientExperience.isWorldEnabled(p.getWorld())) && 
        (pd.getXpSystem().level < newClass.minlevel))
      {
        p.sendMessage(Ancient.brand2 + "You need to be level " + newClass.minlevel + " to join this class");
        return;
      }
      if (!sender.hasPermission("Ancient.classes.set"))
      {
        sender.sendMessage(Ancient.brand2 + "You don't have the required permissions to become this class");
        return;
      }
      if (!newClass.isWorldEnabled(p.getWorld()))
      {
        sender.sendMessage(Ancient.brand2 + "This class cannot be used in this world");
        return;
      }
      AncientRace race = AncientRace.getRaceByName(PlayerData.getPlayerData(p.getUniqueId()).getRacename());
      if ((newClass.requiredraces.size() > 0) && ((race == null) || (!newClass.requiredraces.contains(race.name.toLowerCase()))))
      {
        p.sendMessage(Ancient.brand2 + "Your race can't use this class");
        return;
      }
      if ((sender == null) || ((newClass.permissionNode != null) && (!newClass.permissionNode.equalsIgnoreCase("")) && (!sender.hasPermission(newClass.permissionNode))))
      {
        p.sendMessage(Ancient.brand2 + "You don't have permissions to use this class");
        return;
      }
      AncientClassChangeEvent classevent = new AncientClassChangeEvent(p.getUniqueId(), oldClass, newClass);
      Bukkit.getPluginManager().callEvent(classevent);
      if (classevent.isCancelled()) {
        return;
      }
      try
      {
        if ((oldClass != null) && (oldClass.permGroup != null) && (!oldClass.permGroup.equals("")) && 
          (Ancient.permissionHandler != null)) {
          Ancient.permissionHandler.playerRemoveGroup(p, oldClass.permGroup);
        }
        if ((AncientExperience.isEnabled()) && 
          (oldClass != null)) {
          pd.getClassLevels().put(oldClass.name.toLowerCase(), Integer.valueOf(pd.getXpSystem().xp));
        }
      }
      catch (Exception ignored) {}
      pd.setClassName(newClass.name);
      if (AncientExperience.isEnabled())
      {
        int xp = 0;
        if ((pd.getClassLevels().get(newClass.name.toLowerCase()) != null) && (!AncientClass.resetlevelonchange)) {
          xp = ((Integer)pd.getClassLevels().get(newClass.name.toLowerCase())).intValue();
        }
        pd.getXpSystem().xp = xp;
        pd.getXpSystem().addXP(0, false);
        pd.getXpSystem().recalculateLevel();
      }
      pd.getHpsystem().setHpRegen();
      pd.getHpsystem().setMinecraftHP();
      pd.getHpsystem().setMaxHP();
      
      pd.setBindings(new HashMap());
      p.sendMessage(Ancient.brand2 + "Your class is now " + newClass.name);
      pd.setStance("");
      try
      {
        if ((newClass.permGroup != null) && (!newClass.permGroup.equals("")) && (Ancient.permissionHandler != null)) {
          Ancient.permissionHandler.playerAddGroup(p, newClass.permGroup);
        }
      }
      catch (Exception ignored) {}
      for (Map.Entry<BindingData, String> bind : newClass.bindings.entrySet()) {
        pd.getBindings().put(bind.getKey(), bind.getValue());
      }
      AncientClass.playersOnCd.put(p.getUniqueId(), Long.valueOf(System.currentTimeMillis()));
      File f = new File(Ancient.plugin.getDataFolder() + File.separator + "Class" + File.separator + "changecds.dat");
      try
      {
        FileWriter fw = new FileWriter(f);
        BufferedWriter bw = new BufferedWriter(fw);
        for (UUID uuid : AncientClass.playersOnCd.keySet())
        {
          bw.write(uuid.toString() + ";" + AncientClass.playersOnCd.get(uuid));
          bw.newLine();
        }
        bw.close();
        fw.close();
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    pd.getManasystem().setMaxMana();
  }
}
