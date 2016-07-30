package com.ancientshores.Ancient.Classes.Spells.Conditions;

import org.bukkit.entity.Player;

import com.ancientshores.Ancient.PlayerData;
import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;

public class GetMana extends IArgument {
    @ArgumentDescription(
            description = "Returns the current amount of mana the player has.",
            parameterdescription = {"player"}, returntype = ParameterType.Number, rparams = {ParameterType.Player})
    public GetMana() {
        this.returnType = ParameterType.Number;
        this.requiredTypes = new ParameterType[]{ParameterType.Player};
        this.name = "getmana";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (!(obj[0] instanceof Player[])) {
            return 0;
        }
        Player e = ((Player[]) obj[0])[0];
        return PlayerData.getPlayerData(e.getUniqueId()).getManasystem().getCurrentMana();
    }
}