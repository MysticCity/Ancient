package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.CooldownTimer;
import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import com.ancientshores.Ancient.PlayerData;
import org.bukkit.entity.Entity;

public class IsCooldownReady
  extends IArgument
{
  @ArgumentDescription(description="Returns true if the cooldown with the name is ready", parameterdescription={"cooldownname"}, returntype=ParameterType.Boolean, rparams={ParameterType.String})
  public IsCooldownReady()
  {
    this.returnType = ParameterType.Boolean;
    this.requiredTypes = new ParameterType[] { ParameterType.String };
    this.name = "iscooldownready";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if ((obj.length < 1) || (!(obj[0] instanceof String))) {
      return null;
    }
    String cooldownname = (String)obj[0];
    PlayerData pd = PlayerData.getPlayerData(so.buffcaster.getUniqueId());
    for (CooldownTimer cd : pd.getCooldownTimer()) {
      if (cd.cooldownname.equals(cooldownname)) {
        return Boolean.valueOf(cd.ready);
      }
    }
    return Boolean.valueOf(true);
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Conditions\IsCooldownReady.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */