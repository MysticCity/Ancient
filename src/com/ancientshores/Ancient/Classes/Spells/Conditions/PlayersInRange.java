package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PlayersInRange
  extends IArgument
{
  @ArgumentDescription(description="Returns the amount of players in range", parameterdescription={"location", "range"}, returntype=ParameterType.Number, rparams={ParameterType.Location, ParameterType.Number})
  public PlayersInRange()
  {
    this.returnType = ParameterType.Number;
    this.requiredTypes = new ParameterType[] { ParameterType.Location, ParameterType.Number };
    this.name = "playersinrange";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if ((obj.length == 2) && ((obj[0] instanceof Location[])) && ((obj[1] instanceof Number)))
    {
      if ((((Location[])obj[0]).length <= 0) || (((Location[])(Location[])obj[0])[0] == null)) {
        return Integer.valueOf(0);
      }
      Location l = ((Location[])(Location[])obj[0])[0];
      int range = (int)((Number)obj[1]).doubleValue();
      List<Entity> ents = l.getWorld().getEntities();
      int amount = 0;
      for (Entity e : ents) {
        if (((e instanceof Player)) && (e.getLocation().distance(l) < range)) {
          amount++;
        }
      }
      return Integer.valueOf(amount);
    }
    return Integer.valueOf(0);
  }
}
