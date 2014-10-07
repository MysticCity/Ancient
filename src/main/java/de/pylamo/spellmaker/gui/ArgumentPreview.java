package de.pylamo.spellmaker.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.util.HashSet;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;

import de.pylamo.spellmaker.Arguments;
import de.pylamo.spellmaker.gui.SpellItems.Arguments.ArgumentPanel;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.Parameter;

public class ArgumentPreview extends JPanel {
    private static final long serialVersionUID = 1L;
    public static final HashSet<Argument> arguments = new HashSet<Argument>();

    public ArgumentPreview(Window w) {
        this.setVisible(true);
        this.setLayout(new WrapLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setMinimumSize(new Dimension(250, 200));
        for (Arguments argument : Arguments.values()) {
            ParameterType returntype = argument.getReturnType();
            ParameterType[] pts = argument.getParams();
            Parameter param = null;
            for (Parameter p : Parameter.values()) {
                if (p.name().equalsIgnoreCase(returntype.name())) {
                    param = p;
                    break;
                }
            }
            Parameter[] rparams = new Parameter[pts.length];
            for (int i = 0; i < rparams.length; i++) {
                Parameter rparam = null;
                for (Parameter p : Parameter.values()) {
                    if (p.name().equalsIgnoreCase(pts[i].name())) {
                        rparam = p;
                        break;
                    }
                }
                rparams[i] = rparam;
            }
            try {
                Argument a = new Argument(Argument.getLine(argument.getName(), rparams, argument.getArgnames(), argument.getDescription(), Parameter.getParameterByName(returntype.name())));
                arguments.add(a);
            } catch (Exception e) {
                System.out.println(argument.getName());
            }
            ArgumentPanel si = new ArgumentPanel(argument.getName(), param, argument.getDescription(), rparams, argument.getArgnames(), Argument.getLine(argument.getName(), rparams, argument.getArgnames(), argument.getName(), Parameter.getParameterByName(returntype.name())), w);
            this.add(si);
            w.spellItems.put(si.name, si);
        }
    }
}