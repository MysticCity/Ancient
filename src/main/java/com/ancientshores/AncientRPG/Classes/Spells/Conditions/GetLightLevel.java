package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import org.bukkit.Location;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;

public class GetLightLevel extends IArgument {
    @ArgumentDescription(
            description = "Returns the light level at the specified location",
            parameterdescription = {"location"}, returntype = ParameterType.Number, rparams = {ParameterType.Location})
    public GetLightLevel() {
        this.returnType = ParameterType.Number;
        this.requiredTypes = new ParameterType[]{ParameterType.Location};
        this.name = "getlightlevel";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (obj[0] instanceof Location[]) {
            Location loc = ((Location[]) obj[0])[0];
            if (loc != null && loc.getWorld() != null && loc.getWorld().getBlockAt(loc) != null) {
                return (int) loc.getWorld().getBlockAt(loc).getLightLevel();
            }
        }
        return 0;
    }
}