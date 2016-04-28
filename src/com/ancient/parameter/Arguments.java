package com.ancient.parameter;

import com.ancient.spell.Spell;
import java.util.List;

public class Arguments
{
  private List<Object> values;
  private Spell spell;
  
  public Arguments(Spell spell, List<Object> values)
  {
    this.spell = spell;
    this.values = values;
  }
  
  public List<Object> getValues()
  {
    return this.values;
  }
  
  public Spell getSpell()
  {
    return this.spell;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancient\parameter\Arguments.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */