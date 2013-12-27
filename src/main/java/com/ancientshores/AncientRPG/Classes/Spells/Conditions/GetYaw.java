package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import org.bukkit.Location;

public class GetYaw extends IArgument {
    @ArgumentDescription(
            description = "Returns the yaw of a location",
            parameterdescription = {"location"}, returntype = ParameterType.Number, rparams = {ParameterType.Location})
    public GetYaw() {
        this.pt = ParameterType.Number;
        this.requiredTypes = new ParameterType[]{ParameterType.Location};
        this.name = "getyaw";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (obj.length == 1 && obj[0] instanceof Location[] && ((Location[]) obj[0]).length > 0) {
            return ((Location[]) obj[0])[0].getYaw();
        }
        return null;
    }
}
