package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.CooldownTimer;
import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import com.ancientshores.AncientRPG.PlayerData;

public class IsCooldownReady extends IArgument {
    @ArgumentDescription(
            description = "Returns true if the cooldown with the name is ready",
            parameterdescription = {"cooldownname"}, returntype = ParameterType.Boolean, rparams = {ParameterType.String})
    public IsCooldownReady() {
        this.returnType = ParameterType.Boolean;
        this.requiredTypes = new ParameterType[]{ParameterType.String};
        this.name = "iscooldownready";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (obj.length < 1 || !(obj[0] instanceof String)) {
            return null;
        }
        String s = (String) obj[0];
        PlayerData pd = PlayerData.getPlayerData(so.buffcaster.getUniqueId());
        for (CooldownTimer cd : pd.getCooldownTimer()) {
            if (cd.uuid.equalsIgnoreCase(s)) {
                return cd.ready;
            }
        }
        return true;
    }
}