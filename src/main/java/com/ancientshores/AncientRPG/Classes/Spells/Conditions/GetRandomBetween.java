package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;

import java.util.Random;

public class GetRandomBetween extends IArgument {
    @ArgumentDescription(
            description = "Returns a random number between 2 numbers",
            parameterdescription = {"number1", "number2"}, returntype = ParameterType.Number, rparams = {ParameterType.Number, ParameterType.Number})
    public GetRandomBetween() {
        this.pt = ParameterType.Number;
        this.requiredTypes = new ParameterType[]{ParameterType.Number, ParameterType.Number};
        this.name = "getrandombetween";
    }

    static final Random r = new Random();

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (obj.length == 2 && obj[0] instanceof Number && obj[1] instanceof Number) {
            int min = ((Number) obj[0]).intValue();
            int max = ((Number) obj[1]).intValue();
            return Math.abs(Math.round(r.nextInt(max - min) + min));
        }
        return null;
    }
}
