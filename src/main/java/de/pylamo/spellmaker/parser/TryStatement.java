package de.pylamo.spellmaker.parser;

import de.pylamo.spellmaker.gui.Window;
import de.pylamo.spellmaker.gui.SpellItems.ISpellItem;
import de.pylamo.spellmaker.gui.SpellItems.Condition.TryItem;

public class TryStatement extends ComplexStatement {

    public TryStatement(SpellParser sp, Window w) {
        super(sp, "endtry", w);
    }

    @Override
    public void parseStart(String line) {
    }

    @Override
    public ISpellItem getSpellItem() {
        TryItem ti = new TryItem(false, w);
        ti.firstBlockItem = this.middlestartitem;
        if (this.middlestartitem != null) {
            this.middlelastitem.setPrevious(ti);
        }
        ti.recalculateSize();
        return ti;
    }
}