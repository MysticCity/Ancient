package com.ancientshores.AncientRPG.HP;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.PlayerData;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.util.Map.Entry;

public class DamageConverter {
    public static int standardhp = 600;

    // =======================================================================================
    // Damage config
    // =======================================================================================

    private static final String HpConfigEnabled = "HP.HPsystem enabled";
    private static boolean enabled = true;
    public static final String HpConfigWorlds = "HP.HPsystem enabled world";
    public static String worlds[] = new String[0];
    private static final String HpConfigStandardHP = "HP.HP of a user";
    private static final String HpConfigStarvation = "HP.damage of starvation";
    private static int damageOfStarvation = 50;
    private static final String HpConfigFall = "HP.damage of fall per block";
    private static int damageOfFall = 50;
    private static final String HpConfigFirstFallDamage = "HP.first block fall damage";
    private static int firstFallDamage = 3;
    private static final String HpConfigSpider = "HP.damage of a spider";
    private static int damageOfSpider = 100;
    private static final String HpConfigCaveSpider = "HP.damage of a cave spider";
    private static int damageOfCaveSpider = 100;
    private static int damageOfZombie = 150;
    private static final String HpConfigZombie = "HP.damage of a zombie";
    private static int damageOfBabyZombie = 100;
    private static final String HpConfigBabyZombie = "HP.damage of a baby zombie";
    private static int damageOfVillagerZombie = 150;
    private static final String HpConfigVillagerZombie = "HP.damage of a villager zombie";
    private static int damageOfVillagerBabyZombie = 100;
    private static final String HpConfigVillagerBabyZombie = "HP.damage of a villager baby zombie";
    private static int damageOfEnderDragon = 400;
    private static final String HpConfigEnderDragon = "HP.damage of an ender dragon";
    private static int damageOfPigZombie = 150;
    private static final String HpConfigPigZombie = "HP.damage of a pig zombie";
    private static int damageOfCreeper = 400;
    private static final String HpConfigCreeper = "HP.damage of a creeper";
    private static int damageOfGhast = 250;
    private static final String HpConfigGhast = "HP.damage of a ghast";
    private static int damageOfEnderman = 200;
    private static final String HpConfigIronGolem = "HP.damage of an iron golem";
    private static int damageOfIronGolem = 100;
    private static final String HpConfigSnowman = "HP.damage of a snowman";
    private static int damageOfSnowman = 100;
    private static final String HpConfigOcelot = "HP.damage of a ocelot";
    private static int damageOfOcelot = 100;
    private static final String HpConfigSilverfish = "HP.damage of a silverfish";
    private static int damageOfSilverfish = 40;
    private static final String HpConfigBlaze = "HP.damage of a blaze";
    private static int damageOfBlaze = 120;
    private static final String HpConfigEnderman = "HP.damage of an enderman";
    private static int damageOfGiant = 400;
    private static final String HpConfigGiant = "HP.damage of a giant";
    private static int damageOfWolf = 150;
    private static final String HpConfigWolf = "HP.damage of a wolf";
    private static int damageOfSlime = 100;
    private static final String HpConfigSlime = "HP.damage of a slime";
    private static int damageOfMagmaCube = 100;
    private static final String HpConfigMagmaCube = "HP.damage of a magma cube";
    private static int damageOfFire = 50;
    private static final String HpConfigFire = "HP.damage of fire";
    private static int damageOfCactus = 50;
    private static final String HpConfigCactus = "HP.damage of a Cactus per second";
    private static int damageOfTNT = 400;
    private static final String HpConfigtnt = "HP.damage of TNT";
    private static int damageOfLava = 250;
    private static final String HpConfigLava = "HP.damage of lava per second";
    private static int damageOfDrowning = 50;
    private static final String HpConfigDrowning = "HP.damage of drowning";
    private static int damageOfArrow = 50;
    private static final String HpConfigArrow = "HP.damage of an arrow";
    private static int damageOfSnowball = 10;
    private static final String HpConfigSnowball = "HP.damage of a snowball";
    private static int damageOfEgg = 10;
    private static final String HpConfigEgg = "HP.damage of an egg";
    private static int damageOfLightning = 50;
    private static final String HpConfigLightning = "HP.damage of a lightning";
    public static int damageOfFists = 50;
    private static final String HpConfigFists = "HP.damage of fists";
    private static int damageOfWoodSword = 80;
    private static final String HpConfigWoodSword = "HP.damage of a wood sword";
    private static int damageOfStoneSword = 130;
    private static final String HpConfigStoneSword = "HP.damage of a stone sword";
    private static int damageOfIronSword = 200;
    private static final String HpConfigIronSword = "HP.damage of an iron sword";
    private static int damageOfGoldSword = 250;
    private static final String HpConfigGoldSword = "HP.damage of a gold sword";
    private static int damageOfDiamondSword = 300;
    private static final String HpConfigDiamondSword = "HP.damage of a diamond sword";
    private static int damageOfWoodAxe = 40;
    private static final String HpConfigWoodAxe = "HP.damage of a wooden axe";
    private static int damageOfStoneAxe = 90;
    private static final String HpConfigStoneAxe = "HP.damage of a stone axe";
    private static int damageOfIronAxe = 140;
    private static final String HpConfigIronAxe = "HP.damage of an iron axe";
    private static int damageOfGoldAxe = 190;
    private static final String HpConfigGoldAxe = "HP.damage of a gold axe";
    private static int damageOfDiamondAxe = 230;
    private static final String HpConfigFireball = "HP.damage of a fireball";
    private static int damageOfFireball = 230;
    private static final String HpConfigSmallFireball = "HP.damage of a small fireball";
    private static int damageOfSmallFireball = 180;
    private static final String HpConfigWitherSkull = "HP.damage of a wither skull";
    private static int damageOfWitherSkull = 230;
    private static final String HpConfigDiamondAxe = "HP.damage of a diamond axe";
    public static int hpRegInterval = 3;
    private static final String HpConfigInterval = "HP.regeneration interval";
    public static int minFoodRegLevel = 14;
    private static final String HpConfigMinFoodLevel = "HP.minfoodlevel for regeneration";
    public static int hpReg = 20;
    private static final String HpConfigReg = "HP.regeneration per interval";
    private static int sharpnessExtraDamage = 20;
    private static final String HpConfigSharpness = "HP.extra damage of sharpness enchantment";
    private static int bowExtraDamage = 20;
    private static final String HpConfigBane = "HP.max extra damage of bane of arthropods and smite enchantment";
    private static int baneExtraDamage = 50;
    private static final String HpConfigBowDamage = "HP.extra damage of bow enchantments";
    private static int strenghtPotionExtraDamage = 100;
    private static final String HpConfigStrenghtExtraDamage = "HP.extra damage of strenght potion per tier";
    private static int weakenPotionLessDamage = 100;
    private static final String HpConfigWeakenLessDamage = "HP.less damage of weaken potion per tier";
    public static int regenPotionHp = 20;
    private static final String HpConfigRegenPotion = "HP.regeneration potion per tick per tier";
    public static int healPotionHp = 250;
    private static final String HpConfigHealPotion = "HP.heal per heal potion per tier";
    public static int harmPotionDamage = 250;
    private static final String HpConfigHarmPotion = "HP.damage per harm potion per tier";
    public static int poisonPotionDamage = 20;
    private static final String HpConfigPoisonPotion = "HP.posion potion damage per tick per tier";
    public static int witherDamage = 50;
    public static float displayDivider = 1;
    private static final String HpConfigWither = "HP.damage of wither effect";
    public static int minTimeBetweenAttacks = 500;
    private static final String HpConfigMinTimeBetweenAttacks = "HP.minimum time between each attack on a player in milliseconds";

    // =======================================================================================
    // Armor config
    // =======================================================================================

