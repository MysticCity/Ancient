package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import com.ancientshores.Ancient.Mana.ManaSystem;
import com.ancientshores.Ancient.PlayerData;
import org.bukkit.entity.Player;

public class GetMaxMana
  extends IArgument
{
  @ArgumentDescription(description="Returns the maximum mana of the given player", parameterdescription={"player"}, returntype=ParameterType.Number, rparams={ParameterType.Player})
  public GetMaxMana()
  {
    this.returnType = ParameterType.Number;
    this.requiredTypes = new ParameterType[] { ParameterType.Player };
    this.name = "getmaxmana";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if (!(obj[0] instanceof Player[])) {
      return Integer.valueOf(0);
    }
    Player p = ((Player[])(Player[])obj[0])[0];
    return Integer.valueOf(PlayerData.getPlayerData(p.getUniqueId()).getManasystem().getMaxmana());
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Conditions\GetMaxMana.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */