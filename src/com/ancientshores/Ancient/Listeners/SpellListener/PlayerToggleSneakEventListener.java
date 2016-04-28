package com.ancientshores.Ancient.Listeners.SpellListener;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.PlayerData;
import com.ancientshores.Ancient.Classes.AncientClass;
import com.ancientshores.Ancient.Classes.Spells.Spell;
import com.ancientshores.Ancient.Classes.Spells.Commands.CommandPlayer;
import com.ancientshores.Ancient.Listeners.AncientSpellListener;

public class PlayerToggleSneakEventListener extends ISpellListener {
    public PlayerToggleSneakEventListener(Ancient instance) {
        super(instance);
        this.eventName = "playertogglesneakevent";
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEvent(final PlayerToggleSneakEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (AncientSpellListener.ignoredEvents.contains(event)) {
            return;
        } else {
            AncientSpellListener.ignoredEvents.add(event);
            Bukkit.getScheduler().scheduleSyncDelayedTask(Ancient.plugin, new Runnable() {

                @Override
                public void run() {
                    AncientSpellListener.ignoredEvents.remove(event);
                }
            }, 20);
        }
        Player mPlayer = event.getPlayer();
        PlayerData pd = PlayerData.getPlayerData(mPlayer.getUniqueId());

        HashMap<Spell, UUID[]> spells = new HashMap<Spell, UUID[]>();

        for (Spell p : eventSpells) {
            if (AncientClass.spellAvailable(p, pd)) {
                spells.put(p, new UUID[]{mPlayer.getUniqueId(), mPlayer.getUniqueId()});
            }
        }
        for (Entry<Spell, ConcurrentHashMap<UUID[], Integer>> e : eventBuffs.entrySet()) {
            for (UUID uuids[] : e.getValue().keySet()) {
                if (uuids[0].compareTo(event.getPlayer().getUniqueId()) == 0) {
                    spells.put(e.getKey(), uuids);
                }
            }
        }
        LinkedList<Entry<Spell, UUID[]>> sortedspells = getSortedList(spells);
        for (Entry<Spell, UUID[]> sortedspell : sortedspells) {
            CommandPlayer.scheduleSpell(sortedspell.getKey(), sortedspell.getValue()[0], event, sortedspell.getValue()[1]);
        }
    }
}