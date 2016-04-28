package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.AncientClass;
import com.ancientshores.Ancient.Classes.Commands.ClassCastCommand;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.UUID;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class SilenceCommand
  extends ICommand
{
  @CommandDescription(description="<html>Silences the player for the specified amount of time</html>", argnames={"player", "duration"}, name="Silence", parameters={ParameterType.Player, ParameterType.Number})
  public SilenceCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Player, ParameterType.Number };
  }
  
  public boolean playCommand(final EffectArgs ca)
  {
    if ((ca.getParams().size() == 2) && 
      ((ca.getParams().get(0) instanceof Player[])) && ((ca.getParams().get(1) instanceof Number)))
    {
      final Player[] players = (Player[])ca.getParams().get(0);
      final Integer i = Integer.valueOf((int)((Number)ca.getParams().get(1)).doubleValue());
      Ancient.plugin.scheduleThreadSafeTask(Ancient.plugin, new Runnable()
      {
        public void run()
        {
          Player p;
          for (p : players) {
            if (p != null)
            {
              ClassCastCommand.silencedPlayers.put(ca.getSpellInfo(), p.getUniqueId());
              for (Map.Entry<SpellInformationObject, UUID> entry : AncientClass.executedSpells.entrySet()) {
                if (((UUID)entry.getValue()).compareTo(p.getUniqueId()) == 0) {
                  ((SpellInformationObject)entry.getKey()).canceled = true;
                }
              }
            }
          }
          Ancient.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Ancient.plugin, new Runnable()
          {
            public void run()
            {
              for (Player pl : SilenceCommand.1.this.val$players) {
                if (pl != null) {
                  ClassCastCommand.silencedPlayers.remove(SilenceCommand.1.this.val$ca.getSpellInfo());
                }
              }
            }
          }, Math.round(i.intValue() / 50));
        }
      });
      return true;
    }
    return false;
  }
}
