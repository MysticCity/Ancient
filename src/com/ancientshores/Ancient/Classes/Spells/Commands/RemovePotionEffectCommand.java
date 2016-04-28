package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import java.util.LinkedList;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffectType;

public class RemovePotionEffectCommand
  extends ICommand
{
  @CommandDescription(description="<html>Removes the specified potion effect from the player</html>", argnames={"entity", "effectname"}, name="RemovePotion", parameters={ParameterType.Entity, ParameterType.String})
  public RemovePotionEffectCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Entity, ParameterType.String };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    if ((ca.getParams().size() == 2) && ((ca.getParams().get(0) instanceof Entity[])) && ((ca.getParams().get(1) instanceof String)))
    {
      Entity[] players = (Entity[])ca.getParams().get(0);
      String name = (String)ca.getParams().get(1);
      PotionEffectType pet = getTypeByName(name);
      if (pet == null) {
        return true;
      }
      for (Entity p : players) {
        if ((p != null) && ((p instanceof LivingEntity))) {
          if (((LivingEntity)p).hasPotionEffect(pet)) {
            ((LivingEntity)p).removePotionEffect(pet);
          }
        }
      }
      return true;
    }
    return false;
  }
  
  public PotionEffectType getTypeByName(String name)
  {
    for (PotionEffectType pt : )
    {
      if ((pt != null) && (pt.getName() != null) && (pt.getName().replace("_", "").equalsIgnoreCase(name))) {
        return pt;
      }
      if ((pt != null) && (pt.getName() != null) && (pt.getName().equalsIgnoreCase(name))) {
        return pt;
      }
    }
    return null;
  }
}
