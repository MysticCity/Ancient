package com.ancient.spell.datatypes;

import com.ancient.parameter.Parameter;
import com.ancient.parameter.ParameterType;
import com.ancient.spell.DataType;
import com.ancient.spell.SpellItem;
import com.ancient.spell.SpellParser;
import com.ancient.spellmaker.Returnable;

public class NumberDataType
  extends DataType<Number>
{
  private Number value;
  private Returnable<Number> valueItem;
  
  public NumberDataType(int line, String value)
  {
    super(line, "<html>A number data type, which can store <b>numbers</b>.</html>");
    try
    {
      this.value = Double.valueOf(Double.parseDouble(value));
    }
    catch (NumberFormatException ex)
    {
      SpellItem item = SpellParser.parse(value, line);
      if ((item instanceof Returnable)) {
        this.valueItem = ((Returnable)item);
      }
    }
  }
  
  public Number getValue()
  {
    if (this.valueItem != null) {
      calculateReturn();
    }
    return this.value;
  }
  
  private void calculateReturn()
  {
    this.value = ((Number)this.valueItem.getValue());
  }
  
  public Parameter getReturnType()
  {
    return new Parameter(ParameterType.NUMBER, false);
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancient\spell\datatypes\NumberDataType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */