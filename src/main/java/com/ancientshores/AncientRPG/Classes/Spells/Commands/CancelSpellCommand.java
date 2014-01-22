package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;

public class CancelSpellCommand extends ICommand {
    @CommandDescription(description = "<html>Cancels the currently executing spell</html>",
            argnames = {}, name = "CancelSpell", parameters = {})
    public CancelSpellCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Void};
    }

    @Override
    public boolean playCommand(EffectArgs ca) {
        ca.getSpellInfo().finished = true;
        return false;
    }
}