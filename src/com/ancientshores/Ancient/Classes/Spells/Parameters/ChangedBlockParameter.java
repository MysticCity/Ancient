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
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityChangeBlockEvent;

@ParameterDescription(amount=0, description="Returns the location of the broken block of a blockbreakevent", returntype="Location", name="ChangedBlock")
public class ChangedBlockParameter
  implements IParameter
{
  public void parseParameter(EffectArgs ea, Player p, String[] subparam, ParameterType pt)
  {
    if ((!ea.getSpell().active) && ((ea.getSpellInfo().mEvent instanceof EntityChangeBlockEvent)))
    {
      EntityChangeBlockEvent event = (EntityChangeBlockEvent)ea.getSpellInfo().mEvent;
      int add = 0;
      if (subparam != null) {
        try
        {
          if (ea.getSpell().variables.contains(subparam[0].toLowerCase())) {
            add = ea.getSpellInfo().parseVariable(p.getUniqueId(), subparam[0].toLowerCase());
          } else {
            add = Integer.parseInt(subparam[0]);
          }
        }
        catch (Exception e)
        {
          Ancient.plugin.getLogger().log(Level.WARNING, "Error in subparameter " + Arrays.toString(subparam) + " in command " + ea.getCommand().commandString + " falling back to default");
        }
      }
      switch (pt)
      {
      case Location: 
        Location[] l = new Location[1];
        l[0] = event.getBlock().getLocation();
        ea.getParams().addLast(l);
        return;
      case Locx: 
        Location loc = new Location(p.getWorld(), event.getBlock().getX() + add, 0.0D, 0.0D);
        ea.getParams().addLast(loc);
        return;
      case Locy: 
        ((Location)ea.getParams().getLast()).setY(event.getBlock().getY() + add);
        return;
      case Locz: 
        ((Location)ea.getParams().getLast()).setZ(event.getBlock().getZ() + add);
        return;
      }
    }
    else
    {
      Ancient.plugin.getLogger().log(Level.SEVERE, "Invalid usage of ChangedBlock parameter in Command " + ea.getCommand().commandString + " in spell " + ea.getSpell().name + " in line " + ea.getCommand().lineNumber);
    }
  }
  
  public String getName()
  {
    return "changedblock";
  }
  
  public Object parseParameter(Player p, String[] subparam, SpellInformationObject so)
  {
    if ((!so.mSpell.active) && ((so.mEvent instanceof EntityChangeBlockEvent)))
    {
      EntityChangeBlockEvent event = (EntityChangeBlockEvent)so.mEvent;
      Location[] l = new Location[1];
      l[0] = event.getBlock().getLocation();
      return l;
    }
    return null;
  }
}
