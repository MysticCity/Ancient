package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.ParameterType;

public class SkipCommand extends ICommand {
    public SkipCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Number};
    }

    @Override
    public boolean playCommand(EffectArgs ca) {
        try {
            if (ca.getParams().get(0) instanceof Number) {
                int number = (int) ((Number) ca.getParams().get(0)).doubleValue();
                ca.getSpell().skipCommands(ca.getSpellInfo(), number);
                return true;
            }
        } catch (IndexOutOfBoundsException ignored) {
        }
        return false;
    }
}