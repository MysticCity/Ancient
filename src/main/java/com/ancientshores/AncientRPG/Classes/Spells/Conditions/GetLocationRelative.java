package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: Frederik Haselmeier
 * Date: 23.02.13
 * Time: 23:17
 */
public class GetLocationRelative extends IArgument {
    public GetLocationRelative() {
        this.pt = ParameterType.Number;
        this.requiredTypes = new ParameterType[]{ParameterType.Location, ParameterType.Number, ParameterType.Number, ParameterType.Number};
        this.name = "getrelative";
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
        double forward = ((Number) obj[1]).doubleValue();
        double sideward = ((Number) obj[2]).doubleValue();
        double upward = ((Number) obj[3]).doubleValue();
        double x;
        double y = upward;
        double z;
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
        return new Location[]{l.add(x, y, z)};
    }
}