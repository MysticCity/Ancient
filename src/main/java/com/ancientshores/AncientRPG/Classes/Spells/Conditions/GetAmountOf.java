package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import org.bukkit.Location;

public class GetAmountOf extends IArgument {
    @ArgumentDescription(
            description = "Returns the amount of elements in the collection",
            parameterdescription = {"collection"}, returntype = ParameterType.Number, rparams = {ParameterType.Location})
    public GetAmountOf() {
        this.pt = ParameterType.Number;
        this.requiredTypes = new ParameterType[]{ParameterType.Location};
        this.name = "getamountof";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (obj.length == 1 && obj[0] instanceof Location[]) {
            Location[] locs = (Location[]) obj[0];
            int am = 0;
            for (Location loc : locs) {
                if (loc != null) {
                    am++;
                }
            }
            return am;
        }
        return 0;
    }
}
