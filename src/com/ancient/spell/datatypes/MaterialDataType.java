package com.ancient.spell.datatypes;

import com.ancient.parameter.Parameter;
import com.ancient.parameter.ParameterType;
import com.ancient.spell.DataType;
import com.ancient.spell.SpellItem;
import com.ancient.spell.SpellParser;
import com.ancient.spellmaker.Returnable;
import org.bukkit.Material;

public class MaterialDataType
  extends DataType<Material>
{
  private Material value;
  private Returnable<Material> valueItem;
  
  public MaterialDataType(int line, String value)
  {
    super(line, "<html>A material data type, which can store a <b>material</b>.</html>");
    if (value.toUpperCase().equalsIgnoreCase(value))
    {
      this.value = Material.getMaterial(value);
    }
    else
    {
      SpellItem item = SpellParser.parse(value, line);
      if ((item instanceof Returnable)) {
        this.valueItem = ((Returnable)item);
      }
    }
  }
  
  public Material getValue()
  {
    if (this.valueItem != null) {
      calculateReturn();
    }
    return this.value;
  }
  
  private void calculateReturn()
  {
    this.value = ((Material)this.valueItem.getValue());
  }
  
  public Parameter getReturnType()
  {
    return new Parameter(ParameterType.MATERIAL, false);
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancient\spell\datatypes\MaterialDataType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */