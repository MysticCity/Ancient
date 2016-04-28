package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import org.bukkit.entity.Entity;

public class AreEntities
  extends IArgument
{
  @ArgumentDescription(description="Checks a collection if all of them are entities.", parameterdescription={"collection"}, returntype=ParameterType.Boolean, rparams={ParameterType.Entity})
  public AreEntities()
  {
    this.returnType = ParameterType.Boolean;
    this.requiredTypes = new ParameterType[] { ParameterType.Entity };
    this.name = "areentities";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if (!(obj[0] instanceof Object[])) {
      return Integer.valueOf(0);
    }
    Object[] objs = (Object[])obj[0];
    if (objs.length == 0) {
      return Boolean.valueOf(false);
    }
    for (Object o : objs) {
      if (!(o instanceof Entity)) {
        return Boolean.valueOf(false);
      }
    }
    return Boolean.valueOf(true);
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Conditions\AreEntities.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */