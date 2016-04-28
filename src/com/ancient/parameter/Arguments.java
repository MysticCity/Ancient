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
