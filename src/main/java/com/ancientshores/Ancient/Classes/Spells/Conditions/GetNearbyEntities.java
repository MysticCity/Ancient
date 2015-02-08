package com.ancientshores.Ancient.Classes.Spells.Conditions;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import com.ancientshores.Ancient.Util.GlobalMethods;

// TODO durchlesen
public class GetNearbyEntities extends IArgument {
    @ArgumentDescription(
            description = "Returns the amount of entities nearby the location in the specified range",
            parameterdescription = {"location", "range", "amount"}, returntype = ParameterType.Entity, rparams = {ParameterType.Location, ParameterType.Number, ParameterType.Number})
    public GetNearbyEntities() {
        this.returnType = ParameterType.Entity;
        this.requiredTypes = new ParameterType[]{ParameterType.Location, ParameterType.Number, ParameterType.Number};
        this.name = "getnearbyentities";
    }

    public UUID[] getNearbyEntities(List<Entity> entityset, Location l, double range, int count) {
        final UUID[] nearestEntity = new UUID[count];
        final HashSet<UUID> alreadyParsed = new HashSet<UUID>();
        for (int i = 0; i < count; i++) {
            double curdif = 100000;
            for (Entity e : entityset) {
                if (e instanceof Creature || e instanceof Player) {
                    double dif = e.getLocation().distance(l);
                    if (dif < range && dif < curdif && l != e.getLocation() && !alreadyParsed.contains(e.getUniqueId())) {
                        curdif = dif;
                        nearestEntity[i] = e.getUniqueId();
                    }
                }
            }
            alreadyParsed.add(nearestEntity[i]);
        }
        return GlobalMethods.removeNullArrayCells(nearestEntity);
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (obj.length != 3 || !(obj[0] instanceof Location[]) || ((Location[]) obj[0]).length == 0 || !(obj[1] instanceof Number) || !(obj[2] instanceof Number)) {
            return null;
        }
        double range = ((Number) obj[1]).doubleValue();
        int maxcount = ((Number) obj[2]).intValue();
        Location loc = ((Location[]) obj[0])[0];
        return getNearbyEntities(loc.getWorld().getEntities(), loc, range, maxcount);
    }
}