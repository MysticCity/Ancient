package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import java.util.LinkedList;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class SetItemNameCommand
  extends ICommand
{
  @CommandDescription(description="Sets the item name of the item in the players inventory", argnames={"player", "slot", "name"}, name="SetItemName", parameters={ParameterType.Player, ParameterType.Number, ParameterType.String})
  public SetItemNameCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Player, ParameterType.Number, ParameterType.String };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    try
    {
      if (((ca.getParams().get(0) instanceof Player[])) && ((ca.getParams().get(1) instanceof Number)) && ((ca.getParams().get(2) instanceof String)))
      {
        Player[] target = (Player[])ca.getParams().get(0);
        int slot = ((Number)ca.getParams().get(1)).intValue();
        String description = (String)ca.getParams().get(2);
        for (Player p : target) {
          if (p != null)
          {
            ItemMeta im = p.getInventory().getItem(slot).getItemMeta();
            im.setDisplayName(description);
            p.getInventory().getItem(slot).setItemMeta(im);
          }
        }
      }
    }
    catch (IndexOutOfBoundsException e)
    {
      return false;
    }
    return false;
  }
}
