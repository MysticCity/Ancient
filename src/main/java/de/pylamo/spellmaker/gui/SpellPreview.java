package de.pylamo.spellmaker.gui;

import de.pylamo.spellmaker.Commands;
import de.pylamo.spellmaker.gui.SpellItems.Commands.Command;
import de.pylamo.spellmaker.gui.SpellItems.Commands.SpellItem;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.Parameter;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

public class SpellPreview extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public static final HashSet<Command> commands = new HashSet<Command>();

    public SpellPreview(Window w) {
        this.setVisible(true);
        this.setLayout(new WrapLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setMinimumSize(new Dimension(250, 200));
        String line;
        for (Commands command : Commands.values()) {
            Parameter[] params = new Parameter[command.getParams().length];
            for (int i = 0; i < params.length; i++) {
                params[i] = Parameter.getParameterByName(command.getParams()[i].name());
            }
            String description = command.getDescription();
            commands.add(new Command(Command.getLine(command.getName(), params, command.getArgnames(), description)));
            SpellItem si = new SpellItem(command.getName(), params, command.getArgnames(),
                    Command.getLine(command.getName(), params, command.getArgnames(), description), w);
            si.ti.setDescription(description.trim());
            w.spellItems.put(si.name, si);
            this.add(si);
        }
    }
}
