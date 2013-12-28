package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import com.ancientshores.AncientRPG.Util.GlobalMethods;
import org.bukkit.entity.Entity;

public class GetEntityName extends IArgument {
    @ArgumentDescription(
            description = "Returns the name of the entity",
            parameterdescription = {"entity"}, returntype = ParameterType.String, rparams = {ParameterType.Entity})
    public GetEntityName() {
        this.pt = ParameterType.String;
        this.requiredTypes = new ParameterType[]{ParameterType.Entity};
        this.name = "getentityname";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (obj.length == 1 && obj[0] instanceof Entity[]) {
            return GlobalMethods.getStringByEntity(((Entity[]) obj[0])[0]);
        }
        return "";
    }
}