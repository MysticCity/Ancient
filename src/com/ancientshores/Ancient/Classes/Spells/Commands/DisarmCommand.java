package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.Spell;
import com.ancientshores.Ancient.Listeners.AncientSpellListener;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class DisarmCommand
  extends ICommand
{
  @CommandDescription(description="<html>Disarms the player for the specified time</html>", argnames={"player", "duration"}, name="Disarm", parameters={ParameterType.Player, ParameterType.Number})
  public DisarmCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Player, ParameterType.Number };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    try
    {
      if (((ca.getParams().get(0) instanceof Player[])) && ((ca.getParams().get(1) instanceof Number)))
      {
        final Player[] target = (Player[])ca.getParams().get(0);
        final int time = (int)((Number)ca.getParams().get(1)).doubleValue();
        if ((target != null) && (target.length > 0))
        {
          Ancient.plugin.scheduleThreadSafeTask(Ancient.plugin, new Runnable()
          {
            public void run()
            {
              for (final Player p : target) {
                if (p != null) {
                  if ((time > 0) && (!AncientSpellListener.disarmList.containsKey(p.getUniqueId())))
                  {
                    final ItemStack is = p.getItemInHand();
                    AncientSpellListener.disarmList.put(p.getUniqueId(), is);
                    p.setItemInHand(null);
                    Ancient.plugin.scheduleThreadSafeTask(Ancient.plugin, new Runnable()
                    {
                      public void run()
                      {
                        if (p.getItemInHand() == null) {
                          p.setItemInHand(is);
                        } else {
                          p.getInventory().addItem(new ItemStack[] { is });
                        }
                        AncientSpellListener.disarmList.remove(p.getUniqueId());
                      }
                    }, Math.round(time / 50));
                  }
                }
              }
            }
          });
          return true;
        }
        if (ca.getSpell().active) {
          ca.getCaster().sendMessage("Target not found");
        }
      }
    }
    catch (IndexOutOfBoundsException ignored) {}
    return false;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\DisarmCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */