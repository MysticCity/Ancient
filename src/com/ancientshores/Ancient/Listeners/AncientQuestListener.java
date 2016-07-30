package com.ancientshores.Ancient.Listeners;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Quests.AncientQuest;

public class AncientQuestListener implements Listener {
    final HashMap<UUID, HashSet<AncientQuest>> enabledQuests = new HashMap<UUID, HashSet<AncientQuest>>();
    final Ancient plugin;

    public AncientQuestListener(Ancient instance) {
        plugin = instance;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void registerQuest(AncientQuest quest, Player p) {
        if (!enabledQuests.containsKey(p.getUniqueId())) {
            enabledQuests.put(p.getUniqueId(), new HashSet<AncientQuest>());
        }
        enabledQuests.get(quest).add(quest);
    }

    public void unregisterQuest(AncientQuest quest, Player p) {
        if (enabledQuests.containsKey(p.getUniqueId())) {
            enabledQuests.get(p.getUniqueId()).remove(quest);
        }
    }

    @EventHandler
    public void entityDamageEvent(final EntityDamageEvent e) {
        Ancient.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Ancient.plugin, new Runnable() {
            public void run() {
                if (e.getEntity().isDead()) {
                    if (e instanceof EntityDamageByEntityEvent) {
                        EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) e;
                        if (event.getDamager() instanceof Player) {
                            Player p = (Player) event.getDamager();
                            HashSet<AncientQuest> quests = enabledQuests.get(p.getUniqueId());
                            for (AncientQuest quest : quests) {
                                quest.checkObjectives();
                            }
                        }
                    }
                }
            }
        }, 1);
    }
}