package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import org.bukkit.Location;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;

public class IsInZone extends IArgument {
    @ArgumentDescription(
            description = "Returns true if the location is in a zone specified by 2 other locations",
            parameterdescription = {"target", "start", "end"}, returntype = ParameterType.Number, rparams = {ParameterType.Location, ParameterType.Location, ParameterType.Location})
    public IsInZone() {
        this.returnType = ParameterType.Number;
        this.requiredTypes = new ParameterType[]{ParameterType.Location, ParameterType.Location, ParameterType.Location};
        this.name = "isinzone";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (obj.length == 3 && obj[0] instanceof Location[] && obj[1] instanceof Location[] && obj[2] instanceof Location[]) {
            Location target = ((Location[]) obj[0])[0];
            Location start = ((Location[]) obj[1])[0];
            Location end = ((Location[]) obj[2])[0];
            if (end.getWorld() != target.getWorld() || start.getWorld() != end.getWorld()) {
                return false;
            }
            int startx = start.getBlockX();
            int endx = end.getBlockX();
            if (startx > endx) {
                int buffer = startx;
                startx = endx;
                endx = buffer;
            }
            int starty = start.getBlockY();
            int endy = end.getBlockY();
            if (starty > endy) {
                int buffer = starty;
                starty = endy;
                endy = buffer;
            }
            int startz = start.getBlockZ();
            int endz = end.getBlockZ();
            if (startz > endz) {
                int buffer = startz;
                startz = endz;
                endz = buffer;
            }
            start = new Location(start.getWorld(), startx, starty, startz);
            end = new Location(start.getWorld(), endx, endy, endz);
            boolean s = (target.getX() > start.getX()) && (target.getY() > start.getY()) && (target.getZ() > start.getZ());
            boolean e = (target.getX() < end.getX()) && (target.getY() < end.getY()) && (target.getZ() < end.getZ());
            return (s && e);
        }
        return false;
    }
}