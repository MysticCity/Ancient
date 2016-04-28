package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class EntitiesInRage
  extends IArgument
{
  @ArgumentDescription(description="returns the amount of entities in the range from the location.", parameterdescription={"location", "range"}, returntype=ParameterType.Number, rparams={ParameterType.Location, ParameterType.Number})
  public EntitiesInRage()
  {
    this.returnType = ParameterType.Number;
    this.requiredTypes = new ParameterType[] { ParameterType.Location, ParameterType.Number };
    this.name = "entitiesinrange";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if ((obj.length == 2) && ((obj[0] instanceof Location[])) && ((obj[1] instanceof Number)))
    {
      Location l = ((Location[])(Location[])obj[0])[0];
      int range = (int)((Number)obj[1]).doubleValue();
      List<Entity> ents = l.getWorld().getEntities();
      int amount = 0;
      for (Entity e : ents) {
        if (((e instanceof LivingEntity)) && (e.getLocation().distance(l) < range)) {
          amount++;
        }
      }
      return Integer.valueOf(amount);
    }
    return Integer.valueOf(0);
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Conditions\EntitiesInRage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */