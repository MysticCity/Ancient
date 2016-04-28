package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import java.util.LinkedList;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class EnchantCommand
  extends ICommand
{
  @CommandDescription(description="<html>Enchants the item in hand with the id (or all if allitems is true) with the enchantment and strength</html>", argnames={"material", "name", "level", "allitems"}, name="Enchant", parameters={ParameterType.Number, ParameterType.String, ParameterType.Number, ParameterType.Boolean})
  public EnchantCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Number, ParameterType.String, ParameterType.Number, ParameterType.Boolean };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    if ((ca.getParams().size() == 4) && ((ca.getParams().get(0) instanceof Number)) && ((ca.getParams().get(1) instanceof String)) && ((ca.getParams().get(2) instanceof Number)) && ((ca.getParams().get(3) instanceof Boolean)))
    {
      int id = (int)((Number)ca.getParams().get(0)).doubleValue();
      String name = (String)ca.getParams().get(1);
      int level = (int)((Number)ca.getParams().get(2)).doubleValue();
      boolean allitems = ((Boolean)ca.getParams().get(3)).booleanValue();
      Enchantment et = getTypeByName(name);
      if (allitems)
      {
        for (ItemStack is : ca.getCaster().getInventory().getContents()) {
          if ((is != null) && (is.getTypeId() == id)) {
            is.addEnchantment(et, level);
          }
        }
        for (ItemStack is : ca.getCaster().getInventory().getArmorContents()) {
          if ((is != null) && (is.getTypeId() == id)) {
            is.addEnchantment(et, level);
          }
        }
      }
      else if ((ca.getCaster().getItemInHand() != null) && (ca.getCaster().getItemInHand().getTypeId() == id))
      {
        ca.getCaster().getItemInHand().addEnchantment(et, level);
      }
      if (et == null) {
        return true;
      }
      return true;
    }
    return false;
  }
  
  public Enchantment getTypeByName(String name)
  {
    for (Enchantment e : )
    {
      if ((e != null) && (e.getName() != null) && (e.getName().replace("_", "").equalsIgnoreCase(name))) {
        return e;
      }
      if ((e != null) && (e.getName() != null) && (e.getName().equalsIgnoreCase(name))) {
        return e;
      }
    }
    return null;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\EnchantCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */