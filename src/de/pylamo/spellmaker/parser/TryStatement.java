package de.pylamo.spellmaker.parser;

import de.pylamo.spellmaker.gui.SpellItems.Condition.TryItem;
import de.pylamo.spellmaker.gui.SpellItems.ISpellItem;
import de.pylamo.spellmaker.gui.Window;

public class TryStatement
  extends ComplexStatement
{
  public TryStatement(SpellParser sp, Window w)
  {
    super(sp, "endtry", w);
  }
  
  public void parseStart(String line) {}
  
  public ISpellItem getSpellItem()
  {
    TryItem ti = new TryItem(false, this.w);
    ti.firstBlockItem = this.middlestartitem;
    if (this.middlestartitem != null) {
      this.middlelastitem.setPrevious(ti);
    }
    ti.recalculateSize();
    return ti;
  }
}
