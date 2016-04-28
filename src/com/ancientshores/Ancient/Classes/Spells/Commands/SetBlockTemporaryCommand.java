package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import java.util.HashMap;
import java.util.LinkedList;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.material.MaterialData;

public class SetBlockTemporaryCommand
  extends ICommand
{
  @CommandDescription(description="<html>Sets the id of the block, sets it back after the time</html>", argnames={"location", "material", "duration"}, name="SetBlockTemporary", parameters={ParameterType.Location, ParameterType.Material, ParameterType.Number})
  private static final HashMap<Location, Integer> changedBlocks = new HashMap();
  public static int tasks;
  
  public SetBlockTemporaryCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Location, ParameterType.Material, ParameterType.Number };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    try
    {
      if (((ca.getParams().get(0) instanceof Location[])) && ((ca.getParams().get(1) instanceof Material)) && ((ca.getParams().get(2) instanceof Number)))
      {
        Location[] loc = (Location[])ca.getParams().get(0);
        final Material ma = (Material)ca.getParams().get(1);
        int time = ((Number)ca.getParams().get(2)).intValue();
        final int task = tasks;
        tasks += 1;
        if ((loc != null) && (ma != null))
        {
          if (time > 0) {
            for (final Location l : loc) {
              if (l != null)
              {
                final Material m = l.getBlock().getType();
                final byte data = l.getBlock().getData();
                final MaterialData md = l.getBlock().getState().getData().clone();
                l.getBlock().setType(ma);
                Ancient.plugin.scheduleThreadSafeTask(Ancient.plugin, new Runnable()
                {
                  public void run()
                  {
                    if ((((Integer)SetBlockTemporaryCommand.changedBlocks.get(l)).intValue() == task) && (ma == l.getBlock().getType()))
                    {
                      SetBlockTemporaryCommand.changedBlocks.remove(l);
                      l.getBlock().setType(m);
                      l.getBlock().setData(data);
                      l.getBlock().getState().setData(md);
                    }
                  }
                }, Math.round(time / 50));
                
                changedBlocks.put(l, Integer.valueOf(task));
              }
            }
          }
          return true;
        }
      }
    }
    catch (Exception ignored) {}
    return false;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\SetBlockTemporaryCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */