package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import java.util.HashSet;
import java.util.LinkedList;
import org.bukkit.Server;
import org.bukkit.entity.Player;

public class SetInvisibleCommand
  extends ICommand
{
  @CommandDescription(description="<html>Makes the player invisible or makes them visible</html>", argnames={"player", "isinvisible"}, name="SetInvisible", parameters={ParameterType.Player, ParameterType.Boolean})
  public SetInvisibleCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Player, ParameterType.Boolean };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    try
    {
      if (((ca.getParams().get(0) instanceof Player[])) && ((ca.getParams().get(1) instanceof Boolean)))
      {
        Player[] target = (Player[])ca.getParams().get(0);
        boolean b = ((Boolean)ca.getParams().get(1)).booleanValue();
        for (Player invisP : target) {
          if (invisP != null) {
            if (b)
            {
              for (Player p : Ancient.plugin.getServer().getOnlinePlayers()) {
                p.hidePlayer(invisP);
              }
              InvisibleCommand.invisiblePlayers.add(invisP.getUniqueId());
            }
            else
            {
              for (Player p : Ancient.plugin.getServer().getOnlinePlayers()) {
                p.showPlayer(invisP);
              }
              InvisibleCommand.invisiblePlayers.remove(invisP.getUniqueId());
            }
          }
        }
        return true;
      }
    }
    catch (IndexOutOfBoundsException ignored) {}
    return false;
  }
}
