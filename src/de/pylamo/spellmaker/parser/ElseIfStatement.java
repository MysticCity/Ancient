package de.pylamo.spellmaker.parser;

import de.pylamo.spellmaker.gui.SpellItems.Condition.ConditionStartPanel;
import de.pylamo.spellmaker.gui.SpellItems.Condition.ElseIfItem;
import de.pylamo.spellmaker.gui.SpellItems.ISpellItem;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.IParameter;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.ParameterSlot;
import de.pylamo.spellmaker.gui.Window;

public class ElseIfStatement
  extends ComplexStatement
{
  private IParameter panel;
  
  public ElseIfStatement(SpellParser sp, Window w)
  {
    super(sp, "endelseif", w);
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
    ElseIfItem ifi = new ElseIfItem(false, this.w);
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
