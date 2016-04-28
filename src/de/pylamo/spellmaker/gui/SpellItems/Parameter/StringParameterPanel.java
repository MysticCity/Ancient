package de.pylamo.spellmaker.gui.SpellItems.Parameter;

import de.pylamo.spellmaker.gui.SpellItems.ToolTipItem;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JTextField;

public class StringParameterPanel
  extends ParameterPanel
{
  private static final long serialVersionUID = 1L;
  
  public StringParameterPanel(boolean preview)
  {
    super("String: ", Parameter.String, 1, preview);
    ((JTextField)this.fields.get(0)).setPreferredSize(new Dimension(100, 18));
    this.tip.setDescription("Enter a text in the textfield");
  }
  
  public String getString()
  {
    return ", \"" + ((JTextField)this.fields.get(0)).getText() + "\"";
  }
}
