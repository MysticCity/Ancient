package com.ancientshores.Ancient.Classes.Spells;

import com.ancientshores.Ancient.Listeners.AncientSpellListener;
import com.ancientshores.Ancient.Party.AncientParty;
import com.ancientshores.Ancient.Spells.Commands.AddSpellFreeZoneCommand;
import com.ancientshores.Ancient.Util.GlobalMethods;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.Event;

public class SpellInformationObject
{
  public Entity nearestPlayer;
  public Entity nearestEntity;
  public Entity[] nearestPlayers;
  public Entity[] partyMembers;
  public Entity[] hostilePlayers;
  public Entity[] nearestEntities;
  public Entity[] hostileEntities;
  public Location blockInSight;
  public Entity buffcaster;
  public int skipedCommands = 0;
  public int waittime = 0;
  public Entity nearestPlayerInSight;
  public Entity nearestEntityInSight;
  public Event mEvent;
  public boolean canceled;
  public Command command;
  public boolean working;
  public boolean finished = false;
  public final HashMap<String, Variable> variables = new HashMap();
  public Spell mSpell;
  public String[] chatmessage;
  
  public Collection<Entity> removeEntitiesInSpellfreeZone(Collection<Entity> entityset)
  {
    if (this.mSpell.ignorespellfreezones) {
      return entityset;
    }
    HashSet<Entity> removeSet = new HashSet();
    for (Entity e : entityset) {
      if (!(e instanceof LivingEntity)) {
        removeSet.add(e);
      } else if (AddSpellFreeZoneCommand.isLocationInSpellfreeZone(e.getLocation())) {
        removeSet.add(e);
      }
    }
    entityset.removeAll(removeSet);
    return entityset;
  }
  
  public Collection<UUID> removeUUIDsInSpellfreeZone(Collection<UUID> uuidset)
  {
    if (this.mSpell.ignorespellfreezones) {
      return uuidset;
    }
    HashSet<UUID> removeSet = new HashSet();
    for (UUID uuid : uuidset)
    {
      Entity e = null;
      for (World w : Bukkit.getWorlds()) {
        for (Entity ent : w.getEntities()) {
          if (ent.getUniqueId().compareTo(uuid) == 0)
          {
            e = ent;
            break;
          }
        }
      }
      if ((e == null) || (!(e instanceof LivingEntity)) || (AddSpellFreeZoneCommand.isLocationInSpellfreeZone(e.getLocation()))) {
        removeSet.add(uuid);
      }
    }
    uuidset.removeAll(removeSet);
    return uuidset;
  }
  
  public Collection<Block> removeBlocksInSpellfreeZone(Collection<Block> blockset)
  {
    if (this.mSpell.ignorespellfreezones) {
      return blockset;
    }
    HashSet<Block> removeSet = new HashSet();
    for (Block b : blockset) {
      if (AddSpellFreeZoneCommand.isLocationInSpellfreeZone(b.getLocation())) {
        removeSet.add(b);
      }
    }
    blockset.removeAll(removeSet);
    return blockset;
  }
  
  public Entity getNearestEntity(Player mPlayer, int range)
  {
    Collection<Entity> nearbyentities = new ArrayList();
    for (Entity e : mPlayer.getNearbyEntities(range, range, range)) {
      nearbyentities.add(e);
    }
    Collection<Entity> entityset = removeEntitiesInSpellfreeZone(nearbyentities);
    Entity nearestEntity = null;
    double curdif = 100000.0D;
    try
    {
      for (Entity e : entityset) {
        if (((e instanceof Creature)) || ((e instanceof Player)))
        {
          double dif = e.getLocation().distance(mPlayer.getLocation());
          if ((dif < curdif) && (e != mPlayer))
          {
            curdif = dif;
            nearestEntity = e;
          }
        }
      }
      return nearestEntity;
    }
    catch (Exception ignored) {}
    return null;
  }
  
  public Entity[] getNearestEntities(Location loc, int range, int count)
  {
    Collection<Entity> entityset = new ArrayList();
    for (Entity e : loc.getWorld().getEntities()) {
      entityset.add(e);
    }
    entityset = removeEntitiesInSpellfreeZone(entityset);
    Entity[] nearestEntity = new Entity[count];
    HashSet<Entity> alreadyParsed = new HashSet();
    double curdif;
    for (int i = 0; i < count; i++)
    {
      curdif = 100000.0D;
      for (Entity e : entityset) {
        if (((e instanceof Creature)) || ((e instanceof Player)))
        {
          double dif = e.getLocation().distance(loc);
          if ((dif < curdif) && (dif < range) && (!alreadyParsed.contains(e)))
          {
            curdif = dif;
            nearestEntity[i] = e;
            alreadyParsed.add(nearestEntity[i]);
          }
        }
      }
    }
    return (Entity[])GlobalMethods.removeNullArrayCells(nearestEntity);
  }
  
  public Entity getNearestPlayer(Player mPlayer, int range)
  {
    Collection<Entity> entityset = new ArrayList();
    for (Entity e : mPlayer.getNearbyEntities(range, range, range)) {
      entityset.add(e);
    }
    entityset = removeEntitiesInSpellfreeZone(entityset);
    
    entityset.addAll(AncientSpellListener.deadPlayer);
    
    Entity nearestPlayer = null;
    double curdif = Double.MAX_VALUE;
    for (Entity e : entityset) {
      if ((e instanceof Player))
      {
        double dif = e.getLocation().distance(mPlayer.getLocation());
        if ((dif <= range) && (dif < curdif) && (e != mPlayer))
        {
          curdif = dif;
          nearestPlayer = e;
        }
      }
    }
    return nearestPlayer;
  }
  
  public Entity getNearestEntityInSight(LivingEntity le, int range)
  {
    HashSet<Byte> list = new HashSet();
    list.add(Byte.valueOf((byte)9));
    list.add(Byte.valueOf((byte)8));
    list.add(Byte.valueOf((byte)Material.AIR.getId()));
    list.add(Byte.valueOf((byte)Material.GRASS.getId()));
    list.add(Byte.valueOf((byte)Material.VINE.getId()));
    list.add(Byte.valueOf((byte)Material.SNOW.getId()));
    list.add(Byte.valueOf((byte)Material.ICE.getId()));
    List<Block> blocksInSight = le.getLineOfSight(list, range);
    Entity nearestEntity = null;
    double curdif = 100000.0D;
    
    Collection<Entity> entityset = new ArrayList();
    for (Entity e : le.getNearbyEntities(range, range, range)) {
      entityset.add(e);
    }
    entityset = removeEntitiesInSpellfreeZone(entityset);
    for (Iterator i$ = blocksInSight.iterator(); i$.hasNext();)
    {
      b = (Block)i$.next();
      if (b.getLocation().distance(le.getLocation()) > 2.4000000953674316D) {
        for (Entity e : entityset)
        {
          double dif = Math.abs(e.getLocation().distance(b.getLocation()));
          if ((dif < 2.5D) && 
            (dif < curdif) && (e != le) && (((e instanceof Creature)) || ((e instanceof Player)) || ((e instanceof Ghast)) || ((e instanceof Slime))))
          {
            curdif = dif;
            nearestEntity = e;
          }
        }
      }
    }
    Block b;
    return nearestEntity;
  }
  
