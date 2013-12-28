package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import org.bukkit.Location;

public class GetDistance extends IArgument {
    @ArgumentDescription(
            description = "Returns the distance between two locations in the same world",
            parameterdescription = {"location1", "location2"}, returntype = ParameterType.Number, rparams = {ParameterType.Location, ParameterType.Location})
    public GetDistance() {
        this.pt = ParameterType.Number;
        this.requiredTypes = new ParameterType[]{ParameterType.Location, ParameterType.Location};
        this.name = "getdistance";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (!(obj.length == 2 && obj[0] instanceof Location[] && obj[1] instanceof Location[])) {
            return Integer.MAX_VALUE;
        }
        Location l1 = ((Location[]) obj[0])[0];
        Location l2 = ((Location[]) obj[1])[0];
        return l1.distance(l2);
    }
}