package com.ancientshores.Ancient.Classes.Spells.Conditions;

import org.bukkit.Location;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;

public class GetYaw extends IArgument {
    @ArgumentDescription(
            description = "Returns the yaw of a location",
            parameterdescription = {"location"}, returntype = ParameterType.Number, rparams = {ParameterType.Location})
    public GetYaw() {
        this.returnType = ParameterType.Number;
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