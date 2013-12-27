package de.pylamo.spellmaker.gui;

import de.pylamo.spellmaker.gui.SpellItems.ISpellItem;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.IParameter;

import javax.swing.*;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Frederik Haselmeier
 * Date: 28.01.13
 * Time: 21:56
 */
public class SearchPanel extends JPanel {
    private final Window w;

    public SearchPanel(Window w) {
        this.w = w;
        this.setLayout(new WrapLayout());
    }


    public void search(String searchterm) {
        this.removeAll();
        for (Map.Entry<String, JComponent> entry : w.spellItems.entrySet()) {
            if (entry.getKey().toLowerCase().contains(searchterm.toLowerCase())) {
                if (entry.getValue() instanceof IParameter) {
                    this.add(((IParameter) entry.getValue()).clone());
                } else if (entry.getValue() instanceof ISpellItem) {
                    this.add(((ISpellItem) entry.getValue()).clone());
                }
                ;
            }
        }
        this.revalidate();
        repaint();
    }
}
