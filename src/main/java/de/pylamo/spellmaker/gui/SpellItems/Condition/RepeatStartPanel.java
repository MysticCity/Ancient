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

public class RepeatStartPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    public final ParameterSlot amount;

    public RepeatStartPanel(boolean preview, Window w) {
        FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 1, 1);
        this.setLayout(fl);
        JLabel la = new JLabel("repeat");
        this.add(la);
        amount = new ParameterSlot(Parameter.Number, "amount", preview, w);
        this.add(amount);
        ToolTipItem tti = new ToolTipItem();
        tti.setToolTipText("<html>This is a repeat item, it repeats the codeblock the amount <br> you specified in the number parameter slot</html>");
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