  public Entity getNearestPlayerInSight(Player mPlayer, int range)
  {
    HashSet<Byte> list = new HashSet();
    list.add(Byte.valueOf((byte)9));
    list.add(Byte.valueOf((byte)8));
    list.add(Byte.valueOf((byte)Material.AIR.getId()));
    list.add(Byte.valueOf((byte)Material.GRASS.getId()));
    list.add(Byte.valueOf((byte)Material.VINE.getId()));
    list.add(Byte.valueOf((byte)Material.SNOW.getId()));
    list.add(Byte.valueOf((byte)Material.ICE.getId()));
    List<Block> blocksInSight = mPlayer.getLineOfSight(list, range);
    
    Entity nearestPlayer = null;
    
    Collection<Entity> entityset = new ArrayList();
    for (Entity e : mPlayer.getNearbyEntities(range, range, range)) {
      entityset.add(e);
    }
    entityset = removeEntitiesInSpellfreeZone(entityset);
    
    entityset.addAll(AncientSpellListener.deadPlayer);
    for (Iterator i$ = blocksInSight.iterator(); i$.hasNext();)
    {
      b = (Block)i$.next();
      curdif = 100000.0D;
      for (Entity e : entityset) {
        if ((b.getLocation().distance(mPlayer.getLocation()) > 2.4000000953674316D) && 
          ((e instanceof Player)))
        {
          double dif = Math.abs(e.getLocation().distance(b.getLocation()));
          if ((dif < 2.5D) && 
            (dif <= range) && (dif < curdif) && (e != mPlayer))
          {
            curdif = dif;
            nearestPlayer = e;
          }
        }
      }
    }
    Block b;
    double curdif;
    return nearestPlayer;
  }
  
  public Location getBlockInSight(Player mPlayer, int range)
  {
    HashSet<Byte> list = new HashSet();
    list.add(Byte.valueOf((byte)9));
    list.add(Byte.valueOf((byte)8));
    list.add(Byte.valueOf((byte)Material.AIR.getId()));
    list.add(Byte.valueOf((byte)Material.GRASS.getId()));
    list.add(Byte.valueOf((byte)Material.VINE.getId()));
    list.add(Byte.valueOf((byte)Material.SNOW.getId()));
    Collection<Block> blocksInSight = removeBlocksInSpellfreeZone(mPlayer.getLastTwoTargetBlocks(list, range));
    for (Block b : blocksInSight) {
      if (b.getType() != Material.AIR) {
        return b.getLocation();
      }
    }
    return null;
  }
  
  public Entity[] getNearestPlayers(Player mPlayer, int range, int count)
  {
    Entity[] nearestPlayer = new Entity[count];
    
    Collection<Entity> entityset = new ArrayList();
    for (Entity e : mPlayer.getNearbyEntities(range, range, range)) {
      entityset.add(e);
    }
    entityset = removeEntitiesInSpellfreeZone(entityset);
    
    HashSet<Entity> alreadyParsed = new HashSet();
    
    double curdif = 100000.0D;
    for (int i = 0; i < count; i++)
    {
      for (Entity e : entityset) {
        if ((e instanceof Player))
        {
          double dif = Math.sqrt(Math.abs(e.getLocation().getX() - mPlayer.getLocation().getX()) + Math.abs(e.getLocation().getY() - mPlayer.getLocation().getY()) + Math.abs(e.getLocation().getZ() - mPlayer.getLocation().getZ()));
          if ((dif < curdif) && (e != mPlayer) && (!alreadyParsed.contains(e)))
          {
            curdif = dif;
            nearestPlayer[i] = e;
            alreadyParsed.add(e);
          }
        }
      }
      alreadyParsed.add(nearestPlayer[i]);
    }
    return (Entity[])GlobalMethods.removeNullArrayCells(nearestPlayer);
  }
  
  public Entity[] getNearestHostilePlayers(Player mPlayer, int range, int count)
  {
    Entity[] nearestPlayer = new Entity[count];
    
    Collection<Entity> entityset = new ArrayList();
    for (Entity e : mPlayer.getNearbyEntities(range, range, range)) {
      entityset.add(e);
    }
    entityset = removeEntitiesInSpellfreeZone(entityset);
    
    HashSet<Entity> alreadyParsed = new HashSet();
    double curdif = 100000.0D;
    AncientParty mParty = AncientParty.getPlayersParty(mPlayer.getUniqueId());
    HashSet<UUID> partyMembers = new HashSet();
    if (mParty != null) {
      partyMembers.addAll(mParty.getMembers());
    }
    for (int i = 0; i < count; i++)
    {
      for (Entity e : entityset) {
        if ((e instanceof Player))
        {
          double dif = Math.sqrt(Math.abs(e.getLocation().getX() - mPlayer.getLocation().getX()) + Math.abs(e.getLocation().getY() - mPlayer.getLocation().getY()) + Math.abs(e.getLocation().getZ() - mPlayer.getLocation().getZ()));
          if ((dif < curdif) && (e != mPlayer) && (!alreadyParsed.contains(e)) && (!partyMembers.contains(e.getUniqueId())))
          {
            curdif = dif;
            nearestPlayer[i] = e;
            alreadyParsed.add(e);
          }
        }
      }
      alreadyParsed.add(nearestPlayer[i]);
    }
    return (Entity[])GlobalMethods.removeNullArrayCells(nearestPlayer);
  }
  
