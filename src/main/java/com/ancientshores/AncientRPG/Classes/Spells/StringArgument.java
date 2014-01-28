package com.ancientshores.AncientRPG.Classes.Spells;

import com.ancientshores.AncientRPG.Classes.Spells.Conditions.IArgument;

public class StringArgument extends IArgument {
    final String s;

    public StringArgument(String value) {
        this.returnType = ParameterType.Void;
        this.s = value;
        this.name = "stringargument";
    }

    @Override
    public Object getArgument(Object mPlayer[], SpellInformationObject so) {
        if (so.variables.containsKey(s.toLowerCase())) {
            return so.variables.get(s.toLowerCase()).getVariableObject();
        }
        if (s.trim().startsWith("\"") && s.trim().endsWith("\"")) {
            return s.trim().substring(s.trim().indexOf('"') + 1, s.trim().length() - 1);
        }
        return s.trim();
    }
}