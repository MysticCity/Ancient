package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.HP.AncientHP;
import com.ancientshores.Ancient.PlayerData;
import java.util.LinkedList;
import org.bukkit.entity.Player;

public class SetMaxHitpointsCommand
  extends ICommand
{
  @CommandDescription(description="<html>Sets the maximumhealth of the target to the specified amount</html>", argnames={"player", "amount"}, name="SetMaxHp", parameters={ParameterType.Player, ParameterType.Number})
  public SetMaxHitpointsCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Player, ParameterType.Number };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    try
    {
      if (((ca.getParams().get(0) instanceof Player[])) && ((ca.getParams().get(1) instanceof Number)))
      {
        Player[] target = (Player[])ca.getParams().get(0);
        int hp = (int)((Number)ca.getParams().get(1)).doubleValue();
        for (Player p : target) {
          if (p != null)
          {
            PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
            double ratio = pd.getHpsystem().health / pd.getHpsystem().maxhp;
            pd.getHpsystem().maxhp = hp;
            pd.getHpsystem().health = Math.round(pd.getHpsystem().maxhp * ratio);
            pd.getHpsystem().setMinecraftHP();
          }
        }
        return true;
      }
    }
    catch (IndexOutOfBoundsException ignored) {}
    return false;
  }
}
