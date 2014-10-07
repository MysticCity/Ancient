package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import org.bukkit.entity.Entity;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;

public class GetUniqueEntityId extends IArgument {
    @ArgumentDescription(
            description = "Gets the unique entity id of the entity",
            parameterdescription = {"entity"}, returntype = ParameterType.String, rparams = {ParameterType.Entity})
    public GetUniqueEntityId() {
        this.returnType = ParameterType.String;
        this.requiredTypes = new ParameterType[]{ParameterType.Entity};
        this.name = "getentityid";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (!(obj[0] instanceof Entity[])) {
            return null;
        }
        Entity e = ((Entity[]) obj[0])[0];
        return e.getUniqueId().toString();
    }
}