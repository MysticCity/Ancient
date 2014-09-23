package com.ancientshores.AncientRPG.Listeners.SpellListener;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.PlayerData;
import com.ancientshores.AncientRPG.Classes.AncientRPGClass;
import com.ancientshores.AncientRPG.Classes.Spells.Spell;
import com.ancientshores.AncientRPG.Classes.Spells.Commands.CommandPlayer;
import com.ancientshores.AncientRPG.Listeners.AncientRPGSpellListener;

public class PlayerTeleportEventListener extends ISpellListener {
    public PlayerTeleportEventListener(AncientRPG instance) {
        super(instance);
        this.eventName = "playerteleportevent";
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEvent(final PlayerTeleportEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (AncientRPGSpellListener.ignoredEvents.contains(event)) {
            return;
        } else {
            AncientRPGSpellListener.ignoredEvents.add(event);
            Bukkit.getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, new Runnable() {

                @Override
                public void run() {
                    AncientRPGSpellListener.ignoredEvents.remove(event);
                }
            }, 20);
        }
        Player mPlayer = event.getPlayer();
        PlayerData pd = PlayerData.getPlayerData(mPlayer.getUniqueId());

        HashMap<Spell, UUID[]> spells = new HashMap<Spell, UUID[]>();

        for (Spell p : eventSpells) {
            if (AncientRPGClass.spellAvailable(p, pd)) {
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