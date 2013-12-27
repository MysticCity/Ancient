package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import org.bukkit.Location;

public class GetPitch extends IArgument {
    @ArgumentDescription(
            description = "Returns the pitch of the location",
            parameterdescription = {"location"}, returntype = ParameterType.Number, rparams = {ParameterType.Location})
    public GetPitch() {
        this.pt = ParameterType.Number;
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
