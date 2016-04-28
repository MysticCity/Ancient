package com.ancient.util;

import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;

public class EntityFinder
{
  public static Entity findByUUID(UUID uuid)
  {
    for (World w : ) {
      for (Entity e : w.getEntities()) {
        if (e.getUniqueId().compareTo(uuid) == 0) {
          return e;
        }
      }
    }
    return null;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancient\util\EntityFinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */