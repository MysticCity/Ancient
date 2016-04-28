package de.pylamo.spellmaker.gui.SpellItems.Condition;

import de.pylamo.spellmaker.gui.SpellItems.ToolTipItem;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SimpleStartPanel
  extends JPanel
{
  private static final long serialVersionUID = 1L;
  
  public SimpleStartPanel(String name)
  {
    setLayout(new FlowLayout(1));
    JLabel la = new JLabel(name);
    add(la);
    ToolTipItem tti = new ToolTipItem();
    tti.setToolTipText("<html>This is a try item</html>");
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
