package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import org.bukkit.entity.Entity;

public class GetEntityId
  extends IArgument
{
  @ArgumentDescription(description="Gets the entity id of the entity", parameterdescription={"entity"}, returntype=ParameterType.String, rparams={ParameterType.Entity})
  public GetEntityId()
  {
    this.returnType = ParameterType.String;
    this.requiredTypes = new ParameterType[] { ParameterType.Entity };
    this.name = "getentityid";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if (!(obj[0] instanceof Entity[])) {
      return null;
    }
    Entity e = ((Entity[])(Entity[])obj[0])[0];
    return Integer.valueOf(e.getEntityId());
  }
}
