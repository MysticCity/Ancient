package com.ancientshores.Ancient.Classes;

import com.ancientshores.Ancient.Ancient;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.scheduler.BukkitScheduler;

public class CooldownTimer
  implements Serializable, Runnable, ConfigurationSerializable
{
  private static final long serialVersionUID = 1L;
  public boolean ready;
  public long time;
  public final String cooldownname;
  public long oldtime = 0L;
  public boolean enabled = false;
  public int id = 0;
  
  public CooldownTimer(int time, String cooldownname)
  {
    this.ready = true;
    this.time = time;
    this.cooldownname = cooldownname;
    this.oldtime = System.currentTimeMillis();
  }
  
  public CooldownTimer(Map<String, Object> map)
  {
    this.ready = ((Boolean)map.get("ready")).booleanValue();
    this.time = ((Long)map.get("time")).longValue();
    this.cooldownname = ((String)map.get("cooldownname"));
    this.oldtime = ((Long)map.get("oldtime")).longValue();
    this.enabled = ((Boolean)map.get("enabled")).booleanValue();
    this.id = ((Integer)map.get("id")).intValue();
  }
  
  public void startTimer()
  {
    this.ready = false;
    if (!this.enabled)
    {
      this.id = Bukkit.getScheduler().scheduleSyncRepeatingTask(Ancient.plugin, this, 1L, 1L);
      this.enabled = true;
    }
  }
  
  public void run()
  {
    this.time -= Math.abs(this.oldtime - System.currentTimeMillis());
    this.oldtime = System.currentTimeMillis();
    if (this.time <= 0L)
    {
      this.time = 0L;
      this.enabled = false;
      this.ready = true;
      Bukkit.getScheduler().cancelTask(this.id);
    }
  }
  
  public boolean equals(Object obj)
  {
    try
    {
      CooldownTimer c = (CooldownTimer)obj;
      return this.cooldownname.equals(c.cooldownname);
    }
    catch (Exception e) {}
    return false;
  }
  
  public int hashCode()
  {
    return this.cooldownname.hashCode();
  }
  
  public Map<String, Object> serialize()
  {
    HashMap<String, Object> map = new HashMap();
    map.put("ready", Boolean.valueOf(this.ready));
    map.put("time", Long.valueOf(this.time));
    map.put("cooldownname", this.cooldownname);
    map.put("oldtime", Long.valueOf(this.oldtime));
    map.put("enabled", Boolean.valueOf(this.enabled));
    map.put("id", Integer.valueOf(this.id));
    return map;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\CooldownTimer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */