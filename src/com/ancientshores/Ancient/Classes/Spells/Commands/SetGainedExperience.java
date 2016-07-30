package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.API.AncientGainExperienceEvent;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;

public class SetGainedExperience extends ICommand {
    public SetGainedExperience() {
        this.paramTypes = new ParameterType[]{ParameterType.Number};
    }

    @Override
    public boolean playCommand(EffectArgs ca) {
        if (ca.getParams().size() == 1) {
            if (ca.getParams().get(0) instanceof Number) {
                if (ca.getSpellInfo().mEvent instanceof AncientGainExperienceEvent) {
                    ((AncientGainExperienceEvent) ca.getSpellInfo().mEvent).gainedxp = ((Number) ca.getParams().get(0)).intValue();
                    return true;
                }
            }
        }
        return false;
    }
}