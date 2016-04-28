package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import java.util.HashMap;
import java.util.LinkedList;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class RemoveItemCommand
  extends ICommand
{
  @CommandDescription(description="<html>Removes the amount of items from the players inventory, cancels the spell if it fails if cancelonfail is true</html>", argnames={"player", "material", "amount", "cancelonfail"}, name="RemoveItem", parameters={ParameterType.Player, ParameterType.Material, ParameterType.Number, ParameterType.Boolean})
  public RemoveItemCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Player, ParameterType.Material, ParameterType.Number, ParameterType.Boolean };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    try
    {
      if (((ca.getParams().get(0) instanceof Player[])) && ((ca.getParams().get(1) instanceof Material)) && ((ca.getParams().get(2) instanceof Number)) && ((ca.getParams().get(3) instanceof Boolean)))
      {
        Player[] players = (Player[])ca.getParams().get(0);
        Material mat = (Material)ca.getParams().get(1);
        int amount = (int)((Number)ca.getParams().get(2)).doubleValue();
        boolean cancelOnFail = ((Boolean)ca.getParams().get(3)).booleanValue();
        for (Player p : players)
        {
          int zaehler = 0;
          HashMap<Integer, ? extends ItemStack> items = p.getInventory().all(mat);
          for (ItemStack istack : items.values()) {
            zaehler += istack.getAmount();
          }
          if ((cancelOnFail) && (zaehler < amount))
          {
            ca.getSpellInfo().canceled = true;
            return false;
          }
          int anzahl = amount;
          for (ItemStack istack : items.values()) {
            if (anzahl >= istack.getAmount())
            {
              p.getInventory().remove(istack);
              anzahl -= istack.getAmount();
            }
            else
            {
              istack.setAmount(istack.getAmount() - anzahl);
              break;
            }
          }
          p.updateInventory();
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


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\RemoveItemCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */