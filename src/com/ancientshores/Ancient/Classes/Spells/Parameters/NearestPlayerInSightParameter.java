package com.ancientshores.Ancient.Classes.Spells.Parameters;

import com.ancient.util.PlayerFinder;
import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.Command;
import com.ancientshores.Ancient.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.Ancient.Classes.Spells.IParameter;
import com.ancientshores.Ancient.Classes.Spells.ParameterDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.Spell;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

@ParameterDescription(amount=1, description="<html>returns the nearest players in sight of the caster<br> Textfield: range of parameter</html>", returntype="Player", name="NearestPlayerInSight")
public class NearestPlayerInSightParameter
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
    if ((subparam != null) || (ea.getSpellInfo().nearestPlayerInSight == null))
    {
      Entity playerInSight = ea.getSpellInfo().getNearestPlayerInSight(mPlayer, range);
      ea.getSpellInfo().nearestPlayerInSight = playerInSight;
      if (playerInSight == null) {
        return;
      }
    }
    switch (pt)
    {
    case Player: 
      Entity[] p = { ea.getSpellInfo().nearestPlayerInSight };
      ea.getParams().addLast(p);
      break;
    case Entity: 
      Entity[] e = { ea.getSpellInfo().nearestPlayerInSight };
      ea.getParams().addLast(e);
      break;
    case Location: 
      Location[] l = { ea.getSpellInfo().nearestPlayerInSight.getLocation() };
      ea.getParams().addLast(l);
      break;
    case String: 
      ea.getParams().addLast(PlayerFinder.getPlayerName(ea.getSpellInfo().nearestPlayerInSight.getUniqueId()));
      break;
    default: 
      Ancient.plugin.getLogger().log(Level.SEVERE, "Syntax error in command " + ea.getCommand().commandString);
    }
  }
  
  public String getName()
  {
    return "nearestplayerinsight";
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
    if ((subparam != null) || (so.nearestPlayerInSight == null)) {
      so.nearestPlayerInSight = so.getNearestPlayerInSight(mPlayer, range);
    }
    return so.nearestPlayerInSight;
  }
}