    // Diamond
    private static float ReductionOfDiamondHelmet = 12;
    private static final String ConfigReductionOfDiamondHelmet = "Armor.Diamond.Damage reduction of a diamond helmet in percentage points";
    private static float ReductionOfDiamondChestplate = 32;
    private static final String ConfigReductionOfDiamondChestplate = "Armor.Diamond.Damage reduction of a diamond chestplate in percentage points";
    private static float ReductionOfDiamondLeggins = 24;
    private static final String ConfigReductionOfDiamondLeggins = "Armor.Diamond.Damage reduction of a diamond leggings in percentage points";
    private static float ReductionOfDiamondBoots = 12;
    private static final String ConfigReductionOfDiamondBoots = "Armor.Diamond.Damage reduction of diamond boots in percentage points";
    // Iron
    private static float ReductionOfIronHelmet = 8;
    private static final String ConfigReductionOfIronHelmet = "Armor.Iron.Damage reduction of an iron helmet in percentage points";
    private static float ReductionOfIronChestplate = 24;
    private static final String ConfigReductionOfIronChestplate = "Armor.Iron.Damage reduction of an iron chestplate in percentage points";
    private static float ReductionOfIronLeggins = 20;
    private static final String ConfigReductionOfIronLeggins = "Armor.Iron.Damage reduction of an iron leggings in percentage points";
    private static float ReductionOfIronBoots = 8;
    private static final String ConfigReductionOfIronBoots = "Armor.Iron.Damage reduction of iron boots in percentage points";
    // Gold
    private static float ReductionOfGoldHelmet = 8;
    private static final String ConfigReductionOfGoldHelmet = "Armor.Gold.Damage reduction of a gold helmet in percentage points";
    private static float ReductionOfGoldChestplate = 20;
    private static final String ConfigReductionOfGoldChestplate = "Armor.Gold.Damage reduction of a gold chestplate in percentage points";
    private static float ReductionOfGoldLeggins = 12;
    private static final String ConfigReductionOfGoldLeggins = "Armor.Gold.Damage reduction of a gold leggings in percentage points";
    private static float ReductionOfGoldBoots = 4;
    private static final String ConfigReductionOfGoldBoots = "Armor.Gold.Damage reduction of gold boots in percentage points";
    // Leather
    private static float ReductionOfLeatherCap = 4;
    private static final String ConfigReductionOfLeatherCap = "Armor.Leather.Damage reduction of a leather cap in percentage points";
    private static float ReductionOfLeatherTunic = 12;
    private static final String ConfigReductionOfLeatherTunic = "Armor.Leather.Damage reduction of a leather tunic in percentage points";
    private static float ReductionOfLeatherPants = 8;
    private static final String ConfigReductionOfLeatherPants = "Armor.Leather.Damage reduction of a leather pants in percentage points";
    private static float ReductionOfLeatherBoots = 4;
    private static final String ConfigReductionOfLeatherBoots = "Armor.Leather.Damage reduction of leather boots in percentage points";
    // Chain
    private static float ReductionOfChainHelmet = 8;
    private static final String ConfigReductionOfChainHelmet = "Armor.Chain.Damage reduction of a chain helmet in percentage points";
    private static float ReductionOfChainChestplate = 20;
    private static final String ConfigReductionOfChainChestplate = "Armor.Chain.Damage reduction of a chain chestplate in percentage points";
    private static float ReductionOfChainLeggins = 16;
    private static final String ConfigReductionOfChainLeggins = "Armor.Chain.Damage reduction of a chain leggings in percentage points";
    private static float ReductionOfChainBoots = 4;
    private static final String ConfigReductionOfChainBoots = "Armor.Chain.Damage reduction of chain boots in percentage points";

    // =======================================================================================
    // Methods
    // =======================================================================================

    public static double convertDamageByEventForCreatures(EntityDamageEvent event) {
        CreatureHp hps = CreatureHp.getCreatureHpByEntity((LivingEntity) event.getEntity());
        switch (event.getCause()) {
            case FIRE_TICK:
                if (Math.abs(hps.lastFireDamage - System.currentTimeMillis()) >= 1000) {
                    hps.lastFireDamage = System.currentTimeMillis();
                    return damageOfFire;
                } else {
                    return 0;
                }
            case FIRE:
                if (Math.abs(hps.lastFireDamage - System.currentTimeMillis()) >= 1000) {
                    hps.lastFireDamage = System.currentTimeMillis();
                    return damageOfFire;
                } else {
                    return 0;
                }
            case MAGIC:
                if (event instanceof EntityDamageByEntityEvent) {
                    Entity e = ((EntityDamageByEntityEvent) event).getDamager();
                    if (e instanceof ThrownPotion) {
                        ThrownPotion p = (ThrownPotion) e;
                        for (PotionEffect pe : p.getEffects()) {
                            if (pe.getType().equals(PotionEffectType.HARM)) {
                                return DamageConverter.harmPotionDamage * (pe.getAmplifier() + 1);
                            }
                            if (pe.getType().equals(PotionEffectType.POISON)) {
                                return DamageConverter.poisonPotionDamage * (pe.getAmplifier() + 1);
                            }
                        }
                    } else if (e instanceof Potion) {
                        Potion p = (Potion) e;
                        for (PotionEffect pe : p.getEffects()) {
                            if (pe.getType().equals(PotionEffectType.HARM)) {
                                return DamageConverter.harmPotionDamage * (pe.getAmplifier() + 1);
                            }
                        }
                    }
                }
            case BLOCK_EXPLOSION:
                float percentb = ((float) event.getDamage() / (float) 65);
                return Math.round(damageOfTNT * percentb);
            case STARVATION:
                return damageOfStarvation;
            case FALL:
                if (event.getEntity().getFallDistance() >= firstFallDamage) {
                    return (damageOfFall * (Math.round(event.getEntity().getFallDistance()) - firstFallDamage));
                }
                return 0;
            case ENTITY_EXPLOSION:
                if (event instanceof EntityDamageByEntityEvent) {
                    EntityDamageByEntityEvent mEvent = (EntityDamageByEntityEvent) event;
                    if (mEvent.getDamager() instanceof WitherSkull) {
                        double percente = (event.getDamage() / (float) 49);
                        return damageOfWitherSkull * percente;
                    } else if (mEvent.getDamager() instanceof SmallFireball) {
                        double percente = (event.getDamage() / (float) 17);
                        return damageOfSmallFireball * percente;
                    } else if (mEvent.getDamager() instanceof Fireball) {
                        double percente = (event.getDamage() / (float) 7);
                        return damageOfFireball * percente;
                    }
                }
                float percente = ((float) event.getDamage() / (float) 49);
                return damageOfCreeper * percente;
            case DROWNING:
                return damageOfDrowning;
            case CONTACT:
                if (Math.abs(hps.lastCactusDamage - System.currentTimeMillis()) >= 1000) {
                    hps.lastCactusDamage = System.currentTimeMillis();
                    return damageOfCactus;
                } else {
                    return 0;
                }
            case LAVA:
                if (Math.abs(hps.lastLavaDamage - System.currentTimeMillis()) >= 1000) {
                    hps.lastLavaDamage = System.currentTimeMillis();
                    return damageOfLava;
                }
                break;
            case PROJECTILE:
                if (event instanceof EntityDamageByEntityEvent) {
                    EntityDamageByEntityEvent damageByEntityEvent = (EntityDamageByEntityEvent) event;
                    int id = damageByEntityEvent.getDamager().getType().getTypeId();
                    if (id == EntityType.ARROW.getTypeId()) {
                        if (((Arrow) damageByEntityEvent.getDamager()).getShooter() instanceof Player) {
                            return getBowDamage((Player) ((Arrow) damageByEntityEvent.getDamager()).getShooter());
                        }
                        return damageOfArrow;
                    } else if (id == EntityType.SNOWBALL.getTypeId()) {
                        return damageOfSnowball;
                    } else if (id == EntityType.EGG.getTypeId()) {
                        return damageOfEgg;
                    } else if (id == EntityType.FIREBALL.getTypeId()) {
                        return damageOfFireball;
                    } else if (id == EntityType.SMALL_FIREBALL.getTypeId()) {
                        return damageOfSmallFireball;
                    } else if (id == EntityType.WITHER_SKULL.getTypeId()) {
                        return damageOfWitherSkull;
                    }
                    break;
                }
            case LIGHTNING:
                return damageOfLightning;
            case CUSTOM:
                return event.getDamage();
            case POISON:
                if (event instanceof EntityDamageByEntityEvent) {
                    Entity e = ((EntityDamageByEntityEvent) event).getDamager();
                    if (e instanceof ThrownPotion) {
                        ThrownPotion p = (ThrownPotion) e;
                        for (PotionEffect pe : p.getEffects()) {
                            if (pe.getType().equals(PotionEffectType.HARM)) {
                                return DamageConverter.harmPotionDamage * (pe.getAmplifier() + 1);
                            }
                            if (pe.getType().equals(PotionEffectType.POISON)) {
                                return DamageConverter.poisonPotionDamage * (pe.getAmplifier() + 1);
                            }
                        }
                    }
                }
                return poisonPotionDamage;
            case WITHER:
                return witherDamage;
            case ENTITY_ATTACK: {
                if (Math.abs(hps.lastAttackDamage - System.currentTimeMillis()) < CreatureHp.minTimeBetweenAttacks) {
                    return 0;
                }
                hps.lastAttackDamage = System.currentTimeMillis();
                EntityDamageByEntityEvent damageByEntityEvent = (EntityDamageByEntityEvent) event;
                Entity c = damageByEntityEvent.getDamager();
                if (c instanceof Spider) {
                    return damageOfSpider;
                } else if (c instanceof CaveSpider) {
                    return damageOfCaveSpider;
                } else if (c instanceof Zombie) {
                    Zombie z = (Zombie) c;
                    if (z.isVillager()) {
                        if (z.isBaby()) {
                            return getDamageOfItem(((Zombie) c).getEquipment().getItemInHand().getType(),
                                    (LivingEntity) c, damageOfVillagerBabyZombie);
                        } else {
                            return getDamageOfItem(((Zombie) c).getEquipment().getItemInHand().getType(),
                                    (LivingEntity) c, damageOfVillagerZombie);
                        }
                    } else {
                        if (z.isBaby()) {
                            return getDamageOfItem(((Zombie) c).getEquipment().getItemInHand().getType(),
                                    (LivingEntity) c, damageOfBabyZombie);
                        } else {
                            return getDamageOfItem(((Zombie) c).getEquipment().getItemInHand().getType(),
                                    (LivingEntity) c, damageOfZombie);
                        }
                    }
                } else if (c instanceof EnderDragon) {
                    return damageOfEnderDragon;
                } else if (c instanceof Skeleton) {
                    return getDamageOfItem(((LivingEntity) c).getEquipment().getItemInHand().getType(),
                            (LivingEntity) c, 0);
                } else if (c instanceof PigZombie) {
                    return damageOfPigZombie;
                } else if (c instanceof Creeper) {
                    return Math.round((float) damageOfCreeper * ((float) event.getDamage() / (float) 49));
                } else if (c instanceof Ghast) {
                    return damageOfGhast;
                } else if (c instanceof Enderman) {
                    return damageOfEnderman;
                } else if (c instanceof Giant) {
                    return damageOfGiant;
                } else if (c instanceof Wolf) {
                    return damageOfWolf;
                } else if (c instanceof Slime) {
                    return damageOfSlime;
                } else if (c instanceof Ocelot) {
                    return damageOfOcelot;
                } else if (c instanceof Snowman) {
                    return damageOfSnowman;
                } else if (c instanceof IronGolem) {
                    return damageOfIronGolem;
                } else if (c instanceof Silverfish) {
                    return damageOfSilverfish;
                } else if (c instanceof MagmaCube) {
                    return damageOfMagmaCube;
                } else if (c instanceof Player) {
                    Player p = (Player) c;
                    switch (p.getItemInHand().getType()) {
                        case IRON_SWORD:
                            return getDamageOfEnchantementAndPotion(p, damageOfIronSword);
                        case STONE_SWORD:
                            return getDamageOfEnchantementAndPotion(p, damageOfStoneSword);
                        case DIAMOND_SWORD:
                            return getDamageOfEnchantementAndPotion(p, damageOfDiamondSword);
                        case GOLD_SWORD:
                            return getDamageOfEnchantementAndPotion(p, damageOfGoldSword);
                        case WOOD_SWORD:
                            return getDamageOfEnchantementAndPotion(p, damageOfWoodSword);
                        case IRON_AXE:
                            return getDamageOfEnchantementAndPotion(p, damageOfIronAxe);
                        case STONE_AXE:
                            return getDamageOfEnchantementAndPotion(p, damageOfStoneAxe);
                        case DIAMOND_AXE:
                            return getDamageOfEnchantementAndPotion(p, damageOfDiamondAxe);
                        case GOLD_AXE:
                            return getDamageOfEnchantementAndPotion(p, damageOfGoldAxe);
                        case WOOD_AXE:
                            return getDamageOfEnchantementAndPotion(p, damageOfWoodAxe);
                        default:
                            return getDamageOfEnchantementAndPotion(p, damageOfFists);
                    }
                }
            }
            default:
                break;
        }
        return 0;
    }

