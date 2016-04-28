package de.pylamo.spellmaker.gui.SpellItems.Condition;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EndPanel
  extends JPanel
{
  private static final long serialVersionUID = 1L;
  
  public EndPanel(String text)
  {
    JLabel l = new JLabel();
    l.setText(text);
    setBackground(Color.white);
    add(l);
    setBorder(BorderFactory.createLineBorder(Color.black));
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\de\pylamo\spellmaker\gui\SpellItems\Condition\EndPanel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */