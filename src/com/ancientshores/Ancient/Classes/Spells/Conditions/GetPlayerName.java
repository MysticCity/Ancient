package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import org.bukkit.entity.Player;

public class GetPlayerName
  extends IArgument
{
  @ArgumentDescription(description="Returns the name of the player", parameterdescription={"player"}, returntype=ParameterType.String, rparams={ParameterType.Player})
  public GetPlayerName()
  {
    this.returnType = ParameterType.String;
    this.requiredTypes = new ParameterType[] { ParameterType.Player };
    this.name = "getplayername";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if (!(obj[0] instanceof Player[])) {
      return null;
    }
    Player mPlayer = ((Player[])(Player[])obj[0])[0];
    return mPlayer.getName();
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Conditions\GetPlayerName.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */