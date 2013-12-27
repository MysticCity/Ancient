package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import com.ancientshores.AncientRPG.Classes.Spells.Variable;

public class VariableArgument extends IArgument {
    final String name;

    public VariableArgument(String name) {
        this.name = name;
    }

    @Override
    public Object getArgument(Object[] params, SpellInformationObject so) {
        if (Variable.playerVars.containsKey(so.buffcaster.getName()) && Variable.playerVars.get(so.buffcaster.getName()).containsKey(name.toLowerCase())) {
            return Variable.playerVars.get(so.buffcaster.getName()).get(name.toLowerCase()).getVariableObject();
        }
        if (so.variables.containsKey(name.toLowerCase())) {
            return so.variables.get(name.toLowerCase()).getVariableObject();
        }
        if (so.mSpell.variables.contains(name.toLowerCase())) {
            try {
                return so.parseVariable(so.buffcaster, name.toLowerCase());
            } catch (Exception ignored) {

            }
        }
        return null;
    }
}
