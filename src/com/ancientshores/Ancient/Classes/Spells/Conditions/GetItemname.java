package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class GetItemname
  extends IArgument
{
  @ArgumentDescription(description="Returns the name of the item in the specified slot if it has one. Else the default name. Return nothing when there is no item in this slot.", parameterdescription={"player", "slot"}, returntype=ParameterType.Number, rparams={ParameterType.Player, ParameterType.Number})
  public GetItemname()
  {
    this.returnType = ParameterType.String;
    this.requiredTypes = new ParameterType[] { ParameterType.Player, ParameterType.Number };
    this.name = "getitemname";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if ((obj.length < 2) || (!(obj[0] instanceof Player[])) || (((Player[])obj[0]).length == 0) || (!(obj[1] instanceof Number))) {
      return null;
    }
    try
    {
      Player p = ((Player[])(Player[])obj[0])[0];
      int slot = ((Number)obj[1]).intValue();
      
      ItemStack item = p.getInventory().getItem(slot);
      if (item != null)
      {
        if ((item.hasItemMeta()) && 
          (item.getItemMeta().hasDisplayName())) {
          return item.getItemMeta().getDisplayName();
        }
        return item.getType().toString();
      }
      return null;
    }
    catch (Exception e) {}
    return null;
  }
}
