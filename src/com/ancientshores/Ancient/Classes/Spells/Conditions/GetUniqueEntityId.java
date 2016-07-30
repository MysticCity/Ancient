package com.ancientshores.Ancient.Classes.Spells.Conditions;

import org.bukkit.entity.Entity;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;

public class GetUniqueEntityId extends IArgument {
    @ArgumentDescription(
            description = "Gets the uuid of the entity",
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