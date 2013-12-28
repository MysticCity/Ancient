package de.pylamo.spellmaker.gui.SpellItems.Condition;

import javax.swing.*;
import java.awt.*;

public class SidePanel extends JPanel {
    private static final long serialVersionUID = 1L;

    public SidePanel() {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(25, 100));
        this.setMaximumSize(new Dimension(25, 100));
        this.setSize(25, 100);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setBackground(Color.WHITE);
    }
}