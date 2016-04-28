package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import java.util.LinkedList;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class AddItemCommand
  extends ICommand
{
  @CommandDescription(description="<html>Adds the specified amount of items to the invenory of the player</html>", argnames={"the player", "the itemid", "the amount"}, name="AddItem", parameters={ParameterType.Player, ParameterType.Number, ParameterType.Number})
  public AddItemCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Player, ParameterType.Number, ParameterType.Number };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    try
    {
      if ((ca.getParams().size() == 3) && ((ca.getParams().get(0) instanceof Player[])) && ((ca.getParams().get(1) instanceof Number)) && ((ca.getParams().get(2) instanceof Number)))
      {
        Player[] players = (Player[])ca.getParams().get(0);
        Material mat = Material.getMaterial(((Number)ca.getParams().get(1)).intValue());
        int amount = ((Number)ca.getParams().get(2)).intValue();
        for (Player p : players) {
          if (p != null)
          {
            p.getInventory().addItem(new ItemStack[] { new ItemStack(mat, amount) });
            p.updateInventory();
          }
        }
      }
    }
    catch (IndexOutOfBoundsException e)
    {
      return false;
    }
    return true;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\AddItemCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */