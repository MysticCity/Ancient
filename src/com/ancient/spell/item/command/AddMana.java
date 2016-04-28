package com.ancient.spell.item.command;

import com.ancient.parameter.Parameter;
import com.ancient.parameter.ParameterType;
import com.ancient.spellmaker.CommandParameterizable;
import com.ancientshores.Ancient.Mana.ManaSystem;
import org.bukkit.entity.Player;

public class AddMana
  extends CommandParameterizable
{
  public AddMana(int line)
  {
    super(line, "<html>Adds the specified amount of mana to the players mana system</html>", new Parameter[] { new Parameter(ParameterType.PLAYER, "player(s)", true), new Parameter(ParameterType.NUMBER, "amount", false) });
  }
  
  public Object[] execute()
    throws Exception
  {
    if (!validValues()) {
      throw new IllegalArgumentException(getClass().getName() + " in line " + this.line + " has parameters of a wrong type.");
    }
    Player[] players = (Player[])this.parameterValues[0];
    int amount = Integer.parseInt((String)this.parameterValues[1]);
    for (Player p : players) {
      ManaSystem.addManaByUUID(p.getUniqueId(), amount);
    }
    return new Object[] { Integer.valueOf(this.line) };
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancient\spell\item\command\AddMana.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */