package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.PlayerData;
import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;

public class GetClassName extends IArgument {
    @ArgumentDescription(
            description = "Returns the name of the players class",
            parameterdescription = {"player"}, returntype = ParameterType.String, rparams = {ParameterType.Player})
    public GetClassName() {
        this.returnType = ParameterType.String;
        this.requiredTypes = new ParameterType[]{ParameterType.Player};
        this.name = "getclassname";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (!(obj[0] instanceof Player[])) {
            return null;
        }
        Player mPlayer = ((Player[]) obj[0])[0];
        PlayerData pd = PlayerData.getPlayerData(mPlayer.getUniqueId());
        String classname = pd.getClassName();
        return classname;
    }
}