package com.ancientshores.AncientRPG.Listeners.SpellListener;

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

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.Spell;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import com.ancientshores.AncientRPG.Listeners.AncientRPGSpellListener;

public abstract class ISpellListener implements Listener {
    public final HashSet<Spell> eventSpells = new HashSet<Spell>();
    public final ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>> eventBuffs = new ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>>();
    public String eventName;
    public static int buffId = 0;
    public final AncientRPG plugin;

    public ISpellListener(AncientRPG instance) {
        plugin = instance;
    }

    public int attachBuffToEvent(Spell s, Player[] players) {
        if (s == null) {
            return Integer.MAX_VALUE;
        }
        if (!eventBuffs.containsKey(s)) {
            eventBuffs.put(s, new ConcurrentHashMap<UUID[], Integer>());
        }
       
        UUID[] uuids = new UUID[players.length];
        for (int i = 0; i < players.length; i++) {
        	uuids[i] = players[i].getUniqueId();
        }
        
        eventBuffs.get(s).put(uuids, buffId);
        int oldbuffId = buffId;
        buffId++;
        if (buffId > Integer.MAX_VALUE / 2) {
            buffId = 0;
        }
        return oldbuffId;
    }

    public void detachBuffOfEvent(Spell s, Player[] p, int id) {
        if (!eventBuffs.containsKey(s)) {
            return;
        }
        
        UUID[] uuids = new UUID[p.length];
        for (int i = 0; i < p.length; i++) {
        	uuids[i] = p[i].getUniqueId();
        }
        
        ConcurrentHashMap<UUID[], Integer> innerMap = eventBuffs.get(s);
        HashSet<UUID[]> removeBuffs = new HashSet<UUID[]>();
        if (innerMap == null) {
            return;
        }
        for (UUID[] uuids2 : innerMap.keySet()) {
            if (uuids2.length == 2 && uuids2[0] == uuids[0] && uuids2[1] == uuids[1]) {
                if (innerMap.get(uuids2) != null && innerMap.get(uuids2) == id) {
                    removeBuffs.add(uuids2);
                }
            }
        }
        for (UUID[] uuid : removeBuffs) {
            innerMap.remove(uuid);
        }
    }

    public ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>> getAllBuffs() {
       return this.eventBuffs;
    }

    public void clear() {
        eventSpells.clear();
        eventBuffs.clear();
    }

    public void removeDisruptCommand(Player p, SpellInformationObject spell) {
        eventBuffs.get(p).remove(spell);
    }

    public void detachBuffOfEvent(String buffEvent, Spell sp, Player p) {
        if (buffEvent == null) {
            return;
        }
        
        ConcurrentHashMap<Spell, ConcurrentHashMap<UUID[], Integer>> buffs = this.eventBuffs;
        ConcurrentHashMap<UUID[], Integer> innerMap = buffs.get(sp);
        HashSet<UUID[]> removeBuffs = new HashSet<UUID[]>();
        if (innerMap == null) {
            return;
        }
        for (UUID[] uuids : innerMap.keySet()) {
            if (uuids.length == 2 && (uuids[0].compareTo(p.getUniqueId()) == 0 || uuids[1].compareTo(p.getUniqueId()) == 0)) {
                if (innerMap.get(uuids) != null) {
                    removeBuffs.add(uuids);
                }
            }
        }
        for (UUID[] uuids : removeBuffs) {
            innerMap.remove(uuids);
        }
    }

    public static void addIgnoredEvent(final Event event) {
        AncientRPGSpellListener.ignoredEvents.add(event);
        Bukkit.getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, new Runnable() {

            @Override
            public void run() {
                AncientRPGSpellListener.ignoredEvents.remove(event);
            }
        }, 20);
    }

    public LinkedList<Entry<Spell, UUID[]>> getSortedList(HashMap<Spell, UUID[]> collection) {
        LinkedList<Entry<Spell, UUID[]>> sortedlist = new LinkedList<Entry<Spell, UUID[]>>();
        for (Entry<Spell, UUID[]> entry : collection.entrySet()) {
            if (sortedlist.size() == 0) {
                sortedlist.addLast(entry);
                continue;
            }
            boolean added = false;
            for (int i = 0; i < sortedlist.size(); i++) {
                if (sortedlist.get(i).getKey().priority > entry.getKey().priority) {
                    sortedlist.add(i, entry);
                    added = true;
                    break;
                }
            }
            if (!added) {
                sortedlist.addLast(entry);
            }
        }
        return sortedlist;
    }
}