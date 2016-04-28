package de.pylamo.spellmaker.gui.SpellItems;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

public class SpellmakerScrollPanel
  extends JPanel
{
  private static final long serialVersionUID = 1L;
  JScrollBar jsb = new JScrollBar();
  
  public SpellmakerScrollPanel(final Container c)
  {
    setLayout(new BorderLayout());
    add(c, "Center");
    this.jsb.setMinimum(0);
    this.jsb.setMaximum(60);
    add(this.jsb, "East");
    addComponentListener(new ComponentListener()
    {
      public void componentResized(ComponentEvent e)
      {
        float ratio = SpellmakerScrollPanel.this.getHeight() / c.getHeight();
        SpellmakerScrollPanel.this.jsb.setMaximum(Math.round(ratio * 60.0F));
        SpellmakerScrollPanel.this.jsb.invalidate();
      }
      
      public void componentMoved(ComponentEvent e) {}
      
      public void componentShown(ComponentEvent e) {}
      
      public void componentHidden(ComponentEvent e) {}
    });
  }
}
