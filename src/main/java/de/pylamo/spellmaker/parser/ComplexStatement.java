package de.pylamo.spellmaker.parser;

import de.pylamo.spellmaker.gui.SpellItems.ISpellItem;
import de.pylamo.spellmaker.gui.Window;

import java.io.BufferedReader;
import java.io.IOException;

abstract class ComplexStatement {
    private final String end;
    ISpellItem middlestartitem;
    ISpellItem middlelastitem;
    final Window w;
    private final SpellParser sp;

    ComplexStatement(SpellParser sp, String end, Window w) {
        this.sp = sp;
        this.end = end;
        this.w = w;
    }

    protected abstract void parseStart(String line);

    public void parse(BufferedReader bf, String curline) throws IOException {
        parseStart(curline);
        curline = bf.readLine();
        while (!curline.equalsIgnoreCase(end) && curline != null) {
            curline = curline.trim();
            if (curline.equals("") || curline.equals("")) {
                curline = bf.readLine();
                continue;
            }
            if (curline.toLowerCase().trim().startsWith("repeat")) {
                RepeatStatement rs = new RepeatStatement(sp, w);
                rs.parse(bf, curline);
                ISpellItem spi = rs.getSpellItem();
                addSpellItem(spi);
            } else if (curline.toLowerCase().trim().startsWith("if")) {
                IfStatement rs = new IfStatement(sp, w);
                rs.parse(bf, curline);
                ISpellItem spi = rs.getSpellItem();
                addSpellItem(spi);
            } else if (curline.toLowerCase().trim().startsWith("while")) {
                WhileStatement rs = new WhileStatement(sp, w);
                rs.parse(bf, curline);
                ISpellItem spi = rs.getSpellItem();
                addSpellItem(spi);
            } else if (curline.toLowerCase().trim().startsWith("foreach")) {
                ForeachStatement rs = new ForeachStatement(w, sp);
                rs.parse(bf, curline);
                ISpellItem spi = rs.getSpellItem();
                addSpellItem(spi);
            } else if (curline.toLowerCase().trim().startsWith("try")) {
                TryStatement rs = new TryStatement(sp, w);
                rs.parse(bf, curline);
                ISpellItem spi = rs.getSpellItem();
                addSpellItem(spi);
            } else if (curline.toLowerCase().trim().startsWith("var")) {
                VariableParser vp = new VariableParser(w);
                addSpellItem(vp.getVarPanel(curline));
            } else if (curline.toLowerCase().trim().startsWith("var")) {
                /*
                 * RepeatStatement rs = new RepeatStatement(); rs.parse(bf,
				 * curline); ISpellItem spi = rs.getSpellItem();
				 * addSpellItem(spi);
				 */
            } else {
                addSpellItem(sp.parseSpellItem(curline));
            }
            curline = bf.readLine();
        }
    }

    void addSpellItem(ISpellItem si) {
        if (si != null) {
            w.mp.add(si);
            w.mp.registeredItems.add(si);
            si.setSize(si.getPreferredSize());
            if (middlestartitem == null) {
                middlestartitem = si;
                middlelastitem = si;
            } else {
                si.setVorgaenger(middlelastitem);
                si.setLocation(si.getVorgaenger().getX(), si.getVorgaenger().getY() + si.getVorgaenger().getHeight());
                middlelastitem.setNachfolger(si);
                middlelastitem = si;
            }
        }
    }

    public abstract ISpellItem getSpellItem();
}