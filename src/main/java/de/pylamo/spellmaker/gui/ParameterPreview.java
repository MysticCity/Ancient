package de.pylamo.spellmaker.gui;

import de.pylamo.spellmaker.Parameters;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.*;

import javax.swing.*;
import java.util.HashSet;

public class ParameterPreview extends JPanel {
    private static final long serialVersionUID = 1L;

    public static final HashSet<param> parameters = new HashSet<param>();

    public class param {
        public final String name;
        public final int am;
        public final Parameter type;

        public param(String name, int am, Parameter type) {
            this.name = name;
            this.am = am;
            this.type = type;
        }
    }

    public ParameterPreview(Window w) {
        this.setLayout(new WrapLayout());
        String line;
        for (Parameters param : Parameters.values()) {
            String returntype = param.getReturntype();
            int amount = param.getAmount();
            String description = param.getDescription();
            parameters.add(new param(param.name(), amount, Parameter.getParameterByName(returntype)));
            ParameterPanel pp = new ParameterPanel(param.name(), Parameter.getParameterByName(returntype), amount,
                    true);
            this.add(pp);
            w.spellItems.put(pp.name, pp);
        }
        NumberParameterPanel npp = new NumberParameterPanel(true);
        this.add(npp);
        w.spellItems.put("number", npp);
        StringParameterPanel spp = new StringParameterPanel(true);
        this.add(spp);
        w.spellItems.put("string", spp);
        BooleanParameterPanel bpp = new BooleanParameterPanel(true);
        this.add(bpp);
        w.spellItems.put("boolean", bpp);
    }
}