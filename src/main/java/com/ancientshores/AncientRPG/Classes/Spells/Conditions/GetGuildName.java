package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import com.ancientshores.AncientRPG.Guild.AncientRPGGuild;
import org.bukkit.entity.Player;

public class GetGuildName extends IArgument {
    @ArgumentDescription(
            description = "Returns the guild name of the player",
            parameterdescription = {"player"}, returntype = ParameterType.String, rparams = {ParameterType.Player})
    public GetGuildName() {
        this.pt = ParameterType.String;
        this.requiredTypes = new ParameterType[]{ParameterType.Player};
        this.name = "getguildname";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (!(obj[0] instanceof Player[])) {
            return 0;
        }
        Player p = ((Player[]) obj[0])[0];
        AncientRPGGuild guild = AncientRPGGuild.getPlayersGuild(p.getName());
        if (guild != null) {
            return guild.getgName();
        }
        return "";
    }
}