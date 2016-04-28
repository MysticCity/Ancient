package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.AncientClass;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.Spell;
import com.ancientshores.Ancient.PlayerData;
import java.util.LinkedList;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class BuffForTimeCommand
  extends ICommand
{
  @CommandDescription(description="<html>Buffs the player with the specified buff for the specified time in milliseconds</html>", argnames={"player", "buffname", "duration"}, name="BuffForTime", parameters={ParameterType.Player, ParameterType.String, ParameterType.Number})
  public BuffForTimeCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Player, ParameterType.String, ParameterType.Number };
  }
  
  public boolean playCommand(final EffectArgs ca)
  {
    try
    {
      if (((ca.getParams().get(0) instanceof Player[])) && ((ca.getParams().get(1) instanceof String)) && ((ca.getParams().get(2) instanceof Number)))
      {
        final Player[] players = (Player[])ca.getParams().get(0);
        final String name = (String)ca.getParams().get(1);
        final int number = (int)((Number)ca.getParams().get(2)).doubleValue();
        Ancient.plugin.scheduleThreadSafeTask(Ancient.plugin, new Runnable()
        {
          public void run()
          {
            for (final Player p : players)
            {
              final Spell s = AncientClass.getSpell(name, PlayerData.getPlayerData(p.getUniqueId()));
              if (s != null)
              {
                final int i = s.attachToEventAsBuff(p, ca.getCaster());
                Ancient.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Ancient.plugin, new Runnable()
                {
                  public void run()
                  {
                    s.detachBuffOfEvent(p, BuffForTimeCommand.1.this.val$ca.getCaster(), i);
                  }
                }, Math.round(number / 50));
              }
            }
          }
        });
        return true;
      }
      return false;
    }
    catch (IndexOutOfBoundsException e) {}
    return false;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\BuffForTimeCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */