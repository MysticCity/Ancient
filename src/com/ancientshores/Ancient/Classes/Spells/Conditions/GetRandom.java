package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;

public class GetRandom extends IArgument {
    @ArgumentDescription(
            description = "Returns a random number between 0 and 100 (both inclusive)",
            parameterdescription = {}, returntype = ParameterType.Number, rparams = {})
    public GetRandom() {
        this.returnType = ParameterType.Number;
        this.requiredTypes = new ParameterType[]{};
        this.name = "getrandom";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        return (int) (Math.random() * 100);
    }
}