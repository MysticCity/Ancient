package de.pylamo.spellmaker.gui.SpellItems.Condition;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.pylamo.spellmaker.gui.Window;
import de.pylamo.spellmaker.gui.SpellItems.ToolTipItem;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.Parameter;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.ParameterSlot;


public class ForeachStartItem extends JPanel {
    private static final long serialVersionUID = 1L;

    public final ParameterSlot lefthand;
    public final ParameterSlot righthand;

    public ForeachStartItem(boolean preview, Window w) {
        FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 1, 1);
        this.setLayout(fl);
        JLabel la = new JLabel("foreach ");
        this.add(la);
        lefthand = new ParameterSlot(Parameter.Location, "arrayname", preview, w);
        this.add(lefthand);
        JLabel asp = new JLabel(" as ");
        this.add(asp);
        righthand = new ParameterSlot(Parameter.Variable, "varname", preview, w);
        this.add(righthand);
        ToolTipItem tti = new ToolTipItem();
        tti.setToolTipText("<html>This is a foreach item</html>");
        this.add(tti);
        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
    }

    @Override
    public void revalidate() {
        super.revalidate();
        if (this.getParent() != null) {
            if (this.getParent() != null && this.getParent() instanceof JComponent) {
                ((JComponent) this.getParent()).revalidate();
            }
        }
    }
}