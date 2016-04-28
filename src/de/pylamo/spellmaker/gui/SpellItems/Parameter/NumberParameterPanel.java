package de.pylamo.spellmaker.gui.SpellItems.Parameter;

import de.pylamo.spellmaker.gui.SpellItems.ToolTipItem;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JTextField;

public class NumberParameterPanel
  extends ParameterPanel
{
  private static final long serialVersionUID = 1L;
  
  public NumberParameterPanel(boolean preview)
  {
    super("Number: ", Parameter.Number, 1, preview);
    ((JTextField)this.fields.get(0)).setPreferredSize(new Dimension(70, 18));
    this.tip.setDescription("Enter a number in the textfield");
  }
  
  public String getString()
  {
    return ", " + ((JTextField)this.fields.get(0)).getText();
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\de\pylamo\spellmaker\gui\SpellItems\Parameter\NumberParameterPanel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */