package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import java.util.Collection;
import java.util.LinkedList;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ThrowPotionCommand
  extends ICommand
{
  @CommandDescription(description="The entity throws a potion with the specified effects", argnames={"entity", "potionname", "duration", "amplifier"}, name="ThrowPotion", parameters={ParameterType.Entity, ParameterType.String, ParameterType.Number, ParameterType.Number})
  public ThrowPotionCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Entity, ParameterType.String, ParameterType.Number, ParameterType.Number };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    try
    {
      if (((ca.getParams().get(0) instanceof Entity[])) && ((ca.getParams().get(1) instanceof String)) && ((ca.getParams().get(2) instanceof Number)) && ((ca.getParams().get(3) instanceof Number)))
      {
        Entity[] ents = (Entity[])ca.getParams().get(0);
        int amp = ((Number)ca.getParams().get(3)).intValue();
        int time = ((Number)ca.getParams().get(2)).intValue();
        String name = (String)ca.getParams().get(1);
        PotionEffectType pet = getTypeByName(name);
        for (Entity e : ents)
        {
          ThrownPotion tp = (ThrownPotion)e.getWorld().spawnEntity(e.getLocation(), EntityType.THROWN_EXP_BOTTLE);
          tp.getEffects().add(new PotionEffect(pet, time, amp));
        }
        return true;
      }
    }
    catch (IndexOutOfBoundsException ignored) {}
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
