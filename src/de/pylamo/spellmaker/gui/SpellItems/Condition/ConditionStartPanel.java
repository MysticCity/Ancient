package de.pylamo.spellmaker.gui.SpellItems.Condition;

import de.pylamo.spellmaker.gui.SpellItems.Parameter.Parameter;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.ParameterSlot;
import de.pylamo.spellmaker.gui.SpellItems.ToolTipItem;
import de.pylamo.spellmaker.gui.Window;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ConditionStartPanel
  extends JPanel
{
  private static final long serialVersionUID = 1L;
  public final ParameterSlot conditionslot;
  
  public ConditionStartPanel(String name, boolean preview, Window w)
  {
    FlowLayout fl = new FlowLayout(1, 1, 1);
    setLayout(fl);
    JLabel la = new JLabel(name);
    add(la);
    this.conditionslot = new ParameterSlot(Parameter.Condition, "condition", preview, w);
    add(this.conditionslot);
    ToolTipItem tti = new ToolTipItem();
    tti.setToolTipText("<html>This is an if item, if the specified condition <br> is fulfilled, the following block will be executed</html>");
    add(tti);
    setBackground(Color.white);
    setBorder(BorderFactory.createLineBorder(Color.black));
  }
  
  public void revalidate()
  {
    super.revalidate();
    if ((getParent() != null) && ((getParent() instanceof JComponent))) {
      ((JComponent)getParent()).revalidate();
    }
  }
}
