package de.pylamo.spellmaker.gui.SpellItems.Condition;

import de.pylamo.spellmaker.gui.SpellItems.ISpellItem;
import de.pylamo.spellmaker.gui.Window;

import java.awt.*;

public abstract class ComplexItem extends ISpellItem {
    ComplexItem(Window w) {
        super(w);
    }

    private static final long serialVersionUID = 1L;
    public ISpellItem firstBlockItem;

    protected abstract EndPanel getEndPanel();

    public void setNachfolger(ISpellItem si, Point p) {
        if (si == this) {
            return;
        }
        if (si == null) {
            this.nachfolger = null;
            this.revalidate();
            return;
        }
        if (p.getY() < this.getY() + this.getHeight() / 3 * 2) {
            if (this.firstBlockItem == null) {
                this.firstBlockItem = si;
            }
        } else {
            super.setNachfolger(si);
        }
        this.revalidate();
    }

    private int lastheight;

    @Override
    public void revalidate() {
        if (lastheight != this.getHeight()) {
            this.setNachfolgerLocation();
            lastheight = this.getHeight();
        }
        if (this.getEndPanel() != null) {
            this.getEndPanel().setSize(this.getEndPanel().getPreferredSize());
        }
        this.setNachfolgerLocation();
    }

    @Override
    public void recalculateSize() {
        this.revalidate();
        int y = this.getHeight() + this.getY();
        ISpellItem isi = this.nachfolger;
        while (isi != null) {
            isi.setLocation(this.getX(), y);
            y += isi.getHeight();
            isi = isi.getNachfolger();
        }
    }

    @Override
    public void setNachfolgerLocation() {
        int y = this.getY() + this.getHeight();
        ISpellItem isi = this.getNachfolger();
        while (isi != null) {
            isi.setLocation(this.getX(), y);
            y += isi.getHeight();
            isi = isi.getNachfolger();
        }
    }
}