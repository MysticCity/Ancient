package com.ancientshores.Ancient.Classes.Spells.Conditions;

import org.bukkit.entity.Entity;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import com.ancientshores.Ancient.Util.GlobalMethods;

public class GetEntityName extends IArgument {
    @ArgumentDescription(
            description = "Returns the name of the entity",
            parameterdescription = {"entity"}, returntype = ParameterType.String, rparams = {ParameterType.Entity})
    public GetEntityName() {
        this.returnType = ParameterType.String;
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