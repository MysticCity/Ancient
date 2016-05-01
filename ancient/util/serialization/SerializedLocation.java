package com.ancientshores.Ancient.Util.serialization;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class SerializedLocation
  implements Serializable, ConfigurationSerializable
{
  private static final long serialVersionUID = -4024229540724198112L;
  private final String WORLD;
  private final double X;
  private final double Y;
  private final double Z;
  
  public SerializedLocation(String world, double x, double y, double z)
  {
    this.WORLD = world;
    this.X = x;
    this.Y = y;
    this.Z = z;
  }
  
  public SerializedLocation(Map<String, Object> map)
  {
    this.WORLD = ((String)map.get("world"));
    this.X = ((Double)map.get("x")).doubleValue();
    this.Y = ((Double)map.get("y")).doubleValue();
    this.Z = ((Double)map.get("z")).doubleValue();
  }
  
  public Map<String, Object> serialize()
  {
    HashMap<String, Object> serialized = new HashMap();
    
    serialized.put("world", this.WORLD);
    serialized.put("x", Double.valueOf(this.X));
    serialized.put("y", Double.valueOf(this.Y));
    serialized.put("z", Double.valueOf(this.Z));
    
    return serialized;
  }
}
