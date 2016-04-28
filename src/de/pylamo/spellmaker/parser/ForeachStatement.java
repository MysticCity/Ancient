package de.pylamo.spellmaker.parser;

import de.pylamo.spellmaker.gui.SpellItems.Condition.ForeachItem;
import de.pylamo.spellmaker.gui.SpellItems.Condition.ForeachStartItem;
import de.pylamo.spellmaker.gui.SpellItems.ISpellItem;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.IParameter;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.ParameterSlot;
import de.pylamo.spellmaker.gui.Window;
import java.util.regex.Pattern;

public class ForeachStatement
  extends ComplexStatement
{
  private IParameter arr;
  IParameter var;
  
  public ForeachStatement(Window w, SpellParser sp)
  {
    super(sp, "endforeach", w);
  }
  
  public void parseStart(String line)
  {
    if (line.contains(",")) {
      line = line.substring(line.indexOf(',') + 1);
    }
    String[] parts = null;
    if (line.contains(" as ")) {
      parts = line.split(Pattern.quote(" as "));
    }
    if ((parts != null) && 
      (parts.length == 2))
    {
      ArgumentParser ap1 = new ArgumentParser(this.w);
      ap1.parse(parts[0]);
      this.arr = ap1.getArgumentPanel(this.w);
      ArgumentParser ap2 = new ArgumentParser(this.w);
      ap2.parse(parts[1]);
      this.var = ap2.getArgumentPanel(this.w);
    }
  }
  
  public ISpellItem getSpellItem()
  {
    ForeachItem fei = new ForeachItem(false, this.w);
    fei.firstBlockItem = this.middlestartitem;
    if (this.middlestartitem != null) {
      this.middlelastitem.setPrevious(fei);
    }
    if (this.arr != null) {
      fei.istp.lefthand.add(this.arr);
    }
    if (this.var != null) {
      fei.istp.righthand.add(this.var);
    }
    fei.recalculateSize();
    return fei;
  }
}
