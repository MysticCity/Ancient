package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;

public class SkipCommand extends ICommand {
    public SkipCommand() {
        ParameterType[] buffer = {ParameterType.Number};
        this.paramTypes = buffer;
    }

    @Override
    public boolean playCommand(EffectArgs ca) {
        try {
            if (ca.params.get(0) instanceof Number) {
                int number = (int) ((Number) ca.params.get(0)).doubleValue();
                ca.p.skipCommands(ca.so, number);
                return true;
            }
        } catch (IndexOutOfBoundsException ignored) {

        }
        return false;
    }
}
