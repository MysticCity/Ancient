package com.ancient.spell.datatypes;

import com.ancient.parameter.Parameter;
import com.ancient.parameter.ParameterType;
import com.ancient.spell.DataType;
import com.ancient.spell.SpellItem;
import com.ancient.spell.SpellParser;
import com.ancient.spellmaker.Returnable;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerDataType
  extends DataType<Player>
{
  private Player value;
  private Returnable<Player> valueItem;
  
  public PlayerDataType(int line, String value)
  {
    super(line, "<html>A player data type, which can store a <b>player</b>.</html>");
    try
    {
      this.value = Bukkit.getPlayer(UUID.fromString(value));
      if (this.value == null) {}
      SpellItem item;
      return;
    }
    catch (NumberFormatException ex)
    {
      item = SpellParser.parse(value, line);
      if ((item instanceof Returnable)) {
        this.valueItem = ((Returnable)item);
      }
    }
  }
  
  public Player getValue()
  {
    if (this.valueItem != null) {
      calculateReturn();
    }
    return this.value;
  }
  
  private void calculateReturn()
  {
    this.value = ((Player)this.valueItem.getValue());
  }
  
  public Parameter getReturnType()
  {
    return new Parameter(ParameterType.PLAYER, false);
  }
}
