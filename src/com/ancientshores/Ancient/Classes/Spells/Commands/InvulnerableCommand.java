package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.Spell;
import com.ancientshores.Ancient.Listeners.AncientEntityListener;
import java.util.LinkedList;
import org.bukkit.entity.Player;

public class InvulnerableCommand
  extends ICommand
{
  @CommandDescription(description="<html>Makes the player invulnerable for the specified amount of time</html>", argnames={"player", "duration"}, name="Invulnerable", parameters={ParameterType.Player, ParameterType.Number})
  public InvulnerableCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Player, ParameterType.Number };
  }
  
  public boolean playCommand(final EffectArgs ca)
  {
    try
    {
      if (((ca.getParams().get(0) instanceof Player[])) && ((ca.getParams().get(1) instanceof Number)))
      {
        final Player[] target = (Player[])ca.getParams().get(0);
        final int time = (int)((Number)ca.getParams().get(1)).doubleValue();
        ca.getSpellInfo().working = true;
        int t = Math.round(time / 50);
        if (t == 0) {
          t = Integer.MAX_VALUE;
        }
        if ((target != null) && (target.length > 0) && (target[0] != null))
        {
          Ancient.plugin.scheduleThreadSafeTask(Ancient.plugin, new Runnable()
          {
            public void run()
            {
              if (time > 0) {
                for (Player p : target) {
                  if (p != null) {
                    AncientEntityListener.setinvulnerable(p, true);
                  }
                }
              }
              ca.getSpellInfo().working = false;
            }
          });
          Ancient.plugin.scheduleThreadSafeTask(Ancient.plugin, new Runnable()
          {
            public void run()
            {
              for (Player p : target) {
                if (p != null) {
                  AncientEntityListener.setinvulnerable(p, false);
                }
              }
            }
          }, t);
          
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


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\InvulnerableCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */