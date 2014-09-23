package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;

public class GetFoodLevel extends IArgument {
    @ArgumentDescription(
            description = "Returns the food level of the player",
            parameterdescription = {"player"}, returntype = ParameterType.Number, rparams = {ParameterType.Player})
    public GetFoodLevel() {
        this.returnType = ParameterType.Number;
        this.requiredTypes = new ParameterType[]{ParameterType.Player};
        this.name = "getfoodlevel";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (!(obj[0] instanceof Player[])) {
            return 0;
        }
        Player p = ((Player[]) obj[0])[0];
        return p.getFoodLevel();
    }
}