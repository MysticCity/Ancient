package de.pylamo.spellmaker.parser;

import de.pylamo.spellmaker.gui.Window;
import de.pylamo.spellmaker.gui.SpellItems.ISpellItem;
import de.pylamo.spellmaker.gui.SpellItems.Condition.ElseItem;

public class ElseStatement extends ComplexStatement {

    public ElseStatement(SpellParser sp, Window w) {
        super(sp, "endelse", w);
    }

    @Override
    public void parseStart(String line) {
    }

    @Override
    public ISpellItem getSpellItem() {
        ElseItem ifi = new ElseItem(false, w);
        ifi.firstBlockItem = this.middlestartitem;
        if (this.middlestartitem != null) {
            this.middlelastitem.setPrevious(ifi);
        }
        ifi.recalculateSize();
        return ifi;
    }
}