  public Entity[] getNearestEntities(Player mPlayer, int range, int count)
  {
    Collection<Entity> entityset = new ArrayList();
    for (Entity e : mPlayer.getNearbyEntities(range, range, range)) {
      entityset.add(e);
    }
    entityset = removeEntitiesInSpellfreeZone(entityset);
    
    Entity[] nearestEntity = new Entity[count];
    HashSet<Entity> alreadyParsed = new HashSet();
    for (int i = 0; i < count; i++)
    {
      double curdif = 100000.0D;
      for (Entity e : entityset) {
        if (((e instanceof Creature)) || ((e instanceof Player)))
        {
          double dif = e.getLocation().distance(mPlayer.getLocation());
          if ((dif < curdif) && (e != mPlayer) && (!alreadyParsed.contains(e)))
          {
            curdif = dif;
            nearestEntity[i] = e;
          }
        }
      }
      alreadyParsed.add(nearestEntity[i]);
    }
    return (Entity[])GlobalMethods.removeNullArrayCells(nearestEntity);
  }
  
  public Entity[] getNearestHostileEntities(Player mPlayer, int range, int count)
  {
    List<Entity> entityset = mPlayer.getNearbyEntities(range, range, range);
    Entity[] nearestEntity = new Entity[count];
    HashSet<Entity> alreadyParsed = new HashSet();
    AncientParty mParty = AncientParty.getPlayersParty(mPlayer.getUniqueId());
    HashSet<UUID> partyMembers = new HashSet();
    if (mParty != null) {
      partyMembers.addAll(mParty.getMembers());
    }
    partyMembers = (HashSet)removeUUIDsInSpellfreeZone(partyMembers);
    for (int i = 0; i < count; i++)
    {
      double curdif = 100000.0D;
      for (Entity e : entityset) {
        if (((e instanceof Creature)) || ((e instanceof Player)))
        {
          double dif = e.getLocation().distance(mPlayer.getLocation());
          if ((dif < curdif) && (e != mPlayer) && (!alreadyParsed.contains(e)) && (!partyMembers.contains(e.getUniqueId())))
          {
            curdif = dif;
            nearestEntity[i] = e;
          }
        }
      }
      alreadyParsed.add(nearestEntity[i]);
    }
    return (Entity[])GlobalMethods.removeNullArrayCells(nearestEntity);
  }
  
  public Entity[] getPartyMembers(Player mPlayer, int range)
  {
    AncientParty mParty = AncientParty.getPlayersParty(mPlayer.getUniqueId());
    if (mParty == null) {
      return new Entity[0];
    }
    Collection<Entity> entities = new HashSet();
    Collection<UUID> partyMembers = new HashSet(mParty.getMembers());
    for (Iterator<UUID> iterator = partyMembers.iterator(); iterator.hasNext();)
    {
      UUID uuid = (UUID)iterator.next();
      if ((uuid == null) || (Bukkit.getPlayer(uuid) == null) || (Bukkit.getPlayer(uuid).getLocation().distance(mPlayer.getLocation()) > range)) {
        entities.add(Bukkit.getPlayer(uuid));
      }
    }
    return (Entity[])GlobalMethods.removeNullArrayCells(entities.toArray(new Entity[entities.size()]));
  }
  
  public int parseVariable(UUID uuid, String var)
  {
    return Variables.getParameterIntByString(uuid, var, this.mSpell.newConfigFile);
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\SpellInformationObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */