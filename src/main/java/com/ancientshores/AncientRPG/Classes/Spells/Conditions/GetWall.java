package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.HashSet;

public class GetWall extends IArgument {
    @ArgumentDescription(
            description = "<html>returns a wall with the specified dimensions around the location<br>Parameter 1: the middle point of the wall<br>Parameter 2: the length foreward and backward<br>Parameter 3: the length up and downwards<br>Parameter 4: the length to the left and the right of the location</html>",
            parameterdescription = {"location", "x", "y", "z"}, returntype = ParameterType.Location, rparams = {ParameterType.Location, ParameterType.Number, ParameterType.Number, ParameterType.Number})
    public GetWall() {
        this.name = "getWall";
        this.returnType = ParameterType.Location;
        this.requiredTypes = new ParameterType[]{ParameterType.Location, ParameterType.Number, ParameterType.Number, ParameterType.Number};
    }

    @Override
    public Object getArgument(Object[] params, SpellInformationObject so) {
        if (params.length != 4) {
            return null;
        }
        if (params[0] == null || params[1] == null || params[2] == null || params[3] == null) {
            return null;
        }
        Location l = ((Location[]) params[0])[0];
        int forward = (int) ((Number) params[1]).doubleValue();
        int upward = (int) ((Number) params[2]).doubleValue();
        int sideward = (int) ((Number) params[3]).doubleValue();
        int x;
        int y = upward;
        int z;
        Vector v = l.getDirection();
        if (Math.abs(v.getX()) > Math.abs(v.getZ())) {
            x = forward;
            z = sideward;
        } else {
            z = forward;
            x = sideward;
        }
        HashSet<Location> list = new HashSet<Location>();
        for (int i = 0; i < y; i++) {
            for (int y1 = -z; y1 <= z; y1++) {
                list.add(l.clone().add(-x, i, y1));
                list.add(l.clone().add(x, i, y1));
            }
        }
        for (int i = -x; i <= x; i++) {
            for (int y1 = 0; y1 < y; y1++) {
                list.add(l.clone().add(i, y1, -z));
                list.add(l.clone().add(i, y1, z));
            }
        }
        return list.toArray(new Location[list.size()]);
    }
}