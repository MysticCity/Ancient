package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;

public class BooleanArgument extends IArgument {
    final boolean b;

    public BooleanArgument(Boolean b) {
        this.b = b;
    }

    @Override
    public Object getArgument(Object mPlayer[], SpellInformationObject so) {
        return b;
    }
}