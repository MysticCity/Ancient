package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import java.util.LinkedList;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TeleportCommand
  extends ICommand
{
  @CommandDescription(description="<html>Teleports the player to the location</html>", argnames={"location"}, name="Teleport", parameters={ParameterType.Location})
  public TeleportCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Location };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    try
    {
      if ((ca.getParams().get(0) instanceof Location[]))
      {
        Location[] loc = (Location[])ca.getParams().get(0);
        for (Location l : loc) {
          if (l != null)
          {
            Location lo = ca.getCaster().getLocation();
            lo.setX(l.getX());
            lo.setY(l.getY());
            lo.setZ(l.getZ());
            ca.getCaster().teleport(lo);
          }
        }
        return true;
      }
    }
    catch (Exception ignored) {}
    return false;
  }
}
