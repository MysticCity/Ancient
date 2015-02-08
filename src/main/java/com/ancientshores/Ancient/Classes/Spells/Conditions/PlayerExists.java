package com.ancientshores.Ancient.Classes.Spells.Conditions;

import java.util.UUID;

import org.bukkit.Bukkit;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;

public class PlayerExists extends IArgument {
    @ArgumentDescription(
            description = "Returns true if the player with the specified uuid exists and is online on the server, false otherwise",
            parameterdescription = {"playername"}, returntype = ParameterType.Boolean, rparams = {ParameterType.UUID})
    public PlayerExists() {
        this.returnType = ParameterType.Boolean;
        this.requiredTypes = new ParameterType[]{ParameterType.UUID};
        this.name = "playerexists";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (!(obj[0] instanceof UUID)) {
            return null;
        }
        return Bukkit.getServer().getPlayer(UUID.fromString((String) obj[0])) != null;
    }
}