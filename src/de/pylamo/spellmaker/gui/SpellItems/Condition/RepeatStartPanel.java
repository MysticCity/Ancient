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

public class RepeatStartPanel
  extends JPanel
{
  private static final long serialVersionUID = 1L;
  public final ParameterSlot amount;
  
  public RepeatStartPanel(boolean preview, Window w)
  {
    FlowLayout fl = new FlowLayout(1, 1, 1);
    setLayout(fl);
    JLabel la = new JLabel("repeat");
    add(la);
    this.amount = new ParameterSlot(Parameter.Number, "amount", preview, w);
    add(this.amount);
    ToolTipItem tti = new ToolTipItem();
    tti.setToolTipText("<html>This is a repeat item, it repeats the codeblock the amount <br> you specified in the number parameter slot</html>");
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


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\de\pylamo\spellmaker\gui\SpellItems\Condition\RepeatStartPanel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */