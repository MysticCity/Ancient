package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;

public class NumberArgument extends IArgument {
    double i;

    public NumberArgument(String s) {
        this.name = "number";
        this.pt = ParameterType.Void;
        try {
            i = Double.parseDouble(s.trim());
        } catch (Exception e) {
            i = 0;
        }
    }

    @Override
    public Object getArgument(Object mPlayer[], SpellInformationObject so) {
        return i;
    }
}
