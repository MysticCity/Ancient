package com.ancientshores.Ancient.Util;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class Plane
{
  Vector nv;
  Location p;
  
  public Plane(Vector v1, Vector v2, Location p)
  {
    this.nv = v1.crossProduct(v2);
    this.p = p;
  }
  
  public Plane(Vector nv, Location p)
  {
    this.nv = nv;
    this.p = p;
  }
  
  public double distance(Location p)
  {
    double val = -this.p.toVector().dot(this.nv) / this.nv.length();
    Vector normalized = this.nv.normalize();
    if (val > 0.0D)
    {
      val *= -1.0D;
      normalized.multiply(-1);
    }
    return normalized.getX() * p.getX() + normalized.getY() * p.getY() + normalized.getZ() * p.getZ() + val;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Util\Plane.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */