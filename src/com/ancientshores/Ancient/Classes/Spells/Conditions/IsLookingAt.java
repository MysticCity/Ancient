package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import java.util.UUID;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class IsLookingAt
  extends IArgument
{
  public IsLookingAt()
  {
    this.returnType = ParameterType.Boolean;
    this.requiredTypes = new ParameterType[] { ParameterType.Entity, ParameterType.Entity, ParameterType.Number };
    this.name = "islookingat";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if ((obj.length == 3) && ((obj[1] instanceof Entity[])) && (((Entity[])obj[1]).length > 0) && ((obj[0] instanceof Entity[])) && (((Entity[])obj[0]).length > 0) && ((obj[2] instanceof Number)))
    {
      Entity ent1 = ((Entity[])(Entity[])obj[0])[0];
      Entity ent2 = ((Entity[])(Entity[])obj[1])[0];
      int range = ((Number)obj[2]).intValue();
      if ((ent1 != null) && (ent2 != null) && ((ent1 instanceof LivingEntity)) && ((ent2 instanceof LivingEntity)) && 
        (so.getNearestEntityInSight((LivingEntity)ent1, range).getUniqueId().compareTo(ent2.getUniqueId()) == 0)) {
        return Boolean.valueOf(true);
      }
    }
    return Boolean.valueOf(false);
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Conditions\IsLookingAt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */