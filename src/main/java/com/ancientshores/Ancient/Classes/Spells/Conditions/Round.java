package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;

public class Round extends IArgument {
    @ArgumentDescription(
            description = "Rounds the specified number",
            parameterdescription = {"number"}, returntype = ParameterType.Number, rparams = {ParameterType.Number})
    public Round() {
        this.returnType = ParameterType.Number;
        this.requiredTypes = new ParameterType[]{ParameterType.Number};
        this.name = "round";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (!(obj.length == 1 && obj[0] instanceof Number)) {
            return 0;
        }
        return Math.round((int) (Math.round(((Number) obj[0]).doubleValue())));
    }
}