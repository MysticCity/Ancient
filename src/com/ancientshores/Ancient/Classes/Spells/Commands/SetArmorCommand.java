package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import java.util.LinkedList;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public class SetArmorCommand
  extends ICommand
{
  @CommandDescription(description="Sets the players armor in the specified slot", argnames={"player", "material", "slot", "amount"}, name="SetArmor", parameters={ParameterType.Player, ParameterType.Material, ParameterType.Number, ParameterType.Number})
  public SetArmorCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Player, ParameterType.Material, ParameterType.Number, ParameterType.Number };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    try
    {
      if (((ca.getParams().get(0) instanceof Entity[])) && ((ca.getParams().get(1) instanceof Material)) && ((ca.getParams().get(2) instanceof Number)) && ((ca.getParams().get(3) instanceof Number)))
      {
        Entity[] ents = (Entity[])ca.getParams().get(0);
        Material mat = (Material)ca.getParams().get(1);
        int slot = (int)((Number)ca.getParams().get(2)).doubleValue();
        int amount = (int)((Number)ca.getParams().get(3)).doubleValue();
        for (Entity e : ents) {
          if (e != null)
          {
            LivingEntity ent = (LivingEntity)e;
            ItemStack is = new ItemStack(mat, amount);
            switch (slot)
            {
            case 0: 
              ent.getEquipment().setHelmet(is);
              break;
            case 1: 
              ent.getEquipment().setChestplate(is);
              break;
            case 2: 
              ent.getEquipment().setLeggings(is);
              break;
            case 3: 
              ent.getEquipment().setBoots(is);
            }
            if ((e instanceof Player)) {
              ((Player)e).updateInventory();
            }
          }
        }
      }
    }
    catch (Exception e)
    {
      return false;
    }
    return true;
  }
}
