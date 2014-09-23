package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuild;

public class IsInSameGuild extends IArgument {
    @ArgumentDescription(
            description = "Returns true if the 2 players are in the same guild, false otherwise",
            parameterdescription = {"player1", "player2"}, returntype = ParameterType.Boolean, rparams = {ParameterType.Player, ParameterType.Player})
    public IsInSameGuild() {
        this.returnType = ParameterType.Boolean;
        this.requiredTypes = new ParameterType[]{ParameterType.Player, ParameterType.Player};
        this.name = "isinsameguild";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (!(obj[0] instanceof Player[]) || !(obj[1] instanceof Player[])) {
            return null;
        }
        if (((Player[]) obj[0]).length > 0 && ((Player[]) obj[1]).length > 0 && ((Player[]) obj[0])[0] != null && ((Player[]) obj[1])[0] != null) {
            Player p1 = ((Player[]) obj[0])[0];
            Player p2 = ((Player[]) obj[1])[0];
            AncientRPGGuild guild1 = AncientRPGGuild.getPlayersGuild(p1.getUniqueId());
            AncientRPGGuild guild2 = AncientRPGGuild.getPlayersGuild(p2.getUniqueId());
            if (guild1 != null && guild2 != null && guild1 == guild2) {
                return true;
            }
        }
        return false;
    }
}