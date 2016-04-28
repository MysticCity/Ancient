package com.ancientshores.Ancient.Util;

import java.io.Serializable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class SerializableLocation
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public final double x;
  public final double y;
  public final double z;
  public final String wName;
  
  public SerializableLocation(Location l)
  {
    this.x = l.getX();
    this.y = l.getY();
    this.z = l.getZ();
    this.wName = l.getWorld().getName();
  }
  
  public Location toLocation()
  {
    World w = Bukkit.getWorld(this.wName);
    if (w != null) {
      return new Location(w, this.x, this.y, this.z);
    }
    return null;
  }
}
