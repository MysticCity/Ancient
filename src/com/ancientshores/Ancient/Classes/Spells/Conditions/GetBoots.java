package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public class GetBoots
  extends IArgument
{
  @ArgumentDescription(description="Returns the id of the players boots", parameterdescription={"player"}, returntype=ParameterType.Number, rparams={ParameterType.Player})
  public GetBoots()
  {
    this.returnType = ParameterType.Number;
    this.requiredTypes = new ParameterType[] { ParameterType.Player };
    this.name = "getboots";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if (!(obj[0] instanceof Entity[])) {
      return null;
    }
    Entity ent = ((Entity[])(Entity[])obj[0])[0];
    if (((LivingEntity)ent).getEquipment().getBoots() == null) {
      return Integer.valueOf(0);
    }
    return Integer.valueOf(((LivingEntity)ent).getEquipment().getBoots().getTypeId());
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Conditions\GetBoots.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */