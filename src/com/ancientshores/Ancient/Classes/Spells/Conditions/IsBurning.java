package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class IsBurning
  extends IArgument
{
  @ArgumentDescription(description="Returns true if the entity is buring, false otherwise", parameterdescription={"entity"}, returntype=ParameterType.Boolean, rparams={ParameterType.Entity})
  public IsBurning()
  {
    this.returnType = ParameterType.Boolean;
    this.requiredTypes = new ParameterType[] { ParameterType.Entity };
    this.name = "isburning";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if ((obj.length == 1) && ((obj[0] instanceof Entity[])) && (((Entity[])obj[0]).length > 0))
    {
      Entity e = ((Entity[])(Entity[])obj[0])[0];
      if ((e != null) && ((e instanceof LivingEntity))) {
        return Boolean.valueOf(e.getFireTicks() > 0);
      }
    }
    return Boolean.valueOf(false);
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Conditions\IsBurning.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */