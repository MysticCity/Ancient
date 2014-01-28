package com.ancientshores.AncientRPG.HP;

import com.ancientshores.AncientRPG.AncientRPG;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.entity.Skeleton.SkeletonType;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

public class CreatureHp {
    // ===============================================================
    // static fields
    // ===============================================================
    private static int hpOfChicken = 50;
    private static final String configHpChicken = "CreatureHp.hp of a chicken";
    private static int hpOfCow = 200;
    private static final String configHpCow = "CreatureHp.hp of a cow";
    private static int hpOfMooshroom = 200;
    private static final String configHpMooshroom = "CreatureHp.hp of a mooshroom";
    private static int hpOfOcelot = 200;
    private static final String configHpOcelot = "CreatureHp.hp of an Ocelot";
    private static int hpOfHorse = 500;
    private static final String configHpHorse = "CreatureHp.hp of a horse";
    private static int hpOfPig = 200;
    private static final String configHpPig = "CreatureHp.hp of a pig";
    private static int hpOfSheep = 150;
    private static final String configHpSheep = "CreatureHp.hp of a sheep";
    private static int hpOfSquid = 200;
    private static final String configHpSquid = "CreatureHp.hp of a squid";
    private static int hpOfVillager = 500;
    private static final String configHpVillager = "CreatureHp.hp of a villager";
    private static int hpOfEnderman = 1000;
    private static final String configHpEnderman = "CreatureHp.hp of a Enderman";
    private static int hpOfWolf = 200;
    private static final String configHpWolf = "CreatureHp.hp of a wolf";
    private static int hpOfWolfTamed = 500;
    private static final String configHpWolfTamed = "CreatureHp.hp of a tamed wolf";
    private static int hpOfZombiePigman = 500;
    private static final String configHpZombiePigman = "CreatureHp.hp of a zombie pigman";
    private static int hpOfBlaze = 500;
    private static final String configHpBlaze = "CreatureHp.hp of a blaze";
    private static int hpOfCaveSpider = 350;
    private static final String configHpCaveSpider = "CreatureHp.hp of a cave spider";
    private static int hpOfCreeper = 300;
    private static final String configHpBat = "CreatureHp.hp of a bat";
    private static int hpOfBat = 300;
    private static final String configHpCreeper = "CreatureHp.hp of a creeper";
    private static int hpOfGhast = 300;
    private static final String configHpGhast = "CreatureHp.hp of a ghast";
    private static int hpOfMagmaCubeBig = 450;
    private static final String configHpMagmaCubeBig = "CreatureHp.hp of a magma cube big";
    private static int hpOfMagmaCubeSmall = 100;
    private static final String configHpMagmaCubeSmall = "CreatureHp.hp of a magma cube small";
    private static int hpOfMagmaCubeTiny = 50;
    private static final String configHpMagmaTiny = "CreatureHp.hp of a magma cube tiny";
    private static int hpOfSilverfish = 200;
    private static final String configHpSilverfish = "CreatureHp.hp of a silverfish";
    private static int hpOfSkeleton = 500;
    private static final String configHpSkeleton = "CreatureHp.hp of a skeleton";
    private static int hpOfSlimeBig = 450;
    private static final String configHpSlimeBig = "CreatureHp.hp of a slime big";
    private static int hpOfSlimeSmall = 150;
    private static final String configHpSlimeSmall = "CreatureHp.hp of a slime small";
    private static int hpOfSlimeTiny = 40;
    private static final String configHpSlimeTiny = "CreatureHp.hp of a slime tiny";
    private static final String configHpSpider = "CreatureHp.hp of a spider";
    private static int hpOfSpider = 450;
    private static final String configHpZombie = "CreatureHp.hp of a zombie";
    private static int hpOfZombie = 500;
    private static final String configHpBabyZombie = "CreatureHp.hp of a baby zombie";
    private static int hpOfBabyZombie = 300;
    private static final String configHpZombieVillager = "CreatureHp.hp of a zombie villager";
    private static int hpOfZombieVillager = 500;
    private static final String configHpZombieBabyVillager = "CreatureHp.hp of a baby zombie villager";
    private static int hpOfZombieBabyVillager = 300;
    private static final String configHpWitch = "CreatureHp.hp of a witch";
    private static int hpOfWitch = 1000;
    private static final String configHpSnowGolem = "CreatureHp.hp of a snow golem";
    private static int hpOfSnowGolem = 200;
    private static final String configHpWither = "CreatureHp.hp of a wither";
    private static int hpOfWitherSkeleton = 600;
    private static final String configHpWitherSkeleton = "CreatureHp.hp of a wither skeleton";
    private static int hpOfWither = 500;
    private static int hpOfIronGolem = 2500;
    private static final String configHpIronGolem = "CreatureHp.hp of an iron golem";
    private static int hpOfEnderDragon = 5000;
    private static final String configHpEnderDragon = "CreatureHp.hp of an ender dragon";
    private static int hpOfGiant = 5000;
    private static final String configHpGiant = "CreatureHp.hp of a giant";
    private static boolean enabled = true;
    private static final String enabledConfig = "CreatureHp.enabled";
    private static String enabledWorlds = "all";
    private static final String enabledWorldsConfig = "CreatureHp.enabledWorlds";
    public static int minTimeBetweenAttacks = 500;
    private static final String HpConfigMinTimeBetweenAttacks = "CreatureHp.minimum time between each attack on a creature in milliseconds";

