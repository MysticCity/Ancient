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
        this.paramTypes = new ParameterType[]{ParameterType.Location, ParameterType.Number};
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        try {
            if (ca.getParams().get(0) instanceof Location[]) {
                final Location[] loc = (Location[]) ca.getParams().get(0);
                int yield = 4;
                if (ca.getParams().size() == 2 && ca.getParams().get(1) instanceof Number) {
                    yield = (int) ((Number) ca.getParams().get(1)).doubleValue();
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
                                TNTPrimed tnt = ca.getCaster().getWorld().spawn(l, TNTPrimed.class);
                                tnt.setYield(lol);
                                tnt.setFuseTicks(0);
                                //ca.getCaster().getWorld().createExplosion(l, 4F);
                                for (final Entity e : entityList) {
                                    if (e.getLocation().distance(l) < 4) {
                                        if (ca.getCaster().equals(e)) {
                                            continue;
                                        }
                                        AncientRPGEntityListener.scheduledXpList.put(e, ca.getCaster());
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
            } else if (ca.getSpell().active) {
                ca.getCaster().sendMessage("No target in range");
            }
        } catch (IndexOutOfBoundsException ignored) {

        }
        return false;
    }
}