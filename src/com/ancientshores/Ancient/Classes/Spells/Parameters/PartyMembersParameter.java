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
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

@ParameterDescription(amount=2, description="<html>returns members of the party<br> Textfield 1: range of parameter<br> Textfield 2: maximum amount of targets</html>", returntype="Player", name="PartyMembers")
public class PartyMembersParameter
  implements IParameter
{
  public void parseParameter(EffectArgs effectArgs, Player mPlayer, String[] subparam, ParameterType parameterType)
  {
    int range = 10;
    if (subparam != null) {
      try
      {
        if (effectArgs.getSpell().variables.contains(subparam[0].toLowerCase())) {
          range = effectArgs.getSpellInfo().parseVariable(mPlayer.getUniqueId(), subparam[0].toLowerCase());
        } else {
          range = Integer.parseInt(subparam[0]);
        }
      }
      catch (Exception e)
      {
        Ancient.plugin.getLogger().log(Level.WARNING, "Error in subparameter " + Arrays.toString(subparam) + " in command " + effectArgs.getCommand().commandString + " falling back to default");
      }
    }
    if ((subparam != null) || (effectArgs.getSpellInfo().partyMembers == null))
    {
      Entity[] nEntity = effectArgs.getSpellInfo().getPartyMembers(mPlayer, range);
      effectArgs.getSpellInfo().partyMembers = nEntity;
      if (nEntity == null) {
        return;
      }
    }
    switch (parameterType)
    {
    case Player: 
      effectArgs.getParams().addLast(effectArgs.getSpellInfo().partyMembers);
      break;
    case Entity: 
      effectArgs.getParams().addLast(effectArgs.getSpellInfo().partyMembers);
      break;
    case Location: 
      Location[] l = new Location[effectArgs.getSpellInfo().partyMembers.length];
      for (int i = 0; i < effectArgs.getSpellInfo().partyMembers.length; i++) {
        if (effectArgs.getSpellInfo().partyMembers[i] != null) {
          for (World w : Bukkit.getWorlds()) {
            for (Entity e : w.getEntities()) {
              if (e.getUniqueId().compareTo(effectArgs.getSpellInfo().partyMembers[i].getUniqueId()) == 0)
              {
                l[i] = e.getLocation();
                effectArgs.getParams().addLast(l);
              }
            }
          }
        }
      }
      break;
    case String: 
      String s = "";
      for (Entity e : effectArgs.getSpellInfo().partyMembers) {
        s = s + PlayerFinder.getPlayerName(e.getUniqueId()) + ",";
      }
      effectArgs.getParams().addLast(s);
      break;
    default: 
      Ancient.plugin.getLogger().log(Level.SEVERE, "Syntax error in command " + effectArgs.getCommand().commandString);
    }
  }
  
  public String getName()
  {
    return "partymembers";
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
    if ((subparam != null) || (so.partyMembers == null)) {
      so.partyMembers = so.getPartyMembers(mPlayer, range);
    }
    return so.partyMembers;
  }
}
