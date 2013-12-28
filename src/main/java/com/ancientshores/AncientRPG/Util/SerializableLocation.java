package com.ancientshores.AncientRPG.Util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.Serializable;

public class SerializableLocation implements Serializable {
    private static final long serialVersionUID = 1L;
    public final double x;
    public final double y;
    public final double z;
    public final String wName;

    public SerializableLocation(Location l) {
        x = l.getX();
        y = l.getY();
        z = l.getZ();
        wName = l.getWorld().getName();
    }

    public Location toLocation() {
        World w = Bukkit.getWorld(wName);
        if (w != null) {
            return new Location(w, x, y, z);
        }
        return null;
    }
}