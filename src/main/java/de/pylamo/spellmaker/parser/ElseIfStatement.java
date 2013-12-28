package de.pylamo.spellmaker.parser;

import de.pylamo.spellmaker.gui.SpellItems.Condition.ElseIfItem;
import de.pylamo.spellmaker.gui.SpellItems.ISpellItem;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.IParameter;
import de.pylamo.spellmaker.gui.Window;

public class ElseIfStatement extends ComplexStatement {
    private IParameter panel;

    public ElseIfStatement(SpellParser sp, Window w) {
        super(sp, "endelseif", w);
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
        ElseIfItem ifi = new ElseIfItem(false, w);
        ifi.firstBlockItem = this.middlestartitem;
        if (this.middlestartitem != null) {
            this.middlelastitem.setVorgaenger(ifi);
        }
        if (panel != null) {
            ifi.istp.conditionslot.add(panel);
        }
        ifi.recalculateSize();
        return ifi;
    }
}