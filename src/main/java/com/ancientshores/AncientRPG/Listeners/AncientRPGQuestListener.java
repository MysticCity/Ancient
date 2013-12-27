package com.ancientshores.AncientRPG.Listeners;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Quests.AncientRPGQuest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.HashMap;
import java.util.HashSet;

public class AncientRPGQuestListener implements Listener {
    final HashMap<Player, HashSet<AncientRPGQuest>> enabledQuests = new HashMap<Player, HashSet<AncientRPGQuest>>();
    final AncientRPG plugin;

    public AncientRPGQuestListener(AncientRPG instance) {
        plugin = instance;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void registerQuest(AncientRPGQuest quest, Player p) {
        if (!enabledQuests.containsKey(p)) {
            enabledQuests.put(p, new HashSet<AncientRPGQuest>());
        }
        enabledQuests.get(quest).add(quest);
    }

    public void unregisterQuest(AncientRPGQuest quest, Player p) {
        if (enabledQuests.containsKey(p)) {
            enabledQuests.get(p).remove(quest);
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
                            HashSet<AncientRPGQuest> quests = enabledQuests.get(p);
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
