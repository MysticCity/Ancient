package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.Variable;

public class AddGlobalVariableCommand extends ICommand {
    @CommandDescription(description = "<html>Creates a global variable which is visible to all spells, can be accessed using normal variables.</html>",
            argnames = {"varname"}, name = "AddGlobalVariable", parameters = {ParameterType.String})
    public AddGlobalVariableCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.String};
    }

    @Override
    public boolean playCommand(EffectArgs ca) {
        if (ca.getParams().size() == 1) {
            if (ca.getParams().get(0) instanceof String) {
                String name = (String) ca.getParams().get(0);
                if (!Variable.globVars.containsKey(name)) {
                    Variable v = new Variable(name);
                    Variable.globVars.put(name, v);
                    ca.getSpellInfo().variables.put(name, v);
                }
                return true;
            }
        }
        return false;
    }
}