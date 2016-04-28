package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class IsInAir
  extends IArgument
{
  @ArgumentDescription(description="Returns true if the entity is in air, false otherwise", parameterdescription={"entity"}, returntype=ParameterType.Boolean, rparams={ParameterType.Entity})
  public IsInAir()
  {
    this.returnType = ParameterType.Boolean;
    this.requiredTypes = new ParameterType[] { ParameterType.Entity };
    this.name = "isinair";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if ((obj.length == 1) && ((obj[0] instanceof Entity[])) && (((Entity[])obj[0]).length > 0))
    {
      Entity e = ((Entity[])(Entity[])obj[0])[0];
      if ((e != null) && ((e instanceof LivingEntity))) {
        return Boolean.valueOf((e.getLocation().getBlock().getY() == 0) || (e.getLocation().getBlock().getRelative(0, -1, 0).getType() == Material.AIR) || (e.getLocation().getBlock().getY() == 0));
      }
    }
    return Boolean.valueOf(false);
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Conditions\IsInAir.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */