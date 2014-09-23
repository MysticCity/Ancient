package de.pylamo.spellmaker.gui.SpellItems.Parameter;

import java.awt.Dimension;

public class StringParameterPanel extends ParameterPanel {
    private static final long serialVersionUID = 1L;

    public StringParameterPanel(boolean preview) {
        super("String: ", Parameter.String, 1, preview);
        this.fields.get(0).setPreferredSize(new Dimension(100, 18));
        this.tip.setDescription("Enter a text in the textfield");
    }

    @Override
    public String getString() {
        return ", \"" + fields.get(0).getText() + "\"";
    }
}