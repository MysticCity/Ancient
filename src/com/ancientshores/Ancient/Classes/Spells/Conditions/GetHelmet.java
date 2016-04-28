package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public class GetHelmet
  extends IArgument
{
  @ArgumentDescription(description="Returns the id of the helmet the player currently wears", parameterdescription={"player"}, returntype=ParameterType.Number, rparams={ParameterType.Player})
  public GetHelmet()
  {
    this.returnType = ParameterType.Number;
    this.requiredTypes = new ParameterType[] { ParameterType.Player };
    this.name = "gethelmet";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if (!(obj[0] instanceof Entity[])) {
      return null;
    }
    Entity ent = ((Entity[])(Entity[])obj[0])[0];
    if (((LivingEntity)ent).getEquipment().getHelmet() == null) {
      return Integer.valueOf(0);
    }
    return Integer.valueOf(((LivingEntity)ent).getEquipment().getHelmet().getTypeId());
  }
}
