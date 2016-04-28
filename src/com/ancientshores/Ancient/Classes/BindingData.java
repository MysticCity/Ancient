package com.ancientshores.Ancient.Classes;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class BindingData
  implements Serializable, ConfigurationSerializable
{
  private static final long serialVersionUID = 1L;
  public boolean spoutstack;
  public String spoutname = "";
  public final int id;
  public byte data;
  
  public BindingData(Map<String, Object> map)
  {
    this.spoutstack = ((Boolean)map.get("spoutstack")).booleanValue();
    this.spoutname = ((String)map.get("spoutname"));
    this.id = ((Integer)map.get("id")).intValue();
    this.data = ((Byte)map.get("data")).byteValue();
    if (isBreakable(this.id)) {
      this.data = 0;
    }
  }
  
  public BindingData(ItemStack is)
  {
    this.id = is.getTypeId();
    this.data = is.getData().getData();
    if (isBreakable(this.id)) {
      this.data = 0;
    }
  }
  
  public BindingData(int id, byte data)
  {
    this.id = id;
    this.data = data;
    if (isBreakable(id)) {
      this.data = 0;
    }
  }
  
  public boolean equals(Object c)
  {
    if ((c instanceof BindingData))
    {
      BindingData bd = (BindingData)c;
      if ((bd.id == this.id) && (bd.data == this.data)) {
        return true;
      }
    }
    return false;
  }
  
  public Map<String, Object> serialize()
  {
    HashMap<String, Object> map = new HashMap();
    map.put("spoutstack", Boolean.valueOf(true));
    map.put("spoutname", this.spoutname);
    map.put("id", Integer.valueOf(this.id));
    map.put("data", Byte.valueOf(this.data));
    return map;
  }
  
  public int hashCode()
  {
    return this.id * 319 + this.data * 477;
  }
  
  public boolean isBreakable(int id)
  {
    if ((id >= 256) && (id <= 258)) {
      return true;
    }
    if ((id >= 267) && (id <= 279)) {
      return true;
    }
    if ((id >= 283) && (id <= 286)) {
      return true;
    }
    if ((id >= 290) && (id <= 294)) {
      return true;
    }
    if ((id >= 298) && (id <= 317)) {
      return true;
    }
    if (id == 346) {
      return true;
    }
    if (id == 359) {
      return true;
    }
    return id == 261;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\BindingData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */