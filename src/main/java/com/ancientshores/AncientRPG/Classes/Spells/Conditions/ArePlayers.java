package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;

public class ArePlayers extends IArgument {
    @ArgumentDescription(
            description = "Checks a collection if all of them are players.",
            parameterdescription = {"collection"}, returntype = ParameterType.Boolean, rparams = {ParameterType.Player})
    public ArePlayers() {
        this.returnType = ParameterType.Boolean;
        this.requiredTypes = new ParameterType[]{ParameterType.Player};
        this.name = "areplayers";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (!(obj[0] instanceof Object[])) {
            return 0;
        }
        Object[] objs = (Object[]) obj[0];
        if (objs.length == 0) {
            return false;
        }
        for (Object o : objs) {
            if (!(o instanceof Player)) {
                return false;
            }
        }
        return true;
    }
}