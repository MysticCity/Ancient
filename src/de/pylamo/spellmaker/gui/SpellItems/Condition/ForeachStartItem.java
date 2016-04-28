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

public class ForeachStartItem
  extends JPanel
{
  private static final long serialVersionUID = 1L;
  public final ParameterSlot lefthand;
  public final ParameterSlot righthand;
  
  public ForeachStartItem(boolean preview, Window w)
  {
    FlowLayout fl = new FlowLayout(1, 1, 1);
    setLayout(fl);
    JLabel la = new JLabel("foreach ");
    add(la);
    this.lefthand = new ParameterSlot(Parameter.Location, "arrayname", preview, w);
    add(this.lefthand);
    JLabel asp = new JLabel(" as ");
    add(asp);
    this.righthand = new ParameterSlot(Parameter.Variable, "varname", preview, w);
    add(this.righthand);
    ToolTipItem tti = new ToolTipItem();
    tti.setToolTipText("<html>This is a foreach item</html>");
    add(tti);
    setBackground(Color.white);
    setBorder(BorderFactory.createLineBorder(Color.black));
  }
  
  public void revalidate()
  {
    super.revalidate();
    if ((getParent() != null) && 
      (getParent() != null) && ((getParent() instanceof JComponent))) {
      ((JComponent)getParent()).revalidate();
    }
  }
}
