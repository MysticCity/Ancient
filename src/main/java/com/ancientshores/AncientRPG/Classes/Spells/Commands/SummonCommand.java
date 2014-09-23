package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Tameable;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Listeners.AncientRPGPlayerListener;

public class SummonCommand extends ICommand {
    @CommandDescription(description = "<html>Summons the amount of creatures at the specified location for the specified amount of time</html>",
            argnames = {"location", "creaturename", "duration", "amount"}, name = "Summon", parameters = {ParameterType.Location, ParameterType.String, ParameterType.Number, ParameterType.Number})
    public SummonCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Location, ParameterType.String, ParameterType.Number, ParameterType.Number};
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        try {
            if (ca.getParams().get(0) instanceof Location[] && ca.getParams().get(1) instanceof String && ca.getParams().get(2) instanceof Number && ca.getParams().get(3) instanceof Number) {
                final Location[] loca = (Location[]) ca.getParams().get(0);
                final String cName = (String) ca.getParams().get(1);
                final int time = (int) ((Number) ca.getParams().get(2)).doubleValue();
                final int amount = (int) ((Number) ca.getParams().get(3)).doubleValue();
                if (loca != null && cName != null) {
                    final EntityType e = getCreatureTypeOfString(cName);
                    if (e != null) {
                        AncientRPG.plugin.scheduleThreadSafeTask(AncientRPG.plugin, new Runnable() {
                            public void run() {
                                for (Location loc : loca) {
                                    if (loc == null) {
                                        continue;
                                    }
                                    for (int i = 0; i < amount; i++) {
                                        final Entity mob = ca.getCaster().getWorld().spawnEntity(loc, e);

                                        if (mob instanceof Tameable) {
                                            ((Tameable) mob).setOwner(ca.getCaster());
                                        }
                                        if (time > 0) {
                                            AncientRPGPlayerListener.summonedCreatures.put(mob.getUniqueId(), ca.getCaster().getUniqueId());
                                            AncientRPG.plugin.getServer().getScheduler().scheduleSyncDelayedTask(AncientRPG.plugin, new Runnable() {
                                                public void run() {
                                                    mob.remove();
                                                    AncientRPGPlayerListener.summonedCreatures.remove(mob);
                                                }
                                            }, Math.round(time / 50));
                                        }
                                    }
                                }
                            }
                        });
                        return true;
                    }
                }
            }
        } catch (IndexOutOfBoundsException ignored) {
        }
        return false;
    }

    public static EntityType getCreatureTypeOfString(String s) {
        for (EntityType e : EntityType.values()) {
            if (s.equalsIgnoreCase(e.name())) {
                return e;
            }
        }
        AncientRPG.plugin.getLogger().log(Level.WARNING, "AncientRPG: creature " + s + " not found");
        return null;
    }
}