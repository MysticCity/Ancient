package de.pylamo.spellmaker.parser;

import java.util.regex.Pattern;

import de.pylamo.spellmaker.gui.Window;
import de.pylamo.spellmaker.gui.SpellItems.ISpellItem;
import de.pylamo.spellmaker.gui.SpellItems.Condition.ForeachItem;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.IParameter;

public class ForeachStatement extends ComplexStatement {
    private IParameter arr;
    IParameter var;

    public ForeachStatement(Window w, SpellParser sp) {
        super(sp, "endforeach", w);
    }

    @Override
    public void parseStart(String line) {
        if (line.contains(",")) {
            line = line.substring(line.indexOf(',') + 1);
        }
        String[] parts = null;
        if (line.contains(" as ")) {
            parts = line.split(Pattern.quote(" as "));
        }
        if (parts != null) {
            if (parts.length == 2) {
                ArgumentParser ap1 = new ArgumentParser(w);
                ap1.parse(parts[0]);
                arr = ap1.getArgumentPanel(w);
                ArgumentParser ap2 = new ArgumentParser(w);
                ap2.parse(parts[1]);
                var = ap2.getArgumentPanel(w);
            }
        }
    }

    @Override
    public ISpellItem getSpellItem() {
        ForeachItem fei = new ForeachItem(false, w);
        fei.firstBlockItem = this.middlestartitem;
        if (this.middlestartitem != null) {
            this.middlelastitem.setPrevious(fei);
        }
        if (arr != null) {
            fei.istp.lefthand.add(arr);
        }
        if (var != null) {
            fei.istp.righthand.add(var);
        }
        fei.recalculateSize();
        return fei;
    }
}