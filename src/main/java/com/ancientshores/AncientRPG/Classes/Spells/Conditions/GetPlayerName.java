package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import org.bukkit.entity.Player;

public class GetPlayerName extends IArgument {
    @ArgumentDescription(
            description = "Returns the name of the player",
            parameterdescription = {"player"}, returntype = ParameterType.String, rparams = {ParameterType.String})
    public GetPlayerName() {
        this.pt = ParameterType.String;
        this.requiredTypes = new ParameterType[]{ParameterType.String};
        ;
        this.name = "getplayername";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (!(obj[0] instanceof Player[])) {
            return null;
        }
        Player mPlayer = ((Player[]) obj[0])[0];
        return mPlayer.getName();
    }
}
