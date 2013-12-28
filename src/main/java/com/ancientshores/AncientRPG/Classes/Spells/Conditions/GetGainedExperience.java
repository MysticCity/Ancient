package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.API.AncientGainExperienceEvent;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;

/**
 * Created with IntelliJ IDEA.
 * User: Frederik Haselmeier
 * Date: 02.03.13
 * Time: 14:11
 */
public class GetGainedExperience extends IArgument {
    public GetGainedExperience() {
        this.pt = ParameterType.Number;
        this.requiredTypes = new ParameterType[]{};
        this.name = "getgainedexperience";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (so.mEvent instanceof AncientGainExperienceEvent) {
            return ((AncientGainExperienceEvent) so.mEvent).gainedxp;
        }
        return null;
    }
}