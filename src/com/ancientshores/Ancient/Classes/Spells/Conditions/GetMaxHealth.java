package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import com.ancientshores.Ancient.HP.AncientHP;
import com.ancientshores.Ancient.HP.DamageConverter;
import com.ancientshores.Ancient.PlayerData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class GetMaxHealth
  extends IArgument
{
  @ArgumentDescription(description="Returns the maximum health of the given entity", parameterdescription={"entity"}, returntype=ParameterType.Number, rparams={ParameterType.Entity})
  public GetMaxHealth()
  {
    this.returnType = ParameterType.Number;
    this.requiredTypes = new ParameterType[] { ParameterType.Entity };
    this.name = "getmaxhealth";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if (!(obj[0] instanceof Entity[])) {
      return Integer.valueOf(0);
    }
    LivingEntity e = (LivingEntity)((Entity[])(Entity[])obj[0])[0];
    if ((e instanceof Player))
    {
      Player p = (Player)e;
      PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
      if (DamageConverter.isEnabledInWorld(p.getWorld())) {
        return Double.valueOf(pd.getHpsystem().getMaxHP());
      }
    }
    return Double.valueOf(e.getMaxHealth());
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Conditions\GetMaxHealth.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */