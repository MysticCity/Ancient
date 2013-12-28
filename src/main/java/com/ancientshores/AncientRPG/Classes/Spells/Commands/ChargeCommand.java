package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import org.bukkit.Location;

public class ChargeCommand extends ICommand {
    @CommandDescription(description = "<html>The player charges to the location</html>",
            argnames = {"location", "speed", "maxdistance"}, name = "Charge", parameters = {ParameterType.Location, ParameterType.Number, ParameterType.Number})

    public ChargeCommand() {
        ParameterType[] buffer = {ParameterType.Location, ParameterType.Number, ParameterType.Number};
        this.paramTypes = buffer;
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        try {
            if (ca.params.size() > 0 && ca.params.get(0) instanceof Location[]) {
                final Location[] loc = (Location[]) ca.params.get(0);
                double bps = 10;
                int maxdistance = -1;
                if (ca.params.size() > 1) {
                    bps = ((Number) ca.params.get(1)).doubleValue();
                }
                if (ca.params.size() > 2) {
                    maxdistance = (int) ((Number) ca.params.get(2)).doubleValue();
                }
                if (loc != null && loc.length > 0 && loc[0] != null) {
                    boolean broken = false;
                    for (Location l : loc) {
                        if (l == null) {
                            continue;
                        }
                        double distance = ca.caster.getLocation().distance(l);
                        int deltatime = 1;
                        double bpt = bps / 20;
                        double gestime = distance / bpt;
                        final double xps = (l.getX() - ca.caster.getLocation().getX()) / gestime;
                        final double yps = (l.getY() - ca.caster.getLocation().getY()) / gestime;
                        final double zps = (l.getZ() - ca.caster.getLocation().getZ()) / gestime;
                        Location curLocation = ca.caster.getLocation();
                        int time = 0;
                        for (double i = 0; i < gestime; i += 1) {
                            curLocation.add(new Location(ca.caster.getWorld(), xps, yps, zps));
                            final Location portLocation = new Location(ca.caster.getWorld(), curLocation.getX(), curLocation.getY(), curLocation.getZ());
                            AncientRPG.plugin.scheduleThreadSafeTask(AncientRPG.plugin, new Runnable() {
                                @Override
                                public void run() {
                                    Location lo = ca.caster.getLocation();
                                    lo.setX(portLocation.getX());
                                    lo.setY(portLocation.getY());
                                    lo.setZ(portLocation.getZ());
                                    ca.caster.teleport(lo);
                                }
                            }, time);
                            time += deltatime;
                            if (maxdistance > 0 && Math.abs(xps * i) > maxdistance) {
                                broken = true;
                                break;
                            }
                        }
                        if (!broken) {
                            final Location portLocation = new Location(ca.caster.getWorld(), l.getX(), l.getY(), l.getZ());
                            AncientRPG.plugin.scheduleThreadSafeTask(AncientRPG.plugin, new Runnable() {
                                @Override
                                public void run() {
                                    Location lo = ca.caster.getLocation();
                                    lo.setX(portLocation.getX());
                                    lo.setY(portLocation.getY());
                                    lo.setZ(portLocation.getZ());
                                    ca.caster.teleport(lo);
                                }
                            }, time);
                        }
                    }
                    return true;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        return false;
    }
}