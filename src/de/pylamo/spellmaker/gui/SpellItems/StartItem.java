package de.pylamo.spellmaker.gui.SpellItems;

import de.pylamo.spellmaker.gui.MainPanel;
import de.pylamo.spellmaker.gui.Window;
import java.awt.Color;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashSet;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class StartItem
  extends ISpellItem
  implements MouseListener, MouseMotionListener
{
  private static final long serialVersionUID = 1L;
  private ISpellItem nachfolger;
  private final JLabel l = new JLabel("Start", null, 0);
  private Point p;
  
  public ISpellItem clone()
  {
    return null;
  }
  
  public StartItem(Window w, MainPanel mp)
  {
    super(w);
    mp.registeredItems.add(this);
    setBackground(Color.WHITE);
    
    this.l.addMouseMotionListener(this);
    this.l.addMouseListener(this);
    ToolTipItem tip = new ToolTipItem();
    tip.setDescription("The spellfile starts here, every item you want to use must be connected to this one");
    add(this.l);
    add(tip);
    addMouseListener(this);
    addMouseMotionListener(this);
    setBorder(BorderFactory.createLineBorder(Color.black));
  }
  
  public String getItem()
  {
    return "";
  }
  
  public ISpellItem getNext()
  {
    return this.nachfolger;
  }
  
  public ISpellItem getPrevious()
  {
    return null;
  }
  
  public void mouseDragged(MouseEvent e)
  {
    int x = (int)(getX() + e.getX() - this.p.getX());
    int y = (int)(getY() + e.getY() - this.p.getY());
    if (x < 0) {
      x = 0;
    }
    if (y < 0) {
      y = 0;
    }
    setLocation(x, y);
    getParent().invalidate();
    repaint();
    this.l.repaint();
    getParent().repaint();
    y = getHeight() + getY();
    ISpellItem isi = this.nachfolger;
    while (isi != null)
    {
      isi.setLocation(getX(), y);
      y += isi.getHeight();
      isi = isi.getNext();
    }
  }
  
  public void mouseMoved(MouseEvent arg0) {}
  
  public void mouseClicked(MouseEvent e) {}
  
  public void mouseEntered(MouseEvent e) {}
  
  public void mouseExited(MouseEvent e) {}
  
  public void mousePressed(MouseEvent e)
  {
    this.p = e.getPoint();
    getParent().setComponentZOrder(this, 0);
  }
  
  public void mouseReleased(MouseEvent e)
  {
    ((MainPanel)getParent()).setScrollBars();
  }
  
  public void setNext(ISpellItem is)
  {
    this.nachfolger = is;
  }
  
  public void setPrevious(ISpellItem is) {}
}
