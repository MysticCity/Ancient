package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.Commands.InvisibleCommand;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class IsInvisible extends IArgument {
    @ArgumentDescription(
            description = "true if player is invisible, false otherwise",
            parameterdescription = {"player"}, returntype = ParameterType.Boolean, rparams = {ParameterType.Player})
    public IsInvisible() {
        this.pt = ParameterType.Boolean;
        this.requiredTypes = new ParameterType[]{ParameterType.Player};
        this.name = "isinvisible";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (!(obj[0] instanceof Player[])) {
            return null;
        }
        Player p = ((Player[]) obj[0])[0];
        return p.hasPotionEffect(PotionEffectType.INVISIBILITY) || InvisibleCommand.invisiblePlayers.contains(p);
    }
}