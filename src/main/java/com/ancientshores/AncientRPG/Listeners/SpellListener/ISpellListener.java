package com.ancientshores.AncientRPG.Listeners.SpellListener;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.Spell;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import com.ancientshores.AncientRPG.Listeners.AncientRPGSpellListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public abstract class ISpellListener implements Listener {
    public final HashSet<Spell> eventSpells = new HashSet<Spell>();
    public final ConcurrentHashMap<Spell, ConcurrentHashMap<Player[], Integer>> eventBuffs = new ConcurrentHashMap<Spell, ConcurrentHashMap<Player[], Integer>>();
    public String eventName;
    public static int buffId = 0;
    public final AncientRPG plugin;

    public ISpellListener(AncientRPG instance) {
        plugin = instance;
    }

    public int attachBuffToEvent(Spell s, Player[] p) {
        if (s == null) {
            return Integer.MAX_VALUE;
        }
        if (!eventBuffs.containsKey(s)) {
            eventBuffs.put(s, new ConcurrentHashMap<Player[], Integer>());
        }
        eventBuffs.get(s).put(p, buffId);
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
        ConcurrentHashMap<Player[], Integer> innerMap = eventBuffs.get(s);
        HashSet<Player[]> removeBuffs = new HashSet<Player[]>();
        if (innerMap == null) {
            return;
        }
        for (Player[] players : innerMap.keySet()) {
            if (players.length == 2 && players[0] == p[0] && players[1] == p[1]) {
                if (innerMap.get(players) != null && innerMap.get(players) == id) {
                    removeBuffs.add(players);
                }
            }
        }
        for (Player[] pl : removeBuffs) {
            innerMap.remove(pl);
        }
    }

    public ConcurrentHashMap<Spell, ConcurrentHashMap<Player[], Integer>> getAllBuffs() {
        ConcurrentHashMap<Spell, ConcurrentHashMap<Player[], Integer>> map = new ConcurrentHashMap<Spell, ConcurrentHashMap<Player[], Integer>>();
        map.putAll(this.eventBuffs);
        return map;
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
        ConcurrentHashMap<Spell, ConcurrentHashMap<Player[], Integer>> buffs = this.eventBuffs;
        ConcurrentHashMap<Player[], Integer> innerMap = buffs.get(sp);
        HashSet<Player[]> removeBuffs = new HashSet<Player[]>();
        if (innerMap == null) {
            return;
        }
        for (Player[] players : innerMap.keySet()) {
            if (players.length == 2 && (players[0] == p || players[1] == p)) {
                if (innerMap.get(players) != null) {
                    removeBuffs.add(players);
                }
            }
        }
        for (Player[] pl : removeBuffs) {
            innerMap.remove(pl);
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

    public LinkedList<Entry<Spell, Player[]>> getSortedList(HashMap<Spell, Player[]> collection) {
        LinkedList<Entry<Spell, Player[]>> sortedlist = new LinkedList<Entry<Spell, Player[]>>();
        for (Entry<Spell, Player[]> entry : collection.entrySet()) {
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