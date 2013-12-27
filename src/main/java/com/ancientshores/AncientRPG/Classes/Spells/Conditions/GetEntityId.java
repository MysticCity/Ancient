package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import org.bukkit.entity.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: Frederik Haselmeier
 * Date: 11.02.13
 * Time: 20:45
 */
public class GetEntityId extends IArgument {
    @ArgumentDescription(
            description = "Gets the entity id of the entity",
            parameterdescription = {"entity"}, returntype = ParameterType.String, rparams = {ParameterType.Entity})
    public GetEntityId() {
        this.pt = ParameterType.String;
        this.requiredTypes = new ParameterType[]{ParameterType.Entity};
        this.name = "getentityid";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (!(obj[0] instanceof Entity[])) {
            return null;
        }
        Entity e = ((Entity[]) obj[0])[0];
        return e.getEntityId();
    }
}