    public static double convertDamageByCreature(LivingEntity c, Player mPlayer, double defaultHP, EntityDamageEvent e) {
        PlayerData.getPlayerData(mPlayer.getName()).getHpsystem().lastAttackDamage = System.currentTimeMillis();
        if (c instanceof Spider) {
            return reduceDamageByArmor(damageOfSpider, mPlayer);
        } else if (c instanceof CaveSpider) {
            return reduceDamageByArmor(damageOfCaveSpider, mPlayer);
        } else if (c instanceof Zombie) {
            Zombie z = (Zombie) c;
            if (z.isVillager()) {
                if (z.isBaby()) {
                    return reduceDamageByArmor(
                            getDamageOfItem(c.getEquipment().getItemInHand().getType(), c, damageOfVillagerBabyZombie),
                            mPlayer);
                } else {
                    return reduceDamageByArmor(
                            getDamageOfItem(c.getEquipment().getItemInHand().getType(), c, damageOfVillagerZombie),
                            mPlayer);
                }
            } else {
                if (z.isBaby()) {
                    return reduceDamageByArmor(
                            getDamageOfItem(c.getEquipment().getItemInHand().getType(), c, damageOfBabyZombie),
                            mPlayer);
                } else {
                    return reduceDamageByArmor(
                            getDamageOfItem(c.getEquipment().getItemInHand().getType(), c, damageOfZombie), mPlayer);
                }
            }
        } else if (c instanceof EnderDragon) {
            return reduceDamageByArmor(damageOfEnderDragon, mPlayer);
        } else if (c instanceof PigZombie) {
            return reduceDamageByArmor(damageOfPigZombie, mPlayer);
        } else if (c instanceof Creeper) {
            if (e.getCause() != DamageCause.ENTITY_ATTACK) {
                return 0;
            }
            double distance = c.getLocation().distance(mPlayer.getLocation());
            if (distance <= 1) {
                return damageOfCreeper;
            }
            double damage = (damageOfCreeper) / Math.sqrt(distance);
            return damage;
        } else if (c instanceof Ghast) {
            return reduceDamageByArmor(damageOfGhast, mPlayer);
        } else if (c instanceof Enderman) {
            return reduceDamageByArmor(damageOfEnderman, mPlayer);
        } else if (c instanceof Giant) {
            return reduceDamageByArmor(damageOfGiant, mPlayer);
        } else if (c instanceof Wolf) {
            return reduceDamageByArmor(damageOfWolf, mPlayer);
        } else if (c instanceof Slime) {
            return reduceDamageByArmor(damageOfSlime, mPlayer);
        } else if (c instanceof Ocelot) {
            return reduceDamageByArmor(damageOfOcelot, mPlayer);
        } else if (c instanceof Snowman) {
            return reduceDamageByArmor(damageOfSnowman, mPlayer);
        } else if (c instanceof IronGolem) {
            return reduceDamageByArmor(damageOfIronGolem, mPlayer);
        } else if (c instanceof Silverfish) {
            return reduceDamageByArmor(damageOfSilverfish, mPlayer);
        } else if (c instanceof MagmaCube) {
            return reduceDamageByArmor(damageOfMagmaCube, mPlayer);
        } else if (c instanceof Skeleton) {
            return reduceDamageByArmor(getDamageOfItem(c.getEquipment().getItemInHand().getType(), c, 0), mPlayer);
        } else {
            return AncientRPGHP.getHpByMcDamage(mPlayer.getName(), defaultHP);
        }
    }

    public static boolean isWorldEnabled(Player p) {
        if (worlds.length == 0 || (worlds.length >= 1 && (worlds[0] == null || worlds[0].equals("")))) {
            return true;
        }
        for (String s : worlds) {
            if (p.getWorld().getName().equalsIgnoreCase(s)) {
                return true;
            }
        }
        return false;
    }

