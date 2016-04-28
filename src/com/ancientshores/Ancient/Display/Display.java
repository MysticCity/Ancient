package com.ancientshores.Ancient.Display;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Experience.AncientExperience;
import com.ancientshores.Ancient.Mana.ManaSystem;
import com.ancientshores.Ancient.PlayerData;
import java.io.File;
import java.io.IOException;
import me.confuser.barapi.BarAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Display
{
  public static Bar xpBar = Bar.NONE;
  public static Bar manaBar = Bar.NONE;
  private static long changeTime = 0L;
  private static boolean xpCurrentlyShown;
  private static final String configNodeXPBar = "xp bar";
  private static final String configNodeManaBar = "mana bar";
  private static final String configNodeChangeTime = "switch time when both are on same bar";
  
  public static void loadConfig(Ancient plugin)
  {
    File f = new File(plugin.getDataFolder().getPath() + File.separator + "displayconfig.yml");
    if (f.exists())
    {
      YamlConfiguration yc = new YamlConfiguration();
      try
      {
        yc.load(f);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      xpBar = Bar.getByName(yc.getString("xp bar"));
      manaBar = Bar.getByName(yc.getString("mana bar"));
      changeTime = yc.getLong("switch time when both are on same bar");
      if ((xpBar == manaBar) && (changeTime > 0L))
      {
        BukkitRunnable runnable = new BukkitRunnable()
        {
          public void run() {}
        };
        runnable.runTaskTimer(Ancient.plugin, 0L, changeTime);
      }
    }
    else
    {
      writeConfig(plugin);
      loadConfig(plugin);
    }
  }
  
  public static void writeConfig(Ancient plugin)
  {
    File newfile = new File(plugin.getDataFolder().getPath() + File.separator + "displayconfig.yml");
    if (!newfile.exists()) {
      try
      {
        newfile.createNewFile();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
    YamlConfiguration config = new YamlConfiguration();
    
    config.set("xp bar", xpBar.toString());
    config.set("mana bar", manaBar.toString());
    config.set("switch time when both are on same bar", Long.valueOf(changeTime));
    try
    {
      config.save(newfile);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  public static void updateMana(PlayerData pd)
  {
    Player p = Bukkit.getPlayer(pd.getPlayer());
    if (p == null) {
      return;
    }
    int mana = pd.getManasystem().curmana;
    int maxMana = pd.getManasystem().maxmana;
    switch (manaBar)
    {
    case BOSS: 
      try
      {
        if (manaBar == xpBar)
        {
          if (!xpCurrentlyShown) {
            BarAPI.setMessage(p, "Mana: " + mana, mana / maxMana * 100.0F);
          }
        }
        else {
          BarAPI.setMessage(p, "Mana: " + mana, mana / maxMana * 100.0F);
        }
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
    case XP: 
      if (manaBar == xpBar)
      {
        if (!xpCurrentlyShown)
        {
          p.setLevel(mana);
          p.setExp(mana / maxMana);
        }
      }
      else
      {
        p.setLevel(mana);
        p.setExp(mana / maxMana);
      }
      break;
    }
  }
  
  public static void updateXP(PlayerData pd)
  {
    Player p = Bukkit.getPlayer(pd.getPlayer());
    if (p == null) {
      return;
    }
    AncientExperience expSys = PlayerData.getPlayerData(p.getUniqueId()).getXpSystem();
    
    int xp = expSys.xp;
    
    int xpReqForCurrLvl = expSys.getExperienceOfLevel(expSys.level);
    int xpReqForNextLvl = expSys.getExperienceOfLevel(expSys.level + 1);
    
    int xpOfCurrLvl = xp - xpReqForCurrLvl;
    int deltaxpOfLevel = xpReqForNextLvl - xpReqForCurrLvl;
    
    float done = xpOfCurrLvl / deltaxpOfLevel;
    switch (xpBar)
    {
    case BOSS: 
      try
      {
        if (xpBar == manaBar)
        {
          if (xpCurrentlyShown) {
            BarAPI.setMessage(p, "Level: " + expSys.level, done >= 0.0F ? done : 100.0F);
          }
        }
        else {
          BarAPI.setMessage(p, "Level: " + expSys.level, done >= 0.0F ? done : 100.0F);
        }
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
    case XP: 
      if (xpBar == manaBar)
      {
        if (xpCurrentlyShown)
        {
          p.setLevel(expSys.level);
          p.setExp((done >= 0.0F) && (done <= 1.0F) ? done : 1.0F);
        }
      }
      else
      {
        p.setLevel(expSys.level);
        p.setExp((done >= 0.0F) && (done <= 1.0F) ? done : 1.0F);
      }
      break;
    }
  }
  
  public static void switchBar()
  {
    xpCurrentlyShown ^= true;
    if (xpCurrentlyShown) {
      switch (xpBar)
      {
      case BOSS: 
        for (Player p : Bukkit.getOnlinePlayers())
        {
          AncientExperience expSys = PlayerData.getPlayerData(p.getUniqueId()).getXpSystem();
          
          int xp = expSys.xp;
          int xpReqForCurrLvl = expSys.getExperienceOfLevel(expSys.level);
          int xpReqForNextLvl = expSys.getExperienceOfLevel(expSys.level + 1);
          
          int xpOfCurrLvl = xp - xpReqForCurrLvl;
          int deltaxpOfLevel = xpReqForNextLvl - xpReqForCurrLvl;
          
          float done = xpOfCurrLvl / deltaxpOfLevel * 100.0F;
          
          BarAPI.setMessage(p, "Level: " + expSys.level, (100.0F >= done) && (done >= 0.0F) ? done : 100.0F);
        }
        break;
      case XP: 
        for (Player p : Bukkit.getOnlinePlayers())
        {
          AncientExperience expSys = PlayerData.getPlayerData(p.getUniqueId()).getXpSystem();
          
          int xp = expSys.xp;
          int xpReqForCurrLvl = expSys.getExperienceOfLevel(expSys.level);
          int xpReqForNextLvl = expSys.getExperienceOfLevel(expSys.level + 1);
          
          int xpOfCurrLvl = xp - xpReqForCurrLvl;
          int deltaxpOfLevel = xpReqForNextLvl - xpReqForCurrLvl;
          
          float done = xpOfCurrLvl / deltaxpOfLevel;
          
          p.setLevel(expSys.level);
          p.setExp((100.0F >= done) && (done >= 0.0F) ? done : 100.0F);
        }
        break;
      }
    } else {
      switch (manaBar)
      {
      case BOSS: 
        for (Player p : Bukkit.getOnlinePlayers())
        {
          ManaSystem manaSys = PlayerData.getPlayerData(p.getUniqueId()).getManasystem();
          
          int mana = manaSys.curmana;
          int maxMana = manaSys.maxmana;
          
          float done = mana / maxMana * 100.0F;
          
          BarAPI.setMessage(p, "Mana: " + mana, (100.0F >= done) && (done >= 0.0F) ? done : 100.0F);
        }
        break;
      case XP: 
        for (Player p : Bukkit.getOnlinePlayers())
        {
          ManaSystem manaSys = PlayerData.getPlayerData(p.getUniqueId()).getManasystem();
          
          int mana = manaSys.curmana;
          int maxMana = manaSys.maxmana;
          
          float done = mana / maxMana * 100.0F;
          
          p.setLevel(mana);
          p.setExp((100.0F >= done) && (done >= 0.0F) ? done : 100.0F);
        }
        break;
      }
    }
  }
}
