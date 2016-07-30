package com.ancientshores.Ancient.Classes.Spells.Commands;

import org.bukkit.Location;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;

public class ChargeCommand extends ICommand {
    @CommandDescription(description = "<html>The player charges to the location</html>",
            argnames = {"location", "speed", "maxdistance"}, name = "Charge", parameters = {ParameterType.Location, ParameterType.Number, ParameterType.Number})

    public ChargeCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Location, ParameterType.Number, ParameterType.Number};
    }

    @Override
    public boolean playCommand(final EffectArgs ca) {
        try {
            if (ca.getParams().size() > 0 && ca.getParams().get(0) instanceof Location[]) {
                final Location[] loc = (Location[]) ca.getParams().get(0);
                double bps = 10;
                int maxdistance = -1;
                if (ca.getParams().size() > 1) {
                    bps = ((Number) ca.getParams().get(1)).doubleValue();
                }
                if (ca.getParams().size() > 2) {
                    maxdistance = (int) ((Number) ca.getParams().get(2)).doubleValue();
                }
                if (loc != null && loc.length > 0 && loc[0] != null) {
                    boolean broken = false;
                    for (Location l : loc) {
                        if (l == null) {
                            continue;
                        }
                        double distance = ca.getCaster().getLocation().distance(l);
                        int deltatime = 1;
                        double bpt = bps / 20;
                        double gestime = distance / bpt;
                        final double xps = (l.getX() - ca.getCaster().getLocation().getX()) / gestime;
                        final double yps = (l.getY() - ca.getCaster().getLocation().getY()) / gestime;
                        final double zps = (l.getZ() - ca.getCaster().getLocation().getZ()) / gestime;
                        Location curLocation = ca.getCaster().getLocation();
                        int time = 0;
                        for (double i = 0; i < gestime; i += 1) {
                            curLocation.add(new Location(ca.getCaster().getWorld(), xps, yps, zps));
                            final Location portLocation = new Location(ca.getCaster().getWorld(), curLocation.getX(), curLocation.getY(), curLocation.getZ());
                            Ancient.plugin.scheduleThreadSafeTask(Ancient.plugin, new Runnable() {
                                @Override
                                public void run() {
                                    Location lo = ca.getCaster().getLocation();
                                    lo.setX(portLocation.getX());
                                    lo.setY(portLocation.getY());
                                    lo.setZ(portLocation.getZ());
                                    ca.getCaster().teleport(lo);
                                }
                            }, time);
                            time += deltatime;
                            if (maxdistance > 0 && Math.abs(xps * i) > maxdistance) {
                                broken = true;
                                break;
                            }
                        }
                        if (!broken) {
                            final Location portLocation = new Location(ca.getCaster().getWorld(), l.getX(), l.getY(), l.getZ());
                            Ancient.plugin.scheduleThreadSafeTask(Ancient.plugin, new Runnable() {
                                @Override
                                public void run() {
                                    Location lo = ca.getCaster().getLocation();
                                    lo.setX(portLocation.getX());
                                    lo.setY(portLocation.getY());
                                    lo.setZ(portLocation.getZ());
                                    ca.getCaster().teleport(lo);
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