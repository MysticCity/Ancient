package com.ancientshores.Ancient.HP;

import com.ancient.util.UUIDConverter;
import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.AncientClass;
import com.ancientshores.Ancient.Experience.AncientExperience;
import com.ancientshores.Ancient.PlayerData;
import com.ancientshores.Ancient.Util.LinkedStringHashMap;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class AncientHP
  implements Serializable, ConfigurationSerializable
{
  private static final long serialVersionUID = 1L;
  public double maxhp;
  private double hpReg = DamageConverter.getHPRegeneration();
  public double hpRegInterval = DamageConverter.getHPRegenerationInterval();
  public double health;
  public UUID playerUUID;
  public int task;
  public long lastAttackDamage;
  public boolean damage;
  
  public Map<String, Object> serialize()
  {
    HashMap<String, Object> map = new HashMap();
    
    map.put("maxhp", Double.valueOf(this.maxhp));
    map.put("hpReg", Double.valueOf(this.hpReg));
    map.put("hpRegInterval", Double.valueOf(this.hpRegInterval));
    map.put("hp", Double.valueOf(this.health));
    map.put("uuid", this.playerUUID.toString());
    
    return map;
  }
  
  public AncientHP(Map<String, Object> map)
  {
    this.health = ((Number)map.get("hp")).intValue();
    this.maxhp = ((Number)map.get("maxhp")).intValue();
    this.hpRegInterval = ((Number)map.get("hpRegInterval")).intValue();
    this.hpReg = ((Number)map.get("hpReg")).intValue();
    if (map.containsKey("uuid")) {
      this.playerUUID = UUID.fromString((String)map.get("uuid"));
    } else {
      this.playerUUID = UUIDConverter.getUUID((String)map.get("playername"));
    }
  }
  
  public AncientHP(double maxhp, UUID playeruuid)
  {
    this.health = maxhp;
    this.maxhp = maxhp;
    this.playerUUID = playeruuid;
  }
  
  public void startRegenTimer()
  {
    stopRegenTimer();
    if (!DamageConverter.isEnabled()) {
      return;
    }
    AncientClass mClass = (AncientClass)AncientClass.classList.get(PlayerData.getPlayerData(this.playerUUID).getClassName().toLowerCase());
    if (mClass != null)
    {
      if (DamageConverter.isEnabled()) {
        this.hpReg = ((Float)mClass.hpreglevel.get(Integer.valueOf(PlayerData.getPlayerData(this.playerUUID).getXpSystem().level))).intValue();
      } else {
        this.hpReg = mClass.hpreg;
      }
    }
    else {
      this.hpReg = DamageConverter.getHPRegeneration();
    }
    this.hpRegInterval = DamageConverter.getHPRegenerationInterval();
    this.task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Ancient.plugin, new Runnable()
    {
      public void run()
      {
        if (!AncientHP.this.damage)
        {
          Player p = Bukkit.getPlayer(AncientHP.this.playerUUID);
          if (p == null)
          {
            AncientHP.this.stopRegenTimer();
            return;
          }
          if (p.isDead()) {
            return;
          }
          AncientHP.this.health = p.getHealth();
          if ((p.getFoodLevel() >= DamageConverter.getMinimalFoodLevelForReg()) && 
            (DamageConverter.isEnabledInWorld(p.getWorld()))) {
            AncientHP.addHpByUUID(AncientHP.this.playerUUID, AncientHP.this.hpReg);
          }
        }
        AncientHP.this.damage = false;
      }
    }, Math.round(this.hpRegInterval * 20.0D), Math.round(this.hpRegInterval * 20.0D));
  }
  
  public void stopRegenTimer()
  {
    Bukkit.getScheduler().cancelTask(this.task);
  }
  
  public double getMaxHP()
  {
    setMaxHP();
    setMinecraftHP();
    return this.maxhp;
  }
  
  public void setMaxHP()
  {
    Player p = Bukkit.getPlayer(this.playerUUID);
    if (p == null) {
      return;
    }
    if ((Bukkit.getPlayer(this.playerUUID) != null) && 
      (!DamageConverter.isEnabledInWorld(Bukkit.getPlayer(this.playerUUID).getWorld())))
    {
      this.maxhp = 20.0D;
      
      addMinecraftHpByUUID(this.playerUUID, 0.0F);
      return;
    }
    AncientClass mClass = (AncientClass)AncientClass.classList.get(PlayerData.getPlayerData(this.playerUUID).getClassName().toLowerCase());
    if (mClass != null)
    {
      if (AncientExperience.isEnabled())
      {
        if ((PlayerData.getPlayerData(this.playerUUID).getStance() != null) && (mClass.stances.containsKey(PlayerData.getPlayerData(this.playerUUID).getStance().toLowerCase()))) {
          this.maxhp = ((Float)((AncientClass)mClass.stances.get(PlayerData.getPlayerData(this.playerUUID).getStance().toLowerCase())).hplevel.get(Integer.valueOf(PlayerData.getPlayerData(this.playerUUID).getXpSystem().level))).intValue();
        } else {
          this.maxhp = ((Float)mClass.hplevel.get(Integer.valueOf(PlayerData.getPlayerData(this.playerUUID).getXpSystem().level))).intValue();
        }
      }
      else if (mClass.stances.containsKey(PlayerData.getPlayerData(this.playerUUID).getStance())) {
        this.maxhp = ((AncientClass)mClass.stances.get(PlayerData.getPlayerData(this.playerUUID).getStance())).hp;
      } else {
        this.maxhp = mClass.hp;
      }
    }
    else {
      this.maxhp = DamageConverter.getStandardHP();
    }
    addMinecraftHpByUUID(this.playerUUID, 0.0F);
  }
  
  public double getHPRegen()
  {
    setHpRegen();
    return this.hpReg;
  }
  
  public void setHpRegen()
  {
    AncientClass mClass = (AncientClass)AncientClass.classList.get(PlayerData.getPlayerData(this.playerUUID).getClassName().toLowerCase());
    if (mClass != null)
    {
      if (AncientExperience.isEnabled())
      {
        if ((PlayerData.getPlayerData(this.playerUUID).getStance() != null) && (mClass.stances.containsKey(PlayerData.getPlayerData(this.playerUUID).getStance().toLowerCase()))) {
          this.hpReg = ((Float)((AncientClass)mClass.stances.get(PlayerData.getPlayerData(this.playerUUID).getStance().toLowerCase())).hpreglevel.get(Integer.valueOf(PlayerData.getPlayerData(this.playerUUID).getXpSystem().level))).intValue();
        } else {
          this.hpReg = ((Float)mClass.hpreglevel.get(Integer.valueOf(PlayerData.getPlayerData(this.playerUUID).getXpSystem().level))).intValue();
        }
      }
      else if ((PlayerData.getPlayerData(this.playerUUID).getStance() != null) && (mClass.stances.containsKey(PlayerData.getPlayerData(this.playerUUID).getStance().toLowerCase()))) {
        this.hpReg = ((AncientClass)mClass.stances.get(PlayerData.getPlayerData(this.playerUUID).getStance().toLowerCase())).hpreg;
      } else {
        this.hpReg = mClass.hpreg;
      }
    }
    else {
      this.hpReg = DamageConverter.getHPRegeneration();
    }
  }
  
  public void setMinecraftHP()
  {
    if (Bukkit.getPlayer(this.playerUUID) == null) {
      return;
    }
    Player p = Bukkit.getPlayer(this.playerUUID);
    
    this.health = (this.health * this.maxhp / p.getMaxHealth());
    if (this.health > this.maxhp) {
      this.health = this.maxhp;
    }
    p.setHealthScaled(true);
    p.setMaxHealth(this.maxhp);
    p.setHealth(this.health);
  }
  
  public static void addHpByUUID(UUID uuid, double hp)
  {
    addNormalHp(uuid, hp);
    if (!DamageConverter.isEnabled()) {
      return;
    }
    AncientHP hpinstance = PlayerData.getPlayerData(uuid).getHpsystem();
    if (hpinstance.health < 0.0D) {
      return;
    }
    Player p = Bukkit.getPlayer(uuid);
    
    hpinstance.health = p.getHealth();
    hpinstance.setMinecraftHP();
  }
  
  public static void addMinecraftHpByUUID(UUID uuid, float hp)
  {
    AncientHP hpinstance = PlayerData.getPlayerData(uuid).getHpsystem();
    if (hpinstance.health < 0.0D) {
      return;
    }
    hpinstance.health += hpinstance.maxhp * (hp / 20.0D);
    hpinstance.setMinecraftHP();
  }
  
  public static double getHpByMinecraftDamage(UUID uuid, double hp)
  {
    AncientHP hpinstance = PlayerData.getPlayerData(uuid).getHpsystem();
    return hpinstance.maxhp * (hp / 20.0D);
  }
  
  public static void addNormalHp(UUID uuid, double hp)
  {
    Player p = Bukkit.getPlayer(uuid);
    if (p.getHealth() > 0.0D) {
      if (p.getHealth() + hp > p.getMaxHealth()) {
        p.setHealth(p.getMaxHealth());
      } else {
        p.setHealth(p.getHealth() + hp);
      }
    }
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\HP\AncientHP.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */