package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import com.ancientshores.Ancient.Util.Plane;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

public class IsBehind
  extends IArgument
{
  public IsBehind()
  {
    this.returnType = ParameterType.Boolean;
    this.requiredTypes = new ParameterType[] { ParameterType.Location, ParameterType.Entity };
    this.name = "isbehind";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if ((obj.length == 2) && ((obj[0] instanceof Location[])) && (((Location[])obj[0]).length > 0) && ((obj[1] instanceof Entity[])) && (((Entity[])obj[1]).length > 0))
    {
      Entity e = ((Entity[])(Entity[])obj[1])[0];
      Location l = ((Location[])(Location[])obj[0])[0];
      if ((e != null) && (l != null) && ((e instanceof LivingEntity)))
      {
        LivingEntity le = (LivingEntity)e;
        Vector viewdirection = e.getLocation().getDirection();
        viewdirection.setY(0);
        Plane plane = new Plane(e.getLocation().getDirection(), le.getEyeLocation());
        double angle = viewdirection.angle(le.getEyeLocation().toVector().multiply(-1));
        double distance = plane.distance(l);
        return Boolean.valueOf(((distance < 0.0D) && (Math.abs(angle) > 1.5707963267948966D)) || ((distance > 0.0D) && (Math.abs(angle) < 1.5707963267948966D)));
      }
    }
    return Boolean.valueOf(false);
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Conditions\IsBehind.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */