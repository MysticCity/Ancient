package de.pylamo.spellmaker.gui.SpellItems.Condition;

import de.pylamo.spellmaker.gui.SpellItems.ISpellItem;
import de.pylamo.spellmaker.gui.Window;
import java.awt.Point;

public abstract class ComplexItem
  extends ISpellItem
{
  private static final long serialVersionUID = 1L;
  public ISpellItem firstBlockItem;
  private int lastheight;
  
  ComplexItem(Window w)
  {
    super(w);
  }
  
  protected abstract EndPanel getEndPanel();
  
  public void setNachfolger(ISpellItem si, Point p)
  {
    if (si == this) {
      return;
    }
    if (si == null)
    {
      this.next = null;
      revalidate();
      return;
    }
    if (p.getY() < getY() + getHeight() / 3 * 2)
    {
      if (this.firstBlockItem == null) {
        this.firstBlockItem = si;
      }
    }
    else {
      super.setNext(si);
    }
    revalidate();
  }
  
  public void revalidate()
  {
    if (this.lastheight != getHeight())
    {
      setNextLocation();
      this.lastheight = getHeight();
    }
    if (getEndPanel() != null) {
      getEndPanel().setSize(getEndPanel().getPreferredSize());
    }
    setNextLocation();
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
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\de\pylamo\spellmaker\gui\SpellItems\Condition\ComplexItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */