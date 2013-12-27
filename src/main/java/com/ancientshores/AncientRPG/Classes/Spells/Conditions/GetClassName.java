package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import com.ancientshores.AncientRPG.PlayerData;
import org.bukkit.entity.Player;

public class GetClassName extends IArgument {
    @ArgumentDescription(
            description = "Returns the name of the players class",
            parameterdescription = {"player"}, returntype = ParameterType.String, rparams = {ParameterType.Player})
    public GetClassName() {
        this.pt = ParameterType.String;
        this.requiredTypes = new ParameterType[]{ParameterType.Player};
        this.name = "getclassname";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (!(obj[0] instanceof Player[])) {
            return null;
        }
        Player mPlayer = ((Player[]) obj[0])[0];
        PlayerData pd = PlayerData.getPlayerData(mPlayer.getName());
        String classname = pd.getClassName();
        return classname;
    }
}
