package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import java.util.LinkedList;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class SetBlockCommand
  extends ICommand
{
  @CommandDescription(description="<html>Sets the block at the location to the specified id</html>", argnames={"location", "material"}, name="SetBlock", parameters={ParameterType.Location, ParameterType.Material})
  public SetBlockCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Location, ParameterType.Material };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    try
    {
      if (((ca.getParams().get(0) instanceof Location[])) && ((ca.getParams().get(1) instanceof Material)))
      {
        Location[] loc = (Location[])ca.getParams().get(0);
        Material m = (Material)ca.getParams().get(1);
        if ((loc != null) && (m != null))
        {
          for (Location l : loc) {
            if (l != null) {
              l.getBlock().setType(m);
            }
          }
          return true;
        }
      }
    }
    catch (IndexOutOfBoundsException ignored) {}
    return false;
  }
}
