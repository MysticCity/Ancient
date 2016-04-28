package com.ancientshores.Ancient.Listeners.SpellListener;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.Spell;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import com.ancientshores.Ancient.Listeners.AncientSpellListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitScheduler;

public abstract class ISpellListener
  implements Listener
{
  public final HashSet<Spell> eventSpells = new HashSet();
  public final ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>> eventBuffs = new ConcurrentHashMap();
  public String eventName;
  public static int buffId = 0;
  public final Ancient plugin;
  
  public ISpellListener(Ancient instance)
  {
    this.plugin = instance;
  }
  
  public int attachBuffToEvent(Spell s, Player[] players)
  {
    if (s == null) {
      return Integer.MAX_VALUE;
    }
    if (!this.eventBuffs.containsKey(s)) {
      this.eventBuffs.put(s, new ConcurrentHashMap());
    }
    UUID[] uuids = new UUID[players.length];
    for (int i = 0; i < players.length; i++) {
      uuids[i] = players[i].getUniqueId();
    }
    ((ConcurrentHashMap)this.eventBuffs.get(s)).put(uuids, Integer.valueOf(buffId));
    int oldbuffId = buffId;
    buffId += 1;
    if (buffId > 1073741823) {
      buffId = 0;
    }
    return oldbuffId;
  }
  
  public void detachBuffOfEvent(Spell s, Player[] p, int id)
  {
    if (!this.eventBuffs.containsKey(s)) {
      return;
    }
    UUID[] uuids = new UUID[p.length];
    for (int i = 0; i < p.length; i++) {
      uuids[i] = p[i].getUniqueId();
    }
    ConcurrentHashMap<UUID[], Integer> innerMap = (ConcurrentHashMap)this.eventBuffs.get(s);
    HashSet<UUID[]> removeBuffs = new HashSet();
    if (innerMap == null) {
      return;
    }
    for (UUID[] uuids2 : innerMap.keySet()) {
      if ((uuids2.length == 2) && (uuids2[0] == uuids[0]) && (uuids2[1] == uuids[1]) && 
        (innerMap.get(uuids2) != null) && (((Integer)innerMap.get(uuids2)).intValue() == id)) {
        removeBuffs.add(uuids2);
      }
    }
    for (UUID[] uuid : removeBuffs) {
      innerMap.remove(uuid);
    }
  }
  
  public ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>> getAllBuffs()
  {
    return this.eventBuffs;
  }
  
  public void clear()
  {
    this.eventSpells.clear();
    this.eventBuffs.clear();
  }
  
  public void removeDisruptCommand(Player p, SpellInformationObject spell)
  {
    ((ConcurrentHashMap)this.eventBuffs.get(p)).remove(spell);
  }
  
  public void detachBuffOfEvent(String buffEvent, Spell sp, Player p)
  {
    if (buffEvent == null) {
      return;
    }
    ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>> buffs = this.eventBuffs;
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
  
  public static void addIgnoredEvent(Event event)
  {
    AncientSpellListener.ignoredEvents.add(event);
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
}
