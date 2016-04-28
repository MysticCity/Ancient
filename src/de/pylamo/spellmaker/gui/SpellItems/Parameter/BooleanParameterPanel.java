package de.pylamo.spellmaker.gui.SpellItems.Parameter;

import de.pylamo.spellmaker.gui.SpellItems.ToolTipItem;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class BooleanParameterPanel
  extends ParameterPanel
{
  private static final long serialVersionUID = 1L;
  public final JCheckBox boolbox = new JCheckBox();
  
  public BooleanParameterPanel(boolean preview)
  {
    super("Boolean: ", Parameter.Boolean, 1, preview);
    ((JTextField)this.fields.get(0)).setPreferredSize(new Dimension(40, 20));
    ((JTextField)this.fields.get(0)).setEditable(false);
    ((JTextField)this.fields.get(0)).setText("false");
    this.tip.setDescription("Check the box for true, unchecked = false");
    this.boolbox.setOpaque(false);
    add(this.boolbox);
    this.boolbox.addChangeListener(new ChangeListener()
    {
      public void stateChanged(ChangeEvent arg0)
      {
        if (((JCheckBox)arg0.getSource()).isSelected()) {
          ((JTextField)BooleanParameterPanel.this.fields.get(0)).setText("true");
        } else {
          ((JTextField)BooleanParameterPanel.this.fields.get(0)).setText("false");
        }
      }
    });
  }
  
  public String getString()
  {
    return ", " + ((JTextField)this.fields.get(0)).getText();
  }
}
