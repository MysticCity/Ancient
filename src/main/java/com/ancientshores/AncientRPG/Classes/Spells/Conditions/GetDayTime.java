package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import org.bukkit.Location;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;

public class GetDayTime extends IArgument {
    @ArgumentDescription(
            description = "Returns the time at the specified location",
            parameterdescription = {"world"}, returntype = ParameterType.Number, rparams = {ParameterType.Location})
    public GetDayTime() {
        this.returnType = ParameterType.Number;
        this.requiredTypes = new ParameterType[]{ParameterType.Location};
        this.name = "getdaytime";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (!(obj[0] instanceof Location[])) {
            return 0;
        }
        Location l = ((Location[]) obj[0])[0];
        return l.getWorld().getTime();
    }
}