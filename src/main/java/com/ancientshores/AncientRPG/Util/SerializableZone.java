package com.ancientshores.AncientRPG.Util;

import java.io.Serializable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class SerializableZone implements Serializable
{
	/**
	 * 
	 */
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
		x = l.getX();
		y = l.getY();
		z = l.getZ();
		x2 = l2.getX();
		y2 = l2.getY();
		z2 = l2.getZ();
		wName = l.getWorld().getName();
	}
	
	public boolean isInZone(Location l)
	{
		if(!l.getWorld().getName().equals(wName))
			return false;
		boolean xf = ((l.getX() <= x) && (l.getX() >= x2)) || ((l.getX() >= x) && (l.getX() <= x2));
		boolean yf = ((l.getY() <= y) && (l.getY() >= y2)) || ((l.getY() >= y) && (l.getY() <= y2));
		boolean zf = ((l.getZ() <= z) && (l.getZ() >= z2)) || ((l.getZ() >= z) && (l.getZ() <= z2));
		return (xf && yf && zf);
	}
	
	public Location toLocation1()
	{
		World w = Bukkit.getWorld(wName);
		if(w != null)
		{
			return new Location(w, x, y, z);
		}
		return null;
	}
	
	public Location toLocation2()
	{
		World w = Bukkit.getWorld(wName);
		if(w != null)
		{
			return new Location(w, x2, y2, z2);
		}
		return null;
	}
}
