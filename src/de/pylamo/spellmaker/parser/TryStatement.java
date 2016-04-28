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


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\de\pylamo\spellmaker\parser\TryStatement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */