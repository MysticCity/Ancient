package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;

public class IsSneaking extends IArgument {

    @ArgumentDescription(
            description = "Returns true if the player is sneaking, false otherwise",
            parameterdescription = {"player"}, returntype = ParameterType.Boolean, rparams = {ParameterType.Player})
    public IsSneaking() {
        this.returnType = ParameterType.Boolean;
        this.requiredTypes = new ParameterType[]{ParameterType.Player};
        this.name = "issneaking";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (obj.length == 1 && obj[0] instanceof Player[] && ((Player[]) obj[0]).length > 0) {
            Player p = ((Player[]) obj[0])[0];
            if (p != null) {
                return p.isSneaking();
            }
        }
        return false;
    }
}