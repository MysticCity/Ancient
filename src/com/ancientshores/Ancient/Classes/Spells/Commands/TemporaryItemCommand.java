package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import java.util.HashMap;
import java.util.LinkedList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitScheduler;

public class TemporaryItemCommand
  extends ICommand
{
  @CommandDescription(description="<html>Gives the player specified items for the specified amount of time</html>", argnames={"player", "materialid", "amount", "time"}, name="TemporaryItem", parameters={ParameterType.Player, ParameterType.Number, ParameterType.Number, ParameterType.Number})
  public TemporaryItemCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Player, ParameterType.Number, ParameterType.Number, ParameterType.Number };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    try
    {
      if ((ca.getParams().size() >= 3) && ((ca.getParams().get(0) instanceof Player[])) && ((ca.getParams().get(1) instanceof Number)) && ((ca.getParams().get(2) instanceof Number)) && ((ca.getParams().get(3) instanceof Number)))
      {
        Player[] players = (Player[])ca.getParams().get(0);
        final int id = ((Number)ca.getParams().get(1)).intValue();
        final int amount = ((Number)ca.getParams().get(2)).intValue();
        int time = ((Number)ca.getParams().get(3)).intValue();
        for (final Player p : players) {
          if (p != null)
          {
            ItemStack is = new ItemStack(id, amount);
            if (p.getInventory().firstEmpty() != -1)
            {
              p.getInventory().addItem(new ItemStack[] { is });
              p.updateInventory();
            }
            int ticks = Math.round(time / 50);
            Bukkit.getScheduler().scheduleSyncDelayedTask(Ancient.plugin, new Runnable()
            {
              public void run()
              {
                int anzahl = amount;
                HashMap<Integer, ? extends ItemStack> items = p.getInventory().all(id);
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
            }, ticks);
            
            p.getInventory().addItem(new ItemStack[] { is });
          }
        }
        return true;
      }
    }
    catch (Exception ignored) {}
    return false;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\TemporaryItemCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */