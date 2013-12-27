package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.API.AncientGainExperienceEvent;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;

/**
 * Created with IntelliJ IDEA.
 * User: Frederik Haselmeier
 * Date: 02.03.13
 * Time: 14:03
 */
public class SetGainedExperience extends ICommand {
    public SetGainedExperience() {
        ParameterType[] buffer = {ParameterType.Number};
        this.paramTypes = buffer;
    }

    @Override
    public boolean playCommand(EffectArgs ca) {
        if (ca.params.size() == 1) {
            if (ca.params.get(0) instanceof Number) {
                if (ca.so.mEvent instanceof AncientGainExperienceEvent) {
                    ((AncientGainExperienceEvent) ca.so.mEvent).gainedxp = ((Number) ca.params.get(0)).intValue();
                    return true;
                }
            }
        }
        return false;
    }
}
