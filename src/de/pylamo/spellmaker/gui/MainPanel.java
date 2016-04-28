package de.pylamo.spellmaker.gui;

import de.pylamo.spellmaker.ConfigCreator;
import de.pylamo.spellmaker.Menu;
import de.pylamo.spellmaker.gui.SpellItems.Commands.Command;
import de.pylamo.spellmaker.gui.SpellItems.Commands.SpellItem;
import de.pylamo.spellmaker.gui.SpellItems.Condition.ElseIfItem;
import de.pylamo.spellmaker.gui.SpellItems.Condition.ElseItem;
import de.pylamo.spellmaker.gui.SpellItems.Condition.ForeachItem;
import de.pylamo.spellmaker.gui.SpellItems.Condition.IfItem;
import de.pylamo.spellmaker.gui.SpellItems.Condition.RepeatItem;
import de.pylamo.spellmaker.gui.SpellItems.Condition.TryItem;
import de.pylamo.spellmaker.gui.SpellItems.Condition.WhileItem;
import de.pylamo.spellmaker.gui.SpellItems.ISpellItem;
import de.pylamo.spellmaker.gui.SpellItems.ImageMover;
import de.pylamo.spellmaker.gui.SpellItems.StartItem;
import de.pylamo.spellmaker.gui.SpellItems.TopInformation;
import de.pylamo.spellmaker.gui.SpellItems.Variable.VariableOperationItem;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

