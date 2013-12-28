package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Listeners.AncientRPGEntityListener;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.TNTPrimed;

import java.util.List;

public class ExplosionCommand extends ICommand {
    @CommandDescription(description = "<html>Creates an explosion with the specified strength at the location</html>",
            argnames = {"location", "radius"}, name = "Explosion", parameters = {ParameterType.Location, ParameterType.Number})
    public ExplosionCommand() {
        ParameterType[] buffer = {ParameterType.Location, ParameterType.Number};
        this.paramTypes = buffer;
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        try {
            if (ca.params.get(0) instanceof Location[]) {
                final Location[] loc = (Location[]) ca.params.get(0);
                int yield = 4;
                if (ca.params.size() == 2 && ca.params.get(1) instanceof Number) {
                    yield = (int) ((Number) ca.params.get(1)).doubleValue();
                }
                final int lol = yield;
                if (loc != null) {
                    AncientRPG.plugin.scheduleThreadSafeTask(AncientRPG.plugin, new Runnable() {

                        @Override
                        public void run() {
                            for (final Location l : loc) {
                                if (l == null) {
                                    continue;
                                }
                                final List<Entity> entityList = l.getWorld().getEntities();
                                TNTPrimed tnt = ca.caster.getWorld().spawn(l, TNTPrimed.class);
                                tnt.setYield(lol);
                                tnt.setFuseTicks(0);
                                //ca.caster.getWorld().createExplosion(l, 4F);
                                for (final Entity e : entityList) {
                                    if (e.getLocation().distance(l) < 4) {
                                        if (ca.caster.equals(e)) {
                                            continue;
                                        }
                                        AncientRPGEntityListener.scheduledXpList.put(e, ca.caster);
                                        AncientRPG.plugin.scheduleThreadSafeTask(AncientRPG.plugin, new Runnable() {

                                            @Override
                                            public void run() {
                                                AncientRPGEntityListener.scheduledXpList.remove(e);
                                            }
                                        }, Math.round(1000 / 50));
                                    }
                                }
                            }
                        }
                    });
                    return true;
                }
            } else if (ca.p.active) {
                ca.caster.sendMessage("No target in range");
            }
        } catch (IndexOutOfBoundsException ignored) {

        }
        return false;
    }
}