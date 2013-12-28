package de.pylamo.spellmaker.gui.SpellItems.Parameter;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class BooleanParameterPanel extends ParameterPanel {
    private static final long serialVersionUID = 1L;
    public final JCheckBox boolbox = new JCheckBox();

    public BooleanParameterPanel(boolean preview) {
        super("Boolean: ", Parameter.Boolean, 1, preview);
        this.fields.get(0).setPreferredSize(new Dimension(40, 20));
        this.fields.get(0).setEditable(false);
        this.fields.get(0).setText("false");
        this.tip.setDescription("Check the box for true, unchecked = false");
        boolbox.setOpaque(false);
        this.add(boolbox);
        boolbox.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent arg0) {
                if (((JCheckBox) arg0.getSource()).isSelected()) {
                    fields.get(0).setText("true");
                } else {
                    fields.get(0).setText("false");
                }
            }
        });
    }

    @Override
    public String getString() {
        return ", " + fields.get(0).getText();
    }
}