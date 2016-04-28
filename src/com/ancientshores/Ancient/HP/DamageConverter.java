package com.ancientshores.Ancient.HP;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.PlayerData;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Giant;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.Spider;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.entity.WitherSkull;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class DamageConverter
{
  private static FileConfiguration config;
  
  public static double convertDamageByEventForCreatures(EntityDamageEvent event)
  {
    CreatureHp hps = CreatureHp.getCreatureHpByEntity((LivingEntity)event.getEntity());
    switch (event.getCause())
    {
    case FIRE_TICK: 
    case FIRE: 
      if (Math.abs(hps.lastFireDamage - System.currentTimeMillis()) >= 1000L)
      {
        hps.lastFireDamage = System.currentTimeMillis();
        return config.getDouble("HP.damage of fire");
      }
      return 0.0D;
    case MAGIC: 
      if ((event instanceof EntityDamageByEntityEvent))
      {
        Entity e = ((EntityDamageByEntityEvent)event).getDamager();
        if ((e instanceof ThrownPotion))
        {
          ThrownPotion p = (ThrownPotion)e;
          for (PotionEffect pe : p.getEffects())
          {
            if (pe.getType().equals(PotionEffectType.HARM)) {
              return config.getDouble("HP.damage per harm potion per tier") * (pe.getAmplifier() + 1);
            }
            if (pe.getType().equals(PotionEffectType.POISON)) {
              return config.getDouble("HP.posion potion damage per tick per tier") * (pe.getAmplifier() + 1);
            }
          }
        }
        else if ((e instanceof Potion))
        {
          Potion p = (Potion)e;
          for (PotionEffect pe : p.getEffects()) {
            if (pe.getType().equals(PotionEffectType.HARM)) {
              return config.getDouble("HP.damage per harm potion per tier") * (pe.getAmplifier() + 1);
            }
          }
        }
      }
    case POISON: 
      if ((event instanceof EntityDamageByEntityEvent))
      {
        Entity e = ((EntityDamageByEntityEvent)event).getDamager();
        if ((e instanceof ThrownPotion))
        {
          ThrownPotion p = (ThrownPotion)e;
          for (PotionEffect pe : p.getEffects())
          {
            if (pe.getType().equals(PotionEffectType.HARM)) {
              return config.getDouble("HP.damage per harm potion per tier") * (pe.getAmplifier() + 1);
            }
            if (pe.getType().equals(PotionEffectType.POISON)) {
              return config.getDouble("HP.posion potion damage per tick per tier") * (pe.getAmplifier() + 1);
            }
          }
        }
      }
      return config.getDouble("HP.posion potion damage per tick per tier");
    case BLOCK_EXPLOSION: 
      double percentb = event.getDamage() / 65.0D;
      return Math.round(config.getDouble("HP.damage of TNT") * percentb);
    case STARVATION: 
      return config.getDouble("HP.damage of starvation");
    case FALL: 
      if (event.getEntity().getFallDistance() >= config.getDouble("HP.first block fall damage")) {
        return config.getDouble("HP.damage of fall per block") * (Math.round(event.getEntity().getFallDistance()) - config.getDouble("HP.first block fall damage"));
      }
      return 0.0D;
    case ENTITY_EXPLOSION: 
      if ((event instanceof EntityDamageByEntityEvent))
      {
        EntityDamageByEntityEvent mEvent = (EntityDamageByEntityEvent)event;
        if ((mEvent.getDamager() instanceof WitherSkull))
        {
          double percente = event.getDamage() / 49.0D;
          return config.getDouble("HP.damage of a wither skull") * percente;
        }
        if ((mEvent.getDamager() instanceof SmallFireball))
        {
          double percente = event.getDamage() / 17.0D;
          return config.getDouble("HP.damage of a small fireball") * percente;
        }
        if ((mEvent.getDamager() instanceof Fireball))
        {
          double percente = event.getDamage() / 7.0D;
          return config.getDouble("HP.damage of a fireball") * percente;
        }
      }
      double percente = event.getDamage() / 49.0D;
      return config.getDouble("HP.damage of a creeper") * percente;
    case DROWNING: 
      return config.getDouble("HP.damage of drowning");
    case CONTACT: 
      if (Math.abs(hps.lastCactusDamage - System.currentTimeMillis()) >= 1000L)
      {
        hps.lastCactusDamage = System.currentTimeMillis();
        return config.getDouble("HP.damage of a Cactus per second");
      }
      return 0.0D;
    case LAVA: 
      if (Math.abs(hps.lastLavaDamage - System.currentTimeMillis()) >= 1000L)
      {
        hps.lastLavaDamage = System.currentTimeMillis();
        return config.getDouble("HP.damage of lava per second");
      }
      break;
    case PROJECTILE: 
      if ((event instanceof EntityDamageByEntityEvent))
      {
        EntityDamageByEntityEvent damageByEntityEvent = (EntityDamageByEntityEvent)event;
        int id = damageByEntityEvent.getDamager().getType().getTypeId();
        if (id == EntityType.ARROW.getTypeId())
        {
          if ((((Arrow)damageByEntityEvent.getDamager()).getShooter() instanceof Player)) {
            return getBowDamage((Player)((Arrow)damageByEntityEvent.getDamager()).getShooter());
          }
          return config.getDouble("HP.damage of an arrow");
        }
        if (id == EntityType.SNOWBALL.getTypeId()) {
          return config.getDouble("HP.damage of a snowball");
        }
        if (id == EntityType.EGG.getTypeId()) {
          return config.getDouble("HP.damage of an egg");
        }
        if (id == EntityType.FIREBALL.getTypeId()) {
          return config.getDouble("HP.damage of a fireball");
        }
        if (id == EntityType.SMALL_FIREBALL.getTypeId()) {
          return config.getDouble("HP.damage of a small fireball");
        }
        if (id != EntityType.WITHER_SKULL.getTypeId()) {
          break label1705;
        }
        return config.getDouble("HP.damage of a wither skull");
      }
    case LIGHTNING: 
      return config.getDouble("HP.damage of a lightning");
    case CUSTOM: 
      return event.getDamage();
    case WITHER: 
      return config.getDouble("HP.damage of wither effect");
    case ENTITY_ATTACK: 
      if (Math.abs(hps.lastAttackDamage - System.currentTimeMillis()) < CreatureHp.getMinimumTimeBetweenAttacks()) {
        return 0.0D;
      }
      hps.lastAttackDamage = System.currentTimeMillis();
      EntityDamageByEntityEvent damageByEntityEvent = (EntityDamageByEntityEvent)event;
      Entity c = damageByEntityEvent.getDamager();
      if ((c instanceof CaveSpider)) {
        return config.getDouble("HP.damage of a cave spider");
      }
      if ((c instanceof Spider)) {
        return config.getDouble("HP.damage of a spider");
      }
      if ((c instanceof PigZombie)) {
        return config.getDouble("HP.damage of a pig zombie");
      }
      if ((c instanceof EnderDragon)) {
        return config.getDouble("HP.damage of an ender dragon");
      }
      if ((c instanceof Skeleton)) {
        return getDamageOfItem((LivingEntity)c, 0.0D);
      }
      if ((c instanceof Creeper)) {
        return config.getDouble("HP.damage of a creeper") * event.getDamage() / 49.0D;
      }
      if ((c instanceof Ghast)) {
        return config.getDouble("HP.damage of a ghast");
      }
      if ((c instanceof Enderman)) {
        return config.getDouble("HP.damage of an enderman");
      }
      if ((c instanceof Giant)) {
        return config.getDouble("HP.damage of a giant");
      }
      if ((c instanceof Wolf)) {
        return config.getDouble("HP.damage of a wolf");
      }
      if ((c instanceof MagmaCube)) {
        return config.getDouble("HP.damage of a magma cube");
      }
      if ((c instanceof Slime)) {
        return config.getDouble("HP.damage of a slime");
      }
      if ((c instanceof Ocelot)) {
        return config.getDouble("HP.damage of a ocelot");
      }
      if ((c instanceof Snowman)) {
        return config.getDouble("HP.damage of a snowman");
      }
      if ((c instanceof IronGolem)) {
        return config.getDouble("HP.damage of an iron golem");
      }
      if ((c instanceof Silverfish)) {
        return config.getDouble("HP.damage of a silverfish");
      }
      if ((c instanceof Zombie))
      {
        Zombie z = (Zombie)c;
        if (z.isVillager())
        {
          if (z.isBaby()) {
            return getDamageOfItem((LivingEntity)c, config.getDouble("HP.damage of a villager baby zombie"));
          }
          return getDamageOfItem((LivingEntity)c, config.getDouble("HP.damage of a villager zombie"));
        }
        if (z.isBaby()) {
          return getDamageOfItem((LivingEntity)c, config.getDouble("HP.damage of a baby zombie"));
        }
        return getDamageOfItem((LivingEntity)c, config.getDouble("HP.damage of a zombie"));
      }
      if ((c instanceof Player))
      {
        Player p = (Player)c;
        switch (p.getItemInHand().getType())
        {
        case IRON_SWORD: 
          return getDamageOfEnchantementAndPotion(p, config.getDouble("HP.damage of an iron sword"));
        case STONE_SWORD: 
          return getDamageOfEnchantementAndPotion(p, config.getDouble("HP.damage of a stone sword"));
        case DIAMOND_SWORD: 
          return getDamageOfEnchantementAndPotion(p, config.getDouble("HP.damage of a diamond sword"));
        case GOLD_SWORD: 
          return getDamageOfEnchantementAndPotion(p, config.getDouble("HP.damage of a gold sword"));
        case WOOD_SWORD: 
          return getDamageOfEnchantementAndPotion(p, config.getDouble("HP.damage of a wood sword"));
        case IRON_AXE: 
          return getDamageOfEnchantementAndPotion(p, config.getDouble("HP.damage of an iron axe"));
        case STONE_AXE: 
          return getDamageOfEnchantementAndPotion(p, config.getDouble("HP.damage of a stone axe"));
        case DIAMOND_AXE: 
          return getDamageOfEnchantementAndPotion(p, config.getDouble("HP.damage of a diamond axe"));
        case GOLD_AXE: 
          return getDamageOfEnchantementAndPotion(p, config.getDouble("HP.damage of a gold axe"));
        case WOOD_AXE: 
          return getDamageOfEnchantementAndPotion(p, config.getDouble("HP.damage of a wood axe"));
        }
        return getDamageOfEnchantementAndPotion(p, config.getDouble("HP.damage of fists"));
      }
      break;
    }
    label1705:
    return 0.0D;
  }
  
  public static double convertDamageByCreature(LivingEntity c, Player mPlayer, double defaultHP, EntityDamageEvent e)
  {
    PlayerData.getPlayerData(mPlayer.getUniqueId()).getHpsystem().lastAttackDamage = System.currentTimeMillis();
    if ((c instanceof CaveSpider)) {
      return config.getDouble("HP.damage of a cave spider");
    }
    if ((c instanceof Spider)) {
      return config.getDouble("HP.damage of a spider");
    }
    if ((c instanceof PigZombie)) {
      return config.getDouble("HP.damage of a pig zombie");
    }
    if ((c instanceof EnderDragon)) {
      return config.getDouble("HP.damage of an ender dragon");
    }
    if ((c instanceof Ghast)) {
      return config.getDouble("HP.damage of a ghat");
    }
    if ((c instanceof Enderman)) {
      return config.getDouble("HP.damage of an enderman");
    }
    if ((c instanceof Giant)) {
      return config.getDouble("HP.damage of a giant");
    }
    if ((c instanceof Wolf)) {
      return config.getDouble("HP.damage of a wolf");
    }
    if ((c instanceof MagmaCube)) {
      return config.getDouble("HP.damage of a magma cube");
    }
    if ((c instanceof Slime)) {
      return config.getDouble("HP.damage of a slime");
    }
    if ((c instanceof Ocelot)) {
      return config.getDouble("HP.damage of a ocelot");
    }
    if ((c instanceof Snowman)) {
      return config.getDouble("HP.damage of a snowman");
    }
    if ((c instanceof IronGolem)) {
      return config.getDouble("HP.damage of an iron golem");
    }
    if ((c instanceof Silverfish)) {
      return config.getDouble("HP.damage of a silverfish");
    }
    if ((c instanceof Skeleton)) {
      return getDamageOfItem(c, 0.0D);
    }
    if ((c instanceof Zombie))
    {
      Zombie z = (Zombie)c;
      if (z.isVillager())
      {
        if (z.isBaby()) {
          return getDamageOfItem(c, config.getDouble("HP.damage of a villager baby zombie"));
        }
        return getDamageOfItem(c, config.getDouble("HP.damage of a villager zombie"));
      }
      if (z.isBaby()) {
        return getDamageOfItem(c, config.getDouble("HP.damage of a baby zombie"));
      }
      return getDamageOfItem(c, config.getDouble("HP.damage of a zombie"));
    }
    if ((c instanceof Creeper))
    {
      if (e.getCause() != EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
        return 0.0D;
      }
      double distance = c.getLocation().distance(mPlayer.getLocation());
      if (distance <= 1.0D) {
        return config.getDouble("HP.damage of a creeper");
      }
      return config.getDouble("HP.damage of a creeper") / distance;
    }
    return AncientHP.getHpByMinecraftDamage(mPlayer.getUniqueId(), defaultHP);
  }
  
  public static boolean isEnabledInWorld(World w)
  {
    if (w == null) {
      return false;
    }
    if (!isEnabled()) {
      return false;
    }
    List<String> worlds = config.getStringList("HP.HPsystem enabled world");
    if ((worlds == null) || (worlds.size() == 0)) {
      return true;
    }
    if ((worlds.size() == 1) && (((String)worlds.get(0)).equalsIgnoreCase(""))) {
      return true;
    }
    for (String s : worlds) {
      if (w.getName().equalsIgnoreCase(s)) {
        return true;
      }
    }
    return false;
  }
  
  public static double convertDamageByCause(EntityDamageEvent.DamageCause c, Player p, double damage, EntityDamageEvent event)
  {
    PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
    switch (c)
    {
    case FIRE_TICK: 
    case FIRE: 
      if (Math.abs(pd.lastFireDamage - System.currentTimeMillis()) >= 1000L)
      {
        pd.lastFireDamage = System.currentTimeMillis();
        return config.getDouble("HP.damage of fire");
      }
      return 0.0D;
    case BLOCK_EXPLOSION: 
      double percentb = damage / 65.0D;
      return Math.round(config.getDouble("HP.damage of tnt") * percentb);
    case STARVATION: 
      return config.getDouble("HP.damage of starvation");
    case FALL: 
      if (p.getFallDistance() >= config.getDouble("HP.first block fall damage")) {
        return config.getDouble("HP.damage of fall per block") * (Math.round(p.getFallDistance()) - config.getDouble("HP.first block fall damage"));
      }
      return 0.0D;
    case ENTITY_EXPLOSION: 
      double percente = event.getDamage() / 49.0D;
      if ((event instanceof EntityDamageByEntityEvent))
      {
        EntityDamageByEntityEvent mEvent = (EntityDamageByEntityEvent)event;
        if ((mEvent.getDamager() instanceof WitherSkull)) {
          return config.getDouble("HP.damage of a wither skull") * percente;
        }
        if ((mEvent.getDamager() instanceof SmallFireball)) {
          return config.getDouble("HP.damage of a small fireball") * percente;
        }
        if ((mEvent.getDamager() instanceof Fireball)) {
          return config.getDouble("HP.damage of fireball") * percente;
        }
      }
      return Math.round(config.getDouble("HP.damage of a creeper") * percente);
    case DROWNING: 
      return config.getDouble("HP.damage of drowning");
    case CONTACT: 
      if (Math.abs(pd.lastCactusDamage - System.currentTimeMillis()) >= 1000L)
      {
        pd.lastCactusDamage = System.currentTimeMillis();
        return config.getDouble("HP.damage of a Cactus per second");
      }
      return 0.0D;
    case LAVA: 
      if (Math.abs(pd.lastLavaDamage - System.currentTimeMillis()) >= 1000L)
      {
        pd.lastLavaDamage = System.currentTimeMillis();
        return config.getDouble("HP.damage of lava per second");
      }
      return 0.0D;
    case VOID: 
      return Double.MAX_VALUE;
    case PROJECTILE: 
      if ((event instanceof EntityDamageByEntityEvent))
      {
        EntityDamageByEntityEvent damageByEntityEvent = (EntityDamageByEntityEvent)event;
        short id = damageByEntityEvent.getDamager().getType().getTypeId();
        if (id == EntityType.ARROW.getTypeId())
        {
          if ((((Arrow)damageByEntityEvent.getDamager()).getShooter() instanceof Player)) {
            return getBowDamage((Player)((Arrow)damageByEntityEvent.getDamager()).getShooter());
          }
          return config.getDouble("HP.damage of an arrow");
        }
        if (id == EntityType.SNOWBALL.getTypeId()) {
          return config.getDouble("HP.damage of a snowball");
        }
        if ((damageByEntityEvent.getDamager() instanceof Egg)) {
          return config.getDouble("HP.damage of an egg");
        }
        if (id == EntityType.FIREBALL.getTypeId()) {
          return config.getDouble("HP.damage of a fireball");
        }
        if (id == EntityType.SMALL_FIREBALL.getTypeId()) {
          return config.getDouble("HP.damage of a small fireball");
        }
        if (id != EntityType.WITHER_SKULL.getTypeId()) {
          break label620;
        }
        return config.getDouble("HP.damage of a wither skull");
      }
    case LIGHTNING: 
      return config.getDouble("HP.damage of a lightning");
    }
    label620:
    return 0.0D;
  }
  
  public static void writeConfig()
  {
    File file = new File(Ancient.plugin.getDataFolder().getPath() + File.separator + "hpconfig.yml");
    try
    {
      config.save(file);
    }
    catch (IOException ex)
    {
      ex.printStackTrace();
    }
  }
  
  public static void loadConfig()
  {
    File file = new File(Ancient.plugin.getDataFolder().getPath() + File.separator + "hpconfig.yml");
    if (!file.exists()) {
      Ancient.plugin.saveResource("hpconfig.yml", true);
    }
    config = YamlConfiguration.loadConfiguration(file);
    config.set("Armor", null);
    PlayerData pd;
    for (Iterator i$ = PlayerData.playerData.iterator(); i$.hasNext(); pd.getHpsystem().maxhp = config.getDouble("HP.HP of a user")) {
      pd = (PlayerData)i$.next();
    }
  }
  
  public static double reduceDamageByArmor(double basedamage, LivingEntity attackedEntity)
  {
    return basedamage * (1.0D - Armor.getArmorReduction(attackedEntity) / 100.0D);
  }
  
  public static double getHpByMcDamage(UUID uuid, double hp)
  {
    AncientHP hpinstance = PlayerData.getPlayerData(uuid).getHpsystem();
    return hpinstance.getMaxHP() * (hp / 20.0D);
  }
  
  public static double getDamageOfItem(LivingEntity attacker, double d)
  {
    Material iteminhand = attacker.getEquipment().getItemInHand().getType();
    switch (iteminhand)
    {
    case IRON_SWORD: 
      return getDamageOfEnchantementAndPotion(attacker, config.getDouble("HP.damage of an iron sword"));
    case STONE_SWORD: 
      return getDamageOfEnchantementAndPotion(attacker, config.getDouble("HP.damage of a stone sword"));
    case DIAMOND_SWORD: 
      return getDamageOfEnchantementAndPotion(attacker, config.getDouble("HP.damage of a diamond sword"));
    case GOLD_SWORD: 
      return getDamageOfEnchantementAndPotion(attacker, config.getDouble("HP.damage of a gold sword"));
    case WOOD_SWORD: 
      return getDamageOfEnchantementAndPotion(attacker, config.getDouble("HP.damage of a wood sword"));
    case IRON_AXE: 
      return getDamageOfEnchantementAndPotion(attacker, config.getDouble("HP.damage of an iron axe"));
    case STONE_AXE: 
      return getDamageOfEnchantementAndPotion(attacker, config.getDouble("HP.damage of a stone axe"));
    case DIAMOND_AXE: 
      return getDamageOfEnchantementAndPotion(attacker, config.getDouble("HP.damage of a diamond axe"));
    case GOLD_AXE: 
      return getDamageOfEnchantementAndPotion(attacker, config.getDouble("HP.damage of a gold axe"));
    case WOOD_AXE: 
      return getDamageOfEnchantementAndPotion(attacker, config.getDouble("HP.damage of a wooden axe"));
    }
    return d;
  }
  
  public static double getPlayerDamageOfItem(Player attackedEntity, Material iteminhand, LivingEntity attacker, double basedamage)
  {
    switch (iteminhand)
    {
    case IRON_SWORD: 
      return getDamageOfEnchantementAndPotion(attacker, config.getDouble("HP.damage of an iron sword"));
    case STONE_SWORD: 
      return getDamageOfEnchantementAndPotion(attacker, config.getDouble("HP.damage of a stone sword"));
    case DIAMOND_SWORD: 
      return getDamageOfEnchantementAndPotion(attacker, config.getDouble("HP.damage of a diamond sword"));
    case GOLD_SWORD: 
      return getDamageOfEnchantementAndPotion(attacker, config.getDouble("HP.damage of a gold sword"));
    case WOOD_SWORD: 
      return getDamageOfEnchantementAndPotion(attacker, config.getDouble("HP.damage of a wood sword"));
    case IRON_AXE: 
      return getDamageOfEnchantementAndPotion(attacker, config.getDouble("HP.damage of an iron axe"));
    case STONE_AXE: 
      return getDamageOfEnchantementAndPotion(attacker, config.getDouble("HP.damage of a stone axe"));
    case DIAMOND_AXE: 
      return getDamageOfEnchantementAndPotion(attacker, config.getDouble("HP.damage of a diamond axe"));
    case GOLD_AXE: 
      return getDamageOfEnchantementAndPotion(attacker, config.getDouble("HP.damage of a gold axe"));
    case WOOD_AXE: 
      return getDamageOfEnchantementAndPotion(attacker, config.getDouble("HP.damage of a wood axe"));
    }
    return basedamage;
  }
  
  public static double getBowDamage(Player attacker)
  {
    double basedamage = config.getDouble("HP.damage of an arrow");
    for (Map.Entry<Enchantment, Integer> entry : attacker.getItemInHand().getEnchantments().entrySet()) {
      if (((Enchantment)entry.getKey()).equals(EnchantmentWrapper.ARROW_DAMAGE)) {
        basedamage += config.getDouble("HP.extra damage of bow enchantments") * ((Integer)entry.getValue()).intValue();
      }
    }
    return basedamage;
  }
  
  public static double getDamageOfEnchantementAndPotion(LivingEntity attacker, double d)
  {
    for (PotionEffect pe : attacker.getActivePotionEffects())
    {
      if (pe.getType().getName().equalsIgnoreCase(PotionEffectType.INCREASE_DAMAGE.getName())) {
        d += config.getDouble("HP.extra damage of strenght potion per tier") * (pe.getAmplifier() + 1);
      }
      if (pe.getType().getName().equalsIgnoreCase(PotionEffectType.WEAKNESS.getName())) {
        d -= config.getDouble("HP.less damage of weaken potion per tier") * (pe.getAmplifier() + 1);
      }
    }
    d += config.getDouble("HP.extra damage of sharpness enchantment") * attacker.getEquipment().getItemInHand().getEnchantmentLevel(Enchantment.DAMAGE_ALL);
    d += config.getDouble("HP.max extra damage of bane of arthropods and smite enchantment") * attacker.getEquipment().getItemInHand().getEnchantmentLevel(Enchantment.DAMAGE_ARTHROPODS);
    
    return d;
  }
  
  public static boolean isEnabled()
  {
    return config.getBoolean("HP.HPsystem enabled");
  }
  
  public static double getStandardHP()
  {
    return config.getDouble("HP.HP of a user");
  }
  
  public static double getHPRegeneration()
  {
    return config.getDouble("HP.regeneration per interval");
  }
  
  public static double getHPRegenerationInterval()
  {
    return config.getDouble("HP.regeneration interval");
  }
  
  public static double getMinimalFoodLevelForReg()
  {
    return config.getDouble("HP.minfoodlevel for regeneration");
  }
  
  public static double getWitherDamage()
  {
    return config.getDouble("HP.damage of wither effect");
  }
  
  public static double getHarmPotionDamage()
  {
    return config.getDouble("HP.damage per harm potion per tier");
  }
  
  public static double getPoisonDamage()
  {
    return config.getDouble("HP.posion potion damage per tick per tier");
  }
  
  public static double getFistDamage()
  {
    return config.getDouble("HP.damage of fists");
  }
  
  public static long getTimeBetweenAttacks()
  {
    return config.getLong("HP.minimum time between each attack on a player in milliseconds");
  }
  
  public static double getHealPotionHP()
  {
    return config.getDouble("HP.heal per heal potion per tier");
  }
  
  public static double getRegenerationPotionHP()
  {
    return config.getDouble("HP.regeneration potion per tick per tier");
  }
  
  static {}
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\HP\DamageConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */