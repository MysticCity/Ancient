package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import com.ancientshores.Ancient.Experience.AncientExperience;
import com.ancientshores.Ancient.PlayerData;
import org.bukkit.entity.Player;

public class GetLevel
  extends IArgument
{
  @ArgumentDescription(description="Returns the level of the player", parameterdescription={"player"}, returntype=ParameterType.Number, rparams={ParameterType.Player})
  public GetLevel()
  {
    this.returnType = ParameterType.Number;
    this.requiredTypes = new ParameterType[] { ParameterType.Player };
    this.name = "getlevel";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if (!(obj[0] instanceof Player[])) {
      return null;
    }
    Player mPlayer = ((Player[])(Player[])obj[0])[0];
    if (AncientExperience.isWorldEnabled(mPlayer.getWorld()))
    {
      PlayerData pd = PlayerData.getPlayerData(mPlayer.getUniqueId());
      return Integer.valueOf(pd.getXpSystem().level);
    }
    return Integer.valueOf(0);
  }
}
