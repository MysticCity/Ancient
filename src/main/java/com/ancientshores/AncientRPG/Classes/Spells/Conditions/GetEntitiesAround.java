package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import org.bukkit.Location;

public class GetEntitiesAround extends IArgument {
    public GetEntitiesAround() {
        this.returnType = ParameterType.String;
        this.requiredTypes = new ParameterType[]{ParameterType.Location, ParameterType.Number, ParameterType.Number};
        this.name = "getentitiesaround";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (obj.length == 3 && isValidArgument(obj[0], (new Location[0]).getClass()) && obj[1] instanceof Number && obj[2] instanceof Number) {
            Location l = ((Location[]) obj[0])[0];
            int range = ((Number) obj[1]).intValue();
            int amount = ((Number) obj[2]).intValue();
            return so.getNearestEntities(l, range, amount);
        }
        return null;
    }
}