package com.ancient.spell.datatypes;

import com.ancient.parameter.Parameter;
import com.ancient.parameter.ParameterType;
import com.ancient.spell.DataType;
import com.ancient.spell.SpellItem;
import com.ancient.spell.SpellParser;
import com.ancient.spellmaker.Returnable;

public class BooleanDataType
  extends DataType<Boolean>
{
  private Boolean value;
  private Returnable<Boolean> valueItem;
  
  public BooleanDataType(int line, String value)
  {
    super(line, "<html>A boolean data type, which can store <b>true</b> or <b>false</b>.</html>");
    if (value.toLowerCase().startsWith("true"))
    {
      this.value = Boolean.valueOf(true);
      this.valueItem = null;
    }
    else if (value.toLowerCase().startsWith("false"))
    {
      this.value = Boolean.valueOf(false);
      this.valueItem = null;
    }
    else
    {
      SpellItem item = SpellParser.parse(value, line);
      if ((item instanceof Returnable)) {
        this.valueItem = ((Returnable)item);
      }
    }
  }
  
  public Boolean getValue()
  {
    if (this.valueItem != null) {
      calculateReturn();
    }
    return this.value;
  }
  
  private void calculateReturn()
  {
    this.value = ((Boolean)this.valueItem.getValue());
  }
  
  public Parameter getReturnType()
  {
    return new Parameter(ParameterType.BOOLEAN, false);
  }
}