    static final HashSet<CreatureHp> registeredMonsters = new HashSet<CreatureHp>();
    // ===============================================================
    // object fields
    // ===============================================================
    LivingEntity ent;
    public long lastFireDamage;
    public long lastCactusDamage;
    public long lastLavaDamage;
    public long lastAttackDamage;

    public CreatureHp(LivingEntity e) {
        if (e instanceof HumanEntity) {
            return;
        }
        if (e.getHealth() <= 0) {
            return;
        }
        e.setMaxHealth(getMaxHpByEntity(e));
        e.setHealth(e.getMaxHealth());
        this.ent = e;
        // ent.setHealth(1);
        registeredMonsters.add(this);
    }

    public static CreatureHp getCreatureHpByEntity(LivingEntity e) {
        for (CreatureHp hp : registeredMonsters) {
            if (e.equals(hp.ent)) {
                return hp;
            }
        }
        return new CreatureHp(e);
    }

    public static void removeCreature(LivingEntity e) {
        CreatureHp mhp = null;
        for (CreatureHp hp : registeredMonsters) {
            if (e == hp.ent) {
                mhp = hp;
                break;
            }
        }
        if (mhp != null) {
            registeredMonsters.remove(mhp);
        }
    }

    public static boolean containsCreature(LivingEntity e) {
        for (CreatureHp hp : registeredMonsters) {
            if (e == hp.ent) {
                return true;
            }
        }
        return false;
    }

    public static void loadConfig(AncientRPG instance) {
        File newconf = new File(instance.getDataFolder().getPath() + File.separator + "creaturehpconfig.yml");
        if (newconf.exists()) {
            YamlConfiguration cfg = new YamlConfiguration();
            try {
                cfg.load(newconf);
                enabled = cfg.getBoolean(enabledConfig, enabled);
                enabledWorlds = cfg.getString(enabledWorldsConfig, enabledWorlds);
                minTimeBetweenAttacks = cfg.getInt(HpConfigMinTimeBetweenAttacks, minTimeBetweenAttacks);
                hpOfBat = cfg.getInt(configHpBat, hpOfBat);
                hpOfBlaze = cfg.getInt(configHpBlaze, hpOfBlaze);
                hpOfCaveSpider = cfg.getInt(configHpCaveSpider, hpOfCaveSpider);
                hpOfChicken = cfg.getInt(configHpChicken, hpOfChicken);
                hpOfCow = cfg.getInt(configHpCow, hpOfCow);
                hpOfCreeper = cfg.getInt(configHpCreeper, hpOfCreeper);
                hpOfEnderman = cfg.getInt(configHpEnderman, hpOfEnderman);
                hpOfEnderDragon = cfg.getInt(configHpEnderDragon, hpOfEnderDragon);
                hpOfGhast = cfg.getInt(configHpGhast, hpOfGhast);
                hpOfGiant = cfg.getInt(configHpGiant, hpOfGiant);
                hpOfIronGolem = cfg.getInt(configHpIronGolem, hpOfIronGolem);
                hpOfMagmaCubeTiny = cfg.getInt(configHpMagmaTiny, hpOfMagmaCubeTiny);
                hpOfMagmaCubeSmall = cfg.getInt(configHpMagmaCubeSmall, hpOfMagmaCubeSmall);
                hpOfMagmaCubeBig = cfg.getInt(configHpMagmaCubeBig, hpOfMagmaCubeBig);
                hpOfMooshroom = cfg.getInt(configHpMooshroom, hpOfMooshroom);
                hpOfOcelot = cfg.getInt(configHpOcelot, hpOfOcelot);
                hpOfPig = cfg.getInt(configHpPig, hpOfPig);
                hpOfWitch = cfg.getInt(configHpWitch, hpOfWitch);
                hpOfZombiePigman = cfg.getInt(configHpZombiePigman, hpOfZombiePigman);
                hpOfSheep = cfg.getInt(configHpSheep, hpOfSheep);
                hpOfHorse = cfg.getInt(configHpSheep, hpOfHorse);
                hpOfSilverfish = cfg.getInt(configHpSilverfish, hpOfSilverfish);
                hpOfSkeleton = cfg.getInt(configHpSkeleton, hpOfSkeleton);
                hpOfSlimeTiny = cfg.getInt(configHpSlimeTiny, hpOfSlimeTiny);
                hpOfSlimeSmall = cfg.getInt(configHpSlimeSmall, hpOfSlimeSmall);
                hpOfSlimeBig = cfg.getInt(configHpSlimeBig, hpOfSlimeBig);
                hpOfSnowGolem = cfg.getInt(configHpSnowGolem, hpOfSnowGolem);
                hpOfSpider = cfg.getInt(configHpSpider, hpOfSpider);
                hpOfSquid = cfg.getInt(configHpSquid, hpOfSquid);
                hpOfVillager = cfg.getInt(configHpVillager, hpOfVillager);
                hpOfWolf = cfg.getInt(configHpWolf, hpOfWolf);
                hpOfWolfTamed = cfg.getInt(configHpWolfTamed, hpOfWolfTamed);
                hpOfZombie = cfg.getInt(configHpZombie, hpOfZombie);
                hpOfBabyZombie = cfg.getInt(configHpBabyZombie, hpOfBabyZombie);
                hpOfZombieVillager = cfg.getInt(configHpZombieVillager, hpOfZombieVillager);
                hpOfZombieBabyVillager = cfg.getInt(configHpZombieBabyVillager, hpOfZombieBabyVillager);
                hpOfWither = cfg.getInt(configHpWither, hpOfWither);
                hpOfWitherSkeleton = cfg.getInt(configHpWitherSkeleton, hpOfWitherSkeleton);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            FileConfiguration cfg = instance.getConfig();
            enabled = cfg.getBoolean(enabledConfig, enabled);
            enabledWorlds = cfg.getString(enabledWorldsConfig, enabledWorlds);
            minTimeBetweenAttacks = cfg.getInt(HpConfigMinTimeBetweenAttacks, minTimeBetweenAttacks);
            hpOfBat = cfg.getInt(configHpBat, hpOfBat);
            hpOfBlaze = cfg.getInt(configHpBlaze, hpOfBlaze);
            hpOfCaveSpider = cfg.getInt(configHpCaveSpider, hpOfCaveSpider);
            hpOfChicken = cfg.getInt(configHpChicken, hpOfChicken);
            hpOfCow = cfg.getInt(configHpCow, hpOfCow);
            hpOfCreeper = cfg.getInt(configHpCreeper, hpOfCreeper);
            hpOfEnderman = cfg.getInt(configHpEnderman, hpOfEnderman);
            hpOfEnderDragon = cfg.getInt(configHpEnderDragon, hpOfEnderDragon);
            hpOfGhast = cfg.getInt(configHpGhast, hpOfGhast);
            hpOfGiant = cfg.getInt(configHpGiant, hpOfGiant);
            hpOfIronGolem = cfg.getInt(configHpIronGolem, hpOfIronGolem);
            hpOfMagmaCubeTiny = cfg.getInt(configHpMagmaTiny, hpOfMagmaCubeTiny);
            hpOfMagmaCubeSmall = cfg.getInt(configHpMagmaCubeSmall, hpOfMagmaCubeSmall);
            hpOfMagmaCubeBig = cfg.getInt(configHpMagmaCubeBig, hpOfMagmaCubeBig);
            hpOfMooshroom = cfg.getInt(configHpMooshroom, hpOfMooshroom);
            hpOfOcelot = cfg.getInt(configHpOcelot, hpOfOcelot);
            hpOfPig = cfg.getInt(configHpPig, hpOfPig);
            hpOfWitch = cfg.getInt(configHpWitch, hpOfWitch);
            hpOfZombiePigman = cfg.getInt(configHpZombiePigman, hpOfZombiePigman);
            hpOfSheep = cfg.getInt(configHpSheep, hpOfSheep);
            hpOfSilverfish = cfg.getInt(configHpSilverfish, hpOfSilverfish);
            hpOfSkeleton = cfg.getInt(configHpSkeleton, hpOfSkeleton);
            hpOfSlimeTiny = cfg.getInt(configHpSlimeTiny, hpOfSlimeTiny);
            hpOfSlimeSmall = cfg.getInt(configHpSlimeSmall, hpOfSlimeSmall);
            hpOfSlimeBig = cfg.getInt(configHpSlimeBig, hpOfSlimeBig);
            hpOfSnowGolem = cfg.getInt(configHpSnowGolem, hpOfSnowGolem);
            hpOfHorse = cfg.getInt(configHpSheep, hpOfHorse);
            hpOfSpider = cfg.getInt(configHpSpider, hpOfSpider);
            hpOfSquid = cfg.getInt(configHpSquid, hpOfSquid);
            hpOfVillager = cfg.getInt(configHpVillager, hpOfVillager);
            hpOfWolf = cfg.getInt(configHpWolf, hpOfWolf);
            hpOfWolfTamed = cfg.getInt(configHpWolfTamed, hpOfWolfTamed);
            hpOfZombie = cfg.getInt(configHpZombie, hpOfZombie);
            hpOfBabyZombie = cfg.getInt(configHpBabyZombie, hpOfBabyZombie);
            hpOfZombieVillager = cfg.getInt(configHpZombieVillager, hpOfZombieVillager);
            hpOfZombieBabyVillager = cfg.getInt(configHpZombieBabyVillager, hpOfZombieBabyVillager);
            hpOfWither = cfg.getInt(configHpWither, hpOfWither);
            hpOfWitherSkeleton = cfg.getInt(configHpWitherSkeleton, hpOfWitherSkeleton);
        }
    }

    public static void writeConfig(AncientRPG instance) {
        File newconf = new File(instance.getDataFolder().getPath() + File.separator + "creaturehpconfig.yml");
        if (!newconf.exists()) {
            try {
                newconf.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        YamlConfiguration cfg = new YamlConfiguration();
        try {
            cfg.set(enabledConfig, enabled);
            cfg.set(enabledWorldsConfig, enabledWorlds);
            cfg.set(HpConfigMinTimeBetweenAttacks, minTimeBetweenAttacks);
            cfg.set(configHpBat, hpOfBat);
            cfg.set(configHpBlaze, hpOfBlaze);
            cfg.set(configHpCaveSpider, hpOfCaveSpider);
            cfg.set(configHpChicken, hpOfChicken);
            cfg.set(configHpWither, hpOfWither);
            cfg.set(configHpCow, hpOfCow);
            cfg.set(configHpCreeper, hpOfCreeper);
            cfg.set(configHpEnderman, hpOfEnderman);
            cfg.set(configHpEnderDragon, hpOfEnderDragon);
            cfg.set(configHpGhast, hpOfGhast);
            cfg.set(configHpGiant, hpOfGiant);
            cfg.set(configHpIronGolem, hpOfIronGolem);
            cfg.set(configHpMagmaTiny, hpOfMagmaCubeTiny);
            cfg.set(configHpMagmaCubeSmall, hpOfMagmaCubeSmall);
            cfg.set(configHpMagmaCubeBig, hpOfMagmaCubeBig);
            cfg.set(configHpMooshroom, hpOfMooshroom);
            cfg.set(configHpOcelot, hpOfOcelot);
            cfg.set(configHpPig, hpOfPig);
            cfg.set(configHpZombiePigman, hpOfZombiePigman);
            cfg.set(configHpSheep, hpOfSheep);
            cfg.set(configHpSilverfish, hpOfSilverfish);
            cfg.set(configHpSkeleton, hpOfSkeleton);
            cfg.set(configHpSlimeTiny, hpOfSlimeTiny);
            cfg.set(configHpSlimeSmall, hpOfSlimeSmall);
            cfg.set(configHpSlimeBig, hpOfSlimeBig);
            cfg.set(configHpSnowGolem, hpOfSnowGolem);
            cfg.set(configHpSpider, hpOfSpider);
            cfg.set(configHpSquid, hpOfSquid);
            cfg.set(configHpHorse, hpOfHorse);
            cfg.set(configHpWitch, hpOfWitch);
            cfg.set(configHpVillager, hpOfVillager);
            cfg.set(configHpWolf, hpOfWolf);
            cfg.set(configHpWolfTamed, hpOfWolfTamed);
            cfg.set(configHpZombie, hpOfZombie);
            cfg.set(configHpBabyZombie, hpOfBabyZombie);
            cfg.set(configHpZombieVillager, hpOfZombieVillager);
            cfg.set(configHpZombieBabyVillager, hpOfZombieBabyVillager);
            cfg.set(configHpWitherSkeleton, hpOfWitherSkeleton);
            cfg.save(newconf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startCleaner() {
        Bukkit.getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, new Runnable() {

            @Override
            public void run() {
                HashSet<CreatureHp> scheduledRemovals = new HashSet<CreatureHp>();
                for (CreatureHp hp : CreatureHp.registeredMonsters) {
                    if (hp.ent.isDead()) {
                        scheduledRemovals.add(hp);
                    }
                }
                CreatureHp.registeredMonsters.removeAll(scheduledRemovals);
            }
        }, 1000);
    }

    public static double getMaxHpByEntity(LivingEntity e) {
        switch (e.getType()) {
            case BLAZE:
                return hpOfBlaze;
            case CAVE_SPIDER:
                return hpOfCaveSpider;
            case CHICKEN:
                return hpOfChicken;
            case COW:
                return hpOfCow;
            case CREEPER:
                return hpOfCreeper;
            case ENDERMAN:
                return hpOfEnderman;
            case ENDER_DRAGON:
                return hpOfEnderDragon;
            case GHAST:
                return hpOfGhast;
            case GIANT:
                return hpOfGiant;
            case IRON_GOLEM:
                return hpOfIronGolem;
            case MAGMA_CUBE:
                int size = ((MagmaCube) e).getSize();
                switch (size) {
                    case 1:
                        return hpOfMagmaCubeTiny;
                    case 2:
                        return hpOfMagmaCubeSmall;
                    case 3:
                    case 4:
                        return hpOfMagmaCubeBig;
                }
                if (size >= 3) {
                    return hpOfMagmaCubeBig;
                }
                return 0;
            case MUSHROOM_COW:
                return hpOfMooshroom;
            case OCELOT:
                return hpOfOcelot;
            case PIG:
                return hpOfPig;
            case PIG_ZOMBIE:
                return hpOfZombiePigman;
            case SHEEP:
                return hpOfSheep;
            case SILVERFISH:
                return hpOfSilverfish;
            case SKELETON:
                Skeleton sk = (Skeleton) e;
                if (sk.getSkeletonType() == SkeletonType.WITHER) {
                    return hpOfWitherSkeleton;
                } else {
                    return hpOfSkeleton;
                }
            case WITHER:
                return hpOfWither;
            case SLIME:
                int s = ((Slime) e).getSize();
                switch (s) {
                    case 1:
                        return hpOfSlimeTiny;
                    case 2:
                        return hpOfSlimeSmall;
                }
                if (s >= 3) {
                    return hpOfSlimeBig;
                }
            case SNOWMAN:
                return hpOfSnowGolem;
            case SPIDER:
                return hpOfSpider;
            case SQUID:
                return hpOfSquid;
            case WITCH:
                return hpOfWitch;
            case HORSE:
                return hpOfHorse;
            case VILLAGER:
                return hpOfVillager;
            case WOLF:
                if (((Wolf) e).isTamed()) {
                    return hpOfWolfTamed;
                } else {
                    return hpOfWolf;
                }
            case ZOMBIE:
                if (e instanceof Zombie) {
                    Zombie z = (Zombie) e;
                    if (z.isVillager()) {
                        if (z.isBaby()) {
                            return hpOfZombieBabyVillager;
                        } else {
                            return hpOfZombieVillager;
                        }
                    } else {
                        if (z.isBaby()) {
                            return hpOfBabyZombie;
                        } else {
                            return hpOfZombie;
                        }
                    }
                } else {
                    return e.getMaxHealth();
                }
            case BAT:
                return hpOfBat;
            default:
                break;
        }
        return e.getMaxHealth();
    }

    public static boolean isEnabled(World w) {
        if (!enabled) {
            return false;
        }
        if (enabledWorlds == null || enabledWorlds.equalsIgnoreCase("all")) {
            return true;
        }
        String[] worlds = enabledWorlds.split(",");
        for (String s : worlds) {
            if (s == null) {
                continue;
            }
            if (s.trim().equalsIgnoreCase(w.getName()) || s.equalsIgnoreCase("all")) {
                return true;
            }
        }
        return false;
    }
}