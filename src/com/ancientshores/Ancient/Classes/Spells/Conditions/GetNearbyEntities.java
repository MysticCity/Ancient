package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import com.ancientshores.Ancient.Util.GlobalMethods;
import java.util.HashSet;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class GetNearbyEntities
  extends IArgument
{
  @ArgumentDescription(description="Returns the given amount of entities nearby the location in the specified range", parameterdescription={"location", "range", "amount"}, returntype=ParameterType.Entity, rparams={ParameterType.Location, ParameterType.Number, ParameterType.Number})
  public GetNearbyEntities()
  {
    this.returnType = ParameterType.Entity;
    this.requiredTypes = new ParameterType[] { ParameterType.Location, ParameterType.Number, ParameterType.Number };
    this.name = "getnearbyentities";
  }
  
  public Entity[] getNearbyEntities(List<Entity> entityset, Location l, double range, int count)
  {
    Entity[] nearestEntity = new Entity[count];
    HashSet<Entity> alreadyParsed = new HashSet();
    for (int i = 0; i < count; i++)
    {
      double curdif = 100000.0D;
      for (Entity e : entityset) {
        if (((e instanceof Creature)) || ((e instanceof Player)))
        {
          double dif = e.getLocation().distance(l);
          if ((dif < range) && (dif < curdif) && (l != e.getLocation()) && (!alreadyParsed.contains(e.getUniqueId())))
          {
            curdif = dif;
            nearestEntity[i] = e;
          }
        }
      }
      alreadyParsed.add(nearestEntity[i]);
    }
    return (Entity[])GlobalMethods.removeNullArrayCells(nearestEntity);
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if ((obj.length != 3) || (!(obj[0] instanceof Location[])) || (((Location[])obj[0]).length == 0) || (!(obj[1] instanceof Number)) || (!(obj[2] instanceof Number))) {
      return null;
    }
    double range = ((Number)obj[1]).doubleValue();
    int maxcount = ((Number)obj[2]).intValue();
    Location loc = ((Location[])(Location[])obj[0])[0];
    return getNearbyEntities(loc.getWorld().getEntities(), loc, range, maxcount);
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Conditions\GetNearbyEntities.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */