package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.HP.AncientHP;
import com.ancientshores.Ancient.HP.DamageConverter;
import com.ancientshores.Ancient.PlayerData;
import java.util.LinkedList;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class SetHpCommand
  extends ICommand
{
  @CommandDescription(description="<html>Sets the health of the target to the specified amount</html>", argnames={"entity", "amount"}, name="SetHp", parameters={ParameterType.Entity, ParameterType.Number})
  public SetHpCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Entity, ParameterType.Number };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    if ((ca.getParams().size() == 2) && 
      ((ca.getParams().get(0) instanceof Entity[])) && ((ca.getParams().get(1) instanceof Number)))
    {
      for (Entity e : (Entity[])ca.getParams().get(0)) {
        if (e != null) {
          if (((e instanceof Player)) && (DamageConverter.isEnabledInWorld(e.getWorld())))
          {
            PlayerData.getPlayerData(((Player)e).getUniqueId()).getHpsystem().health = ((int)((Number)ca.getParams().get(1)).doubleValue());
            PlayerData.getPlayerData(((Player)e).getUniqueId()).getHpsystem().setMinecraftHP();
          }
          else
          {
            ((LivingEntity)e).setHealth(((Integer)ca.getParams().get(1)).intValue());
          }
        }
      }
      return true;
    }
    return false;
  }
}
