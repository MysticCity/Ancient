package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;

public class HasPermission extends IArgument {
    @ArgumentDescription(
            description = "Returns true if the player has the specified permission, false otherwise",
            parameterdescription = {"player", "permission"}, returntype = ParameterType.Boolean, rparams = {ParameterType.Player, ParameterType.String})
    public HasPermission() {
        this.returnType = ParameterType.Boolean;
        this.requiredTypes = new ParameterType[]{ParameterType.Player, ParameterType.String};
        this.name = "haspermission";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (obj.length != 2 || !(obj[0] instanceof Player[]) || !(obj[1] instanceof String)) {
            return false;
        }
        Player p = ((Player[]) obj[0])[0];
        String s = (String) obj[1];
        return p.hasPermission(s);
    }
}