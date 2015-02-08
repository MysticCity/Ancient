package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;

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