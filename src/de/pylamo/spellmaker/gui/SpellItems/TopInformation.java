package de.pylamo.spellmaker.gui.SpellItems;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TopInformation
  extends JPanel
{
  private static final long serialVersionUID = 1L;
  public final JLabel l = new JLabel();
  private final ToolTipItem tip = new ToolTipItem();
  
  public TopInformation(String name)
  {
    this.l.setText(name);
    
    setOpaque(false);
    add(this.l);
    add(this.tip);
  }
  
  public void setDescription(String desc)
  {
    String desc1 = desc.trim();
    this.tip.setDescription(desc1);
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\de\pylamo\spellmaker\gui\SpellItems\TopInformation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */