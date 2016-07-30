package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;

public class Replace extends IArgument {
    @ArgumentDescription(
            description = "Replaces the searchstring in the source by the replacestring",
            parameterdescription = {"source", "searchstring", "replacestring"}, returntype = ParameterType.String, rparams = {ParameterType.String, ParameterType.String, ParameterType.String})
    public Replace() {
        this.returnType = ParameterType.String;
        this.requiredTypes = new ParameterType[]{ParameterType.String, ParameterType.String, ParameterType.String};
        this.name = "replace";
    }

    @Override
    public Object getArgument(Object obj[], SpellInformationObject so) {
        if (obj.length < 3 || obj[0] == null || obj[1] == null || obj[2] == null) {
            return null;
        }
        String source = obj[0].toString();
        String searchstring = obj[1].toString();
        String replacestring = obj[2].toString();
        return source.replace(searchstring, replacestring);
    }
}