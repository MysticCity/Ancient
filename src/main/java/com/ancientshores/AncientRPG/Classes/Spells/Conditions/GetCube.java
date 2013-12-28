package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import org.bukkit.Location;

import java.util.HashSet;

public class GetCube extends IArgument {
    @ArgumentDescription(
            description = "<html>returns the outline of a cube with the specified radius at the given locaiton<br>Parameter 1: the middle point of the Coboid<br>Parameter 2: the radius of the cube</html>",
            parameterdescription = {"location", "radius"}, returntype = ParameterType.Location, rparams = {ParameterType.Location, ParameterType.Number})
    public GetCube() {
        this.name = "getCube";
        this.pt = ParameterType.Location;
        this.requiredTypes = new ParameterType[]{ParameterType.Location, ParameterType.Number};
    }

    @Override
    public Object getArgument(Object[] params, SpellInformationObject so) {
        if (params.length < 2) {
            return null;
        }
        // TODO Auto-generated method stub
        if (!(params[0] instanceof Location[])) {
            return null;
        }
        if (!(params[1] instanceof Number)) {
            return null;
        }
        int rad = (int) ((Number) params[1]).doubleValue();
        HashSet<Location> list = new HashSet<Location>();
        Location l = ((Location[]) params[0])[0];
        for (int i = -rad; i <= rad; i++) {
            for (int y = -rad; y <= rad; y++) {
                list.add(l.clone().add(-rad, y, i));
                list.add(l.clone().add(rad, y, i));
                list.add(l.clone().add(y, -rad, i));
                list.add(l.clone().add(y, rad, i));
                list.add(l.clone().add(i, y, -rad));
                list.add(l.clone().add(i, y, rad));
            }
        }
        return list.toArray(new Location[list.size()]);
    }
}