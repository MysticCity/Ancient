package com.ancient.spell.item.command;

import com.ancient.exceptions.AncientExperienceNotEnabledException;
import com.ancient.parameter.Parameter;
import com.ancient.parameter.ParameterType;
import com.ancient.spellmaker.CommandParameterizable;
import com.ancientshores.Ancient.Experience.AncientExperience;
import com.ancientshores.Ancient.PlayerData;
import org.bukkit.entity.Player;

public class AddExperience
  extends CommandParameterizable
{
  public AddExperience(int line)
  {
    super(line, "<html>Adds the specific amount of experience to the player<br>1. The player(s) who receive(s) the xp<br>2. The amount of experience the player(s) receive(s)", new Parameter[] { new Parameter(ParameterType.PLAYER, "players", true), new Parameter(ParameterType.NUMBER, "XP amount", false) });
  }
  
  public Object[] execute()
    throws AncientExperienceNotEnabledException
  {
    if (!validValues()) {
      throw new IllegalArgumentException(getClass().getName() + " in line " + this.line + " has parameters of a wrong type.");
    }
    Player[] players = (Player[])this.parameterValues[0];
    int amount = Integer.parseInt((String)this.parameterValues[1]);
    for (Player p : players)
    {
      if (!AncientExperience.isWorldEnabled(p.getWorld())) {
        throw new AncientExperienceNotEnabledException();
      }
      PlayerData.getPlayerData(p.getUniqueId()).getXpSystem().addXP(amount, false);
    }
    return new Object[] { Integer.valueOf(this.line) };
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancient\spell\item\command\AddExperience.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */