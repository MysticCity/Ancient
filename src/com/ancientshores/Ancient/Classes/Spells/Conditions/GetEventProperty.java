package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import java.lang.reflect.Field;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class GetEventProperty
  extends IArgument
{
  @ArgumentDescription(description="Returns the class object of the event with the specified name", parameterdescription={"objectname"}, returntype=ParameterType.Void, rparams={ParameterType.String})
  public GetEventProperty()
  {
    this.returnType = ParameterType.Void;
    this.requiredTypes = new ParameterType[] { ParameterType.String };
    this.name = "geteventproperty";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if ((obj.length == 1) && ((obj[0] instanceof String)))
    {
      String property = (String)obj[0];
      if (so.mEvent != null)
      {
        Object o = so.mEvent;
        for (Field field : o.getClass().getDeclaredFields()) {
          if (field.getName().equalsIgnoreCase(property))
          {
            field.setAccessible(true);
            try
            {
              return field.get(o);
            }
            catch (IllegalArgumentException e)
            {
              e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
              e.printStackTrace();
            }
          }
        }
      }
    }
    return "";
  }
  
  public Object AutoCast(Object o)
  {
    if ((o instanceof Player)) {
      return new Player[] { (Player)o };
    }
    if ((o instanceof Entity)) {
      return new Entity[] { (Entity)o };
    }
    if ((o instanceof Location)) {
      return new Location[] { (Location)o };
    }
    return o;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Conditions\GetEventProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */