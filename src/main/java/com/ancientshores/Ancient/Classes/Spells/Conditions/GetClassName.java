package com.ancientshores.Ancient.Classes.Spells.Conditions;

import org.bukkit.entity.Player;

import com.ancientshores.Ancient.PlayerData;
import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;

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