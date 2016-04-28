package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.Spell;
import java.util.LinkedList;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class LightningEffectCommand
  extends ICommand
{
  @CommandDescription(description="<html>Lightning effect will strike at the location</html>", argnames={"location"}, name="LightningEffect", parameters={ParameterType.Location})
  public LightningEffectCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Location };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    try
    {
      if ((ca.getParams().get(0) != null) && ((ca.getParams().get(0) instanceof Location[])) && (((Location[])ca.getParams().get(0)).length > 0) && (ca.getParams().get(0) != null))
      {
        Location[] loc = (Location[])ca.getParams().getFirst();
        for (Location l : loc) {
          if (l != null) {
            ca.getCaster().getWorld().strikeLightningEffect(l);
          }
        }
        return true;
      }
      if (ca.getSpell().active) {
        ca.getCaster().sendMessage("No target in range");
      }
    }
    catch (IndexOutOfBoundsException ignored) {}
    return false;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\LightningEffectCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */