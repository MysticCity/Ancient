package de.pylamo.spellmaker.gui.SpellItems.Commands;

import de.pylamo.spellmaker.gui.MainPanel;
import de.pylamo.spellmaker.gui.SimpleDragObject;
import de.pylamo.spellmaker.gui.SimpleDragObject.TransferableSimpleDragObject;
import de.pylamo.spellmaker.gui.SpellItems.ISpellItem;
import de.pylamo.spellmaker.gui.SpellItems.ImageMover;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.IParameter;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.Parameter;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.ParameterSlot;
import de.pylamo.spellmaker.gui.SpellItems.TopInformation;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DragSourceMotionListener;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;

public class SpellItem
  extends ISpellItem
{
  private static final long serialVersionUID = 1L;
  public final String name;
  public final ParameterSlot[] parameters;
  public final TopInformation ti;
  private static final Color bgc = Color.WHITE;
  private String s;
  boolean preview = false;
  Parameter[] params;
  String[] paramdesc;
  
  public ISpellItem clone()
  {
    SpellItem is;
    SpellItem is;
    if (this.preview) {
      is = new SpellItem(this.name, this.params, this.paramdesc, this.s, this.w);
    } else {
      is = new SpellItem(this.name, this.params, this.paramdesc, this.w);
    }
    for (int i = 0; i < this.parameters.length; i++) {
      if (this.parameters[i].content != null)
      {
        is.parameters[i].add(this.parameters[i].content.clone());
        is.parameters[i].content = this.parameters[i].content.clone();
      }
    }
    return is;
  }
  
  public SpellItem(String name, Parameter[] params, String[] paramdesc, String s, de.pylamo.spellmaker.gui.Window w)
  {
    super(w);
    this.s = s;
    this.preview = true;
    this.params = params;
    this.paramdesc = paramdesc;
    setLayout(new BoxLayout(this, 1));
    setOpaque(true);
    setBorder(BorderFactory.createLineBorder(Color.black));
    addMouseListener(this);
    addMouseMotionListener(this);
    setBackground(bgc);
    this.name = name;
    this.ti = new TopInformation(name);
    add(this.ti);
    this.parameters = new ParameterSlot[params.length];
    for (int i = 0; i < params.length; i++)
    {
      this.parameters[i] = new ParameterSlot(params[i], paramdesc[i], true, w);
      add(this.parameters[i]);
    }
    DragSource ds = new DragSource();
    SpellItemDragGestureListener sis = new SpellItemDragGestureListener(null);
    ds.createDefaultDragGestureRecognizer(this, 1, sis);
    ds.addDragSourceMotionListener(sis);
    ds.addDragSourceListener(new DragSourceListener()
    {
      public void dropActionChanged(DragSourceDragEvent dsde) {}
      
      public void dragOver(DragSourceDragEvent dsde) {}
      
      public void dragExit(DragSourceEvent dse) {}
      
      public void dragEnter(DragSourceDragEvent dsde) {}
      
      public void dragDropEnd(DragSourceDropEvent dsde) {}
    });
  }
  
  public SpellItem(String name, Parameter[] params, String[] paramdesc, de.pylamo.spellmaker.gui.Window w)
  {
    super(w);
    w.mp.registeredItems.add(this);
    setOpaque(true);
    setBackground(bgc);
    this.params = params;
    this.paramdesc = paramdesc;
    setBorder(BorderFactory.createLineBorder(Color.black));
    setLayout(new BoxLayout(this, 1));
    addMouseListener(this);
    this.name = name;
    this.parameters = new ParameterSlot[params.length];
    this.ti = new TopInformation(name);
    add(this.ti);
    addMouseMotionListener(this);
    this.b = true;
    for (int i = 0; i < params.length; i++)
    {
      this.parameters[i] = new ParameterSlot(params[i], paramdesc[i], false, w);
      add(this.parameters[i]);
    }
  }
  
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    g.setColor(Color.black);
  }
  
  public String getItem()
  {
    String s = this.name;
    for (ParameterSlot ps : this.parameters) {
      if (ps.content != null) {
        s = s + ps.content.getString();
      }
    }
    return s;
  }
  
  private class SpellItemDragGestureListener
    implements DragGestureListener, DragSourceMotionListener
  {
    private SpellItemDragGestureListener() {}
    
    public void dragGestureRecognized(DragGestureEvent dge)
    {
      String s = "[SPELLITEM]" + SpellItem.this.s;
      SimpleDragObject sdo = new SimpleDragObject(s);
      BufferedImage bi = new BufferedImage(SpellItem.this.getWidth(), SpellItem.this.getHeight(), 2);
      SpellItem.this.paint(bi.getGraphics());
      Cursor cursor = null;
      ImageMover.start(bi, MouseInfo.getPointerInfo().getLocation());
      if (dge.getDragAction() == 1) {
        cursor = DragSource.DefaultCopyDrop;
      }
      dge.startDrag(cursor, new SimpleDragObject.TransferableSimpleDragObject(sdo));
    }
    
    public void dragMouseMoved(DragSourceDragEvent dsde)
    {
      ImageMover.w.setLocation(new Point(dsde.getLocation().x + 2, dsde.getLocation().y + 4));
    }
  }
}
