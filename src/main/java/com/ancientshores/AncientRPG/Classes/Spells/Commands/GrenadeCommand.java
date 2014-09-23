package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.util.Vector;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Listeners.AncientRPGEntityListener;

public class GrenadeCommand extends ICommand {
    @CommandDescription(description = "<html>The caster throws tnt which explodes after the time</html>",
            argnames = {"time"}, name = "Grenade", parameters = {ParameterType.Number})
    public GrenadeCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Number};
    }

    public boolean playCommand(final EffectArgs ca) {
        if (ca.getParams().size() == 1) {
            if (ca.getParams().get(0) instanceof Number) {
                final int time = (int) ((Number) ca.getParams().get(0)).doubleValue();
                Location spawnloc = ca.getCaster().getLocation().add(0, 2, 0);
                final TNTPrimed grenade = ca.getCaster().getWorld().spawn(spawnloc, TNTPrimed.class);
                Vector throwv = ca.getCaster().getLocation().getDirection().normalize();
                grenade.setVelocity(throwv);
                int ticks = Math.round(time / 50);
                if (ticks == 0) {
                    ticks = 1;
                }
                grenade.setFuseTicks(ticks);
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, new Runnable() {
                    public void run() {
                        final List<Entity> entityList = grenade.getLocation().getWorld().getEntities();
                        for (final Entity e : entityList) {
                            if (e.getLocation().distance(grenade.getLocation()) < 4) {
                                if (ca.getCaster().equals(e)) {
                                    continue;
                                }
                                AncientRPGEntityListener.scheduledXpList.put(e.getUniqueId(), ca.getCaster().getUniqueId());
                                AncientRPG.plugin.getServer().getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, new Runnable() {

                                    @Override
                                    public void run() {
                                        AncientRPGEntityListener.scheduledXpList.remove(e.getUniqueId());
                                    }
                                }, Math.round(2));
                            }
                        }
                    }
                }, ticks);
            }
            return true;
        }
        return false;
    }
}