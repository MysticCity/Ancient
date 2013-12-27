package de.pylamo.spellmaker.gui.SpellItems;

import javax.swing.*;

public class TopInformation extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public final JLabel l = new JLabel();
    private final ToolTipItem tip = new ToolTipItem();

    public TopInformation(String name) {
        l.setText(name);
        //this.setSize(200, 25);
        this.setOpaque(false);
        this.add(l);
        this.add(tip);
    }

    public void setDescription(String desc) {
        String desc1 = desc.trim();
        tip.setDescription(desc1);
    }
}
