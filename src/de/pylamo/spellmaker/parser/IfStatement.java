package de.pylamo.spellmaker.parser;

import de.pylamo.spellmaker.gui.SpellItems.Condition.ConditionStartPanel;
import de.pylamo.spellmaker.gui.SpellItems.Condition.IfItem;
import de.pylamo.spellmaker.gui.SpellItems.ISpellItem;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.IParameter;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.ParameterSlot;
import de.pylamo.spellmaker.gui.Window;

public class IfStatement
  extends ComplexStatement
{
  private IParameter panel;
  
  public IfStatement(SpellParser sp, Window w)
  {
    super(sp, "endif", w);
  }
  
  public void parseStart(String line)
  {
    if (line.contains(",")) {
      line = line.substring(line.indexOf(',') + 1);
    }
    ConditionParser cp = new ConditionParser();
    this.panel = cp.parse(line, this.w);
  }
  
  public ISpellItem getSpellItem()
  {
    IfItem ifi = new IfItem(false, this.w);
    ifi.firstBlockItem = this.middlestartitem;
    if (this.middlestartitem != null) {
      this.middlelastitem.setPrevious(ifi);
    }
    if (this.panel != null) {
      ifi.istp.conditionslot.add(this.panel);
    }
    ifi.recalculateSize();
    return ifi;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\de\pylamo\spellmaker\parser\IfStatement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */