package de.pylamo.spellmaker.gui.SpellItems.Condition;

import javax.swing.*;
import java.awt.*;

public class EndPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    public EndPanel(String text) {
        JLabel l = new JLabel();
        l.setText(text);
        this.setBackground(Color.white);
        this.add(l);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
    }
}