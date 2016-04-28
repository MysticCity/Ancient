package com.ancient.spell.item.command;

import com.ancient.parameter.Parameter;
import com.ancient.parameter.ParameterType;
import com.ancient.spellmaker.CommandParameterizable;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Blind
  extends CommandParameterizable
{
  public Blind(int line)
  {
    super(line, "<html>Blinds the specified player(s) for the entered amount of time.</html>", new Parameter[] { new Parameter(ParameterType.PLAYER, "player(s)", true), new Parameter(ParameterType.NUMBER, "duration (in seconds)", false) });
  }
  
  public Object[] execute()
    throws Exception
  {
    if (!validValues()) {
      throw new IllegalArgumentException(getClass().getName() + " in line " + this.line + " has parameters of a wrong type.");
    }
    Player[] players = (Player[])this.parameterValues[0];
    int time = Integer.parseInt((String)this.parameterValues[1]) * 20;
    for (Player p : players) {
      p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, time, 1));
    }
    return new Object[] { Integer.valueOf(this.line) };
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancient\spell\item\command\Blind.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */