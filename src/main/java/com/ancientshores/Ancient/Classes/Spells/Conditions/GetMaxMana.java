package com.ancientshores.Ancient.Classes.Spells.Conditions;

import org.bukkit.entity.Player;

import com.ancientshores.Ancient.PlayerData;
import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;

public class GetMaxMana extends IArgument {
    @ArgumentDescription(
            description = "Returns the maximum mana of the given player",
            parameterdescription = {"player"}, returntype = ParameterType.Number, rparams = {ParameterType.Player})
    public GetMaxMana() {
        this.returnType = ParameterType.Number;
        this.requiredTypes = new ParameterType[]{ParameterType.Player};
        this.name = "getmaxmana";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (!(obj[0] instanceof Player[])) {
            return 0;
        }

        Player p = ((Player[]) obj[0])[0];
        return PlayerData.getPlayerData(p.getUniqueId()).getManasystem().getMaxmana();
    }
}