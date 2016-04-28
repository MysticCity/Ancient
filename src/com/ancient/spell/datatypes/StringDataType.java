package com.ancient.spell.datatypes;

import com.ancient.parameter.Parameter;
import com.ancient.parameter.ParameterType;
import com.ancient.spell.DataType;
import com.ancient.spell.SpellItem;
import com.ancient.spell.SpellParser;
import com.ancient.spellmaker.Returnable;

public class StringDataType
  extends DataType<String>
{
  private String value;
  private Returnable<String> valueItem;
  
  public StringDataType(int line, String value)
  {
    super(line, "<html>A string data type, which can store <b>text</b>.</html>");
    if (value.startsWith("\""))
    {
      this.value = value.substring(1, value.length());
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
  
  public String getValue()
  {
    if (this.valueItem != null) {
      calculateReturn();
    }
    return this.value;
  }
  
  private void calculateReturn()
  {
    this.value = ((String)this.valueItem.getValue());
  }
  
  public Parameter getReturnType()
  {
    return new Parameter(ParameterType.STRING, false);
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancient\spell\datatypes\StringDataType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */