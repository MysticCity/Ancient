package com.ancientshores.Ancient.Listeners;

import com.ancientshores.Ancient.API.AncientClassChangeEvent;
import com.ancientshores.Ancient.API.AncientLevelupEvent;
import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.AncientClass;
import com.ancientshores.Ancient.Classes.Spells.Commands.CommandPlayer;
import com.ancientshores.Ancient.Classes.Spells.Spell;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import com.ancientshores.Ancient.Experience.AncientExperience;
import com.ancientshores.Ancient.HP.CreatureHp;
import com.ancientshores.Ancient.PlayerData;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;

public class AncientSpellListener
  implements Listener
{
  public static Ancient plugin;
  public static final HashSet<Spell> damageEventSpells = new HashSet();
  public static final ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>> damageEventBuffs = new ConcurrentHashMap();
  public static final HashSet<Spell> attackEventSpells = new HashSet();
  public static final ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>> attackEventBuffs = new ConcurrentHashMap();
  public static final HashSet<Spell> damageByEntityEventSpells = new HashSet();
  public static final ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>> damageByEntityEventBuffs = new ConcurrentHashMap();
  public static final HashSet<Spell> ChangeBlockEventSpells = new HashSet();
  public static final ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>> ChangeBlockEventBuffs = new ConcurrentHashMap();
  public static final HashSet<Spell> joinEventSpells = new HashSet();
  public static final ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>> joinEventBuffs = new ConcurrentHashMap();
  public static final HashSet<Spell> interactEventSpells = new HashSet();
  public static final ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>> interactEventBuffs = new ConcurrentHashMap();
  public static final HashSet<Spell> chatEventSpells = new HashSet();
  public static final ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>> chatEventBuffs = new ConcurrentHashMap();
  public static final HashSet<Spell> regenEventSpells = new HashSet();
  public static final ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>> regenEventBuffs = new ConcurrentHashMap();
  public static final HashSet<Spell> moveEventSpells = new HashSet();
  public static final ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>> moveEventBuffs = new ConcurrentHashMap();
  public static final HashSet<Spell> playerDeathSpells = new HashSet();
  public static final ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>> playerDeathBuffs = new ConcurrentHashMap();
  public static final HashSet<Spell> LevelupEventSpells = new HashSet();
  public static final ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>> LevelupEventBuffs = new ConcurrentHashMap();
  public static final HashSet<Spell> ProjectileHitEventSpells = new HashSet();
  public static final ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>> ProjectileHitEventBuffs = new ConcurrentHashMap();
  public static final HashSet<Spell> UseBedEventSpells = new HashSet();
  public static final ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>> UseBedEventBuffs = new ConcurrentHashMap();
  public static final HashSet<Spell> killEntityEventSpells = new HashSet();
  public static final ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>> killEntityEventBuffs = new ConcurrentHashMap();
  public static final HashSet<Spell> classChangeEventSpells = new HashSet();
  public static final ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>> classChangeEventBuffs = new ConcurrentHashMap();
  public static final ConcurrentHashMap<UUID, ItemStack> disarmList = new ConcurrentHashMap();
  public static int buffId = 0;
  public static final HashSet<Entity> deadPlayer = new HashSet();
  public static final ConcurrentHashMap<UUID, Location> revivePlayer = new ConcurrentHashMap();
  public static final ConcurrentHashMap<UUID, HashSet<SpellInformationObject>> disruptOnMove = new ConcurrentHashMap();
  public static final ConcurrentHashMap<UUID, HashSet<SpellInformationObject>> disruptOnDeath = new ConcurrentHashMap();
  public static final HashSet<Event> ignoredEvents = new HashSet();
  
  public static void clearAll()
  {
    damageEventSpells.clear();
    damageEventBuffs.clear();
    damageByEntityEventBuffs.clear();
    damageByEntityEventSpells.clear();
    joinEventBuffs.clear();
    joinEventSpells.clear();
    interactEventBuffs.clear();
    interactEventSpells.clear();
    attackEventBuffs.clear();
    attackEventSpells.clear();
    ChangeBlockEventBuffs.clear();
    ChangeBlockEventSpells.clear();
    chatEventBuffs.clear();
    chatEventSpells.clear();
    regenEventBuffs.clear();
    regenEventSpells.clear();
    moveEventBuffs.clear();
    moveEventSpells.clear();
    playerDeathBuffs.clear();
    playerDeathSpells.clear();
    LevelupEventBuffs.clear();
    LevelupEventSpells.clear();
    ProjectileHitEventBuffs.clear();
    ProjectileHitEventSpells.clear();
    classChangeEventBuffs.clear();
    classChangeEventSpells.clear();
    ignoredEvents.clear();
  }
  
  public AncientSpellListener(Ancient instance)
  {
    plugin = instance;
    plugin.getServer().getPluginManager().registerEvents(this, plugin);
  }
  
  public static ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>> getAllBuffs()
  {
    ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>> map = new ConcurrentHashMap();
    
    map.putAll(damageEventBuffs);
    map.putAll(attackEventBuffs);
    map.putAll(damageByEntityEventBuffs);
    map.putAll(ChangeBlockEventBuffs);
    map.putAll(joinEventBuffs);
    map.putAll(interactEventBuffs);
    map.putAll(chatEventBuffs);
    map.putAll(regenEventBuffs);
    map.putAll(moveEventBuffs);
    map.putAll(playerDeathBuffs);
    map.putAll(LevelupEventBuffs);
    map.putAll(ProjectileHitEventBuffs);
    map.putAll(classChangeEventBuffs);
    
    return map;
  }
  
  public static void addDisruptCommand(Player p, SpellInformationObject spell, ConcurrentHashMap<UUID, HashSet<SpellInformationObject>> map)
  {
    if (spell == null) {
      return;
    }
    if (!map.containsKey(p.getUniqueId())) {
      map.put(p.getUniqueId(), new HashSet());
    }
    ((HashSet)map.get(p.getUniqueId())).add(spell);
  }
  
  public static void removeDisruptCommand(Player p, SpellInformationObject spell, ConcurrentHashMap<UUID, HashSet<SpellInformationObject>> map)
  {
    ((HashSet)map.get(p.getUniqueId())).remove(spell);
  }
  
  public static int attachBuffToEvent(Spell s, ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>> map, Player[] p)
  {
    if (s == null) {
      return Integer.MAX_VALUE;
    }
    if (!map.containsKey(s)) {
      map.put(s, new ConcurrentHashMap());
    }
    ((ConcurrentHashMap)map.get(s)).put(new UUID[] { p[0].getUniqueId(), p[1].getUniqueId() }, Integer.valueOf(buffId));
    
    int oldbuffId = buffId;
    buffId += 1;
    if (buffId > 1073741823) {
      buffId = 0;
    }
    return oldbuffId;
  }
  
  public static void detachBuffToEvent(Spell s, ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>> map, Player[] p, int id)
  {
    if (!map.containsKey(s)) {
      return;
    }
    ConcurrentHashMap<UUID[], Integer> innerMap = (ConcurrentHashMap)map.get(s);
    HashSet<UUID[]> removeBuffs = new HashSet();
    if (innerMap == null) {
      return;
    }
    for (UUID[] uuids : innerMap.keySet()) {
      if ((uuids.length == 2) && ((uuids[0].compareTo(p[0].getUniqueId()) == 0) || (uuids[1].compareTo(p[1].getUniqueId()) == 0)) && 
        (innerMap.get(uuids) != null) && (((Integer)innerMap.get(uuids)).intValue() == id)) {
        removeBuffs.add(uuids);
      }
    }
    for (UUID[] uuids : removeBuffs) {
      innerMap.remove(uuids);
    }
  }
  
  public static void detachBuffOfEvent(String buffEvent, Spell sp, Player p)
  {
    if (buffEvent == null) {
      return;
    }
    ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>> buffs = null;
    if (buffEvent.equalsIgnoreCase("damageevent")) {
      buffs = damageEventBuffs;
    } else if (buffEvent.equalsIgnoreCase("damagebyentityevent")) {
      buffs = damageByEntityEventBuffs;
    } else if (buffEvent.equalsIgnoreCase("attackevent")) {
      buffs = attackEventBuffs;
    } else if (buffEvent.equalsIgnoreCase("changeblockevent")) {
      buffs = ChangeBlockEventBuffs;
    } else if (buffEvent.equalsIgnoreCase("joinevent")) {
      buffs = joinEventBuffs;
    } else if (buffEvent.equalsIgnoreCase("interactevent")) {
      buffs = interactEventBuffs;
    } else if (buffEvent.equalsIgnoreCase("chatevent")) {
      buffs = chatEventBuffs;
    } else if (buffEvent.equalsIgnoreCase("regenevent")) {
      buffs = regenEventBuffs;
    } else if (buffEvent.equalsIgnoreCase("moveevent")) {
      buffs = moveEventBuffs;
    } else if (buffEvent.equalsIgnoreCase("levelupevent")) {
      buffs = LevelupEventBuffs;
    } else if (buffEvent.equalsIgnoreCase("playerdeathevent")) {
      buffs = playerDeathBuffs;
    } else if (buffEvent.equalsIgnoreCase("usebedevents")) {
      buffs = UseBedEventBuffs;
    } else if (buffEvent.equalsIgnoreCase("projectilehitevent")) {
      buffs = ProjectileHitEventBuffs;
    } else if (buffEvent.equalsIgnoreCase("killentityevent")) {
      buffs = killEntityEventBuffs;
    } else if (buffEvent.equalsIgnoreCase("classchangeevent")) {
      buffs = classChangeEventBuffs;
    }
    if ((buffs != null) && 
      (!buffs.containsKey(sp))) {
      return;
    }
    ConcurrentHashMap<UUID[], Integer> innerMap = (ConcurrentHashMap)buffs.get(sp);
    HashSet<UUID[]> removeBuffs = new HashSet();
    if (innerMap == null) {
      return;
    }
    for (UUID[] uuids : innerMap.keySet()) {
      if ((uuids.length == 2) && ((uuids[0].compareTo(p.getUniqueId()) == 0) || (uuids[1].compareTo(p.getUniqueId()) == 0)) && 
        (innerMap.get(uuids) != null)) {
        removeBuffs.add(uuids);
      }
    }
    for (UUID[] uuids : removeBuffs) {
      innerMap.remove(uuids);
    }
  }
  
  @EventHandler
  public void onPlayerRegainhealth(final EntityRegainHealthEvent event)
  {
    if (event.isCancelled()) {
      return;
    }
    if (ignoredEvents.contains(event)) {
      return;
    }
    ignoredEvents.add(event);
    Bukkit.getScheduler().scheduleSyncDelayedTask(Ancient.plugin, new Runnable()
    {
      public void run()
      {
        AncientSpellListener.ignoredEvents.remove(event);
      }
    }, 20L);
    if ((event.getEntity() instanceof Player))
    {
      Player mPlayer = (Player)event.getEntity();
      PlayerData pd = PlayerData.getPlayerData(mPlayer.getUniqueId());
      HashMap<Spell, UUID[]> spells = new HashMap();
      for (Spell p : regenEventSpells) {
        if (AncientClass.spellAvailable(p, pd)) {
          spells.put(p, new UUID[] { mPlayer.getUniqueId(), mPlayer.getUniqueId() });
        }
      }
      for (Iterator i$ = regenEventBuffs.entrySet().iterator(); i$.hasNext();)
      {
        e = (Map.Entry)i$.next();
        for (UUID[] uuids : ((ConcurrentHashMap)e.getValue()).keySet()) {
          if (uuids[0].compareTo(mPlayer.getUniqueId()) == 0) {
            spells.put(e.getKey(), uuids);
          }
        }
      }
      Map.Entry<Spell, ConcurrentHashMap<UUID[], Integer>> e;
      LinkedList<Map.Entry<Spell, UUID[]>> sortedspells = getSortedList(spells);
      for (Map.Entry<Spell, UUID[]> sortedspell : sortedspells) {
        CommandPlayer.scheduleSpell((Spell)sortedspell.getKey(), ((UUID[])sortedspell.getValue())[0], event, ((UUID[])sortedspell.getValue())[1]);
      }
    }
  }
  
  @EventHandler(priority=EventPriority.HIGH)
  public void onPlayerDeath(final PlayerDeathEvent event)
  {
    if ((disruptOnDeath.containsKey(event.getEntity().getUniqueId())) && (disruptOnDeath.get(event.getEntity().getUniqueId()) != null)) {
      for (SpellInformationObject sp : (HashSet)disruptOnDeath.get(event.getEntity().getUniqueId()))
      {
        sp.canceled = true;
        sp.finished = true;
        event.getEntity().sendMessage("Spell cancelled");
        removeDisruptCommand(event.getEntity(), sp, disruptOnDeath);
      }
    }
    if (ignoredEvents.contains(event)) {
      return;
    }
    ignoredEvents.add(event);
    Bukkit.getScheduler().scheduleSyncDelayedTask(Ancient.plugin, new Runnable()
    {
      public void run()
      {
        AncientSpellListener.ignoredEvents.remove(event);
      }
    }, 20L);
    
    Player mPlayer = event.getEntity();
    PlayerData pd = PlayerData.getPlayerData(mPlayer.getUniqueId());
    
    HashMap<Spell, UUID[]> spells = new HashMap();
    for (Spell p : playerDeathSpells) {
      if (AncientClass.spellAvailable(p, pd)) {
        spells.put(p, new UUID[] { mPlayer.getUniqueId(), mPlayer.getUniqueId() });
      }
    }
    for (Iterator i$ = playerDeathBuffs.entrySet().iterator(); i$.hasNext();)
    {
      e = (Map.Entry)i$.next();
      for (UUID[] uuids : ((ConcurrentHashMap)e.getValue()).keySet()) {
        if (uuids[0].compareTo(event.getEntity().getUniqueId()) == 0) {
          spells.put(e.getKey(), uuids);
        }
      }
    }
    Map.Entry<Spell, ConcurrentHashMap<UUID[], Integer>> e;
    LinkedList<Map.Entry<Spell, UUID[]>> sortedspells = getSortedList(spells);
    for (Map.Entry<Spell, UUID[]> sortedspell : sortedspells) {
      CommandPlayer.scheduleSpell((Spell)sortedspell.getKey(), ((UUID[])sortedspell.getValue())[0], event, ((UUID[])sortedspell.getValue())[1]);
    }
  }
  
  @EventHandler
  public void onProjectileHit(final ProjectileHitEvent event)
  {
    if (ignoredEvents.contains(event)) {
      return;
    }
    ignoredEvents.add(event);
    Bukkit.getScheduler().scheduleSyncDelayedTask(Ancient.plugin, new Runnable()
    {
      public void run()
      {
        AncientSpellListener.ignoredEvents.remove(event);
      }
    }, 20L);
    if (!(event.getEntity().getShooter() instanceof Player)) {
      return;
    }
    PlayerData pd = PlayerData.getPlayerData(((Player)event.getEntity().getShooter()).getUniqueId());
    Player mPlayer = (Player)event.getEntity().getShooter();
    
    HashMap<Spell, UUID[]> spells = new HashMap();
    for (Spell p : ProjectileHitEventSpells) {
      if (AncientClass.spellAvailable(p, pd)) {
        spells.put(p, new UUID[] { mPlayer.getUniqueId(), mPlayer.getUniqueId() });
      }
    }
    for (Iterator i$ = ProjectileHitEventBuffs.entrySet().iterator(); i$.hasNext();)
    {
      e = (Map.Entry)i$.next();
      for (UUID[] uuids : ((ConcurrentHashMap)e.getValue()).keySet()) {
        if (uuids[0].compareTo(mPlayer.getUniqueId()) == 0) {
          spells.put(e.getKey(), uuids);
        }
      }
    }
    Map.Entry<Spell, ConcurrentHashMap<UUID[], Integer>> e;
    LinkedList<Map.Entry<Spell, UUID[]>> sortedspells = getSortedList(spells);
    for (Map.Entry<Spell, UUID[]> sortedspell : sortedspells) {
      CommandPlayer.scheduleSpell((Spell)sortedspell.getKey(), ((UUID[])sortedspell.getValue())[0], event, ((UUID[])sortedspell.getValue())[1]);
    }
  }
  
  public static final HashSet<UUID> alreadyDead = new HashSet();
  
  public void onPlayerKill(EntityDamageByEntityEvent event)
  {
    Entity damager = event.getDamager();
    if (alreadyDead.contains(event.getEntity().getUniqueId())) {
      return;
    }
    if ((!(event.getEntity() instanceof Player)) && 
      ((event.getEntity() instanceof LivingEntity)) && 
      (CreatureHp.isEnabledInWorld(event.getEntity().getWorld()))) {
      return;
    }
    if (((damager instanceof Player)) && (!AncientExperience.alreadyDead.contains(event.getEntity().getUniqueId())) && 
      ((event.getEntity() instanceof LivingEntity)) && (((LivingEntity)event.getEntity()).getHealth() - event.getDamage() <= 0.0D))
    {
      alreadyDead.add(event.getEntity().getUniqueId());
      
      Player mPlayer = (Player)damager;
      PlayerData pd = PlayerData.getPlayerData(mPlayer.getUniqueId());
      
      HashMap<Spell, UUID[]> spells = new HashMap();
      for (Spell p : killEntityEventSpells) {
        if (AncientClass.spellAvailable(p, pd)) {
          spells.put(p, new UUID[] { mPlayer.getUniqueId(), mPlayer.getUniqueId() });
        }
      }
      for (Iterator i$ = killEntityEventBuffs.entrySet().iterator(); i$.hasNext();)
      {
        e = (Map.Entry)i$.next();
        for (UUID[] uuids : ((ConcurrentHashMap)e.getValue()).keySet()) {
          if (uuids[0].compareTo(mPlayer.getUniqueId()) == 0) {
            spells.put(e.getKey(), uuids);
          }
        }
      }
      Map.Entry<Spell, ConcurrentHashMap<UUID[], Integer>> e;
      LinkedList<Map.Entry<Spell, UUID[]>> sortedspells = getSortedList(spells);
      for (Map.Entry<Spell, UUID[]> sortedspell : sortedspells) {
        CommandPlayer.scheduleSpell((Spell)sortedspell.getKey(), ((UUID[])sortedspell.getValue())[0], event, ((UUID[])sortedspell.getValue())[1]);
      }
    }
  }
  
  public void onPlayerAttack(EntityDamageByEntityEvent event)
  {
    Entity damager = event.getDamager();
    if ((event.getDamager() instanceof Projectile)) {
      damager = (Entity)((Projectile)event.getDamager()).getShooter();
    }
    if ((damager instanceof Player))
    {
      Player mPlayer = (Player)damager;
      PlayerData pd = PlayerData.getPlayerData(mPlayer.getUniqueId());
      
      HashMap<Spell, UUID[]> spells = new HashMap();
      for (Spell p : attackEventSpells) {
        if (AncientClass.spellAvailable(p, pd)) {
          spells.put(p, new UUID[] { mPlayer.getUniqueId(), mPlayer.getUniqueId() });
        }
      }
      for (Iterator i$ = attackEventBuffs.entrySet().iterator(); i$.hasNext();)
      {
        e = (Map.Entry)i$.next();
        for (UUID[] uuids : ((ConcurrentHashMap)e.getValue()).keySet()) {
          if (uuids[0].compareTo(damager.getUniqueId()) == 0) {
            spells.put(e.getKey(), uuids);
          }
        }
      }
      Map.Entry<Spell, ConcurrentHashMap<UUID[], Integer>> e;
      LinkedList<Map.Entry<Spell, UUID[]>> sortedspells = getSortedList(spells);
      for (Map.Entry<Spell, UUID[]> sortedspell : sortedspells) {
        CommandPlayer.scheduleSpell((Spell)sortedspell.getKey(), ((UUID[])sortedspell.getValue())[0], event, ((UUID[])sortedspell.getValue())[1]);
      }
    }
  }
  
  @EventHandler(priority=EventPriority.HIGHEST)
  public void onPlayerDamage(final EntityDamageEvent event)
  {
    if (event.isCancelled()) {
      return;
    }
    if ((ignoredEvents.contains(event)) || (event.getDamage() == 0.0D)) {
      return;
    }
    if ((event instanceof EntityDamageByEntityEvent)) {
      onPlayerKill((EntityDamageByEntityEvent)event);
    }
    if (event.getDamage() == 2.147483647E9D) {
      return;
    }
    ignoredEvents.add(event);
    Bukkit.getScheduler().scheduleSyncDelayedTask(Ancient.plugin, new Runnable()
    {
      public void run()
      {
        AncientSpellListener.ignoredEvents.remove(event);
      }
    }, 20L);
    if ((event.getEntity() instanceof Player))
    {
      Player mPlayer = (Player)event.getEntity();
      PlayerData pd = PlayerData.getPlayerData(mPlayer.getUniqueId());
      if (event.getCause().equals(EntityDamageEvent.DamageCause.FIRE))
      {
        if (Math.abs(pd.lastFireDamageSpell - System.currentTimeMillis()) < 1000L) {
          return;
        }
        pd.lastFireDamageSpell = System.currentTimeMillis();
      }
      else if (event.getCause().equals(EntityDamageEvent.DamageCause.LAVA))
      {
        if (Math.abs(pd.lastLavaDamageSpell - System.currentTimeMillis()) < 1000L) {
          return;
        }
        pd.lastLavaDamageSpell = System.currentTimeMillis();
      }
      else if (event.getCause().equals(EntityDamageEvent.DamageCause.CONTACT))
      {
        if (Math.abs(pd.lastCactusDamageSpell - System.currentTimeMillis()) < 1000L) {
          return;
        }
        pd.lastCactusDamageSpell = System.currentTimeMillis();
      }
      HashMap<Spell, UUID[]> spells = new HashMap();
      for (Spell p : damageEventSpells) {
        if (AncientClass.spellAvailable(p, pd)) {
          spells.put(p, new UUID[] { mPlayer.getUniqueId(), mPlayer.getUniqueId() });
        }
      }
      for (Iterator i$ = damageEventBuffs.entrySet().iterator(); i$.hasNext();)
      {
        e = (Map.Entry)i$.next();
        for (UUID[] uuids : ((ConcurrentHashMap)e.getValue()).keySet()) {
          if (uuids[0].compareTo(event.getEntity().getUniqueId()) == 0) {
            spells.put(e.getKey(), uuids);
          }
        }
      }
      Map.Entry<Spell, ConcurrentHashMap<UUID[], Integer>> e;
      LinkedList<Map.Entry<Spell, UUID[]>> sortedspells = getSortedList(spells);
      for (Map.Entry<Spell, UUID[]> sortedspell : sortedspells) {
        CommandPlayer.scheduleSpell((Spell)sortedspell.getKey(), ((UUID[])sortedspell.getValue())[0], event, ((UUID[])sortedspell.getValue())[1]);
      }
      if ((event instanceof EntityDamageByEntityEvent)) {
        onPlayerDamageByEntity((EntityDamageByEntityEvent)event);
      }
    }
    if ((event instanceof EntityDamageByEntityEvent)) {
      onPlayerAttack((EntityDamageByEntityEvent)event);
    }
  }
  
  public void onPlayerDamageByEntity(EntityDamageByEntityEvent event)
  {
    Entity Damager = event.getEntity();
    if ((event.getDamager() instanceof Arrow)) {
      Damager = (Entity)((Arrow)event.getDamager()).getShooter();
    }
    if (((Damager instanceof Creature)) || ((Damager instanceof Player)))
    {
      Player mPlayer = (Player)event.getEntity();
      PlayerData pd = PlayerData.getPlayerData(mPlayer.getUniqueId());
      
      HashMap<Spell, UUID[]> spells = new HashMap();
      for (Spell p : damageByEntityEventSpells) {
        if (AncientClass.spellAvailable(p, pd)) {
          spells.put(p, new UUID[] { mPlayer.getUniqueId(), mPlayer.getUniqueId() });
        }
      }
      for (Iterator i$ = damageByEntityEventBuffs.entrySet().iterator(); i$.hasNext();)
      {
        e = (Map.Entry)i$.next();
        for (UUID[] uuids : ((ConcurrentHashMap)e.getValue()).keySet()) {
          if (uuids[0].compareTo(event.getEntity().getUniqueId()) == 0) {
            spells.put(e.getKey(), uuids);
          }
        }
      }
      Map.Entry<Spell, ConcurrentHashMap<UUID[], Integer>> e;
      LinkedList<Map.Entry<Spell, UUID[]>> sortedspells = getSortedList(spells);
      for (Map.Entry<Spell, UUID[]> sortedspell : sortedspells) {
        CommandPlayer.scheduleSpell((Spell)sortedspell.getKey(), ((UUID[])sortedspell.getValue())[0], event, ((UUID[])sortedspell.getValue())[1]);
      }
    }
  }
  
  @EventHandler(priority=EventPriority.HIGHEST)
  public void onPlayerMove(final PlayerMoveEvent event)
  {
    if (event.isCancelled()) {
      return;
    }
    if (ignoredEvents.contains(event)) {
      return;
    }
    ignoredEvents.add(event);
    Bukkit.getScheduler().scheduleSyncDelayedTask(Ancient.plugin, new Runnable()
    {
      public void run()
      {
        AncientSpellListener.ignoredEvents.remove(event);
      }
    }, 20L);
    if ((event.getTo().getWorld() != event.getPlayer().getWorld()) || (event.getTo().distance(event.getPlayer().getLocation()) > 0.1D))
    {
      HashSet<SpellInformationObject> soobs = new HashSet();
      if (disruptOnMove.containsKey(event.getPlayer().getUniqueId())) {
        for (SpellInformationObject sp : (HashSet)disruptOnMove.get(event.getPlayer().getUniqueId()))
        {
          sp.canceled = true;
          sp.finished = true;
          event.getPlayer().sendMessage("Spell cancelled");
          soobs.add(sp);
        }
      }
      for (SpellInformationObject so : soobs) {
        removeDisruptCommand(event.getPlayer(), so, disruptOnMove);
      }
      if (!lastMoved.containsKey(event.getPlayer().getUniqueId())) {
        lastMoved.put(event.getPlayer().getUniqueId(), Long.valueOf(0L));
      }
      if (Math.abs(((Long)lastMoved.get(event.getPlayer().getUniqueId())).longValue() - System.currentTimeMillis()) >= 1000L)
      {
        lastMoved.put(event.getPlayer().getUniqueId(), Long.valueOf(System.currentTimeMillis()));
        Player mPlayer = event.getPlayer();
        PlayerData pd = PlayerData.getPlayerData(mPlayer.getUniqueId());
        HashMap<Spell, UUID[]> spells = new HashMap();
        for (Spell p : moveEventSpells) {
          if (AncientClass.spellAvailable(p, pd)) {
            spells.put(p, new UUID[] { mPlayer.getUniqueId(), mPlayer.getUniqueId() });
          }
        }
        for (Iterator i$ = moveEventBuffs.entrySet().iterator(); i$.hasNext();)
        {
          e = (Map.Entry)i$.next();
          for (UUID[] uuids : ((ConcurrentHashMap)e.getValue()).keySet()) {
            if (uuids[0].compareTo(event.getPlayer().getUniqueId()) == 0) {
              spells.put(e.getKey(), uuids);
            }
          }
        }
        Map.Entry<Spell, ConcurrentHashMap<UUID[], Integer>> e;
        LinkedList<Map.Entry<Spell, UUID[]>> sortedspells = getSortedList(spells);
        for (Map.Entry<Spell, UUID[]> sortedspell : sortedspells) {
          CommandPlayer.scheduleSpell((Spell)sortedspell.getKey(), ((UUID[])sortedspell.getValue())[0], event, ((UUID[])sortedspell.getValue())[1]);
        }
      }
    }
  }
  
  @EventHandler
  public void onPlayerDisconnect(PlayerQuitEvent event)
  {
    deadPlayer.remove(event.getPlayer().getUniqueId());
    revivePlayer.remove(event.getPlayer().getUniqueId());
  }
  
  @EventHandler
  public void onPlayerRespawn(final PlayerRespawnEvent event)
  {
    if (ignoredEvents.contains(event)) {
      return;
    }
    ignoredEvents.add(event);
    Bukkit.getScheduler().scheduleSyncDelayedTask(Ancient.plugin, new Runnable()
    {
      public void run()
      {
        AncientSpellListener.ignoredEvents.remove(event);
      }
    }, 20L);
    if (revivePlayer.containsKey(event.getPlayer().getUniqueId()))
    {
      final Location l = (Location)revivePlayer.get(event.getPlayer().getUniqueId());
      Ancient.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Ancient.plugin, new Runnable()
      {
        public void run()
        {
          event.getPlayer().teleport(l);
        }
      }, 1L);
    }
    deadPlayer.remove(event.getPlayer().getUniqueId());
    revivePlayer.remove(event.getPlayer().getUniqueId());
  }
  
  @EventHandler(priority=EventPriority.HIGHEST)
  public void onPlayerChangeBlock(final BlockBreakEvent event)
  {
    if (event.isCancelled()) {
      return;
    }
    if (ignoredEvents.contains(event)) {
      return;
    }
    ignoredEvents.add(event);
    Bukkit.getScheduler().scheduleSyncDelayedTask(Ancient.plugin, new Runnable()
    {
      public void run()
      {
        AncientSpellListener.ignoredEvents.remove(event);
      }
    }, 20L);
    
    Player mPlayer = event.getPlayer();
    PlayerData pd = PlayerData.getPlayerData(mPlayer.getUniqueId());
    
    HashMap<Spell, UUID[]> spells = new HashMap();
    for (Spell p : ChangeBlockEventSpells) {
      if (AncientClass.spellAvailable(p, pd)) {
        spells.put(p, new UUID[] { mPlayer.getUniqueId(), mPlayer.getUniqueId() });
      }
    }
    for (Iterator i$ = ChangeBlockEventBuffs.entrySet().iterator(); i$.hasNext();)
    {
      e = (Map.Entry)i$.next();
      for (UUID[] uuids : ((ConcurrentHashMap)e.getValue()).keySet()) {
        if (uuids[0].compareTo(event.getPlayer().getUniqueId()) == 0) {
          spells.put(e.getKey(), uuids);
        }
      }
    }
    Map.Entry<Spell, ConcurrentHashMap<UUID[], Integer>> e;
    LinkedList<Map.Entry<Spell, UUID[]>> sortedspells = getSortedList(spells);
    for (Map.Entry<Spell, UUID[]> sortedspell : sortedspells) {
      CommandPlayer.scheduleSpell((Spell)sortedspell.getKey(), ((UUID[])sortedspell.getValue())[0], event, ((UUID[])sortedspell.getValue())[1]);
    }
  }
  
  @EventHandler(priority=EventPriority.HIGHEST)
  public void onPlayerJoin(final PlayerJoinEvent event)
  {
    if (ignoredEvents.contains(event)) {
      return;
    }
    ignoredEvents.add(event);
    Bukkit.getScheduler().scheduleSyncDelayedTask(Ancient.plugin, new Runnable()
    {
      public void run()
      {
        AncientSpellListener.ignoredEvents.remove(event);
      }
    }, 20L);
    
    Player mPlayer = event.getPlayer();
    PlayerData pd = PlayerData.getPlayerData(mPlayer.getUniqueId());
    
    HashMap<Spell, UUID[]> spells = new HashMap();
    for (Spell p : joinEventSpells) {
      if (AncientClass.spellAvailable(p, pd)) {
        spells.put(p, new UUID[] { mPlayer.getUniqueId(), mPlayer.getUniqueId() });
      }
    }
    for (Iterator i$ = joinEventBuffs.entrySet().iterator(); i$.hasNext();)
    {
      e = (Map.Entry)i$.next();
      for (UUID[] uuids : ((ConcurrentHashMap)e.getValue()).keySet()) {
        if (uuids[0].compareTo(event.getPlayer().getUniqueId()) == 0) {
          spells.put(e.getKey(), uuids);
        }
      }
    }
    Map.Entry<Spell, ConcurrentHashMap<UUID[], Integer>> e;
    LinkedList<Map.Entry<Spell, UUID[]>> sortedspells = getSortedList(spells);
    for (Map.Entry<Spell, UUID[]> sortedspell : sortedspells) {
      CommandPlayer.scheduleSpell((Spell)sortedspell.getKey(), ((UUID[])sortedspell.getValue())[0], event, ((UUID[])sortedspell.getValue())[1]);
    }
  }
  
  @EventHandler(priority=EventPriority.HIGHEST)
  public void onPlayerInteract(final PlayerInteractEvent event)
  {
    if (event.isCancelled()) {
      return;
    }
    if (ignoredEvents.contains(event)) {
      return;
    }
    ignoredEvents.add(event);
    Bukkit.getScheduler().scheduleSyncDelayedTask(Ancient.plugin, new Runnable()
    {
      public void run()
      {
        AncientSpellListener.ignoredEvents.remove(event);
      }
    }, 20L);
    
    Player mPlayer = event.getPlayer();
    PlayerData pd = PlayerData.getPlayerData(mPlayer.getUniqueId());
    
    HashMap<Spell, UUID[]> spells = new HashMap();
    for (Spell p : interactEventSpells) {
      if (AncientClass.spellAvailable(p, pd)) {
        spells.put(p, new UUID[] { mPlayer.getUniqueId(), mPlayer.getUniqueId() });
      }
    }
    for (Iterator i$ = interactEventBuffs.entrySet().iterator(); i$.hasNext();)
    {
      e = (Map.Entry)i$.next();
      for (UUID[] uuids : ((ConcurrentHashMap)e.getValue()).keySet()) {
        if (uuids[0].compareTo(event.getPlayer().getUniqueId()) == 0) {
          spells.put(e.getKey(), uuids);
        }
      }
    }
    Map.Entry<Spell, ConcurrentHashMap<UUID[], Integer>> e;
    LinkedList<Map.Entry<Spell, UUID[]>> sortedspells = getSortedList(spells);
    for (Map.Entry<Spell, UUID[]> sortedspell : sortedspells) {
      CommandPlayer.scheduleSpell((Spell)sortedspell.getKey(), ((UUID[])sortedspell.getValue())[0], event, ((UUID[])sortedspell.getValue())[1]);
    }
  }
  
  @EventHandler(priority=EventPriority.HIGHEST)
  public void onPlayerChat(final AsyncPlayerChatEvent event)
  {
    if (event.isCancelled()) {
      return;
    }
    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
    {
      public void run()
      {
        if (AncientSpellListener.ignoredEvents.contains(event)) {
          return;
        }
        AncientSpellListener.ignoredEvents.add(event);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Ancient.plugin, new Runnable()
        {
          public void run()
          {
            AncientSpellListener.ignoredEvents.remove(AncientSpellListener.11.this.val$event);
          }
        }, 20L);
        
        Player mPlayer = event.getPlayer();
        PlayerData pd = PlayerData.getPlayerData(mPlayer.getUniqueId());
        
        HashMap<Spell, UUID[]> spells = new HashMap();
        for (Spell p : AncientSpellListener.chatEventSpells) {
          if (AncientClass.spellAvailable(p, pd)) {
            spells.put(p, new UUID[] { mPlayer.getUniqueId(), mPlayer.getUniqueId() });
          }
        }
        for (Iterator i$ = AncientSpellListener.chatEventBuffs.entrySet().iterator(); i$.hasNext();)
        {
          e = (Map.Entry)i$.next();
          for (UUID[] uuids : ((ConcurrentHashMap)e.getValue()).keySet()) {
            if (uuids[0].compareTo(event.getPlayer().getUniqueId()) == 0) {
              spells.put(e.getKey(), uuids);
            }
          }
        }
        Map.Entry<Spell, ConcurrentHashMap<UUID[], Integer>> e;
        LinkedList<Map.Entry<Spell, UUID[]>> sortedspells = AncientSpellListener.this.getSortedList(spells);
        for (Map.Entry<Spell, UUID[]> sortedspell : sortedspells) {
          CommandPlayer.scheduleSpell((Spell)sortedspell.getKey(), ((UUID[])sortedspell.getValue())[0], event, ((UUID[])sortedspell.getValue())[1]);
        }
      }
    });
  }
  
  @EventHandler(priority=EventPriority.HIGHEST)
  public void onPlayerLevelUp(final AncientLevelupEvent event)
  {
    if (event.isCancelled()) {
      return;
    }
    if (ignoredEvents.contains(event)) {
      return;
    }
    ignoredEvents.add(event);
    Bukkit.getScheduler().scheduleSyncDelayedTask(Ancient.plugin, new Runnable()
    {
      public void run()
      {
        AncientSpellListener.ignoredEvents.remove(event);
      }
    }, 20L);
    
    UUID uuid = event.uuid;
    PlayerData pd = PlayerData.getPlayerData(uuid);
    HashMap<Spell, UUID[]> spells = new HashMap();
    for (Spell p : LevelupEventSpells) {
      if (AncientClass.spellAvailable(p, pd)) {
        spells.put(p, new UUID[] { uuid, uuid });
      }
    }
    for (Iterator i$ = LevelupEventBuffs.entrySet().iterator(); i$.hasNext();)
    {
      e = (Map.Entry)i$.next();
      for (UUID[] p : ((ConcurrentHashMap)e.getValue()).keySet()) {
        if (p[0].compareTo(uuid) == 0) {
          spells.put(e.getKey(), p);
        }
      }
    }
    Map.Entry<Spell, ConcurrentHashMap<UUID[], Integer>> e;
    LinkedList<Map.Entry<Spell, UUID[]>> sortedspells = getSortedList(spells);
    for (Map.Entry<Spell, UUID[]> sortedspell : sortedspells) {
      CommandPlayer.scheduleSpell((Spell)sortedspell.getKey(), ((UUID[])sortedspell.getValue())[0], event, ((UUID[])sortedspell.getValue())[1]);
    }
  }
  
  @EventHandler(priority=EventPriority.HIGHEST)
  public void onPlayerClassChange(final AncientClassChangeEvent event)
  {
    if (event.isCancelled()) {
      return;
    }
    if (ignoredEvents.contains(event)) {
      return;
    }
    ignoredEvents.add(event);
    Bukkit.getScheduler().scheduleSyncDelayedTask(Ancient.plugin, new Runnable()
    {
      public void run()
      {
        AncientSpellListener.ignoredEvents.remove(event);
      }
    }, 20L);
    
    UUID uuid = event.uuid;
    PlayerData pd = PlayerData.getPlayerData(uuid);
    HashMap<Spell, UUID[]> spells = new HashMap();
    for (Spell p : classChangeEventSpells) {
      if (AncientClass.spellAvailable(p, pd)) {
        spells.put(p, new UUID[] { uuid, uuid });
      }
    }
    for (Iterator i$ = classChangeEventBuffs.entrySet().iterator(); i$.hasNext();)
    {
      e = (Map.Entry)i$.next();
      for (UUID[] p : ((ConcurrentHashMap)e.getValue()).keySet()) {
        if (p[0].compareTo(uuid) == 0) {
          spells.put(e.getKey(), p);
        }
      }
    }
    Map.Entry<Spell, ConcurrentHashMap<UUID[], Integer>> e;
    LinkedList<Map.Entry<Spell, UUID[]>> sortedspells = getSortedList(spells);
    for (Map.Entry<Spell, UUID[]> sortedspell : sortedspells) {
      CommandPlayer.scheduleSpell((Spell)sortedspell.getKey(), ((UUID[])sortedspell.getValue())[0], event, ((UUID[])sortedspell.getValue())[1]);
    }
  }
  
  @EventHandler(priority=EventPriority.HIGHEST)
  public void onBedUse(final PlayerBedEnterEvent event)
  {
    if (event.isCancelled()) {
      return;
    }
    if (ignoredEvents.contains(event)) {
      return;
    }
    ignoredEvents.add(event);
    Bukkit.getScheduler().scheduleSyncDelayedTask(Ancient.plugin, new Runnable()
    {
      public void run()
      {
        AncientSpellListener.ignoredEvents.remove(event);
      }
    }, 20L);
    
    Player mPlayer = event.getPlayer();
    PlayerData pd = PlayerData.getPlayerData(mPlayer.getUniqueId());
    
    HashMap<Spell, UUID[]> spells = new HashMap();
    for (Spell p : UseBedEventSpells) {
      if (AncientClass.spellAvailable(p, pd)) {
        spells.put(p, new UUID[] { mPlayer.getUniqueId(), mPlayer.getUniqueId() });
      }
    }
    for (Iterator i$ = UseBedEventBuffs.entrySet().iterator(); i$.hasNext();)
    {
      e = (Map.Entry)i$.next();
      for (UUID[] uuids : ((ConcurrentHashMap)e.getValue()).keySet()) {
        if (uuids[0].compareTo(event.getPlayer().getUniqueId()) == 0) {
          spells.put(e.getKey(), uuids);
        }
      }
    }
    Map.Entry<Spell, ConcurrentHashMap<UUID[], Integer>> e;
    LinkedList<Map.Entry<Spell, UUID[]>> sortedspells = getSortedList(spells);
    for (Map.Entry<Spell, UUID[]> sortedspell : sortedspells) {
      CommandPlayer.scheduleSpell((Spell)sortedspell.getKey(), ((UUID[])sortedspell.getValue())[0], event, ((UUID[])sortedspell.getValue())[1]);
    }
  }
  
  @EventHandler(priority=EventPriority.HIGHEST)
  public void onEntityDeath(final EntityDeathEvent event)
  {
    if (ignoredEvents.contains(event)) {
      return;
    }
    ignoredEvents.add(event);
    Bukkit.getScheduler().scheduleSyncDelayedTask(Ancient.plugin, new Runnable()
    {
      public void run()
      {
        AncientSpellListener.ignoredEvents.remove(event);
      }
    }, 20L);
    
    UUID entityUUID = null;
    for (UUID uuid : AncientEntityListener.scheduledXpList.keySet()) {
      if (uuid.compareTo(event.getEntity().getUniqueId()) == 0)
      {
        entityUUID = uuid;
        break;
      }
    }
    if (entityUUID == null) {
      return;
    }
    Player mPlayer = Bukkit.getServer().getPlayer((UUID)AncientEntityListener.scheduledXpList.get(entityUUID));
    PlayerData pd = PlayerData.getPlayerData(mPlayer.getUniqueId());
    HashMap<Spell, UUID[]> spells = new HashMap();
    for (Spell p : killEntityEventSpells) {
      if (AncientClass.spellAvailable(p, pd)) {
        spells.put(p, new UUID[] { mPlayer.getUniqueId(), mPlayer.getUniqueId() });
      }
    }
    for (Iterator i$ = killEntityEventBuffs.entrySet().iterator(); i$.hasNext();)
    {
      e = (Map.Entry)i$.next();
      for (UUID[] uuids : ((ConcurrentHashMap)e.getValue()).keySet()) {
        if (uuids[0].compareTo(event.getEntity().getUniqueId()) == 0) {
          spells.put(e.getKey(), uuids);
        }
      }
    }
    Map.Entry<Spell, ConcurrentHashMap<UUID[], Integer>> e;
    LinkedList<Map.Entry<Spell, UUID[]>> sortedspells = getSortedList(spells);
    for (Map.Entry<Spell, UUID[]> sortedspell : sortedspells) {
      CommandPlayer.scheduleSpell((Spell)sortedspell.getKey(), ((UUID[])sortedspell.getValue())[0], event, ((UUID[])sortedspell.getValue())[1]);
    }
  }
  
  public static void addIgnoredEvent(Event event)
  {
    ignoredEvents.add(event);
    Bukkit.getScheduler().scheduleSyncDelayedTask(Ancient.plugin, new Runnable()
    {
      public void run()
      {
        AncientSpellListener.ignoredEvents.remove(this.val$event);
      }
    }, 20L);
  }
  
  public LinkedList<Map.Entry<Spell, UUID[]>> getSortedList(HashMap<Spell, UUID[]> collection)
  {
    LinkedList<Map.Entry<Spell, UUID[]>> sortedlist = new LinkedList();
    for (Map.Entry<Spell, UUID[]> entry : collection.entrySet()) {
      if (sortedlist.size() == 0)
      {
        sortedlist.addLast(entry);
      }
      else
      {
        boolean added = false;
        for (int i = 0; i < sortedlist.size(); i++) {
          if (((Spell)((Map.Entry)sortedlist.get(i)).getKey()).priority > ((Spell)entry.getKey()).priority)
          {
            sortedlist.add(i, entry);
            added = true;
            break;
          }
        }
        if (!added) {
          sortedlist.addLast(entry);
        }
      }
    }
    return sortedlist;
  }
  
  public static final ConcurrentHashMap<UUID, Long> lastMoved = new ConcurrentHashMap();
}
