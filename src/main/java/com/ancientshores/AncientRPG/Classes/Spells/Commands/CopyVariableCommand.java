package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.Variable;

public class CopyVariableCommand extends ICommand {

    @CommandDescription(description = "<html>Copies the content of the source to the target</html>",
            argnames = {"variable1", "variable2"}, name = "CopyVariable", parameters = {ParameterType.String, ParameterType.String})
    public CopyVariableCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.String, ParameterType.String};
    }

    @Override
    public boolean playCommand(EffectArgs ca) {
        if (ca.getParams().size() == 2) {
            if (ca.getParams().get(0) instanceof String && ca.getParams().get(1) instanceof String) {
                String source = (String) ca.getParams().get(0);
                String target = (String) ca.getParams().get(1);
                Variable v1 = ca.getSpellInfo().variables.get(source);
                Variable v2 = ca.getSpellInfo().variables.get(target);
                if (v1 != null && v2 != null) {
                    v2.obj = v1.obj;
                    if (Variable.globVars.containsKey(v2.name)) {
                        Variable.globVars.get(v2.name).obj = v2.obj;
                    }
                }
                return true;
            }
        }
        return false;
    }
}