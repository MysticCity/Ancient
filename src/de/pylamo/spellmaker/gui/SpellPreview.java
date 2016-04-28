package de.pylamo.spellmaker.gui;

import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import de.pylamo.spellmaker.Commands;
import de.pylamo.spellmaker.gui.SpellItems.Commands.Command;
import de.pylamo.spellmaker.gui.SpellItems.Commands.SpellItem;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.Parameter;
import de.pylamo.spellmaker.gui.SpellItems.TopInformation;
import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.HashSet;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class SpellPreview
  extends JPanel
{
  private static final long serialVersionUID = 1L;
  public static final HashSet<Command> commands = new HashSet();
  
  public SpellPreview(Window w)
  {
    setVisible(true);
    setLayout(new WrapLayout());
    setBorder(BorderFactory.createLineBorder(Color.black));
    setMinimumSize(new Dimension(250, 200));
    for (Commands command : Commands.values())
    {
      Parameter[] params = new Parameter[command.getParams().length];
      for (int i = 0; i < params.length; i++) {
        params[i] = Parameter.getParameterByName(command.getParams()[i].name());
      }
      String description = command.getDescription();
      commands.add(new Command(Command.getLine(command.getName(), params, command.getArgnames(), description)));
      SpellItem si = new SpellItem(command.getName(), params, command.getArgnames(), Command.getLine(command.getName(), params, command.getArgnames(), description), w);
      si.ti.setDescription(description.trim());
      w.spellItems.put(si.name, si);
      add(si);
    }
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\de\pylamo\spellmaker\gui\SpellPreview.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */