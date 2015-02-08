package com.ancientshores.Ancient.Classes.Spells.Conditions;

import org.bukkit.Location;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;

public class GetPitch extends IArgument {
    @ArgumentDescription(
            description = "Returns the pitch of the location",
            parameterdescription = {"location"}, returntype = ParameterType.Number, rparams = {ParameterType.Location})
    public GetPitch() {
        this.returnType = ParameterType.Number;
        this.requiredTypes = new ParameterType[]{ParameterType.Location};
        this.name = "getpitch";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (obj.length == 1 && obj[0] instanceof Location[]) {
            return ((Location[]) obj[0])[0].getPitch();
        }
        return null;
    }
}