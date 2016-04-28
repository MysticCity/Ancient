package de.pylamo.spellmaker.parser;

import de.pylamo.spellmaker.gui.SpellItems.Condition.ElseItem;
import de.pylamo.spellmaker.gui.SpellItems.ISpellItem;
import de.pylamo.spellmaker.gui.Window;

public class ElseStatement
  extends ComplexStatement
{
  public ElseStatement(SpellParser sp, Window w)
  {
    super(sp, "endelse", w);
  }
  
  public void parseStart(String line) {}
  
  public ISpellItem getSpellItem()
  {
    ElseItem ifi = new ElseItem(false, this.w);
    ifi.firstBlockItem = this.middlestartitem;
    if (this.middlestartitem != null) {
      this.middlelastitem.setPrevious(ifi);
    }
    ifi.recalculateSize();
    return ifi;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\de\pylamo\spellmaker\parser\ElseStatement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */