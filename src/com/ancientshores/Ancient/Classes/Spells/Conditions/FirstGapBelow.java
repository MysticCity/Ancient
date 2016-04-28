package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class FirstGapBelow
  extends IArgument
{
  @ArgumentDescription(description="Returns the first gap below the specified location with atleast the amount of free blocks", parameterdescription={"location", "free blocks"}, returntype=ParameterType.Location, rparams={ParameterType.Location, ParameterType.Number})
  public FirstGapBelow()
  {
    this.returnType = ParameterType.Location;
    this.requiredTypes = new ParameterType[] { ParameterType.Location, ParameterType.Number };
    this.name = "firstgapbelow";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if ((obj.length == 2) && ((obj[0] instanceof Location[])) && ((obj[1] instanceof Number)))
    {
      Location l = null;
      int freespace = ((Number)obj[1]).intValue();
      for (Location l1 : (Location[])obj[0]) {
        if (l1 != null)
        {
          l = l1;
          break;
        }
      }
      try
      {
        if (l != null)
        {
          Block b = l.getBlock();
          do
          {
            if (b.getLocation().getBlockY() > 256) {
              return null;
            }
            b = b.getRelative(BlockFace.DOWN);
          } while (b.getTypeId() != 0);
          Block b1 = b.getRelative(BlockFace.SELF);
          for (int i = 0;; i++)
          {
            if (i >= freespace - 1) {
              break label195;
            }
            b1 = b1.getRelative(BlockFace.DOWN);
            if (b1.getTypeId() != 0) {
              break;
            }
          }
          label195:
          return new Location[] { b.getLocation() };
        }
      }
      catch (Exception ignored) {}
    }
    return null;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Conditions\FirstGapBelow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */