package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.Spell;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import com.ancientshores.AncientRPG.Listeners.AncientRPGSpellListener;
import org.bukkit.entity.Player;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class HasBuff extends IArgument {
    @ArgumentDescription(
            description = "Returns true if the player has the specified buff activated, false otherwise",
            parameterdescription = {"player", "buffname"}, returntype = ParameterType.Boolean, rparams = {ParameterType.Player, ParameterType.String})
    public HasBuff() {
        this.pt = ParameterType.Boolean;
        this.requiredTypes = new ParameterType[]{ParameterType.Player, ParameterType.String};
        this.name = "hasbuff";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (obj.length != 2 || !(obj[0] instanceof Player[]) || !(obj[1] instanceof String)) {
            return false;
        }
        Player p = ((Player[]) obj[0])[0];
        String s = (String) obj[1];
        if (p != null) {
            for (Entry<Spell, ConcurrentHashMap<Player[], Integer>> e : AncientRPGSpellListener.getAllBuffs().entrySet()) {
                if (e.getKey().name != null && e.getKey().name.equalsIgnoreCase(s)) {
                    for (Entry<Player[], Integer> entry : e.getValue().entrySet()) {
                        if (entry.getKey().length == 2 && entry.getKey()[0] == p) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
