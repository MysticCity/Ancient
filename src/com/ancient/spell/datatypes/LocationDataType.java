package com.ancient.spell.datatypes;

import com.ancient.parameter.Parameter;
import com.ancient.parameter.ParameterType;
import com.ancient.spell.DataType;
import com.ancient.spell.SpellItem;
import com.ancient.spell.SpellParser;
import com.ancient.spellmaker.Returnable;
import com.ancient.stuff.Splitter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationDataType
  extends DataType<Location>
{
  private Location value;
  private Returnable<String> world;
  private Returnable<Number> x;
  private Returnable<Number> y;
  private Returnable<Number> z;
  
  public LocationDataType(int line, String value)
  {
    super(line, "<html>A location data type, which can store a <b>location</b> consisting of a <b>world name</b> and the <b>x-</b>, <b>y-</b> and <b>z-coordinates</b> as <b>numbers</b>.</html>");
    
    String[] splitted = Splitter.splitByArgument(value);
    if (splitted.length != 4) {}
    SpellItem world = SpellParser.parse(splitted[0], line);
    SpellItem x = SpellParser.parse(splitted[1], line);
    SpellItem y = SpellParser.parse(splitted[2], line);
    SpellItem z = SpellParser.parse(splitted[3], line);
    if (((world instanceof Returnable)) && ((x instanceof Returnable)) && ((y instanceof Returnable)) && ((z instanceof Returnable)))
    {
      this.world = ((Returnable)world);
      this.x = ((Returnable)x);
      this.y = ((Returnable)y);
      this.z = ((Returnable)z);
    }
  }
  
  public Location getValue()
  {
    if (this.world != null) {
      calculateReturn();
    }
    return this.value;
  }
  
  private void calculateReturn()
  {
    this.value = new Location(Bukkit.getWorld((String)this.world.getValue()), ((Number)this.x.getValue()).doubleValue(), ((Number)this.y.getValue()).doubleValue(), ((Number)this.z.getValue()).doubleValue());
  }
  
  public Parameter getReturnType()
  {
    return new Parameter(ParameterType.LOCATION, false);
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancient\spell\datatypes\LocationDataType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */