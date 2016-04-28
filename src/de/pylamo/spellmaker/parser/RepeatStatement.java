package de.pylamo.spellmaker.parser;

import de.pylamo.spellmaker.gui.SpellItems.Condition.RepeatItem;
import de.pylamo.spellmaker.gui.SpellItems.Condition.RepeatStartPanel;
import de.pylamo.spellmaker.gui.SpellItems.ISpellItem;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.IParameter;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.ParameterSlot;
import de.pylamo.spellmaker.gui.Window;

public class RepeatStatement
  extends ComplexStatement
{
  private IParameter p;
  
  public RepeatStatement(SpellParser sp, Window w)
  {
    super(sp, "endrepeat", w);
  }
  
  public void parseStart(String line)
  {
    if (line.contains(","))
    {
      ArgumentParser ap = new ArgumentParser(this.w);
      ap.parse(line.split(java.util.regex.Pattern.quote(","))[1].trim());
      this.p = ap.getArgumentPanel(this.w);
    }
  }
  
  public ISpellItem getSpellItem()
  {
    RepeatItem ri = new RepeatItem(false, this.w);
    ri.firstBlockItem = this.middlestartitem;
    if (this.middlestartitem != null) {
      this.middlelastitem.setPrevious(ri);
    }
    if (this.p != null)
    {
      ri.istp.amount.content = this.p;
      ri.istp.amount.add(this.p);
    }
    ri.recalculateSize();
    return ri;
  }
}
