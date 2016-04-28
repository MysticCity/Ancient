package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Listeners.AncientSpellListener;
import java.util.LinkedList;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class DisruptOnDeathCommand
  extends ICommand
{
  @CommandDescription(description="<html> Disrupts the spell if player dies in the specified time</html>", argnames={"player", "duration"}, name="DisruptOnDeath", parameters={ParameterType.Player, ParameterType.Number})
  public DisruptOnDeathCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Player, ParameterType.Number };
  }
  
  public boolean playCommand(final EffectArgs ca)
  {
    if ((ca.getParams().size() == 2) && 
      ((ca.getParams().get(0) instanceof Player[])) && ((ca.getParams().get(1) instanceof Number)))
    {
      final Player[] players = (Player[])ca.getParams().get(0);
      final int time = (int)((Number)ca.getParams().get(1)).doubleValue();
      Ancient.plugin.scheduleThreadSafeTask(Ancient.plugin, new Runnable()
      {
        public void run()
        {
          for (final Player p : players)
          {
            AncientSpellListener.addDisruptCommand(p, ca.getSpellInfo(), AncientSpellListener.disruptOnDeath);
            Ancient.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Ancient.plugin, new Runnable()
            {
              public void run()
              {
                AncientSpellListener.removeDisruptCommand(p, DisruptOnDeathCommand.1.this.val$ca.getSpellInfo(), AncientSpellListener.disruptOnDeath);
              }
            }, Math.round(time / 50));
          }
        }
      });
    }
    return true;
  }
}
