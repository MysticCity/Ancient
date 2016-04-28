package com.ancientshores.Ancient.Util;

import java.io.Serializable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class SerializableZone
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public final double x;
  public final double y;
  public final double z;
  public final double x2;
  public final double y2;
  public final double z2;
  public final String wName;
  
  public SerializableZone(Location l, Location l2)
  {
    this.x = l.getX();
    this.y = l.getY();
    this.z = l.getZ();
    this.x2 = l2.getX();
    this.y2 = l2.getY();
    this.z2 = l2.getZ();
    this.wName = l.getWorld().getName();
  }
  
  public boolean isInZone(Location l)
  {
    if (!l.getWorld().getName().equals(this.wName)) {
      return false;
    }
    boolean xf = ((l.getX() <= this.x) && (l.getX() >= this.x2)) || ((l.getX() >= this.x) && (l.getX() <= this.x2));
    boolean yf = ((l.getY() <= this.y) && (l.getY() >= this.y2)) || ((l.getY() >= this.y) && (l.getY() <= this.y2));
    boolean zf = ((l.getZ() <= this.z) && (l.getZ() >= this.z2)) || ((l.getZ() >= this.z) && (l.getZ() <= this.z2));
    return (xf) && (yf) && (zf);
  }
  
  public Location toLocation1()
  {
    World w = Bukkit.getWorld(this.wName);
    if (w != null) {
      return new Location(w, this.x, this.y, this.z);
    }
    return null;
  }
  
  public Location toLocation2()
  {
    World w = Bukkit.getWorld(this.wName);
    if (w != null) {
      return new Location(w, this.x2, this.y2, this.z2);
    }
    return null;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Util\SerializableZone.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */