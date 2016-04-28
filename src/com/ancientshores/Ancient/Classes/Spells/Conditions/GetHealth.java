package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class GetHealth
  extends IArgument
{
  @ArgumentDescription(description="Returns the health of the player", parameterdescription={"entity"}, returntype=ParameterType.Number, rparams={ParameterType.Entity})
  public GetHealth()
  {
    this.returnType = ParameterType.Number;
    this.requiredTypes = new ParameterType[] { ParameterType.Entity };
    this.name = "gethealth";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if (!(obj[0] instanceof Entity[])) {
      return Integer.valueOf(0);
    }
    LivingEntity e = (LivingEntity)((Entity[])(Entity[])obj[0])[0];
    return Double.valueOf(e.getHealth());
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Conditions\GetHealth.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */