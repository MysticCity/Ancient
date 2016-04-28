package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import org.bukkit.entity.Player;

public class HasPermission
  extends IArgument
{
  @ArgumentDescription(description="Returns true if the player has the specified permission, false otherwise", parameterdescription={"player", "permission"}, returntype=ParameterType.Boolean, rparams={ParameterType.Player, ParameterType.String})
  public HasPermission()
  {
    this.returnType = ParameterType.Boolean;
    this.requiredTypes = new ParameterType[] { ParameterType.Player, ParameterType.String };
    this.name = "haspermission";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if ((obj.length != 2) || (!(obj[0] instanceof Player[])) || (!(obj[1] instanceof String))) {
      return Boolean.valueOf(false);
    }
    Player p = ((Player[])(Player[])obj[0])[0];
    String s = (String)obj[1];
    return Boolean.valueOf(p.hasPermission(s));
  }
}
