package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import com.ancientshores.Ancient.Mana.ManaSystem;
import com.ancientshores.Ancient.PlayerData;
import org.bukkit.entity.Player;

public class GetMana
  extends IArgument
{
  @ArgumentDescription(description="Returns the current amount of mana the player has.", parameterdescription={"player"}, returntype=ParameterType.Number, rparams={ParameterType.Player})
  public GetMana()
  {
    this.returnType = ParameterType.Number;
    this.requiredTypes = new ParameterType[] { ParameterType.Player };
    this.name = "getmana";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if (!(obj[0] instanceof Player[])) {
      return Integer.valueOf(0);
    }
    Player e = ((Player[])(Player[])obj[0])[0];
    return Integer.valueOf(PlayerData.getPlayerData(e.getUniqueId()).getManasystem().getCurrentMana());
  }
}
