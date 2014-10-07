package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.entity.Player;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.Spell;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import com.ancientshores.AncientRPG.Listeners.AncientRPGSpellListener;

public class HasBuff extends IArgument {
    @ArgumentDescription(
            description = "Returns true if the player has the specified buff activated, false otherwise",
            parameterdescription = {"player", "buffname"}, returntype = ParameterType.Boolean, rparams = {ParameterType.Player, ParameterType.String})
    public HasBuff() {
        this.returnType = ParameterType.Boolean;
        this.requiredTypes = new ParameterType[]{ParameterType.Player, ParameterType.String};
        this.name = "hasbuff";
    }
    
    // TODO bei if (entry.getKey().length == 2) muss geändert werden. jeder spieler muss durchgesehen werden
    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (obj.length != 2 || !(obj[0] instanceof Player[]) || !(obj[1] instanceof String)) {
            return false;
        }
        Player p = ((Player[]) obj[0])[0];
        String s = (String) obj[1];
        if (p != null) {
            for (Entry<Spell, ConcurrentHashMap<UUID[], Integer>> e : AncientRPGSpellListener.getAllBuffs().entrySet()) {
                if (e.getKey().name != null && e.getKey().name.equalsIgnoreCase(s)) {
                    for (Entry<UUID[], Integer> entry : e.getValue().entrySet()) {
                        if (entry.getKey().length == 2 && entry.getKey()[0].compareTo(p.getUniqueId()) == 0) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}