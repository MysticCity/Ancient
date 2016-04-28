package com.ancient.spell.item.command;

import com.ancient.parameter.Parameter;
import com.ancient.parameter.ParameterType;
import com.ancient.spellmaker.CommandParameterizable;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class AddItem
  extends CommandParameterizable
{
  public AddItem(int line)
  {
    super(line, "<html>Adds the specified amount of items to the invenory of the player(s)</html>", new Parameter[] { new Parameter(ParameterType.PLAYER, "player(s)", true), new Parameter(ParameterType.MATERIAL, "item", false), new Parameter(ParameterType.NUMBER, "amount", false) });
  }
  
  public Object[] execute()
    throws Exception
  {
    if (!validValues()) {
      throw new IllegalArgumentException(getClass().getName() + " in line " + this.line + " has parameters of a wrong type.");
    }
    Player[] players = (Player[])this.parameterValues[0];
    Material material = (Material)this.parameterValues[1];
    int amount = Integer.parseInt((String)this.parameterValues[2]);
    for (Player p : players)
    {
      p.getInventory().addItem(new ItemStack[] { new ItemStack(material, amount) });
      p.updateInventory();
    }
    return new Object[] { Integer.valueOf(this.line) };
  }
}