    public static double convertDamageByCause(DamageCause c, Player p, double damage, EntityDamageEvent event) {
        PlayerData pd = PlayerData.getPlayerData(p.getName());
        switch (c) {
            case FIRE_TICK:
                if (Math.abs(pd.lastFireDamage - System.currentTimeMillis()) >= 1000) {
                    pd.lastFireDamage = System.currentTimeMillis();
                    return reduceDamageByArmor(damageOfFire, p);
                } else {
                    return 0;
                }
            case FIRE:
                if (Math.abs(pd.lastFireDamage - System.currentTimeMillis()) >= 1000) {
                    pd.lastFireDamage = System.currentTimeMillis();
                    return reduceDamageByArmor(damageOfFire, p);
                } else {
                    return 0;
                }
            case BLOCK_EXPLOSION:
                float percentb = ((float) damage / (float) 65);
                return reduceDamageByArmor(Math.round(damageOfTNT * percentb), p);
            case STARVATION:
                return damageOfStarvation;
            case FALL:
                if (p.getFallDistance() >= firstFallDamage) {
                    return (damageOfFall * (Math.round(p.getFallDistance()) - firstFallDamage));
                }
                return 0;
            case ENTITY_EXPLOSION:
                float percente = ((float) event.getDamage() / (float) 49);
                if (event instanceof EntityDamageByEntityEvent) {
                    EntityDamageByEntityEvent mEvent = (EntityDamageByEntityEvent) event;
                    if (mEvent.getDamager() instanceof WitherSkull) {
                        return damageOfWitherSkull * percente;
                    } else if (mEvent.getDamager() instanceof SmallFireball) {
                        return damageOfSmallFireball * percente;
                    } else if (mEvent.getDamager() instanceof Fireball) {
                        return damageOfFireball * percente;
                    }
                }
                return Math.round(damageOfCreeper * percente);
            case DROWNING:
                return damageOfDrowning;
            case CONTACT:
                if (Math.abs(pd.lastCactusDamage - System.currentTimeMillis()) >= 1000) {
                    pd.lastCactusDamage = System.currentTimeMillis();
                    return reduceDamageByArmor(damageOfCactus, p);
                } else {
                    return 0;
                }
            case LAVA:
                if (Math.abs(pd.lastLavaDamage - System.currentTimeMillis()) >= 1000) {
                    pd.lastLavaDamage = System.currentTimeMillis();
                    return reduceDamageByArmor(damageOfLava, p);
                } else {
                    return Integer.MIN_VALUE;
                }
            case VOID:
                return 100000;
            case PROJECTILE:
                if (event instanceof EntityDamageByEntityEvent) {
                    EntityDamageByEntityEvent damageByEntityEvent = (EntityDamageByEntityEvent) event;
                    short id = damageByEntityEvent.getDamager().getType().getTypeId();
                    if (id == EntityType.ARROW.getTypeId()) {
                        if (((Arrow) damageByEntityEvent.getDamager()).getShooter() instanceof Player) {
                            return reduceDamageByArmor(
                                    getBowDamage((Player) ((Arrow) damageByEntityEvent.getDamager()).getShooter()), p);
                        }
                        return reduceDamageByArmor(damageOfArrow, p);
                    } else if (id == EntityType.SNOWBALL.getTypeId()) {
                        return reduceDamageByArmor(damageOfSnowball, p);
                    } else if (damageByEntityEvent.getDamager() instanceof Egg) {
                        return reduceDamageByArmor(damageOfEgg, p);
                    } else if (id == EntityType.FIREBALL.getTypeId()) {
                        return reduceDamageByArmor(damageOfFireball, p);
                    } else if (id == EntityType.SMALL_FIREBALL.getTypeId()) {
                        return reduceDamageByArmor(damageOfSmallFireball, p);
                    } else if (id == EntityType.WITHER_SKULL.getTypeId()) {
                        return reduceDamageByArmor(damageOfWitherSkull, p);
                    }
                    break;
                }
            case LIGHTNING:
                return reduceDamageByArmor(damageOfLightning, p);
            default:
                break;
        }
        return 0;
    }

