package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;

import java.util.Random;

public class ChanceCommand extends ICommand {
    static final Random r = new Random();

    public ChanceCommand() {
        ParameterType[] buffer = {ParameterType.Number, ParameterType.Number};
        this.paramTypes = buffer;
    }

    @Override
    public boolean playCommand(EffectArgs ca) {
        try {
            if (!(ca.params.get(0) instanceof Number) || !(ca.params.get(1) instanceof Number)) {
                return false;
            }
            int chance = (int) ((Number) ca.params.get(0)).doubleValue();
            int skipcommands = (int) ((Number) ca.params.get(1)).doubleValue();
            if (!(r.nextInt(100) < chance)) {
                ca.p.skipCommands(ca.so, skipcommands);
            }
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }
}