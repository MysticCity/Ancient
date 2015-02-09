package com.ancientshores.Ancient.Classes.Spells.Conditions;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;

public class GetBlockRelative extends IArgument {
    @ArgumentDescription(
            description = "Returns the block relative to the player.",
            parameterdescription = {"location", "forward", "sideward", "upward"}, returntype = ParameterType.Number, rparams = {ParameterType.Location, ParameterType.Number, ParameterType.Number, ParameterType.Number})
    public GetBlockRelative() {
        this.returnType = ParameterType.Number;
        this.requiredTypes = new ParameterType[]{ParameterType.Location, ParameterType.Number, ParameterType.Number, ParameterType.Number};
        this.name = "getBlockRelative";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (obj.length < 4) {
            return null;
        }
        if (!(obj[0] instanceof Location[])) {
            return null;
        }
        Location l = ((Location[]) obj[0])[0];
        int forward = (int) ((Number) obj[1]).doubleValue();
        int sideward = (int) ((Number) obj[2]).doubleValue();
        int upward = (int) ((Number) obj[3]).doubleValue();
        int x;
        int y = upward;
        int z;
        Block b = l.getBlock();
        Vector v = l.getDirection();
        if (Math.abs(v.getX()) > Math.abs(v.getZ())) {
            if (v.getX() > 0) {
                x = forward;
            } else {
                x = -forward;
            }

            if (v.getZ() > 0) {
                z = sideward;
            } else {
                z = -sideward;
            }
        } else {

            if (v.getZ() > 0) {
                z = forward;
            } else {
                z = -forward;
            }

            if (v.getX() > 0) {
                x = sideward;
            } else {
                x = -sideward;
            }
        }
        return new Location[]{b.getRelative(x, y, z).getLocation()};
    }
}