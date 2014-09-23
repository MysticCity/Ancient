package de.pylamo.spellmaker.gui.SpellItems.Condition;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.pylamo.spellmaker.gui.SpellItems.ToolTipItem;

public class SimpleStartPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    public SimpleStartPanel(String name) {
        this.setLayout(new FlowLayout(1));
        JLabel la = new JLabel(name);
        this.add(la);
        ToolTipItem tti = new ToolTipItem();
        tti.setToolTipText("<html>This is a try item</html>");
        this.add(tti);
        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
    }

    @Override
    public void revalidate() {
        super.revalidate();
        if (this.getParent() != null && this.getParent() instanceof JComponent) {
            ((JComponent) this.getParent()).revalidate();
        }
    }
}