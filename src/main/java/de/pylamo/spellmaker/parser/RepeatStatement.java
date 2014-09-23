package de.pylamo.spellmaker.parser;

import java.util.regex.Pattern;

import de.pylamo.spellmaker.gui.Window;
import de.pylamo.spellmaker.gui.SpellItems.ISpellItem;
import de.pylamo.spellmaker.gui.SpellItems.Condition.RepeatItem;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.IParameter;

public class RepeatStatement extends ComplexStatement {
    private IParameter p;

    public RepeatStatement(SpellParser sp, Window w) {
        super(sp, "endrepeat", w);
    }

    @Override
    public void parseStart(String line) {
        if (line.contains(",")) {
            ArgumentParser ap = new ArgumentParser(w);
            ap.parse(line.split(Pattern.quote(","))[1].trim());
            p = ap.getArgumentPanel(w);
        }
    }

    @Override
    public ISpellItem getSpellItem() {
        RepeatItem ri = new RepeatItem(false, w);
        ri.firstBlockItem = this.middlestartitem;
        if (this.middlestartitem != null) {
            this.middlelastitem.setPrevious(ri);
        }
        if (p != null) {
            ri.istp.amount.content = p;
            ri.istp.amount.add(p);
        }
        ri.recalculateSize();
        return ri;
    }
}