package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;

public class NumberArgument extends IArgument {
    double i;

    public NumberArgument(String s) {
        this.name = "number";
        this.returnType = ParameterType.Void;
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