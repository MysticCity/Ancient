package de.pylamo.spellmaker.gui.SpellItems.Condition;

import de.pylamo.spellmaker.gui.SpellItems.Parameter.Parameter;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.ParameterSlot;
import de.pylamo.spellmaker.gui.SpellItems.ToolTipItem;
import de.pylamo.spellmaker.gui.Window;

import javax.swing.*;
import java.awt.*;

public class ConditionStartPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    public final ParameterSlot conditionslot;

    public ConditionStartPanel(String name, boolean preview, Window w) {
        FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 1, 1);
        this.setLayout(fl);
        JLabel la = new JLabel(name);
        this.add(la);
        conditionslot = new ParameterSlot(Parameter.Condition, "condition", preview, w);
        this.add(conditionslot);
        ToolTipItem tti = new ToolTipItem();
        tti.setToolTipText("<html>This is an if item, if the specified condition <br> is fulfilled, the following block will be executed</html>");
        this.add(tti);
        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
    }

    @Override
    public void revalidate() {
        super.revalidate();
        if (this.getParent() != null) {
            this.getParent().revalidate();
        }
    }
}