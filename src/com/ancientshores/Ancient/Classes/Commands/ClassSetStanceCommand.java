package com.ancientshores.Ancient.Classes.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.AncientClass;
import com.ancientshores.Ancient.Classes.BindingData;
import com.ancientshores.Ancient.Experience.AncientExperience;
import com.ancientshores.Ancient.HP.AncientHP;
import com.ancientshores.Ancient.Mana.ManaSystem;
import com.ancientshores.Ancient.PlayerData;
import com.ancientshores.Ancient.Util.LinkedStringHashMap;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Server;
import org.bukkit.entity.Player;

public class ClassSetStanceCommand
{
  public static void setStanceCommand(Object[] args, Player p)
  {
    if (args.length == 1)
    {
      p.sendMessage(Ancient.brand2 + "Not enough arguments");
      return;
    }
    if (AncientClass.playersOnCd.containsKey(p.getUniqueId()))
    {
      long t = System.currentTimeMillis();
      long div = t - ((Long)AncientClass.playersOnCd.get(p.getUniqueId())).longValue();
      if (div < AncientClass.changeCd * 1000)
      {
        p.sendMessage(Ancient.brand2 + "The class change cooldown hasn't expired yet");
        long timeleft = ((Long)AncientClass.playersOnCd.get(p.getUniqueId())).longValue() + AncientClass.changeCd * 1000 - System.currentTimeMillis();
        int minutes = (int)(timeleft / 1000.0D / 60.0D + 1.0D);
        if (minutes == 1) {
          p.sendMessage("You have to wait another minute.");
        } else {
          p.sendMessage("You have to wait another " + minutes + " minutes.");
        }
        return;
      }
    }
    PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
    Player csender = p;
    if ((args.length == 3) && (csender.hasPermission("Ancient.classes.admin")))
    {
      Player pl = Ancient.plugin.getServer().getPlayer(UUID.fromString((String)args[1]));
      if (pl != null)
      {
        pd = PlayerData.getPlayerData(p.getUniqueId());
        p = pl;
      }
      else
      {
        p.sendMessage(Ancient.brand2 + "Player not found");
        return;
      }
    }
    AncientClass rootclass = (AncientClass)AncientClass.classList.get(pd.getClassName().toLowerCase());
    if (rootclass == null)
    {
      p.sendMessage(Ancient.brand2 + "You need a class to set a stance!");
      return;
    }
    AncientClass oldstance = (AncientClass)rootclass.stances.get(pd.getStance());
    AncientClass stance = (AncientClass)rootclass.stances.get(((String)args[2]).toLowerCase());
    if (stance != null) {
      setStance(oldstance, stance, rootclass, p, csender);
    } else {
      p.sendMessage(Ancient.brand2 + "This stance does not exist (typo?)");
    }
  }
  
  public static boolean canSetStanceClass(AncientClass newClass, Player p)
  {
    PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
    if ((AncientExperience.isWorldEnabled(p.getWorld())) && 
      (pd.getXpSystem().level < newClass.minlevel))
    {
      p.sendMessage(Ancient.brand2 + "You need to be level " + newClass.minlevel + " to join this class");
      return false;
    }
    if (!newClass.isWorldEnabled(p.getWorld())) {
      return false;
    }
    if ((newClass.preclass != null) && (!newClass.preclass.equals("")) && (!newClass.preclass.toLowerCase().equals(pd.getClassName().toLowerCase()))) {
      return false;
    }
    return (newClass.permissionNode == null) || (newClass.permissionNode.equalsIgnoreCase("")) || (p.hasPermission(newClass.permissionNode));
  }
  
  public static void setStance(AncientClass oldstance, AncientClass newStance, AncientClass rootclass, Player p, Player sender)
  {
    PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
    if (newStance != null)
    {
      if ((sender != p) && (AncientExperience.isWorldEnabled(p.getWorld())) && 
        (pd.getXpSystem().level < newStance.minlevel))
      {
        p.sendMessage(Ancient.brand2 + "You need to be level " + newStance.minlevel + " to join this stance");
        return;
      }
      if (!newStance.isWorldEnabled(p.getWorld()))
      {
        p.sendMessage(Ancient.brand2 + "This stance cannot be used in this world");
        return;
      }
      if ((sender == p) && (newStance.permissionNode != null) && (!newStance.permissionNode.equalsIgnoreCase("")) && (!sender.hasPermission(newStance.permissionNode)))
      {
        p.sendMessage(Ancient.brand2 + "You don't have permissions to set join stance");
        return;
      }
      if ((sender == p) && (newStance.preclass != null) && (!newStance.preclass.equals("")) && (!oldstance.name.equalsIgnoreCase(newStance.preclass)))
      {
        p.sendMessage(Ancient.brand2 + "You need to be the stance " + newStance.preclass);
        return;
      }
      try
      {
        if ((oldstance != null) && (oldstance.permGroup != null) && (!oldstance.permGroup.equals("")))
        {
          if (Ancient.permissionHandler != null) {
            Ancient.permissionHandler.playerRemoveGroup(p, oldstance.permGroup);
          }
          if (AncientExperience.isEnabled()) {
            pd.getClassLevels().put(oldstance.name.toLowerCase(), Integer.valueOf(pd.getXpSystem().xp));
          }
        }
      }
      catch (Exception ignored) {}
      pd.setStance(newStance.name);
      if (AncientExperience.isEnabled())
      {
        pd.getHpsystem().setHpRegen();
        pd.getHpsystem().setMinecraftHP();
      }
      pd.setBindings(new HashMap());
      pd.getHpsystem().maxhp = newStance.hp;
      p.sendMessage(Ancient.brand2 + "Your stance is now " + newStance.name);
      try
      {
        if ((newStance.permGroup != null) && (!newStance.permGroup.equals("")) && (Ancient.permissionHandler != null)) {
          Ancient.permissionHandler.playerAddGroup(p, newStance.permGroup);
        }
      }
      catch (Exception ignored) {}
      for (Map.Entry<BindingData, String> bind : rootclass.bindings.entrySet()) {
        pd.getBindings().put(bind.getKey(), bind.getValue());
      }
      for (Map.Entry<BindingData, String> bind : newStance.bindings.entrySet()) {
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
          bw.write(uuid + ";" + AncientClass.playersOnCd.get(uuid));
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


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Commands\ClassSetStanceCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */