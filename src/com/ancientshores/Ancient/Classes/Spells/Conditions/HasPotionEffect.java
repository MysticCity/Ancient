package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.Commands.PotionEffectCommand;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffectType;

public class HasPotionEffect
  extends IArgument
{
  @ArgumentDescription(description="Returns if the entity has the specified potioneffect", parameterdescription={"entity", "potioneffecttype"}, returntype=ParameterType.Boolean, rparams={ParameterType.Entity, ParameterType.String})
  public HasPotionEffect()
  {
    this.returnType = ParameterType.Boolean;
    this.requiredTypes = new ParameterType[] { ParameterType.Entity, ParameterType.String };
    this.name = "haspotioneffect";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if ((obj.length != 2) || (!(obj[0] instanceof Entity[])) || (!(obj[1] instanceof String))) {
      return Boolean.valueOf(false);
    }
    Entity e = ((Entity[])(Entity[])obj[0])[0];
    String s = (String)obj[1];
    PotionEffectType pet = PotionEffectCommand.getTypeByName(s);
    if ((e != null) && ((e instanceof LivingEntity)) && (pet != null))
    {
      LivingEntity le = (LivingEntity)e;
      return Boolean.valueOf(le.hasPotionEffect(pet));
    }
    return Boolean.valueOf(false);
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Conditions\HasPotionEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */