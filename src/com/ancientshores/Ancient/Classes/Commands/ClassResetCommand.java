package com.ancientshores.Ancient.Classes.Commands;

import com.ancientshores.Ancient.API.AncientClassChangeEvent;
import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.AncientClass;
import com.ancientshores.Ancient.Experience.AncientExperience;
import com.ancientshores.Ancient.PlayerData;
import com.ancientshores.Ancient.Util.LinkedStringHashMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

public class ClassResetCommand
{
  public static void resetCommand(Player p)
  {
    PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
    AncientClass oldClass = (AncientClass)AncientClass.classList.get(pd.getClassName().toLowerCase());
    reset(p, oldClass, pd);
    p.sendMessage(Ancient.brand2 + "Successfully reset your class");
  }
  
  public static void reset(Player p, AncientClass oldClass, PlayerData pd)
  {
    AncientClassChangeEvent classevent = new AncientClassChangeEvent(p.getUniqueId(), oldClass, null);
    Bukkit.getPluginManager().callEvent(classevent);
    if (classevent.isCancelled()) {
      return;
    }
    if ((oldClass != null) && (oldClass.permGroup != null) && (!oldClass.permGroup.equals("")))
    {
      if (Ancient.permissionHandler != null)
      {
        Ancient.permissionHandler.playerRemoveGroup(p, oldClass.permGroup);
        for (Map.Entry<String, AncientClass> entry : oldClass.stances.entrySet()) {
          Ancient.permissionHandler.playerRemoveGroup(p, ((AncientClass)entry.getValue()).permGroup);
        }
      }
      if (AncientExperience.isEnabled()) {
        pd.getClassLevels().put(oldClass.name, Integer.valueOf(pd.getXpSystem().xp));
      }
    }
    boolean defaultClassEnabledInNewWorld = false;
    for (String className : AncientClass.classList.keySet()) {
      if ((className.equalsIgnoreCase(AncientClass.standardclassName)) && 
        (((AncientClass)AncientClass.classList.get(className)).isWorldEnabled(p.getWorld())))
      {
        defaultClassEnabledInNewWorld = true;
        break;
      }
    }
    String newClassName;
    String newClassName;
    if (defaultClassEnabledInNewWorld) {
      newClassName = AncientClass.standardclassName;
    } else {
      newClassName = "";
    }
    if (AncientExperience.isEnabled())
    {
      pd.getClassLevels().put(oldClass.name.toLowerCase(), Integer.valueOf(pd.getXpSystem().xp));
      if (pd.getClassLevels().get(newClassName) == null) {
        pd.getClassLevels().put(newClassName, Integer.valueOf(0));
      }
      pd.getXpSystem().xp = ((Integer)pd.getClassLevels().get(newClassName)).intValue();
      
      pd.getXpSystem().addXP(0, false);
    }
    pd.setClassName(newClassName);
    pd.setStance("");
  }
}
