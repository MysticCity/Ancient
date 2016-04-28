package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public class GetItemInHands
  extends IArgument
{
  @ArgumentDescription(description="Returns the amount of items of the specified id in the players inventory", parameterdescription={"player"}, returntype=ParameterType.Player, rparams={ParameterType.Player})
  public GetItemInHands()
  {
    this.returnType = ParameterType.Number;
    this.requiredTypes = new ParameterType[] { ParameterType.Player };
    this.name = "getiteminhands";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if (!(obj[0] instanceof Entity[])) {
      return null;
    }
    Entity ent = ((Entity[])(Entity[])obj[0])[0];
    if (((LivingEntity)ent).getEquipment().getItemInHand() == null) {
      return Integer.valueOf(0);
    }
    return Integer.valueOf(((LivingEntity)ent).getEquipment().getItemInHand().getTypeId());
  }
}
