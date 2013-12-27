package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

public class WaterBreathingCommand extends ICommand implements Runnable, Listener {
    public static final ConcurrentHashMap<Entity, Integer> waterbreathingPlayers = new ConcurrentHashMap<Entity, Integer>();

    @CommandDescription(description = "<html>Enables water breathing for the specified amount of time</html>",
            argnames = {"entity", "duration"}, name = "WaterBreathing", parameters = {ParameterType.Entity, ParameterType.Number})
    public WaterBreathingCommand() {
        ParameterType[] buffer = {ParameterType.Entity, ParameterType.Number};
        this.paramTypes = buffer;
        try {
            Bukkit.getScheduler().scheduleSyncRepeatingTask(AncientRPG.plugin, this, 1, 1);
            AncientRPG.plugin.getServer().getPluginManager().registerEvents(this, AncientRPG.plugin);
        } catch (Exception e) {

        }
    }

    @Override
    public boolean playCommand(EffectArgs ca) {
        if (ca.params.size() == 2 && ca.params.get(0) instanceof Entity[] && ca.params.get(1) instanceof Number) {
            Entity[] players = (Entity[]) ca.params.get(0);
            int time = (int) ((Number) ca.params.get(1)).doubleValue();
            for (Entity e : players) {
                if (e == null || !(e instanceof LivingEntity)) {
                    continue;
                }
                int t = Math.round(time / 50);
                if (t == 0) {
                    t = Integer.MAX_VALUE;
                }
                if (waterbreathingPlayers.contains(e)) {
                    if (waterbreathingPlayers.get(e) > time) {
                        return true;
                    }
                }
                waterbreathingPlayers.put(e, t);
            }
            return true;
        }
        return false;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onWaterDamage(EntityDamageEvent event) {
        if (event.getCause().equals(DamageCause.DROWNING) && waterbreathingPlayers.containsKey(event.getEntity())) {
            event.setCancelled(true);
        }
    }

    public void run() {
        HashSet<Entity> removeplayers = new HashSet<Entity>();
        for (Entity p : waterbreathingPlayers.keySet()) {
            int newamount = waterbreathingPlayers.get(p) - 1;
            waterbreathingPlayers.put(p, newamount);
            if (newamount <= 0) {
                removeplayers.add(p);
            }
        }
        for (Entity p : removeplayers) {
            waterbreathingPlayers.remove(p);
        }
    }

}
