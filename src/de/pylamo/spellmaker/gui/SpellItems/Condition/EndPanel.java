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
