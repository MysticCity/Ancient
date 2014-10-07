package de.pylamo.spellmaker.parser;

import de.pylamo.spellmaker.gui.Window;
import de.pylamo.spellmaker.gui.SpellItems.ISpellItem;
import de.pylamo.spellmaker.gui.SpellItems.Condition.WhileItem;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.IParameter;

public class WhileStatement extends ComplexStatement {
    private IParameter panel;

    public WhileStatement(SpellParser sp, Window w) {
        super(sp, "endwhile", w);
    }

    @Override
    public void parseStart(String line) {
        if (line.contains(",")) {
            line = line.substring(line.indexOf(',') + 1);
        }
        ConditionParser cp = new ConditionParser();
        panel = cp.parse(line, w);
    }

    @Override
    public ISpellItem getSpellItem() {
        WhileItem ifi = new WhileItem(false, w);
        ifi.firstBlockItem = this.middlestartitem;
        if (this.middlestartitem != null) {
            this.middlelastitem.setPrevious(ifi);
        }
        if (panel != null) {
            ifi.istp.conditionslot.add(panel);
        }
        ifi.recalculateSize();
        return ifi;
    }
}