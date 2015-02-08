package com.ancientshores.Ancient.Classes.Spells.Commands;

import java.util.Random;

import com.ancientshores.Ancient.Classes.Spells.ParameterType;

public class ChanceCommand extends ICommand {
    static final Random r = new Random();

    public ChanceCommand() {
        this.paramTypes = new ParameterType[]{ParameterType.Number, ParameterType.Number};
    }

    @Override
    public boolean playCommand(EffectArgs ca) {
        try {
            if (!(ca.getParams().get(0) instanceof Number) || !(ca.getParams().get(1) instanceof Number)) {
                return false;
            }
            int chance = (int) ((Number) ca.getParams().get(0)).doubleValue();
            int skipcommands = (int) ((Number) ca.getParams().get(1)).doubleValue();
            if (!(r.nextInt(100) < chance)) {
                ca.getSpell().skipCommands(ca.getSpellInfo(), skipcommands);
            }
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }
}