package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import org.bukkit.entity.Player;

public class GetFoodLevel
  extends IArgument
{
  @ArgumentDescription(description="Returns the food level of the player", parameterdescription={"player"}, returntype=ParameterType.Number, rparams={ParameterType.Player})
  public GetFoodLevel()
  {
    this.returnType = ParameterType.Number;
    this.requiredTypes = new ParameterType[] { ParameterType.Player };
    this.name = "getfoodlevel";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if (!(obj[0] instanceof Player[])) {
      return Integer.valueOf(0);
    }
    Player p = ((Player[])(Player[])obj[0])[0];
    return Integer.valueOf(p.getFoodLevel());
  }
}
