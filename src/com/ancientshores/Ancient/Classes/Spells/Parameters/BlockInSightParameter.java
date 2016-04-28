package com.ancientshores.Ancient.Classes.Spells.Parameters;

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
import org.bukkit.entity.Player;

@ParameterDescription(amount=1, description="<html>returns the nearest block in sight of the player</html>", returntype="Location", name="BlockInSight")
public class BlockInSightParameter
  implements IParameter
{
  public void parseParameter(EffectArgs ea, Player p, String[] subparam, ParameterType pt)
  {
    int range = 10;
    if (subparam != null) {
      try
      {
        if (ea.getSpell().variables.contains(subparam[0].toLowerCase())) {
          range = ea.getSpellInfo().parseVariable(p.getUniqueId(), subparam[0].toLowerCase());
        } else {
          range = Integer.parseInt(subparam[0]);
        }
      }
      catch (Exception e)
      {
        Ancient.plugin.getLogger().log(Level.WARNING, "Error in subparameter " + Arrays.toString(subparam) + " in command " + ea.getCommand().commandString + " falling back to default");
      }
    }
    if ((subparam != null) || (ea.getSpellInfo().blockInSight == null))
    {
      Location nBlock = ea.getSpellInfo().getBlockInSight(p, range);
      ea.getSpellInfo().blockInSight = nBlock;
      if (nBlock == null) {
        return;
      }
    }
    switch (pt)
    {
    case Location: 
      Location[] l = { ea.getSpellInfo().blockInSight };
      ea.getParams().addLast(l);
      return;
    }
    Ancient.plugin.getLogger().log(Level.SEVERE, "Syntax error in command " + ea.getCommand().commandString);
  }
  
  public String getName()
  {
    return "blockinsight";
  }
  
  public Object parseParameter(Player p, String[] subparam, SpellInformationObject so)
  {
    int range = 10;
    if (subparam != null) {
      try
      {
        if (so.mSpell.variables.contains(subparam[0].toLowerCase())) {
          range = so.parseVariable(p.getUniqueId(), subparam[0].toLowerCase());
        } else {
          range = Integer.parseInt(subparam[0]);
        }
      }
      catch (Exception ignored) {}
    }
    if ((subparam != null) || (so.blockInSight == null))
    {
      Location nBlock = so.getBlockInSight(p, range);
      so.blockInSight = nBlock;
      if (nBlock == null) {
        return null;
      }
    }
    return so.blockInSight;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Parameters\BlockInSightParameter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */