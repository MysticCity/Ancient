package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;

public class GetTimeMillis extends IArgument {
    @ArgumentDescription(
            description = "Returns the system time in milliseconds",
            parameterdescription = {}, returntype = ParameterType.Number, rparams = {})
    public GetTimeMillis() {
        this.returnType = ParameterType.Number;
        this.requiredTypes = new ParameterType[]{};
        this.name = "gettimemillis";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        return (double) System.currentTimeMillis();
    }
}