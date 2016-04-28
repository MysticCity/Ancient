package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import org.bukkit.entity.Player;

public class ArePlayers
  extends IArgument
{
  @ArgumentDescription(description="Checks a collection if all of them are players.", parameterdescription={"collection"}, returntype=ParameterType.Boolean, rparams={ParameterType.Player})
  public ArePlayers()
  {
    this.returnType = ParameterType.Boolean;
    this.requiredTypes = new ParameterType[] { ParameterType.Player };
    this.name = "areplayers";
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
      if (!(o instanceof Player)) {
        return Boolean.valueOf(false);
      }
    }
    return Boolean.valueOf(true);
  }
}
