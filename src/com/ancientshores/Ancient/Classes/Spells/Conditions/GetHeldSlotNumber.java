package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public class GetHeldSlotNumber
  extends IArgument
{
  @ArgumentDescription(description="Returns the slot number of the slot the player currently holds", parameterdescription={"player"}, returntype=ParameterType.Number, rparams={ParameterType.Player})
  public GetHeldSlotNumber()
  {
    this.returnType = ParameterType.Number;
    this.requiredTypes = new ParameterType[] { ParameterType.Player };
    this.name = "getheldslotnumber";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if ((obj[0] instanceof Player[]))
    {
      Player[] e = (Player[])obj[0];
      if (e.length > 0)
      {
        Player p = e[0];
        if (p != null) {
          return Integer.valueOf(p.getInventory().getHeldItemSlot());
        }
      }
    }
    return null;
  }
}