public class MainPanel
  extends JLayeredPane
{
  private static final long serialVersionUID = 1L;
  private final MainPanel instance;
  public final StartItem startItem;
  public final HashSet<ISpellItem> registeredItems = new HashSet();
  private final Window w;
  
  public MainPanel(Window w)
  {
    this.w = w;
    this.instance = this;
    this.startItem = new StartItem(w, this);
    this.startItem.setSize(100, 25);
    this.startItem.setLocation(100, 100);
    add(this.startItem);
    setVisible(true);
    setBorder(BorderFactory.createLineBorder(Color.black));
    new MainDropTargetListener(this);
    setPreferredSize(new Dimension(200, 200));
  }
  
  Dimension getMaxSize()
  {
    int maxx = 0;
    int maxy = 0;
    for (Component c : getComponents())
    {
      if (c.getX() + c.getWidth() > maxx) {
        maxx = c.getX() + c.getWidth() + 10;
      }
      if (c.getY() + c.getHeight() > maxy) {
        maxy = c.getY() + c.getHeight() + 10;
      }
    }
    return new Dimension(maxx + 120, maxy + 360);
  }
  
  Dimension getMaxDimension(Dimension d1, Dimension d2)
  {
    double x = d1.getWidth();
    double y = d1.getHeight();
    if (x < d2.getWidth()) {
      x = d2.getWidth();
    }
    if (y < d2.getHeight()) {
      y = d2.getHeight();
    }
    return new Dimension((int)x, (int)y);
  }
  
  public void invalidate()
  {
    super.invalidate();
    getMaxSize();
    setPreferredSize(getMaxDimension(getPreferredSize(), getMaxSize()));
    this.w.mainScrollPane.revalidate();
  }
  
  public void save()
  {
    ISpellItem is = this.startItem;
    String output = "";
    while (is != null)
    {
      output = output + is.getItem() + '\n';
      is = is.getNext();
    }
    output = output.trim();
    try
    {
      File f = new File(this.w.m.spellname + ".spell");
      BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "UTF-8"));
      bw.write(this.w.m.spellname + "\n");
      if (this.w.m.active)
      {
        bw.write("active\n");
      }
      else if (!this.w.m.buff)
      {
        bw.write("passive:" + this.w.m.priority + "\n");
        bw.write(this.w.m.event + "\n");
      }
      else
      {
        bw.write("buff:" + this.w.m.priority + "\n");
        bw.write(this.w.m.event + "\n");
      }
      if (this.w.m.variables.size() > 0)
      {
        String vars = "variables: ";
        int i = 0;
        for (String s : this.w.m.variables)
        {
          if (i == 0) {
            vars = vars + s;
          } else {
            vars = vars + ", " + s;
          }
          i++;
        }
        bw.write(vars + "\n");
      }
      if (this.w.m.minlevel != 0) {
        bw.write("minlevel: " + this.w.m.minlevel + "\n");
      }
      if (!this.w.m.permission.equals("")) {
        bw.write("permission: " + this.w.m.permission + "\n");
      }
      bw.write(output);
      bw.close();
      JOptionPane.showMessageDialog(this.w, "Spell successfully saved to: " + f.getAbsolutePath(), "Information", 1);
      new ConfigCreator(this.w);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  public void setScrollBars()
  {
    setPreferredSize(getMaxSize());
    this.w.mainScrollPane.revalidate();
  }
  
  private static boolean isInRect(int x, int y, int width, int height, int xp, int yp)
  {
    boolean bx = (xp > x) && (xp < x + width);
    boolean by = (yp > y) && (yp < y + height);
    return (bx) && (by);
  }
  
  public ISpellItem getSpellItemBeneath(ISpellItem is, Point p)
  {
    for (ISpellItem isi : this.instance.registeredItems) {
      if (isi != is) {
        if ((isInRect(isi.getX(), isi.getY(), isi.getWidth(), isi.getHeight(), (int)p.getX(), (int)p.getY())) && (isi.isInVisiblePart(p))) {
          return isi;
        }
      }
    }
    return null;
  }
  
  class MainDropTargetListener
    extends DropTargetAdapter
    implements DropTargetListener
  {
    private DropTarget dropTarget;
    private final JLayeredPane panel;
    
    public MainDropTargetListener(JLayeredPane panel)
    {
      this.panel = panel;
      this.dropTarget = new DropTarget(panel, 1, this, true, null);
    }
    
    public void dropActionChanged(DropTargetDragEvent event) {}
    
    public void drop(DropTargetDropEvent event)
    {
      try
      {
        Transferable tr = event.getTransferable();
        String s = ((SimpleDragObject)tr.getTransferData(SimpleDragObject.dataFlavor)).s;
        if (s.equals("[IFITEM]"))
        {
          IfItem ifi = new IfItem(false, MainPanel.this.w);
          ifi.setLocation(event.getLocation());
          event.acceptDrop(1);
          MainPanel.this.registeredItems.add(ifi);
          ISpellItem is = MainPanel.this.getSpellItemBeneath(ifi, event.getLocation());
          if (is != null)
          {
            ISpellItem n = is.getNext();
            if (n != null)
            {
              ISpellItem isi = ifi.getNext();
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
                ifi.setNext(n);
                n.setPrevious(ifi);
              }
            }
            is.setNext(ifi);
            ifi.setPrevious(is);
            ifi.setLocation(ifi.getPrevious().getX(), ifi.getPrevious().getY() + ifi.getPrevious().getHeight());
            int y = ifi.getHeight() + ifi.getY();
            ISpellItem isi = ifi.getNext();
            while (isi != null)
            {
              isi.setLocation(ifi.getX(), y);
              y += isi.getHeight();
              isi = isi.getNext();
            }
          }
          this.panel.add(ifi);
          return;
        }
        if (s.equals("[ELSEIFITEM]"))
        {
          ElseIfItem ifi = new ElseIfItem(false, MainPanel.this.w);
          ifi.setLocation(event.getLocation());
          event.acceptDrop(1);
          MainPanel.this.registeredItems.add(ifi);
          ISpellItem is = MainPanel.this.getSpellItemBeneath(ifi, event.getLocation());
          if (is != null)
          {
            ISpellItem n = is.getNext();
            if (n != null)
            {
              ISpellItem isi = ifi.getNext();
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
                ifi.setNext(n);
                n.setPrevious(ifi);
              }
            }
            is.setNext(ifi);
            ifi.setPrevious(is);
            ifi.setLocation(ifi.getPrevious().getX(), ifi.getPrevious().getY() + ifi.getPrevious().getHeight());
            int y = ifi.getHeight() + ifi.getY();
            ISpellItem isi = ifi.getNext();
            while (isi != null)
            {
              isi.setLocation(ifi.getX(), y);
              y += isi.getHeight();
              isi = isi.getNext();
            }
          }
          this.panel.add(ifi);
          return;
        }
        if (s.equals("[ELSEITEM]"))
        {
          ElseItem ifi = new ElseItem(false, MainPanel.this.w);
          ifi.setLocation(event.getLocation());
          event.acceptDrop(1);
          MainPanel.this.registeredItems.add(ifi);
          ISpellItem is = MainPanel.this.getSpellItemBeneath(ifi, event.getLocation());
          if (is != null)
          {
            ISpellItem n = is.getNext();
            if (n != null)
            {
              ISpellItem isi = ifi.getNext();
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
                ifi.setNext(n);
                n.setPrevious(ifi);
              }
            }
            is.setNext(ifi);
            ifi.setPrevious(is);
            ifi.setLocation(ifi.getPrevious().getX(), ifi.getPrevious().getY() + ifi.getPrevious().getHeight());
            int y = ifi.getHeight() + ifi.getY();
            ISpellItem isi = ifi.getNext();
            while (isi != null)
            {
              isi.setLocation(ifi.getX(), y);
              y += isi.getHeight();
              isi = isi.getNext();
            }
          }
          this.panel.add(ifi);
          return;
        }
        if (s.equals("[WHILEITEM]"))
        {
          WhileItem ifi = new WhileItem(false, MainPanel.this.w);
          ifi.setLocation(event.getLocation());
          event.acceptDrop(1);
          MainPanel.this.registeredItems.add(ifi);
          ISpellItem is = MainPanel.this.getSpellItemBeneath(ifi, event.getLocation());
          if (is != null)
          {
            ISpellItem n = is.getNext();
            if (n != null)
            {
              ISpellItem isi = ifi.getNext();
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
                ifi.setNext(n);
                n.setPrevious(ifi);
              }
            }
            is.setNext(ifi);
            ifi.setPrevious(is);
            ifi.setLocation(ifi.getPrevious().getX(), ifi.getPrevious().getY() + ifi.getPrevious().getHeight());
            int y = ifi.getHeight() + ifi.getY();
            ISpellItem isi = ifi.getNext();
            while (isi != null)
            {
              isi.setLocation(ifi.getX(), y);
              y += isi.getHeight();
              isi = isi.getNext();
            }
          }
          this.panel.add(ifi);
          return;
        }
        if (s.equals("[REPEATITEM]"))
        {
          RepeatItem ifi = new RepeatItem(false, MainPanel.this.w);
          ifi.setLocation(event.getLocation());
          event.acceptDrop(1);
          MainPanel.this.registeredItems.add(ifi);
          ISpellItem is = MainPanel.this.getSpellItemBeneath(ifi, event.getLocation());
          if (is != null)
          {
            ISpellItem n = is.getNext();
            if (n != null)
            {
              ISpellItem isi = ifi.getNext();
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
                ifi.setNext(n);
                n.setPrevious(ifi);
              }
            }
            is.setNext(ifi);
            ifi.setPrevious(is);
            ifi.setLocation(ifi.getPrevious().getX(), ifi.getPrevious().getY() + ifi.getPrevious().getHeight());
            int y = ifi.getHeight() + ifi.getY();
            ISpellItem isi = ifi.getNext();
            while (isi != null)
            {
              isi.setLocation(ifi.getX(), y);
              y += isi.getHeight();
              isi = isi.getNext();
            }
          }
          this.panel.add(ifi);
          return;
        }
        if (s.equals("[TRYITEM]"))
        {
          TryItem ifi = new TryItem(false, MainPanel.this.w);
          ifi.setLocation(event.getLocation());
          event.acceptDrop(1);
          MainPanel.this.registeredItems.add(ifi);
          ISpellItem is = MainPanel.this.getSpellItemBeneath(ifi, event.getLocation());
          if (is != null)
          {
            ISpellItem n = is.getNext();
            if (n != null)
            {
              ISpellItem isi = ifi.getNext();
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
                ifi.setNext(n);
                n.setPrevious(ifi);
              }
            }
            is.setNext(ifi);
            ifi.setPrevious(is);
            ifi.setLocation(ifi.getPrevious().getX(), ifi.getPrevious().getY() + ifi.getPrevious().getHeight());
            int y = ifi.getHeight() + ifi.getY();
            ISpellItem isi = ifi.getNext();
            while (isi != null)
            {
              isi.setLocation(ifi.getX(), y);
              y += isi.getHeight();
              isi = isi.getNext();
            }
          }
          this.panel.add(ifi);
          return;
        }
        if (s.equals("[FOREACHITEM]"))
        {
          ForeachItem ifi = new ForeachItem(false, MainPanel.this.w);
          ifi.setLocation(event.getLocation());
          event.acceptDrop(1);
          MainPanel.this.registeredItems.add(ifi);
          ISpellItem is = MainPanel.this.getSpellItemBeneath(ifi, event.getLocation());
          if (is != null)
          {
            ISpellItem n = is.getNext();
            if (n != null)
            {
              ISpellItem isi = ifi.getNext();
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
                ifi.setNext(n);
                n.setPrevious(ifi);
              }
            }
            is.setNext(ifi);
            ifi.setPrevious(is);
            ifi.setLocation(ifi.getPrevious().getX(), ifi.getPrevious().getY() + ifi.getPrevious().getHeight());
            int y = ifi.getHeight() + ifi.getY();
            ISpellItem isi = ifi.getNext();
            while (isi != null)
            {
              isi.setLocation(ifi.getX(), y);
              y += isi.getHeight();
              isi = isi.getNext();
            }
          }
          this.panel.add(ifi);
          return;
        }
        if (s.equals("[VariableOperationItem]"))
        {
          VariableOperationItem voi = new VariableOperationItem(false, MainPanel.this.w);
          voi.setLocation(event.getLocation());
          event.acceptDrop(1);
          MainPanel.this.registeredItems.add(voi);
          voi.setSize(voi.getPreferredSize());
          ISpellItem is = MainPanel.this.getSpellItemBeneath(voi, event.getLocation());
          if (is != null)
          {
            ISpellItem n = is.getNext();
            if (n != null)
            {
              ISpellItem isi = voi.getNext();
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
                voi.setNext(n);
                n.setPrevious(voi);
              }
            }
            is.setNext(voi);
            voi.setPrevious(is);
            voi.setLocation(voi.getPrevious().getX(), voi.getPrevious().getY() + voi.getPrevious().getHeight());
            int y = voi.getHeight() + voi.getY();
            ISpellItem isi = voi.getNext();
            while (isi != null)
            {
              isi.setLocation(voi.getX(), y);
              y += isi.getHeight();
              isi = isi.getNext();
            }
          }
          this.panel.add(voi);
          return;
        }
        if (!s.startsWith("[SPELLITEM]"))
        {
          event.rejectDrop();
          return;
        }
        s = s.replace("[SPELLITEM]", "");
        Command c = new Command(s);
        SpellItem p = new SpellItem(c.name, c.parameters, c.paramdesc, MainPanel.this.w);
        if (event.isDataFlavorSupported(SimpleDragObject.dataFlavor))
        {
          p.setLocation(event.getLocation());
          p.setSize(p.getPreferredSize());
          p.ti.setDescription(c.desc);
          event.acceptDrop(1);
          this.panel.add(p);
          MainPanel.this.registeredItems.add(p);
          ISpellItem is = MainPanel.this.getSpellItemBeneath(p, event.getLocation());
          if (is != null)
          {
            ISpellItem n = is.getNext();
            if (n != null)
            {
              ISpellItem isi = p.getNext();
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
                p.setNext(n);
                n.setPrevious(p);
              }
            }
            is.setNext(p);
            p.setPrevious(is);
            p.setLocation(p.getPrevious().getX(), p.getPrevious().getY() + p.getPrevious().getHeight());
            int y = p.getHeight() + p.getY();
            ISpellItem isi = p.getNext();
            while (isi != null)
            {
              isi.setLocation(p.getX(), y);
              y += isi.getHeight();
              isi = isi.getNext();
            }
          }
          event.dropComplete(true);
          return;
        }
        event.rejectDrop();
      }
      catch (Exception e)
      {
        e.printStackTrace();
        event.rejectDrop();
      }
    }
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\de\pylamo\spellmaker\gui\MainPanel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */