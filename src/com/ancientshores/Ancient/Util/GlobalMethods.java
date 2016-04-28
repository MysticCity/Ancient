package com.ancientshores.Ancient.Util;

import java.lang.reflect.Array;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.Boat;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EnderDragonPart;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.EnderSignal;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Fish;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Giant;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Squid;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.entity.minecart.PoweredMinecart;
import org.bukkit.entity.minecart.RideableMinecart;
import org.bukkit.entity.minecart.StorageMinecart;

public class GlobalMethods
{
  public static String getStringByEntity(Entity e)
  {
    String s = "";
    if ((e instanceof Arrow)) {
      return "arrow";
    }
    if ((e instanceof Blaze)) {
      return "blaze";
    }
    if ((e instanceof Boat)) {
      return "boat";
    }
    if ((e instanceof CaveSpider)) {
      return "cavespider";
    }
    if ((e instanceof Chicken)) {
      return "chicken";
    }
    if ((e instanceof MushroomCow)) {
      return "mushroomcow";
    }
    if ((e instanceof Cow)) {
      return "cow";
    }
    if ((e instanceof Creeper)) {
      return "creeper";
    }
    if ((e instanceof Egg)) {
      return "egg";
    }
    if ((e instanceof EnderCrystal)) {
      return "endercrystal";
    }
    if ((e instanceof EnderDragon)) {
      return "enderdragon";
    }
    if ((e instanceof EnderDragonPart)) {
      return "enderdragonpart";
    }
    if ((e instanceof Enderman)) {
      return "enderman";
    }
    if ((e instanceof EnderPearl)) {
      return "enderpearl";
    }
    if ((e instanceof EnderSignal)) {
      return "endersignal";
    }
    if ((e instanceof ExperienceOrb)) {
      return "experienceorb";
    }
    if ((e instanceof SmallFireball)) {
      return "smallfireball";
    }
    if ((e instanceof Fireball)) {
      return "fireball";
    }
    if ((e instanceof Fish)) {
      return "fish";
    }
    if ((e instanceof Ghast)) {
      return "ghast";
    }
    if ((e instanceof Giant)) {
      return "giant";
    }
    if ((e instanceof IronGolem)) {
      return "irongolem";
    }
    if ((e instanceof LightningStrike)) {
      return "lightningstrike";
    }
    if ((e instanceof MagmaCube)) {
      return "magmacube";
    }
    if ((e instanceof PoweredMinecart)) {
      return "poweredminecart";
    }
    if ((e instanceof StorageMinecart)) {
      return "storageminecart";
    }
    if ((e instanceof RideableMinecart)) {
      return "minecart";
    }
    if ((e instanceof Ocelot)) {
      return "ocelot";
    }
    if ((e instanceof Painting)) {
      return "painting";
    }
    if ((e instanceof Pig)) {
      return "pig";
    }
    if ((e instanceof PigZombie)) {
      return "pigzombie";
    }
    if ((e instanceof Player)) {
      return "player";
    }
    if ((e instanceof Sheep)) {
      return "sheep";
    }
    if ((e instanceof Silverfish)) {
      return "silverfish";
    }
    if ((e instanceof Skeleton)) {
      return "skeleton";
    }
    if ((e instanceof Slime)) {
      return "slime";
    }
    if ((e instanceof Snowball)) {
      return "snowball";
    }
    if ((e instanceof Snowman)) {
      return "snowman";
    }
    if ((e instanceof Spider)) {
      return "spider";
    }
    if ((e instanceof Squid)) {
      return "squid";
    }
    if ((e instanceof ThrownExpBottle)) {
      return "thrownexpbottle";
    }
    if ((e instanceof ThrownPotion)) {
      return "thrownpotion";
    }
    if ((e instanceof TNTPrimed)) {
      return "tntprimed";
    }
    if ((e instanceof Villager)) {
      return "villager";
    }
    if ((e instanceof Wolf)) {
      return "wolf";
    }
    if ((e instanceof Zombie)) {
      return "zombie";
    }
    return s;
  }
  
  public static <T> T[] removeNullArrayCells(T[] objs)
  {
    if ((objs == null) || (objs.length == 0)) {
      return objs;
    }
    int count = 0;
    Class<T> c = objs.getClass().getComponentType();
    for (T obj1 : objs) {
      if (obj1 != null) {
        count++;
      }
    }
    T[] robj = (Object[])Array.newInstance(c, count);
    int x = 0;
    for (T obj : objs) {
      if (obj != null)
      {
        robj[x] = obj;
        x++;
      }
    }
    return robj;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Util\GlobalMethods.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */