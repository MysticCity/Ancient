package com.ancientshores.AncientRPG.Listeners;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Quests.AncientRPGQuest;

public class AncientRPGQuestListener implements Listener {
    final HashMap<UUID, HashSet<AncientRPGQuest>> enabledQuests = new HashMap<UUID, HashSet<AncientRPGQuest>>();
    final AncientRPG plugin;

    public AncientRPGQuestListener(AncientRPG instance) {
        plugin = instance;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void registerQuest(AncientRPGQuest quest, Player p) {
        if (!enabledQuests.containsKey(p.getUniqueId())) {
            enabledQuests.put(p.getUniqueId(), new HashSet<AncientRPGQuest>());
        }
        enabledQuests.get(quest).add(quest);
    }

    public void unregisterQuest(AncientRPGQuest quest, Player p) {
        if (enabledQuests.containsKey(p.getUniqueId())) {
            enabledQuests.get(p.getUniqueId()).remove(quest);
        }
    }

    @EventHandler
    public void entityDamageEvent(final EntityDamageEvent e) {
        AncientRPG.plugin.getServer().getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, new Runnable() {
            public void run() {
                if (e.getEntity().isDead()) {
                    if (e instanceof EntityDamageByEntityEvent) {
                        EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) e;
                        if (event.getDamager() instanceof Player) {
                            Player p = (Player) event.getDamager();
                            HashSet<AncientRPGQuest> quests = enabledQuests.get(p.getUniqueId());
                            for (AncientRPGQuest quest : quests) {
                                quest.checkObjectives();
                            }
                        }
                    }
                }
            }
        }, 1);
    }
}