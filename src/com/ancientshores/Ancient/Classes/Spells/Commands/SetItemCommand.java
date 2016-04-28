package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import java.util.LinkedList;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class SetItemCommand
  extends ICommand
{
  @CommandDescription(description="Sets the item in the specified slot of the player's inventory", argnames={"player", "material", "slot", "amount"}, name="SetItem", parameters={ParameterType.Player, ParameterType.Material, ParameterType.Number, ParameterType.Number})
  public SetItemCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Player, ParameterType.Material, ParameterType.Number, ParameterType.Number };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    try
    {
      if (((ca.getParams().get(0) instanceof Player[])) && ((ca.getParams().get(1) instanceof Material)) && ((ca.getParams().get(2) instanceof Number)) && ((ca.getParams().get(3) instanceof Number)))
      {
        Player[] players = (Player[])ca.getParams().get(0);
        Material mat = (Material)ca.getParams().get(1);
        int amount = (int)((Number)ca.getParams().get(3)).doubleValue();
        int slot = (int)((Number)ca.getParams().get(2)).doubleValue();
        for (Player p : players) {
          if (p != null)
          {
            ItemStack is = new ItemStack(mat, amount);
            p.getInventory().setItem(slot, is);
            p.updateInventory();
          }
        }
      }
    }
    catch (Exception e)
    {
      return false;
    }
    return true;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\SetItemCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */