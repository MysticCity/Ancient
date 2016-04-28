package com.ancientshores.Ancient.Listeners;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.Commands.ChangeAggroCommand;
import com.ancientshores.Ancient.Classes.Spells.Commands.CommandPlayer;
import com.ancientshores.Ancient.Experience.AncientExperience;
import com.ancientshores.Ancient.Guild.AncientGuild;
import com.ancientshores.Ancient.HP.AncientHP;
import com.ancientshores.Ancient.HP.Armor;
import com.ancientshores.Ancient.HP.CreatureHp;
import com.ancientshores.Ancient.HP.DamageConverter;
import com.ancientshores.Ancient.Party.AncientParty;
import com.ancientshores.Ancient.PlayerData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

public class AncientEntityListener
  implements Listener
{
  public static final Collection<UUID> StunList = Collections.newSetFromMap(new ConcurrentHashMap());
  public static final Collection<UUID> invulnerableList = Collections.newSetFromMap(new ConcurrentHashMap());
  public static final ConcurrentHashMap<UUID, UUID> scheduledXpList = new ConcurrentHashMap();
  public static Ancient plugin;
  public static final boolean ignored = false;
  
  public AncientEntityListener(Ancient instance)
  {
    plugin = instance;
    plugin.getServer().getPluginManager().registerEvents(this, plugin);
  }
  
  public static void stun(Entity e)
  {
    StunList.add(e.getUniqueId());
  }
  
  public static void unstun(Entity e)
  {
    StunList.remove(e.getUniqueId());
  }
  
  @EventHandler(priority=EventPriority.MONITOR)
  public void damageMonitor(EntityDamageEvent event)
  {
    if ((event.isCancelled()) || (event.getDamage() == 0.0D)) {
      return;
    }
    if ((event.getEntity() instanceof LivingEntity))
    {
      final LivingEntity entity = (LivingEntity)event.getEntity();
      
      ItemStack[] unfinalArmor = (ItemStack[])entity.getEquipment().getArmorContents().clone();
      if (Armor.hasChangedArmor(entity)) {
        unfinalArmor = Armor.getChangedArmor(entity);
      } else {
        Armor.addChangedArmor(entity, unfinalArmor);
      }
      final ItemStack[] armor = unfinalArmor;
      
      entity.getEquipment().setArmorContents(null);
      
      Armor.damageArmor(armor);
      
      Bukkit.getScheduler().scheduleSyncDelayedTask(Ancient.plugin, new Runnable()
      {
        public void run()
        {
          if (Armor.hasChangedArmor(entity))
          {
            entity.getEquipment().setArmorContents(armor);
            Armor.removeChangedArmor(entity);
          }
        }
      }, 1L);
    }
  }
  
  public static boolean ignoreNextHpEvent = false;
  
  @EventHandler(priority=EventPriority.LOWEST)
  public void monsterHpListener(EntityDamageEvent event)
  {
    if ((event.isCancelled()) || (event.getDamage() == 2.147483647E9D)) {
      return;
    }
    double damage = event.getDamage();
    if ((!ignoreNextHpEvent) && (CreatureHp.isEnabledInWorld(event.getEntity().getWorld())) && (DamageConverter.isEnabled()) && ((event.getEntity() instanceof LivingEntity)) && (!(event.getEntity() instanceof HumanEntity))) {
      damage = DamageConverter.convertDamageByEventForCreatures(event);
    }
    event.setDamage(damage);
  }
  
  @EventHandler(priority=EventPriority.HIGHEST)
  public void onMonsterDespawn(EntityDeathEvent event)
  {
    if ((CreatureHp.isEnabledInWorld(event.getEntity().getWorld())) && (event.getEntity() != null) && (!(event.getEntity() instanceof HumanEntity)))
    {
      LivingEntity c = event.getEntity();
      if (CreatureHp.containsCreature(c)) {
        CreatureHp.removeCreature(c);
      }
    }
  }
  
  @EventHandler(priority=EventPriority.HIGHEST)
  public void armorReductionListener(EntityDamageEvent event)
  {
    if ((event.isCancelled()) || (event.getDamage() == 2.147483647E9D)) {
      return;
    }
    double damage = event.getDamage();
    if ((!ignoreNextHpEvent) && ((event.getEntity() instanceof LivingEntity)) && (CreatureHp.isEnabledInWorld(event.getEntity().getWorld())) && (DamageConverter.isEnabled())) {
      damage = DamageConverter.reduceDamageByArmor(damage, (LivingEntity)event.getEntity());
    }
    event.setDamage(damage);
  }
  
  @EventHandler(priority=EventPriority.LOWEST)
  public void onEntityDeathWithArmor(EntityDeathEvent event)
  {
    if (Armor.hasChangedArmor(event.getEntity()))
    {
      event.getDrops().addAll(addArmorDrops(event.getEntity()));
      
      Armor.removeChangedArmor(event.getEntity());
    }
  }
  
  private List<ItemStack> addArmorDrops(LivingEntity entity)
  {
    List<ItemStack> list = new ArrayList();
    EntityEquipment equ = entity.getEquipment();
    
    ItemStack[] armor = Armor.getChangedArmor(entity);
    for (ItemStack item : armor) {
      if ((item != null) && (item.getType() != Material.AIR)) {
        if ((entity instanceof Player))
        {
          list.add(item);
        }
        else
        {
          float possibility;
          switch (item.getType())
          {
          case LEATHER_HELMET: 
          case CHAINMAIL_HELMET: 
          case IRON_HELMET: 
          case DIAMOND_HELMET: 
          case GOLD_HELMET: 
            possibility = equ.getHelmetDropChance();
            break;
          case LEATHER_CHESTPLATE: 
          case CHAINMAIL_CHESTPLATE: 
          case IRON_CHESTPLATE: 
          case DIAMOND_CHESTPLATE: 
          case GOLD_CHESTPLATE: 
            possibility = equ.getChestplateDropChance();
            break;
          case LEATHER_LEGGINGS: 
          case CHAINMAIL_LEGGINGS: 
          case IRON_LEGGINGS: 
          case DIAMOND_LEGGINGS: 
          case GOLD_LEGGINGS: 
            possibility = equ.getLeggingsDropChance();
            break;
          case LEATHER_BOOTS: 
          case CHAINMAIL_BOOTS: 
          case IRON_BOOTS: 
          case DIAMOND_BOOTS: 
          case GOLD_BOOTS: 
            possibility = equ.getBootsDropChance();
            break;
          default: 
            possibility = 0.0F;
          }
          if (Math.random() * 100.0D <= possibility) {
            list.add(item);
          }
        }
      }
    }
    return list;
  }
  
  @EventHandler(priority=EventPriority.HIGHEST)
  public void entityChangeTarget(EntityTargetLivingEntityEvent event)
  {
    if (((event.getEntity() instanceof Creature)) && 
      (ChangeAggroCommand.tauntedEntities.containsKey(event.getEntity().getUniqueId())))
    {
      Entity entity = null;
      for (World w : Bukkit.getWorlds()) {
        for (Entity e : w.getEntities()) {
          if (e.getUniqueId().compareTo((UUID)ChangeAggroCommand.tauntedEntities.get(event.getEntity().getUniqueId())) == 0) {
            entity = e;
          }
        }
      }
      ((Creature)event.getEntity()).setTarget((LivingEntity)entity);
      event.setCancelled(true);
    }
  }
  
  public static void setinvulnerable(Entity e, boolean invurnable)
  {
    if (invurnable) {
      invulnerableList.add(e.getUniqueId());
    } else {
      invulnerableList.remove(e.getUniqueId());
    }
  }
  
  @EventHandler(priority=EventPriority.HIGHEST)
  public void onEntityRegainHealth(EntityRegainHealthEvent event)
  {
    double amount = event.getAmount();
    if (((event.getEntity() instanceof Player)) && (!event.isCancelled()) && (DamageConverter.isEnabled()))
    {
      Player mPlayer = (Player)event.getEntity();
      if (!DamageConverter.isEnabledInWorld(event.getEntity().getWorld())) {
        return;
      }
      if (event.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED) {
        amount = 0.0D;
      }
      if (event.getRegainReason() != EntityRegainHealthEvent.RegainReason.CUSTOM) {
        if (event.getRegainReason() == EntityRegainHealthEvent.RegainReason.MAGIC_REGEN) {
          for (PotionEffect pe : mPlayer.getActivePotionEffects()) {
            if (mPlayer.hasPotionEffect(PotionEffectType.REGENERATION)) {
              amount = DamageConverter.getRegenerationPotionHP() * (pe.getAmplifier() + 1);
            }
          }
        } else if ((event.getRegainReason() == EntityRegainHealthEvent.RegainReason.MAGIC) && 
          (AncientPlayerListener.healpotions.containsKey(event.getEntity().getUniqueId()))) {
          amount = ((Double)AncientPlayerListener.healpotions.get(event.getEntity().getUniqueId())).doubleValue();
        }
      }
    }
    event.setAmount(amount);
  }
  
  @EventHandler
  public void onPotionSplash(PotionSplashEvent event)
  {
    HashSet<Entity> entities = new HashSet();
    entities.addAll(event.getAffectedEntities());
    for (Entity e : entities) {
      if ((e instanceof Player))
      {
        Player p = (Player)e;
        PotionEffect pe = getPotionEffectByType(PotionEffectType.HEAL, event.getPotion().getEffects());
        if (pe != null) {
          AncientHP.addHpByUUID(p.getUniqueId(), DamageConverter.getHealPotionHP() * (pe.getAmplifier() + 1));
        }
      }
    }
  }
  
  public PotionEffect getPotionEffectByType(PotionEffectType type, Collection<PotionEffect> pe)
  {
    for (PotionEffect p : pe) {
      if (p.getType().equals(type)) {
        return p;
      }
    }
    return null;
  }
  
  public void checkForFriendlyFire(Player attacker, Player attacked, EntityDamageByEntityEvent event)
  {
    if (attacked == attacker) {
      return;
    }
    AncientGuild attackerGuild = AncientGuild.getPlayersGuild(attacker.getUniqueId());
    AncientGuild attackedGuild = AncientGuild.getPlayersGuild(attacked.getUniqueId());
    if ((attackerGuild != null) && (attackerGuild.equals(attackedGuild)) && 
      (!attackedGuild.friendlyFire)) {
      event.setCancelled(true);
    }
    AncientParty attackerParty = AncientParty.getPlayersParty(attacked.getUniqueId());
    AncientParty attackedParty = AncientParty.getPlayersParty(attacked.getUniqueId());
    if ((attackerParty != null) && (attackerParty.equals(attackedParty)) && 
      (!attackerParty.isFriendlyFireEnabled())) {
      event.setCancelled(true);
    }
  }
  
  @EventHandler(priority=EventPriority.MONITOR)
  public void onProjectileLaunch(ProjectileLaunchEvent event)
  {
    if (event.isCancelled()) {
      return;
    }
    if ((event.getEntity().getShooter() instanceof Entity))
    {
      UUID uuid = ((Entity)event.getEntity().getShooter()).getUniqueId();
      if (AncientPlayerListener.playersWhoThrowed.containsKey(uuid))
      {
        String name = (String)AncientPlayerListener.playersWhoThrowed.remove(uuid);
        
        AncientPlayerListener.thrownProjectiles.put(event.getEntity(), name);
      }
    }
  }
  
  @EventHandler(priority=EventPriority.MONITOR)
  public void onProjectileHit(ProjectileHitEvent event)
  {
    AncientPlayerListener.thrownProjectiles.remove(event.getEntity());
  }
  
  @EventHandler(priority=EventPriority.HIGHEST)
  public void onEntityDeath(final EntityDeathEvent event)
  {
    Entity deathEntity = null;
    unstun(event.getEntity());
    for (UUID uuid : scheduledXpList.keySet()) {
      if (uuid.compareTo(event.getEntity().getUniqueId()) == 0) {
        deathEntity = event.getEntity();
      }
    }
    if (deathEntity == null) {
      return;
    }
    Player p = Bukkit.getServer().getPlayer((UUID)scheduledXpList.get(deathEntity.getUniqueId()));
    scheduledXpList.remove(deathEntity.getUniqueId());
    PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
    CommandPlayer.alreadyDead.add(event.getEntity().getUniqueId());
    Ancient.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Ancient.plugin, new Runnable()
    {
      public void run()
      {
        CommandPlayer.alreadyDead.remove(event.getEntity().getUniqueId());
      }
    }, 250L);
    if (pd.getXpSystem() != null) {
      pd.getXpSystem().addXP(AncientExperience.getXPOfEntity(deathEntity), true);
    }
  }
  
  @EventHandler(priority=EventPriority.LOWEST)
  public void entityChangeAggro(EntityTargetEvent event)
  {
    if (((event.getEntity() instanceof Creature)) && 
      (ChangeAggroCommand.tauntedEntities.containsKey(event.getEntity().getUniqueId())))
    {
      Entity entity = null;
      for (World w : Bukkit.getWorlds()) {
        for (Entity e : w.getEntities()) {
          if (e.getUniqueId().compareTo((UUID)ChangeAggroCommand.tauntedEntities.get(event.getEntity().getUniqueId())) == 0) {
            entity = e;
          }
        }
      }
      event.setTarget(entity);
      event.setCancelled(true);
    }
  }
  
  @EventHandler(priority=EventPriority.MONITOR)
  public void xpListener(EntityDamageEvent event)
  {
    if ((AncientExperience.isEnabled()) && ((event instanceof EntityDamageByEntityEvent))) {
      AncientExperience.processEntityDamageByEntityEvent((EntityDamageByEntityEvent)event);
    }
  }
  
  @EventHandler(priority=EventPriority.LOWEST)
  public void onEntityDamage(EntityDamageEvent event)
  {
    if (event.getDamage() == 2.147483647E9D) {
      return;
    }
    if (invulnerableList.contains(event.getEntity().getUniqueId()))
    {
      event.setCancelled(true);
      return;
    }
    if ((event instanceof EntityDamageByEntityEvent))
    {
      EntityDamageByEntityEvent ede = (EntityDamageByEntityEvent)event;
      Entity damager = ede.getDamager();
      if (((ede.getDamager() instanceof Projectile)) && (((Projectile)ede.getDamager()).getShooter() != null)) {
        damager = ede.getDamager();
      }
      if (((event.getEntity() instanceof Player)) && ((damager instanceof Player)))
      {
        checkForFriendlyFire((Player)event.getEntity(), (Player)damager, (EntityDamageByEntityEvent)event);
        if (event.isCancelled()) {
          return;
        }
      }
      if (StunList.contains(damager.getUniqueId()))
      {
        event.setCancelled(true);
        return;
      }
    }
    if ((event.getEntity() instanceof Player))
    {
      if (event.isCancelled()) {
        return;
      }
      if ((!ignoreNextHpEvent) && (DamageConverter.isEnabledInWorld(event.getEntity().getWorld()))) {
        processHpSystem(event);
      }
    }
  }
  
  private void processHpSystem(EntityDamageEvent event)
  {
    double damage = event.getDamage();
    PlayerData.getPlayerData(((Player)event.getEntity()).getUniqueId());
    if (DamageConverter.isEnabled())
    {
      if ((event.getDamage() < 0.0D) || (event.isCancelled())) {
        return;
      }
      Player mPlayer = (Player)event.getEntity();
      if ((!mPlayer.getGameMode().equals(GameMode.CREATIVE)) && (!event.isCancelled()) && 
        (!mPlayer.isDead()))
      {
        if ((event.getCause() == EntityDamageEvent.DamageCause.FALL) || (event.getCause() == EntityDamageEvent.DamageCause.STARVATION))
        {
          damage = DamageConverter.convertDamageByCause(event.getCause(), mPlayer, event.getDamage(), event);
        }
        else if (event.getCause() == EntityDamageEvent.DamageCause.MAGIC)
        {
          if ((event instanceof EntityDamageByEntityEvent))
          {
            Entity e = ((EntityDamageByEntityEvent)event).getDamager();
            if ((e instanceof ThrownPotion))
            {
              ThrownPotion p = (ThrownPotion)e;
              for (PotionEffect pe : p.getEffects()) {
                if (pe.getType().equals(PotionEffectType.HARM)) {
                  damage = DamageConverter.getHarmPotionDamage() * (pe.getAmplifier() + 1);
                }
              }
            }
            else if ((e instanceof Potion))
            {
              Potion p = (Potion)e;
              for (PotionEffect pe : p.getEffects()) {
                if (pe.getType().equals(PotionEffectType.HARM)) {
                  damage = DamageConverter.getHarmPotionDamage() * (pe.getAmplifier() + 1);
                }
              }
            }
          }
        }
        else if (event.getCause() == EntityDamageEvent.DamageCause.POISON)
        {
          for (PotionEffect pe : mPlayer.getActivePotionEffects()) {
            if (pe.getType().equals(PotionEffectType.POISON)) {
              damage = DamageConverter.getPoisonDamage() * (pe.getAmplifier() + 1);
            }
          }
        }
        else if (event.getCause() == EntityDamageEvent.DamageCause.WITHER)
        {
          damage = DamageConverter.getWitherDamage();
        }
        else if ((event instanceof EntityDamageByEntityEvent))
        {
          EntityDamageByEntityEvent damagerevent = (EntityDamageByEntityEvent)event;
          if ((damagerevent.getDamager() instanceof Player))
          {
            Player attackingPlayer = (Player)damagerevent.getDamager();
            if (Math.abs(PlayerData.getPlayerData(mPlayer.getUniqueId()).getHpsystem().lastAttackDamage - System.currentTimeMillis()) < DamageConverter.getTimeBetweenAttacks()) {
              damage = 0.0D;
            } else {
              damage = DamageConverter.getPlayerDamageOfItem(mPlayer, attackingPlayer.getItemInHand().getType(), attackingPlayer, DamageConverter.getFistDamage());
            }
            PlayerData.getPlayerData(mPlayer.getUniqueId()).getHpsystem().lastAttackDamage = System.currentTimeMillis();
          }
          else if ((damagerevent.getDamager() instanceof LivingEntity))
          {
            LivingEntity c = (LivingEntity)damagerevent.getDamager();
            damage = DamageConverter.convertDamageByCreature(c, mPlayer, event.getDamage(), event);
          }
          else
          {
            damage = DamageConverter.convertDamageByCause(event.getCause(), mPlayer, event.getDamage(), event);
          }
        }
        else
        {
          damage = DamageConverter.convertDamageByCause(event.getCause(), mPlayer, event.getDamage(), event);
        }
        event.setDamage(Math.round(damage));
      }
    }
  }
}
