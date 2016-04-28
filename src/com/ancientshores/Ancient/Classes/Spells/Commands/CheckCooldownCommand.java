package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.Classes.Spells.CommandDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.Spell;
import com.ancientshores.Ancient.PlayerData;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;
import org.bukkit.entity.Player;

public class CheckCooldownCommand
  extends ICommand
{
  @CommandDescription(description="<html>Checks if the cooldown with the specified name is ready, cancels if not</html>", argnames={"cdname"}, name="CheckCooldown", parameters={ParameterType.String})
  private final HashMap<UUID, Long> lastcdcheck = new HashMap();
  
  public CheckCooldownCommand()
  {
    this.paramTypes = new ParameterType[] { ParameterType.String };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    try
    {
      if ((ca.getParams().get(0) instanceof String))
      {
        String cooldownName = (String)ca.getParams().get(0);
        PlayerData pd = PlayerData.getPlayerData(ca.getCaster().getUniqueId());
        if (pd.isCastReady(cooldownName)) {
          return true;
        }
        if (ca.getSpell().active) {
          if (!this.lastcdcheck.containsKey(ca.getCaster().getUniqueId()))
          {
            this.lastcdcheck.put(ca.getCaster().getUniqueId(), Long.valueOf(System.currentTimeMillis()));
          }
          else if (Math.abs(System.currentTimeMillis() - ((Long)this.lastcdcheck.get(ca.getCaster().getUniqueId())).longValue()) > 1000L)
          {
            ca.getCaster().sendMessage("This spell is not ready");
            ca.getCaster().sendMessage("Time remaining: " + pd.getRemainingTime(cooldownName) / 1000.0D);
            this.lastcdcheck.put(ca.getCaster().getUniqueId(), Long.valueOf(System.currentTimeMillis()));
          }
        }
      }
    }
    catch (Exception ignored)
    {
      ignored.printStackTrace();
    }
    return false;
  }
}
