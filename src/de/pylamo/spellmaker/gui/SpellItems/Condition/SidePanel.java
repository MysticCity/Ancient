package de.pylamo.spellmaker.gui.SpellItems.Condition;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

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