package com.ancientshores.Ancient.Classes.Spells.Conditions;

import org.bukkit.Location;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;

public class AreLocations extends IArgument {
    @ArgumentDescription(
            description = "Checks a collection if all of them are locations.",
            parameterdescription = {"collection"}, returntype = ParameterType.Boolean, rparams = {ParameterType.Location})
    public AreLocations() {
        this.returnType = ParameterType.Boolean;
        this.requiredTypes = new ParameterType[]{ParameterType.Location};
        this.name = "arelocations";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (!(obj[0] instanceof Object[])) {
            return 0;
        }
        Object[] objs = (Object[]) obj[0];
        if (objs.length == 0) {
            return false;
        }
        for (Object o : objs) {
            if (!(o instanceof Location)) {
                return false;
            }
        }
        return true;
    }
}