    public static void writeConfig(AncientRPG plugin) {
        File newconf = new File(plugin.getDataFolder().getPath() + File.separator + "hpconfig.yml");
        if (!newconf.exists()) {
            try {
                newconf.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        YamlConfiguration yc = new YamlConfiguration();
        try {
            yc.set(HpConfigEnabled, enabled);
            if (yc.get(HpConfigWorlds) == null) {
                yc.set(HpConfigWorlds, "");
            }
            yc.set("HP.displayed hp divisor", displayDivider);
            yc.set(HpConfigStandardHP, standardhp);
            yc.set(HpConfigZombie, damageOfZombie);
            yc.set(HpConfigBabyZombie, damageOfBabyZombie);
            yc.set(HpConfigVillagerBabyZombie, damageOfVillagerBabyZombie);
            yc.set(HpConfigVillagerZombie, damageOfVillagerZombie);
            yc.set(HpConfigSpider, damageOfSpider);
            yc.set(HpConfigCaveSpider, damageOfCaveSpider);
            yc.set(HpConfigEnderDragon, damageOfEnderDragon);
            yc.set(HpConfigInterval, hpRegInterval);
            yc.set(HpConfigFireball, damageOfFireball);
            yc.set(HpConfigSmallFireball, damageOfSmallFireball);
            yc.set(HpConfigWitherSkull, damageOfWitherSkull);
            yc.set(HpConfigReg, hpReg);
            yc.set(HpConfigPigZombie, damageOfPigZombie);
            yc.set(HpConfigCreeper, damageOfCreeper);
            yc.set(HpConfigMinFoodLevel, minFoodRegLevel);
            yc.set(HpConfigFall, damageOfFall);
            yc.set(HpConfigFirstFallDamage, firstFallDamage);
            yc.set(HpConfigStarvation, damageOfStarvation);
            yc.set(HpConfigGhast, damageOfGhast);
            yc.set(HpConfigEnderman, damageOfEnderman);
            yc.set(HpConfigGiant, damageOfGiant);
            yc.set(HpConfigWolf, damageOfWolf);
            yc.set(HpConfigSlime, damageOfSlime);
            yc.set(HpConfigMagmaCube, damageOfMagmaCube);
            yc.set(HpConfigFire, damageOfFire);
            yc.set(HpConfigCactus, damageOfCactus);
            yc.set(HpConfigSilverfish, damageOfSilverfish);
            yc.set(HpConfigBlaze, damageOfBlaze);
            yc.set(HpConfigIronGolem, damageOfIronGolem);
            yc.set(HpConfigOcelot, damageOfOcelot);
            yc.set(HpConfigSnowman, damageOfSnowman);
            yc.set(HpConfigtnt, damageOfTNT);
            yc.set(HpConfigLava, damageOfLava);
            yc.set(HpConfigDrowning, damageOfDrowning);
            yc.set(HpConfigArrow, damageOfArrow);
            yc.set(HpConfigEgg, damageOfEgg);
            yc.set(HpConfigSnowball, damageOfSnowball);
            yc.set(HpConfigLightning, damageOfLightning);
            yc.set(HpConfigFists, damageOfFists);
            yc.set(HpConfigWoodSword, damageOfWoodSword);
            yc.set(HpConfigStoneSword, damageOfStoneSword);
            yc.set(HpConfigIronSword, damageOfIronSword);
            yc.set(HpConfigGoldSword, damageOfGoldSword);
            yc.set(HpConfigDiamondSword, damageOfDiamondSword);
            yc.set(HpConfigWoodAxe, damageOfWoodAxe);
            yc.set(HpConfigStoneAxe, damageOfStoneAxe);
            yc.set(HpConfigIronAxe, damageOfIronAxe);
            yc.set(HpConfigGoldAxe, damageOfGoldAxe);
            yc.set(HpConfigDiamondAxe, damageOfDiamondAxe);
            yc.set(HpConfigSharpness, sharpnessExtraDamage);
            yc.set(HpConfigBane, baneExtraDamage);
            yc.set(HpConfigBowDamage, bowExtraDamage);
            yc.set(HpConfigStrenghtExtraDamage, strenghtPotionExtraDamage);
            yc.set(HpConfigWeakenLessDamage, weakenPotionLessDamage);
            yc.set(HpConfigRegenPotion, regenPotionHp);
            yc.set(HpConfigHealPotion, healPotionHp);
            yc.set(HpConfigHarmPotion, harmPotionDamage);
            yc.set(HpConfigPoisonPotion, poisonPotionDamage);
            yc.set(HpConfigWither, witherDamage);
            yc.set(HpConfigMinTimeBetweenAttacks, minTimeBetweenAttacks);
            // Diamond
            yc.set(ConfigReductionOfDiamondHelmet, ReductionOfDiamondHelmet);
            yc.set(ConfigReductionOfDiamondChestplate, ReductionOfDiamondChestplate);
            yc.set(ConfigReductionOfDiamondLeggins, ReductionOfDiamondLeggins);
            yc.set(ConfigReductionOfDiamondBoots, ReductionOfDiamondBoots);
            // Iron
            yc.set(ConfigReductionOfIronHelmet, ReductionOfIronHelmet);
            yc.set(ConfigReductionOfIronChestplate, ReductionOfIronChestplate);
            yc.set(ConfigReductionOfIronLeggins, ReductionOfIronLeggins);
            yc.set(ConfigReductionOfIronBoots, ReductionOfIronBoots);
            // Gold
            yc.set(ConfigReductionOfGoldHelmet, ReductionOfGoldHelmet);
            yc.set(ConfigReductionOfGoldChestplate, ReductionOfGoldChestplate);
            yc.set(ConfigReductionOfGoldLeggins, ReductionOfGoldLeggins);
            yc.set(ConfigReductionOfGoldBoots, ReductionOfGoldBoots);
            // Chain
            yc.set(ConfigReductionOfChainHelmet, ReductionOfChainHelmet);
            yc.set(ConfigReductionOfChainChestplate, ReductionOfChainChestplate);
            yc.set(ConfigReductionOfChainLeggins, ReductionOfChainLeggins);
            yc.set(ConfigReductionOfChainBoots, ReductionOfChainBoots);
            // Leather
            yc.set(ConfigReductionOfLeatherCap, ReductionOfLeatherCap);
            yc.set(ConfigReductionOfLeatherTunic, ReductionOfLeatherTunic);
            yc.set(ConfigReductionOfLeatherPants, ReductionOfLeatherPants);
            yc.set(ConfigReductionOfLeatherBoots, ReductionOfLeatherBoots);
            yc.save(newconf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadConfig(AncientRPG plugin) {
        File newconf = new File(plugin.getDataFolder().getPath() + File.separator + "hpconfig.yml");
        if (newconf.exists()) {
            YamlConfiguration yc = new YamlConfiguration();
            try {
                yc.load(newconf);
                worlds = yc.getString(HpConfigWorlds, "").split(",");
                for (int i = 0; i < worlds.length; i++) {
                    worlds[i] = worlds[i].trim();
                }
                enabled = yc.getBoolean(HpConfigEnabled, enabled);
                standardhp = yc.getInt(HpConfigStandardHP, standardhp);
                displayDivider = (float) yc.getDouble("HP.displayed hp divisor", displayDivider);
                damageOfZombie = yc.getInt(HpConfigZombie, damageOfZombie);
                damageOfBabyZombie = yc.getInt(HpConfigBabyZombie, damageOfBabyZombie);
                damageOfVillagerBabyZombie = yc.getInt(HpConfigVillagerBabyZombie, damageOfVillagerBabyZombie);
                damageOfVillagerZombie = yc.getInt(HpConfigVillagerZombie, damageOfVillagerZombie);
                damageOfFireball = yc.getInt(HpConfigFireball, damageOfFireball);
                damageOfSmallFireball = yc.getInt(HpConfigSmallFireball, damageOfSmallFireball);
                damageOfWitherSkull = yc.getInt(HpConfigWitherSkull, damageOfWitherSkull);
                damageOfSpider = yc.getInt(HpConfigSpider, damageOfSpider);
                damageOfEnderDragon = yc.getInt(HpConfigEnderDragon, damageOfEnderDragon);
                damageOfPigZombie = yc.getInt(HpConfigPigZombie, damageOfPigZombie);
                damageOfCreeper = yc.getInt(HpConfigCreeper, damageOfCreeper);
                damageOfEgg = yc.getInt(HpConfigEgg, damageOfEgg);
                damageOfSnowball = yc.getInt(HpConfigSnowball, damageOfSnowball);
                damageOfGhast = yc.getInt(HpConfigGhast, damageOfGhast);
                damageOfEnderman = yc.getInt(HpConfigEnderman, damageOfEnderman);
                damageOfGiant = yc.getInt(HpConfigGiant, damageOfGiant);
                damageOfWolf = yc.getInt(HpConfigWolf, damageOfWolf);
                damageOfSlime = yc.getInt(HpConfigSlime, damageOfSlime);
                damageOfMagmaCube = yc.getInt(HpConfigMagmaCube, damageOfMagmaCube);
                damageOfFall = yc.getInt(HpConfigFall, damageOfFall);
                firstFallDamage = yc.getInt(HpConfigFirstFallDamage, firstFallDamage);
                minFoodRegLevel = yc.getInt(HpConfigMinFoodLevel, minFoodRegLevel);
                damageOfStarvation = yc.getInt(HpConfigStarvation, damageOfStarvation);
                damageOfFire = yc.getInt(HpConfigFire, damageOfFire);
                baneExtraDamage = yc.getInt(HpConfigBane, baneExtraDamage);
                damageOfCactus = yc.getInt(HpConfigCactus, damageOfCactus);
                damageOfSilverfish = yc.getInt(HpConfigSilverfish, damageOfSilverfish);
                damageOfIronGolem = yc.getInt(HpConfigIronGolem, damageOfIronGolem);
                damageOfOcelot = yc.getInt(HpConfigOcelot, damageOfOcelot);
                damageOfSnowman = yc.getInt(HpConfigSnowman, damageOfSnowman);
                damageOfBlaze = yc.getInt(HpConfigBlaze, damageOfBlaze);
                damageOfCaveSpider = yc.getInt(HpConfigCaveSpider, damageOfCaveSpider);
                damageOfTNT = yc.getInt(HpConfigtnt, damageOfTNT);
                damageOfLava = yc.getInt(HpConfigLava, damageOfLava);
                damageOfDrowning = yc.getInt(HpConfigDrowning, damageOfDrowning);
                damageOfArrow = yc.getInt(HpConfigArrow, damageOfArrow);
                damageOfLightning = yc.getInt(HpConfigLightning, damageOfLightning);
                damageOfFists = yc.getInt(HpConfigFists, damageOfFists);
                damageOfStoneSword = yc.getInt(HpConfigStoneSword, damageOfStoneSword);
                damageOfIronSword = yc.getInt(HpConfigIronSword, damageOfIronSword);
                damageOfGoldSword = yc.getInt(HpConfigGoldSword, damageOfGoldSword);
                damageOfDiamondSword = yc.getInt(HpConfigDiamondSword, damageOfDiamondSword);
                damageOfWoodSword = yc.getInt(HpConfigWoodSword, damageOfWoodSword);
                sharpnessExtraDamage = yc.getInt(HpConfigSharpness, sharpnessExtraDamage);
                bowExtraDamage = yc.getInt(HpConfigBowDamage, bowExtraDamage);
                strenghtPotionExtraDamage = yc.getInt(HpConfigStrenghtExtraDamage, strenghtPotionExtraDamage);
                weakenPotionLessDamage = yc.getInt(HpConfigWeakenLessDamage, weakenPotionLessDamage);
                regenPotionHp = yc.getInt(HpConfigRegenPotion, regenPotionHp);
                healPotionHp = yc.getInt(HpConfigHealPotion, healPotionHp);
                harmPotionDamage = yc.getInt(HpConfigHarmPotion, harmPotionDamage);
                poisonPotionDamage = yc.getInt(HpConfigPoisonPotion, poisonPotionDamage);
                witherDamage = yc.getInt(HpConfigWither, witherDamage);
                damageOfWoodAxe = yc.getInt(HpConfigWoodAxe, damageOfWoodAxe);
                damageOfStoneAxe = yc.getInt(HpConfigStoneAxe, damageOfStoneAxe);
                damageOfIronAxe = yc.getInt(HpConfigIronAxe, damageOfIronAxe);
                damageOfGoldAxe = yc.getInt(HpConfigGoldAxe, damageOfGoldAxe);
                damageOfDiamondAxe = yc.getInt(HpConfigDiamondAxe, damageOfDiamondAxe);
                minTimeBetweenAttacks = yc.getInt(HpConfigMinTimeBetweenAttacks, minTimeBetweenAttacks);
                // Diamond
                ReductionOfDiamondHelmet = (float) yc.getDouble(ConfigReductionOfDiamondHelmet,
                        ReductionOfDiamondHelmet);
                ReductionOfDiamondChestplate = (float) yc.getDouble(ConfigReductionOfDiamondChestplate,
                        ReductionOfDiamondChestplate);
                ReductionOfDiamondLeggins = (float) yc.getDouble(ConfigReductionOfDiamondLeggins,
                        ReductionOfDiamondLeggins);
                ReductionOfDiamondBoots = (float) yc.getDouble(ConfigReductionOfDiamondBoots, ReductionOfDiamondBoots);
                // Iron
                ReductionOfIronHelmet = (float) yc.getDouble(ConfigReductionOfIronHelmet, ReductionOfIronHelmet);
                ReductionOfIronChestplate = (float) yc.getDouble(ConfigReductionOfIronChestplate,
                        ReductionOfIronChestplate);
                ReductionOfIronLeggins = (float) yc.getDouble(ConfigReductionOfIronLeggins, ReductionOfIronLeggins);
                ReductionOfIronBoots = (float) yc.getDouble(ConfigReductionOfIronBoots, ReductionOfIronBoots);
                // Gold
                ReductionOfGoldHelmet = (float) yc.getDouble(ConfigReductionOfGoldHelmet, ReductionOfGoldHelmet);
                ReductionOfGoldChestplate = (float) yc.getDouble(ConfigReductionOfGoldChestplate,
                        ReductionOfGoldChestplate);
                ReductionOfGoldLeggins = (float) yc.getDouble(ConfigReductionOfGoldLeggins, ReductionOfGoldLeggins);
                ReductionOfGoldBoots = (float) yc.getDouble(ConfigReductionOfGoldBoots, ReductionOfGoldBoots);
                // Chain
                ReductionOfChainHelmet = (float) yc.getDouble(ConfigReductionOfChainHelmet, ReductionOfChainHelmet);
                ReductionOfChainChestplate = (float) yc.getDouble(ConfigReductionOfChainChestplate,
                        ReductionOfChainChestplate);
                ReductionOfChainLeggins = (float) yc.getDouble(ConfigReductionOfChainLeggins, ReductionOfChainLeggins);
                ReductionOfChainBoots = (float) yc.getDouble(ConfigReductionOfChainBoots, ReductionOfChainBoots);
                // Leather
                ReductionOfLeatherCap = (float) yc.getDouble(ConfigReductionOfLeatherCap, ReductionOfLeatherCap);
                ReductionOfLeatherTunic = (float) yc.getDouble(ConfigReductionOfLeatherTunic, ReductionOfLeatherTunic);
                ReductionOfLeatherPants = (float) yc.getDouble(ConfigReductionOfLeatherPants, ReductionOfLeatherPants);
                ReductionOfLeatherBoots = (float) yc.getDouble(ConfigReductionOfLeatherBoots, ReductionOfLeatherBoots);
                hpRegInterval = (int) yc.getDouble(HpConfigInterval, hpRegInterval);
                hpReg = yc.getInt(HpConfigReg, hpReg);
                for (PlayerData pd : PlayerData.playerData) {
                    pd.getHpsystem().maxhp = standardhp;
                }
            } catch (Exception e) {

            }
        } else {
            worlds = plugin.getConfig().getString(HpConfigWorlds, "").split(",");
            for (int i = 0; i < worlds.length; i++) {
                worlds[i] = worlds[i].trim();
            }
            enabled = plugin.getConfig().getBoolean(HpConfigEnabled, enabled);
            standardhp = plugin.getConfig().getInt(HpConfigStandardHP, standardhp);
            damageOfZombie = plugin.getConfig().getInt(HpConfigZombie, damageOfZombie);
            damageOfBabyZombie = plugin.getConfig().getInt(HpConfigBabyZombie, damageOfBabyZombie);
            damageOfVillagerBabyZombie = plugin.getConfig().getInt(HpConfigVillagerBabyZombie,
                    damageOfVillagerBabyZombie);
            damageOfVillagerZombie = plugin.getConfig().getInt(HpConfigVillagerZombie, damageOfVillagerZombie);
            damageOfFireball = plugin.getConfig().getInt(HpConfigFireball, damageOfFireball);
            damageOfSmallFireball = plugin.getConfig().getInt(HpConfigSmallFireball, damageOfSmallFireball);
            damageOfWitherSkull = plugin.getConfig().getInt(HpConfigWitherSkull, damageOfWitherSkull);
            damageOfSpider = plugin.getConfig().getInt(HpConfigSpider, damageOfSpider);
            damageOfEnderDragon = plugin.getConfig().getInt(HpConfigEnderDragon, damageOfEnderDragon);
            damageOfPigZombie = plugin.getConfig().getInt(HpConfigPigZombie, damageOfPigZombie);
            damageOfCreeper = plugin.getConfig().getInt(HpConfigCreeper, damageOfCreeper);
            damageOfEgg = plugin.getConfig().getInt(HpConfigEgg, damageOfEgg);
            damageOfSnowball = plugin.getConfig().getInt(HpConfigSnowball, damageOfSnowball);
            damageOfGhast = plugin.getConfig().getInt(HpConfigGhast, damageOfGhast);
            damageOfEnderman = plugin.getConfig().getInt(HpConfigEnderman, damageOfEnderman);
            damageOfGiant = plugin.getConfig().getInt(HpConfigGiant, damageOfGiant);
            damageOfWolf = plugin.getConfig().getInt(HpConfigWolf, damageOfWolf);
            damageOfSlime = plugin.getConfig().getInt(HpConfigSlime, damageOfSlime);
            damageOfMagmaCube = plugin.getConfig().getInt(HpConfigMagmaCube, damageOfMagmaCube);
            damageOfFall = plugin.getConfig().getInt(HpConfigFall, damageOfFall);
            firstFallDamage = plugin.getConfig().getInt(HpConfigFirstFallDamage, firstFallDamage);
            minFoodRegLevel = plugin.getConfig().getInt(HpConfigMinFoodLevel, minFoodRegLevel);
            damageOfStarvation = plugin.getConfig().getInt(HpConfigStarvation, damageOfStarvation);
            damageOfFire = plugin.getConfig().getInt(HpConfigFire, damageOfFire);
            baneExtraDamage = plugin.getConfig().getInt(HpConfigBane, baneExtraDamage);
            damageOfCactus = plugin.getConfig().getInt(HpConfigCactus, damageOfCactus);
            damageOfSilverfish = plugin.getConfig().getInt(HpConfigSilverfish, damageOfSilverfish);
            damageOfIronGolem = plugin.getConfig().getInt(HpConfigIronGolem, damageOfIronGolem);
            damageOfOcelot = plugin.getConfig().getInt(HpConfigOcelot, damageOfOcelot);
            damageOfSnowman = plugin.getConfig().getInt(HpConfigSnowman, damageOfSnowman);
            damageOfBlaze = plugin.getConfig().getInt(HpConfigBlaze, damageOfBlaze);
            damageOfCaveSpider = plugin.getConfig().getInt(HpConfigCaveSpider, damageOfCaveSpider);
            damageOfTNT = plugin.getConfig().getInt(HpConfigtnt, damageOfTNT);
            damageOfLava = plugin.getConfig().getInt(HpConfigLava, damageOfLava);
            damageOfDrowning = plugin.getConfig().getInt(HpConfigDrowning, damageOfDrowning);
            damageOfArrow = plugin.getConfig().getInt(HpConfigArrow, damageOfArrow);
            damageOfLightning = plugin.getConfig().getInt(HpConfigLightning, damageOfLightning);
            damageOfFists = plugin.getConfig().getInt(HpConfigFists, damageOfFists);
            damageOfStoneSword = plugin.getConfig().getInt(HpConfigStoneSword, damageOfStoneSword);
            damageOfIronSword = plugin.getConfig().getInt(HpConfigIronSword, damageOfIronSword);
            damageOfGoldSword = plugin.getConfig().getInt(HpConfigGoldSword, damageOfGoldSword);
            damageOfDiamondSword = plugin.getConfig().getInt(HpConfigDiamondSword, damageOfDiamondSword);
            damageOfWoodSword = plugin.getConfig().getInt(HpConfigWoodSword, damageOfWoodSword);
            sharpnessExtraDamage = plugin.getConfig().getInt(HpConfigSharpness, sharpnessExtraDamage);
            bowExtraDamage = plugin.getConfig().getInt(HpConfigBowDamage, bowExtraDamage);
            strenghtPotionExtraDamage = plugin.getConfig().getInt(HpConfigStrenghtExtraDamage,
                    strenghtPotionExtraDamage);
            weakenPotionLessDamage = plugin.getConfig().getInt(HpConfigWeakenLessDamage, weakenPotionLessDamage);
            regenPotionHp = plugin.getConfig().getInt(HpConfigRegenPotion, regenPotionHp);
            healPotionHp = plugin.getConfig().getInt(HpConfigHealPotion, healPotionHp);
            harmPotionDamage = plugin.getConfig().getInt(HpConfigHarmPotion, harmPotionDamage);
            poisonPotionDamage = plugin.getConfig().getInt(HpConfigPoisonPotion, poisonPotionDamage);
            witherDamage = plugin.getConfig().getInt(HpConfigWither, witherDamage);
            damageOfWoodAxe = plugin.getConfig().getInt(HpConfigWoodAxe, damageOfWoodAxe);
            damageOfStoneAxe = plugin.getConfig().getInt(HpConfigStoneAxe, damageOfStoneAxe);
            damageOfIronAxe = plugin.getConfig().getInt(HpConfigIronAxe, damageOfIronAxe);
            damageOfGoldAxe = plugin.getConfig().getInt(HpConfigGoldAxe, damageOfGoldAxe);
            damageOfDiamondAxe = plugin.getConfig().getInt(HpConfigDiamondAxe, damageOfDiamondAxe);
            minTimeBetweenAttacks = plugin.getConfig().getInt(HpConfigMinTimeBetweenAttacks, minTimeBetweenAttacks);
            // Diamond
            ReductionOfDiamondHelmet = (float) plugin.getConfig().getDouble(ConfigReductionOfDiamondHelmet,
                    ReductionOfDiamondHelmet);
            ReductionOfDiamondChestplate = (float) plugin.getConfig().getDouble(ConfigReductionOfDiamondChestplate,
                    ReductionOfDiamondChestplate);
            ReductionOfDiamondLeggins = (float) plugin.getConfig().getDouble(ConfigReductionOfDiamondLeggins,
                    ReductionOfDiamondLeggins);
            ReductionOfDiamondBoots = (float) plugin.getConfig().getDouble(ConfigReductionOfDiamondBoots,
                    ReductionOfDiamondBoots);
            // Iron
            ReductionOfIronHelmet = (float) plugin.getConfig().getDouble(ConfigReductionOfIronHelmet,
                    ReductionOfIronHelmet);
            ReductionOfIronChestplate = (float) plugin.getConfig().getDouble(ConfigReductionOfIronChestplate,
                    ReductionOfIronChestplate);
            ReductionOfIronLeggins = (float) plugin.getConfig().getDouble(ConfigReductionOfIronLeggins,
                    ReductionOfIronLeggins);
            ReductionOfIronBoots = (float) plugin.getConfig().getDouble(ConfigReductionOfIronBoots,
                    ReductionOfIronBoots);
            // Gold
            ReductionOfGoldHelmet = (float) plugin.getConfig().getDouble(ConfigReductionOfGoldHelmet,
                    ReductionOfGoldHelmet);
            ReductionOfGoldChestplate = (float) plugin.getConfig().getDouble(ConfigReductionOfGoldChestplate,
                    ReductionOfGoldChestplate);
            ReductionOfGoldLeggins = (float) plugin.getConfig().getDouble(ConfigReductionOfGoldLeggins,
                    ReductionOfGoldLeggins);
            ReductionOfGoldBoots = (float) plugin.getConfig().getDouble(ConfigReductionOfGoldBoots,
                    ReductionOfGoldBoots);
            // Chain
            ReductionOfChainHelmet = (float) plugin.getConfig().getDouble(ConfigReductionOfChainHelmet,
                    ReductionOfChainHelmet);
            ReductionOfChainChestplate = (float) plugin.getConfig().getDouble(ConfigReductionOfChainChestplate,
                    ReductionOfChainChestplate);
            ReductionOfChainLeggins = (float) plugin.getConfig().getDouble(ConfigReductionOfChainLeggins,
                    ReductionOfChainLeggins);
            ReductionOfChainBoots = (float) plugin.getConfig().getDouble(ConfigReductionOfChainBoots,
                    ReductionOfChainBoots);
            // Leather
            ReductionOfLeatherCap = (float) plugin.getConfig().getDouble(ConfigReductionOfLeatherCap,
                    ReductionOfLeatherCap);
            ReductionOfLeatherTunic = (float) plugin.getConfig().getDouble(ConfigReductionOfLeatherTunic,
                    ReductionOfLeatherTunic);
            ReductionOfLeatherPants = (float) plugin.getConfig().getDouble(ConfigReductionOfLeatherPants,
                    ReductionOfLeatherPants);
            ReductionOfLeatherBoots = (float) plugin.getConfig().getDouble(ConfigReductionOfLeatherBoots,
                    ReductionOfLeatherBoots);
            hpRegInterval = (int) plugin.getConfig().getDouble(HpConfigInterval, hpRegInterval);
            hpReg = plugin.getConfig().getInt(HpConfigReg, hpReg);
            for (PlayerData pd : PlayerData.playerData) {
                pd.getHpsystem().maxhp = standardhp;
            }
        }
    }

    public static double reduceDamageByArmor(double basedamage, LivingEntity attackedEntity) {
        return basedamage;//(float) basedamage * (1 - getArmorReduction(attackedEntity) / 100);
    }

    private static float getArmorReduction(LivingEntity attackedEntity) {
        float reduction = 0;
        if (attackedEntity.getEquipment().getHelmet() != null) {
            switch (attackedEntity.getEquipment().getHelmet().getType()) {
                case DIAMOND_HELMET:
                    reduction += ReductionOfDiamondHelmet;
                    break;
                case IRON_HELMET:
                    reduction += ReductionOfIronHelmet;
                    break;
                case GOLD_HELMET:
                    reduction += ReductionOfGoldHelmet;
                    break;
                case LEATHER_HELMET:
                    reduction += ReductionOfLeatherCap;
                    break;
                case CHAINMAIL_HELMET:
                    reduction += ReductionOfChainHelmet;
                    break;
                default:
                    break;
            }
        }
        if (attackedEntity.getEquipment().getChestplate() != null) {
            switch (attackedEntity.getEquipment().getChestplate().getType()) {
                case DIAMOND_CHESTPLATE:
                    reduction += ReductionOfDiamondChestplate;
                    break;
                case IRON_CHESTPLATE:
                    reduction += ReductionOfIronChestplate;
                    break;
                case GOLD_CHESTPLATE:
                    reduction += ReductionOfGoldChestplate;
                    break;
                case LEATHER_CHESTPLATE:
                    reduction += ReductionOfLeatherTunic;
                    break;
                case CHAINMAIL_CHESTPLATE:
                    reduction += ReductionOfChainChestplate;
                    break;
                default:
                    break;
            }
        }
        if (attackedEntity.getEquipment().getLeggings() != null) {
            switch (attackedEntity.getEquipment().getLeggings().getType()) {
                case DIAMOND_LEGGINGS:
                    reduction += ReductionOfDiamondLeggins;
                    break;
                case IRON_LEGGINGS:
                    reduction += ReductionOfIronLeggins;
                    break;
                case GOLD_LEGGINGS:
                    reduction += ReductionOfGoldLeggins;
                    break;
                case LEATHER_LEGGINGS:
                    reduction += ReductionOfLeatherPants;
                    break;
                case CHAINMAIL_LEGGINGS:
                    reduction += ReductionOfChainLeggins;
                    break;
                default:
                    break;
            }
        }
        if (attackedEntity.getEquipment().getBoots() != null) {
            switch (attackedEntity.getEquipment().getBoots().getType()) {
                case DIAMOND_BOOTS:
                    reduction += ReductionOfDiamondBoots;
                    break;
                case IRON_BOOTS:
                    reduction += ReductionOfIronBoots;
                    break;
                case GOLD_BOOTS:
                    reduction += ReductionOfGoldBoots;
                    break;
                case LEATHER_BOOTS:
                    reduction += ReductionOfLeatherBoots;
                    break;
                case CHAINMAIL_BOOTS:
                    reduction += ReductionOfChainBoots;
                    break;
                default:
                    break;
            }
        }
        return reduction;
    }

    private static float getArmorReduction(Player attackedEntity) {
        float reduction = 0;
        if (attackedEntity.getInventory().getHelmet() != null) {
            switch (attackedEntity.getInventory().getHelmet().getType()) {
                case DIAMOND_HELMET:
                    reduction += ReductionOfDiamondHelmet;
                    break;
                case IRON_HELMET:
                    reduction += ReductionOfIronHelmet;
                    break;
                case GOLD_HELMET:
                    reduction += ReductionOfGoldHelmet;
                    break;
                case LEATHER_HELMET:
                    reduction += ReductionOfLeatherCap;
                    break;
                case CHAINMAIL_HELMET:
                    reduction += ReductionOfChainHelmet;
                    break;
                default:
                    break;
            }
        }
        if (attackedEntity.getInventory().getChestplate() != null) {
            switch (attackedEntity.getInventory().getChestplate().getType()) {
                case DIAMOND_CHESTPLATE:
                    reduction += ReductionOfDiamondChestplate;
                    break;
                case IRON_CHESTPLATE:
                    reduction += ReductionOfIronChestplate;
                    break;
                case GOLD_CHESTPLATE:
                    reduction += ReductionOfGoldChestplate;
                    break;
                case LEATHER_CHESTPLATE:
                    reduction += ReductionOfLeatherTunic;
                    break;
                case CHAINMAIL_CHESTPLATE:
                    reduction += ReductionOfChainChestplate;
                    break;
                default:
                    break;
            }
        }
        if (attackedEntity.getInventory().getLeggings() != null) {
            switch (attackedEntity.getInventory().getLeggings().getType()) {
                case DIAMOND_LEGGINGS:
                    reduction += ReductionOfDiamondLeggins;
                    break;
                case IRON_LEGGINGS:
                    reduction += ReductionOfIronLeggins;
                    break;
                case GOLD_LEGGINGS:
                    reduction += ReductionOfGoldLeggins;
                    break;
                case LEATHER_LEGGINGS:
                    reduction += ReductionOfLeatherPants;
                    break;
                case CHAINMAIL_LEGGINGS:
                    reduction += ReductionOfChainLeggins;
                    break;
                default:
                    break;
            }
        }
        if (attackedEntity.getInventory().getBoots() != null) {
            switch (attackedEntity.getInventory().getBoots().getType()) {
                case DIAMOND_BOOTS:
                    reduction += ReductionOfDiamondBoots;
                    break;
                case IRON_BOOTS:
                    reduction += ReductionOfIronBoots;
                    break;
                case GOLD_BOOTS:
                    reduction += ReductionOfGoldBoots;
                    break;
                case LEATHER_BOOTS:
                    reduction += ReductionOfLeatherBoots;
                    break;
                case CHAINMAIL_BOOTS:
                    reduction += ReductionOfChainBoots;
                    break;
                default:
                    break;
            }
        }
        return reduction;
    }

    public static double getHpByMcDamage(String name, double hp) {
        AncientRPGHP hpinstance = PlayerData.getPlayerData(name).getHpsystem();
        return (hpinstance.maxhp * (hp / 20));
    }

    public static float getDamageOfItem(Material iteminhand, LivingEntity attacker, int basedamage) {
        switch (iteminhand) {
            case IRON_SWORD:
                return getDamageOfEnchantementAndPotion(attacker, damageOfIronSword);
            case STONE_SWORD:
                return getDamageOfEnchantementAndPotion(attacker, damageOfStoneSword);
            case DIAMOND_SWORD:
                return getDamageOfEnchantementAndPotion(attacker, damageOfDiamondSword);
            case GOLD_SWORD:
                return getDamageOfEnchantementAndPotion(attacker, damageOfGoldSword);
            case WOOD_SWORD:
                return getDamageOfEnchantementAndPotion(attacker, damageOfWoodSword);
            case IRON_AXE:
                return getDamageOfEnchantementAndPotion(attacker, damageOfIronAxe);
            case STONE_AXE:
                return getDamageOfEnchantementAndPotion(attacker, damageOfStoneAxe);
            case DIAMOND_AXE:
                return getDamageOfEnchantementAndPotion(attacker, damageOfDiamondAxe);
            case GOLD_AXE:
                return getDamageOfEnchantementAndPotion(attacker, damageOfGoldAxe);
            case WOOD_AXE:
                return getDamageOfEnchantementAndPotion(attacker, damageOfWoodAxe);
            default:
                break;
        }
        return basedamage;
    }

    public static double getPlayerDamageOfItem(Player attackedEntity, Material iteminhand, LivingEntity attacker, int basedamage) {
        switch (iteminhand) {
            case IRON_SWORD:
                return reduceDamageByArmor(getDamageOfEnchantementAndPotion(attacker, damageOfIronSword),
                        attackedEntity);
            case STONE_SWORD:
                return reduceDamageByArmor(getDamageOfEnchantementAndPotion(attacker, damageOfStoneSword),
                        attackedEntity);
            case DIAMOND_SWORD:
                return reduceDamageByArmor(getDamageOfEnchantementAndPotion(attacker, damageOfDiamondSword),
                        attackedEntity);
            case GOLD_SWORD:
                return reduceDamageByArmor(getDamageOfEnchantementAndPotion(attacker, damageOfGoldSword),
                        attackedEntity);
            case WOOD_SWORD:
                return reduceDamageByArmor(getDamageOfEnchantementAndPotion(attacker, damageOfWoodSword),
                        attackedEntity);
            case IRON_AXE:
                return reduceDamageByArmor(getDamageOfEnchantementAndPotion(attacker, damageOfIronAxe), attackedEntity);
            case STONE_AXE:
                return reduceDamageByArmor(getDamageOfEnchantementAndPotion(attacker, damageOfStoneAxe),
                        attackedEntity);
            case DIAMOND_AXE:
                return reduceDamageByArmor(getDamageOfEnchantementAndPotion(attacker, damageOfDiamondAxe),
                        attackedEntity);
            case GOLD_AXE:
                return reduceDamageByArmor(getDamageOfEnchantementAndPotion(attacker, damageOfGoldAxe), attackedEntity);
            case WOOD_AXE:
                return reduceDamageByArmor(getDamageOfEnchantementAndPotion(attacker, damageOfWoodAxe), attackedEntity);
            default:
                break;
        }
        return reduceDamageByArmor(basedamage, attackedEntity);
    }

    public static double getBowDamage(Player attacker, Player mPlayer) {
        int basedamage = damageOfArrow;
        for (Entry<Enchantment, Integer> entry : attacker.getItemInHand().getEnchantments().entrySet()) {
            if (entry.getKey().equals(EnchantmentWrapper.ARROW_DAMAGE)) {
                basedamage += bowExtraDamage * entry.getValue();
            }
        }
        return reduceDamageByArmor(basedamage, mPlayer);
    }

    public static float getBowDamage(Player attacker) {
        int basedamage = damageOfArrow;
        for (Entry<Enchantment, Integer> entry : attacker.getItemInHand().getEnchantments().entrySet()) {
            if (entry.getKey().equals(EnchantmentWrapper.ARROW_DAMAGE)) {
                basedamage += bowExtraDamage * entry.getValue();
            }
        }
        return basedamage;
    }

    public static float getDamageOfEnchantementAndPotion(LivingEntity attacker, float basedamage) {
        for (PotionEffect pe : attacker.getActivePotionEffects()) {
            if (pe.getType().equals(PotionEffectType.INCREASE_DAMAGE)) {
                basedamage += strenghtPotionExtraDamage * pe.getAmplifier();
            }
            if (pe.getType().equals(PotionEffectType.WEAKNESS)) {
                basedamage -= weakenPotionLessDamage * pe.getAmplifier();
            }
        }
        for (Entry<Enchantment, Integer> entry : attacker.getEquipment().getItemInHand().getEnchantments().entrySet()) {
            if (entry.getKey().getId() == Enchantment.DAMAGE_ALL.getId()) {
                basedamage += sharpnessExtraDamage * entry.getValue();
            }
            if (entry.getKey().getId() == Enchantment.DAMAGE_ARTHROPODS.getId()) {
                basedamage += baneExtraDamage * entry.getValue();
            }
        }
        return basedamage;
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static boolean isEnabled(World w) {
        if (worlds.length == 0 || (worlds.length >= 1 && (worlds[0] == null || worlds[0].equals(""))) && enabled) {
            return true;
        }
        for (String s : worlds) {
            if (s == null) {
                continue;
            }
            if ((w.getName().equalsIgnoreCase(s) || s.equalsIgnoreCase("all")) && enabled) {
                return true;
            }
        }
        return false;
    }
}
