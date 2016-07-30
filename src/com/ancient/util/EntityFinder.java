package com.ancient.util;

import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;

public class EntityFinder
{
  public static Entity findByUUID(UUID uuid)
  {
    
      World w = Bukkit.getWorld(uuid);
      
      for (Entity e : w.getEntities()) {
        if (e.getUniqueId().compareTo(uuid) == 0) {
          return e;
        }
      }
      
    
    return null;
  }
}
