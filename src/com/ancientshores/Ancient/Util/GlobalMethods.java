package com.ancientshores.Ancient.Util;

import java.lang.reflect.Array;

import org.bukkit.entity.*;

public class GlobalMethods {
    public static String getStringByEntity(Entity e) {
        String s = "";
        if (e instanceof Arrow) {
            return "arrow";
        }
        if (e instanceof Blaze) {
            return "blaze";
        }
        if (e instanceof Boat) {
            return "boat";
        }
        if (e instanceof CaveSpider) {
            return "cavespider";
        }
        if (e instanceof Chicken) {
            return "chicken";
        }
        if (e instanceof MushroomCow) {
            return "mushroomcow";
        }
        if (e instanceof Cow) {
            return "cow";
        }
        if (e instanceof Creeper) {
            return "creeper";
        }
        if (e instanceof Egg) {
            return "egg";
        }
        if (e instanceof EnderCrystal) {
            return "endercrystal";
        }
        if (e instanceof EnderDragon) {
            return "enderdragon";
        }
        if (e instanceof EnderDragonPart) {
            return "enderdragonpart";
        }
        if (e instanceof Enderman) {
            return "enderman";
        }
        if (e instanceof EnderPearl) {
            return "enderpearl";
        }
        if (e instanceof EnderSignal) {
            return "endersignal";
        }
        if (e instanceof ExperienceOrb) {
            return "experienceorb";
        }
        if (e instanceof SmallFireball) {
            return "smallfireball";
        }
        if (e instanceof Fireball) {
            return "fireball";
        }
        if (e instanceof Fish) {
            return "fish";
        }
        if (e instanceof Ghast) {
            return "ghast";
        }
        if (e instanceof Giant) {
            return "giant";
        }
        if (e instanceof IronGolem) {
            return "irongolem";
        }
        if (e instanceof LightningStrike) {
            return "lightningstrike";
        }
        if (e instanceof MagmaCube) {
            return "magmacube";
        }
        if (e instanceof org.bukkit.entity.minecart.PoweredMinecart) {
            return "poweredminecart";
        }
        if (e instanceof org.bukkit.entity.minecart.StorageMinecart) {
        	return "storageminecart";
        }
        if (e instanceof org.bukkit.entity.minecart.RideableMinecart) {
            return "minecart";
        }
        if (e instanceof Ocelot) {
            return "ocelot";
        }
        if (e instanceof Painting) {
            return "painting";
        }
        if (e instanceof Pig) {
            return "pig";
        }
        if (e instanceof PigZombie) {
            return "pigzombie";
        }
        if (e instanceof Player) {
            return "player";
        }
        if (e instanceof Sheep) {
            return "sheep";
        }
        if (e instanceof Silverfish) {
            return "silverfish";
        }
        if (e instanceof Skeleton) {
            return "skeleton";
        }
        if (e instanceof Slime) {
            return "slime";
        }
        if (e instanceof Snowball) {
            return "snowball";
        }
        if (e instanceof Snowman) {
            return "snowman";
        }
        if (e instanceof Spider) {
            return "spider";
        }
        if (e instanceof Squid) {
            return "squid";
        }
        if (e instanceof ThrownExpBottle) {
            return "thrownexpbottle";
        }
        if (e instanceof ThrownPotion) {
            return "thrownpotion";
        }
        if (e instanceof TNTPrimed) {
            return "tntprimed";
        }
        if (e instanceof Villager) {
            return "villager";
        }
        if (e instanceof Wolf) {
            return "wolf";
        }
        if (e instanceof Zombie) {
            return "zombie";
        }
        return s;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] removeNullArrayCells(T[] objs) {
        if (objs == null || objs.length == 0) {
            return objs;
        }
        int count = 0;
        Class<T> c = (Class<T>) objs.getClass().getComponentType();
        for (T obj1 : objs) {
            if (obj1 != null) {
                count++;
            }
        }
        T[] robj = (T[]) Array.newInstance(c, count);
        int x = 0;
        for (T obj : objs) {
            if (obj != null) {
                robj[x] = obj;
                x++;
            }
        }
        return robj;
    }
}