package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class GetPlayerByName extends IArgument {
    @ArgumentDescription(
            description = "finds an online player on the server with the specified name",
            parameterdescription = {"name"}, returntype = ParameterType.Player, rparams = {ParameterType.String})
    public GetPlayerByName() {
        this.pt = ParameterType.Player;
        this.requiredTypes = new ParameterType[]{ParameterType.String};
        this.name = "getplayerbyname";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (!(obj[0] instanceof String)) {
            return null;
        }
        return new Player[]{Bukkit.getServer().getPlayer((String) obj[0])};
    }
}