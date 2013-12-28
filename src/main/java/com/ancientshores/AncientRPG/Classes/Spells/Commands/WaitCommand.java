package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;

public class WaitCommand extends ICommand {
    @CommandDescription(description = "<html>Delays the execution of the spell for the specified amount of time in millaseconds.</html>",
            argnames = {"duration"}, name = "Wait", parameters = {ParameterType.Number})

    public WaitCommand() {
        ParameterType[] buffer = {ParameterType.Number};
        this.paramTypes = buffer;
    }

    @Override
    public boolean playCommand(EffectArgs ca) {
        try {
            if (ca.params.get(0) instanceof Number) {
                ca.so.waittime += (int) ((Number) ca.params.get(0)).doubleValue();
                return true;
            }
        } catch (IndexOutOfBoundsException ignored) {
        }
        return false;
    }
}