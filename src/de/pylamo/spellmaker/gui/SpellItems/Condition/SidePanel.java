package de.pylamo.spellmaker.gui.SpellItems.Condition;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class SidePanel
  extends JPanel
{
  private static final long serialVersionUID = 1L;
  
  public SidePanel()
  {
    setLayout(null);
    setPreferredSize(new Dimension(25, 100));
    setMaximumSize(new Dimension(25, 100));
    setSize(25, 100);
    setBorder(BorderFactory.createLineBorder(Color.black));
    setBackground(Color.WHITE);
  }
}
