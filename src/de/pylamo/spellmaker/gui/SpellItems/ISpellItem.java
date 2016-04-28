package de.pylamo.spellmaker.gui.SpellItems;

import de.pylamo.spellmaker.gui.MainPanel;
import de.pylamo.spellmaker.gui.SpellItems.Condition.ComplexItem;
import de.pylamo.spellmaker.gui.Window;
import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashSet;
import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public abstract class ISpellItem
  extends JPanel
  implements MouseListener, MouseMotionListener
{
  protected boolean b;
  private static final long serialVersionUID = 1L;
  protected ISpellItem previous;
  protected ISpellItem next;
  private final JPopupMenu menu = new JPopupMenu();
  protected final Window w;
  
  protected ISpellItem(final Window w)
  {
    this.w = w;
    JMenuItem menuItem = new JMenuItem("delete");
    JMenuItem menuItem2 = new JMenuItem("copy");
    ActionListener al = new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        ISpellItem.this.delete();
      }
    };
    menuItem.addActionListener(al);
    menuItem2.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        ISpellItem si = ISpellItem.this.clone();
        si.setSize(si.getPreferredSize());
        si.setLocation(ISpellItem.this.getX() + 30, ISpellItem.this.getY() + 20);
        si.revalidate();
        w.mp.add(si);
        w.mp.revalidate();
        w.mp.repaint();
        si.revalidate();
      }
    });
    this.menu.add(menuItem);
    this.menu.add(menuItem2);
  }
  
  public abstract ISpellItem clone();
  
  void delete()
  {
    if (getParent() != null)
    {
      JComponent c = (JComponent)getParent();
      c.remove(this);
      this.w.mp.registeredItems.remove(this);
      if (((this.previous instanceof ComplexItem)) && (((ComplexItem)this.previous).firstBlockItem == this))
      {
        if (((ComplexItem)this.previous).firstBlockItem == this)
        {
          ((ComplexItem)this.previous).firstBlockItem = null;
          this.previous.revalidate();
        }
      }
      else if (getPrevious() != null)
      {
        getPrevious().setNext(getNext());
        getPrevious().revalidate();
      }
      if (getNext() != null)
      {
        getNext().revalidate();
        getNext().setPrevious(getPrevious());
        if (getPrevious() != null) {
          getPrevious().setNextLocation();
        }
      }
      c.repaint();
    }
  }
  
  public abstract String getItem();
  
  public ISpellItem getNext()
  {
    return this.next;
  }
  
  public void recalculateSize()
  {
    revalidate();
    int y = getHeight() + getY();
    ISpellItem isi = this.next;
    while (isi != null)
    {
      isi.setLocation(getX(), y);
      y += isi.getHeight();
      isi = isi.getNext();
    }
  }
  
  public ISpellItem getPrevious()
  {
    return this.previous;
  }
  
  Point menuloc = null;
  
  public void setNext(ISpellItem is)
  {
    if (is == this) {
      return;
    }
    this.next = is;
    ISpellItem isi = this.previous;
    while (isi != null)
    {
      isi.revalidate();
      isi = isi.getPrevious();
    }
  }
  
  public void setPrevious(ISpellItem is)
  {
    if (is == this) {
      return;
    }
    this.previous = is;
  }
  
  ISpellItem getRootItem()
  {
    ISpellItem is = this;
    while (is.getPrevious() != null) {
      is = is.getPrevious();
    }
    return is;
  }
  
  public void mouseClicked(MouseEvent e)
  {
    if ((this.b) && 
      (e.getButton() == 3))
    {
      this.menuloc = e.getPoint();
      this.menu.show((Component)e.getSource(), e.getX(), e.getY());
    }
  }
  
  private boolean dragEnabled = false;
  protected Point p;
  
  public void mouseEntered(MouseEvent e) {}
  
  public void mouseExited(MouseEvent e) {}
  
  public void mousePressed(MouseEvent e)
  {
    if (this.b)
    {
      this.p = e.getPoint();
      ((JLayeredPane)getParent()).moveToFront(this);
      ISpellItem isi = this.next;
      while (isi != null)
      {
        ((JLayeredPane)getParent()).moveToFront(isi);
        isi = isi.getNext();
      }
    }
  }
  
  public void mouseReleased(MouseEvent e)
  {
    if (this.b)
    {
      this.dragEnabled = false;
      ((MainPanel)getParent()).setScrollBars();
      ISpellItem is = this.w.mp.getSpellItemBeneath(this, new Point(getX() + e.getX(), getY() + e.getY()));
      if (is != null)
      {
        ISpellItem n = is.getNext();
        if ((is instanceof ComplexItem))
        {
          ((ComplexItem)is).setNachfolger(this, new Point(e.getX() + getX(), e.getY() + getY()));
          this.previous = is;
        }
        else
        {
          is.setNext(this);
          this.previous = is;
          setLocation(this.previous.getX(), this.previous.getY() + this.previous.getHeight());
        }
        if ((n != null) && ((!(is instanceof ComplexItem)) || (this != ((ComplexItem)is).firstBlockItem)))
        {
          ISpellItem isi = this.next;
          while ((isi != null) && (isi.getNext() != null)) {
            isi = isi.getNext();
          }
          if (isi != null)
          {
            isi.setNext(n);
            n.setPrevious(isi);
          }
          else
          {
            this.next = n;
            n.setPrevious(this);
          }
        }
        ISpellItem isi = this.previous;
        while (isi != null)
        {
          isi.revalidate();
          isi = isi.getPrevious();
        }
        isi = this.next;
        revalidate();
        isi = getRootItem();
        isi.setNextLocation();
      }
    }
  }
  
  public void setNextLocation()
  {
    int y = getY() + getHeight();
    ISpellItem isi = getNext();
    while (isi != null)
    {
      isi.setLocation(getX(), y);
      y += isi.getHeight();
      isi = isi.getNext();
    }
  }
  
  public void revalidate()
  {
    super.revalidate();
    if (this.previous != null) {
      this.previous.revalidate();
    }
    if (this.b) {
      setSize(getPreferredSize());
    }
  }
  
  public boolean isInVisiblePart(Point p)
  {
    return true;
  }
  
  public void mouseDragged(MouseEvent e)
  {
    if ((this.b) && ((this.dragEnabled) || (Math.sqrt(Math.pow(this.p.getX() - e.getX(), 2.0D) + Math.pow(this.p.getY() - e.getY(), 2.0D)) > 3.0D)))
    {
      this.dragEnabled = true;
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
      y = getHeight() + getY();
      ISpellItem isi = this.next;
      while (isi != null)
      {
        isi.setLocation(getX(), y);
        y += isi.getHeight();
        isi = isi.getNext();
      }
      if (this.previous != null)
      {
        revalidate();
        if (((this.previous instanceof ComplexItem)) && (((ComplexItem)this.previous).firstBlockItem == this))
        {
          ((ComplexItem)this.previous).firstBlockItem = null;
          this.previous.revalidate();
          this.previous = null;
          return;
        }
        this.previous.setNext(null);
        this.previous = null;
      }
    }
  }
  
  public void mouseMoved(MouseEvent e) {}
}
