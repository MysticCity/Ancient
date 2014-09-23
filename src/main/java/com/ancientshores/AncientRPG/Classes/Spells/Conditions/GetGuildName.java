package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuild;

public class GetGuildName extends IArgument {
    @ArgumentDescription(
            description = "Returns the guild name of the player",
            parameterdescription = {"player"}, returntype = ParameterType.String, rparams = {ParameterType.Player})
    public GetGuildName() {
        this.returnType = ParameterType.String;
        this.requiredTypes = new ParameterType[]{ParameterType.Player};
        this.name = "getguildname";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (!(obj[0] instanceof Player[])) {
            return 0;
        }
        Player p = ((Player[]) obj[0])[0];
        AncientRPGGuild guild = AncientRPGGuild.getPlayersGuild(p.getUniqueId());
        if (guild != null) {
            return guild.getGuildName();
        }
        return "";
    }
}