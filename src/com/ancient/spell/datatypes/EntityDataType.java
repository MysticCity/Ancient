package com.ancient.spell.datatypes;

import com.ancient.parameter.Parameter;
import com.ancient.parameter.ParameterType;
import com.ancient.spell.DataType;
import com.ancient.spell.SpellItem;
import com.ancient.spell.SpellParser;
import com.ancient.spellmaker.Returnable;
import com.ancient.util.EntityFinder;
import java.util.UUID;
import org.bukkit.entity.Entity;

public class EntityDataType
  extends DataType<Entity>
{
  private Entity value;
  private Returnable<Entity> valueItem;
  
  public EntityDataType(int line, String value)
  {
    super(line, "<html>An entity data type, which can store an <b>entity</b>.</html>");
    try
    {
      this.value = EntityFinder.findByUUID(UUID.fromString(value));
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
  
  public Entity getValue()
  {
    if (this.valueItem != null) {
      calculateReturn();
    }
    return this.value;
  }
  
  private void calculateReturn()
  {
    this.value = ((Entity)this.valueItem.getValue());
  }
  
  public Parameter getReturnType()
  {
    return new Parameter(ParameterType.ENTITY, false);
  }
}
