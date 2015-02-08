package com.ancientshores.Ancient.Classes.Spells.Conditions;

import org.bukkit.entity.Player;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import com.ancientshores.Ancient.Party.AncientParty;

public class IsInSameParty extends IArgument {
    @ArgumentDescription(
            description = "Returns true if the 2 players are in the same party, false otherwise",
            parameterdescription = {"player1", "player2"}, returntype = ParameterType.Boolean, rparams = {ParameterType.Player, ParameterType.Player})
    public IsInSameParty() {
        this.returnType = ParameterType.Boolean;
        this.requiredTypes = new ParameterType[]{ParameterType.Player, ParameterType.Player};
        this.name = "isinsameparty";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (!(obj[0] instanceof Player[]) || !(obj[1] instanceof Player[])) {
            return null;
        }
        if (((Player[]) obj[0]).length > 0 && ((Player[]) obj[1]).length > 0 && ((Player[]) obj[0])[0] != null && ((Player[]) obj[1])[0] != null) {
            Player p1 = ((Player[]) obj[0])[0];
            Player p2 = ((Player[]) obj[1])[0];
            AncientParty party1 = AncientParty.getPlayersParty(p1.getUniqueId());
            AncientParty party2 = AncientParty.getPlayersParty(p2.getUniqueId());
            if (party1 != null && party1 == party2) {
                return true;
            }
        }
        return false;
    }
}