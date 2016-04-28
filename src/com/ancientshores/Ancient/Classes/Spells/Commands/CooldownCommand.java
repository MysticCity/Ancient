package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.PlayerData;
import java.util.LinkedList;
import org.bukkit.entity.Player;

public class CooldownCommand
  extends ICommand
{
  @CommandDescription(description="<html>Triggers the cooldown with the specified name for the given amount of time</html>", argnames={"cooldownname", "duration"}, name="Cooldown", parameters={ParameterType.String, ParameterType.Number})
  public CooldownCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.String, ParameterType.Number };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    try
    {
      if (((ca.getParams().get(0) instanceof String)) && ((ca.getParams().get(1) instanceof Number)))
      {
        String cooldownName = (String)ca.getParams().get(0);
        int time = ((Number)ca.getParams().get(1)).intValue();
        PlayerData pd = PlayerData.getPlayerData(ca.getCaster().getUniqueId());
        
        pd.addTimer(cooldownName, time);
        pd.startTimer(cooldownName);
        
        return true;
      }
    }
    catch (IndexOutOfBoundsException ignored) {}
    return false;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\CooldownCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */