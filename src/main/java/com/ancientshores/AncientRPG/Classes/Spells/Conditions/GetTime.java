package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import org.bukkit.Location;

public class GetTime extends IArgument {
    @ArgumentDescription(
            description = "Returns the time of the world",
            parameterdescription = {"world"}, returntype = ParameterType.Number, rparams = {ParameterType.Location})
    public GetTime() {
        this.returnType = ParameterType.Number;
        this.requiredTypes = new ParameterType[]{ParameterType.Location};
        this.name = "gettime";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (obj.length > 0 && (obj[0] instanceof Location[])) {
            Location[] l = (Location[]) obj[0];
            if (l.length == 0) {
                return null;
            }
            return l[0].getWorld().getTime();
        }
        return null;
    }
}