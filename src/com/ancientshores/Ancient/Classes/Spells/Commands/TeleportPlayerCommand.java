package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import java.util.LinkedList;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class TeleportPlayerCommand
  extends ICommand
{
  @CommandDescription(description="<html>Teleports the target to the location</html>", argnames={"entity", "location"}, name="TeleportPlayer", parameters={ParameterType.Entity, ParameterType.Location})
  public TeleportPlayerCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Entity, ParameterType.Location };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    if ((ca.getParams().size() == 2) && ((ca.getParams().get(0) instanceof Entity[])) && ((ca.getParams().get(1) instanceof Location[])))
    {
      Entity[] en = (Entity[])ca.getParams().get(0);
      Location[] loc = (Location[])ca.getParams().get(1);
      for (Location l : loc) {
        if (l != null) {
          for (Entity e : en) {
            if (e != null) {
              e.teleport(l);
            }
          }
        }
      }
      return true;
    }
    return false;
  }
}
