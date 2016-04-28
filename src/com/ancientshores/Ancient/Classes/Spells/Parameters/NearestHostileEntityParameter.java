package com.ancientshores.Ancient.Classes.Spells.Parameters;

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

public class NearestHostileEntityParameter
  implements IParameter
{
  public void parseParameter(EffectArgs ea, Player mPlayer, String[] subparam, ParameterType pt)
  {
    int range = 10;
    int count = 1;
    if (subparam != null)
    {
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
      try
      {
        if (ea.getSpell().variables.contains(subparam[1].toLowerCase())) {
          count = ea.getSpellInfo().parseVariable(mPlayer.getUniqueId(), subparam[1].toLowerCase());
        } else {
          count = Integer.parseInt(subparam[1]);
        }
      }
      catch (Exception e)
      {
        Ancient.plugin.getLogger().log(Level.WARNING, "Error in subparameter " + Arrays.toString(subparam) + " in command " + ea.getCommand().commandString + " falling back to default");
      }
    }
    if ((subparam != null) || (ea.getSpellInfo().hostileEntities == null) || (ea.getSpellInfo().hostileEntities[0] == null))
    {
      Entity[] nEntities = ea.getSpellInfo().getNearestHostileEntities(mPlayer, range, count);
      ea.getSpellInfo().hostileEntities = nEntities;
      if (nEntities == null) {
        return;
      }
    }
    switch (pt)
    {
    case Entity: 
      Entity[] entities = ea.getSpellInfo().hostileEntities;
      ea.getParams().addLast(entities);
      break;
    case Location: 
      Location[] l = new Location[ea.getSpellInfo().hostileEntities.length];
      for (int i = 0; i < ea.getSpellInfo().hostileEntities.length; i++) {
        if (ea.getSpellInfo().hostileEntities[i] != null) {
          for (World w : Bukkit.getWorlds()) {
            for (Entity e : w.getEntities()) {
              if (e.getUniqueId().compareTo(ea.getSpellInfo().hostileEntities[i].getUniqueId()) == 0) {
                l[i] = e.getLocation();
              }
            }
          }
        }
      }
      ea.getParams().addLast(l);
      break;
    default: 
      Ancient.plugin.getLogger().log(Level.SEVERE, "Syntax error in command " + ea.getCommand().commandString);
    }
  }
  
  public String getName()
  {
    return "nearesthostilenetity";
  }
  
  public Object parseParameter(Player mPlayer, String[] subparam, SpellInformationObject so)
  {
    int range = 10;
    int count = 1;
    if (subparam != null)
    {
      try
      {
        if (so.mSpell.variables.contains(subparam[0].toLowerCase())) {
          range = so.parseVariable(mPlayer.getUniqueId(), subparam[0].toLowerCase());
        } else {
          range = Integer.parseInt(subparam[0]);
        }
      }
      catch (Exception ignored) {}
      try
      {
        if (so.mSpell.variables.contains(subparam[1].toLowerCase())) {
          count = so.parseVariable(mPlayer.getUniqueId(), subparam[1].toLowerCase());
        } else {
          count = Integer.parseInt(subparam[1]);
        }
      }
      catch (Exception ignored) {}
    }
    if ((subparam != null) || (so.hostileEntities == null) || (so.hostileEntities[0] == null)) {
      so.hostileEntities = so.getNearestHostileEntities(mPlayer, range, count);
    }
    return so.hostileEntities;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Parameters\NearestHostileEntityParameter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */