package com.ancientshores.Ancient.Classes.Spells.Parameters;

import com.ancient.util.PlayerFinder;
import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.Command;
import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.Ancient.Classes.Spells.IParameter;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.Spell;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class NearestHostilePlayerParameter
  implements IParameter
{
  public void parseParameter(EffectArgs ea, Player mPlayer, String[] subparam, ParameterType pt)
  {
    int range = 10;
    if (subparam != null) {
      try
      {
        if (ea.getSpell().variables.contains(subparam[0].toLowerCase())) {
          range = ea.getSpellInfo().parseVariable(mPlayer.getUniqueId(), subparam[0].toLowerCase());
        } else {
          range = Integer.parseInt(subparam[0]);
        }
      }
      catch (Exception e)
      {
        Ancient.plugin.getLogger().log(Level.WARNING, "Error in subparameter " + Arrays.toString(subparam) + " in command " + ea.getCommand().commandString + " falling back to default");
      }
    }
    if ((subparam != null) || (ea.getSpellInfo().hostilePlayers == null) || (ea.getSpellInfo().hostilePlayers[0] == null))
    {
      Entity[] players = ea.getSpellInfo().getNearestHostilePlayers(mPlayer, range, 3);
      ea.getSpellInfo().hostilePlayers = players;
      if (players == null) {
        return;
      }
    }
    switch (pt)
    {
    case Entity: 
      ea.getParams().addLast(ea.getSpellInfo().hostilePlayers);
      break;
    case Location: 
      Location[] l = new Location[ea.getSpellInfo().hostilePlayers.length];
      for (int i = 0; i < ea.getSpellInfo().hostilePlayers.length; i++) {
        if (ea.getSpellInfo().hostilePlayers[i] != null) {
          for (World w : Bukkit.getWorlds()) {
            for (Entity e : w.getEntities()) {
              if (e.getUniqueId().compareTo(ea.getSpellInfo().hostilePlayers[i].getUniqueId()) == 0) {
                l[i] = e.getLocation();
              }
            }
          }
        }
      }
      ea.getParams().addLast(l);
      break;
    case String: 
      String s = "";
      for (Entity p : ea.getSpellInfo().hostilePlayers) {
        s = s + PlayerFinder.getPlayerName(p.getUniqueId()) + ",";
      }
      ea.getParams().addLast(s);
      break;
    default: 
      Ancient.plugin.getLogger().log(Level.SEVERE, "Syntax error in command " + ea.getCommand().commandString);
    }
  }
  
  public String getName()
  {
    return "nearesthostileplayer";
  }
  
  public Object parseParameter(Player mPlayer, String[] subparam, SpellInformationObject so)
  {
    int range = 10;
    if (subparam != null) {
      try
      {
        if (so.mSpell.variables.contains(subparam[0].toLowerCase())) {
          range = so.parseVariable(mPlayer.getUniqueId(), subparam[0].toLowerCase());
        } else {
          range = Integer.parseInt(subparam[0]);
        }
      }
      catch (Exception ignored) {}
    }
    if ((subparam != null) || (so.hostilePlayers == null) || (so.hostilePlayers[0] == null)) {
      so.hostilePlayers = so.getNearestHostilePlayers(mPlayer, range, 3);
    }
    return so.hostilePlayers;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Parameters\NearestHostilePlayerParameter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */