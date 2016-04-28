package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Listeners.AncientPlayerListener;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.UUID;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class InvisibleCommand
  extends ICommand
{
  @CommandDescription(description="<html>Makes the player invisible for the specified amount of time</html>", argnames={"player", "duration"}, name="Invisible", parameters={ParameterType.Player, ParameterType.Number})
  public static final HashSet<UUID> invisiblePlayers = new HashSet();
  
  public InvisibleCommand()
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
        Ancient.plugin.scheduleThreadSafeTask(Ancient.plugin, new Runnable()
        {
          public void run()
          {
            int t = Math.round(time / 50);
            if (t == 0) {
              t = Integer.MAX_VALUE;
            }
            for (final Player invisP : target) {
              if (invisP != null)
              {
                for (Player p : Ancient.plugin.getServer().getOnlinePlayers()) {
                  p.hidePlayer(invisP);
                }
                InvisibleCommand.invisiblePlayers.add(invisP.getUniqueId());
                final int id = AncientPlayerListener.addInvis(invisP);
                Ancient.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Ancient.plugin, new Runnable()
                {
                  public void run()
                  {
                    if (!AncientPlayerListener.removeInvis(invisP, id)) {
                      return;
                    }
                    for (Player p : Ancient.plugin.getServer().getOnlinePlayers())
                    {
                      p.showPlayer(invisP);
                      InvisibleCommand.invisiblePlayers.remove(invisP.getUniqueId());
                    }
                  }
                }, t);
              }
            }
          }
        });
        return true;
      }
    }
    catch (IndexOutOfBoundsException ignored) {}
    return false;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\InvisibleCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */