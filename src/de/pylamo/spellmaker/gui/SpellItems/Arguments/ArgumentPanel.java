package de.pylamo.spellmaker.gui.SpellItems.Arguments;

import de.pylamo.spellmaker.gui.SimpleDragObject;
import de.pylamo.spellmaker.gui.SimpleDragObject.TransferableSimpleDragObject;
import de.pylamo.spellmaker.gui.SpellItems.ImageMover;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.IParameter;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.Parameter;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.ParameterSlot;
import de.pylamo.spellmaker.gui.SpellItems.TopInformation;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
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
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;

public class ArgumentPanel
  extends IParameter
{
  private static final long serialVersionUID = 1L;
  public final String name;
  public final Parameter p;
  private final TopInformation ti;
  public final ParameterSlot[] parameters;
  Parameter[] params;
  String[] paramdesc;
  private String s;
  String description;
  de.pylamo.spellmaker.gui.Window w;
  
  public IParameter clone()
  {
    ArgumentPanel is = null;
    if (this.preview) {
      is = new ArgumentPanel(this.name, this.p, this.description, this.params, this.paramdesc, this.s, this.w);
    } else {
      is = new ArgumentPanel(this.name, this.p, this.description, this.params, this.paramdesc, this.w);
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
  
  public ArgumentPanel(String name, Parameter p, String desc, Parameter[] params, String[] paramdesc, String s, de.pylamo.spellmaker.gui.Window w)
  {
    super(true);
    this.s = s;
    this.w = w;
    this.p = p;
    this.params = params;
    this.paramdesc = paramdesc;
    this.name = name;
    this.ti = new TopInformation(name);
    this.description = desc;
    this.ti.setDescription(desc);
    setBorder(BorderFactory.createLineBorder(Color.black));
    
    setBackground(p.getColor());
    FlowLayout fl = new FlowLayout(1, 1, 1);
    setLayout(fl);
    add(this.ti);
    this.parameters = new ParameterSlot[params.length];
    for (int i = 0; i < params.length; i++)
    {
      this.parameters[i] = new ParameterSlot(params[i], paramdesc[i], true, w);
      add(this.parameters[i]);
    }
    DragSource ds = new DragSource();
    ParameterDragGestureListener pdgl = new ParameterDragGestureListener(null);
    ds.createDefaultDragGestureRecognizer(this, 1, pdgl);
    ds.addDragSourceMotionListener(pdgl);
    ds.addDragSourceListener(new DragSourceListener()
    {
      public void dropActionChanged(DragSourceDragEvent dsde) {}
      
      public void dragOver(DragSourceDragEvent dsde) {}
      
      public void dragExit(DragSourceEvent dse) {}
      
      public void dragEnter(DragSourceDragEvent dsde) {}
      
      public void dragDropEnd(DragSourceDropEvent dsde) {}
    });
  }
  
  public ArgumentPanel(String name, Parameter p, String desc, Parameter[] params, String[] paramdesc, de.pylamo.spellmaker.gui.Window w)
  {
    super(false);
    setOpaque(true);
    setBackground(p.getColor());
    this.params = params;
    this.paramdesc = paramdesc;
    this.description = desc;
    this.w = w;
    setBorder(BorderFactory.createLineBorder(Color.black));
    FlowLayout fl = new FlowLayout(1, 1, 1);
    setLayout(fl);
    this.name = name;
    this.parameters = new ParameterSlot[params.length];
    this.ti = new TopInformation(name);
    this.p = p;
    this.ti.setDescription(desc);
    this.ti.l.addMouseListener(this);
    add(this.ti);
    for (int i = 0; i < params.length; i++)
    {
      this.parameters[i] = new ParameterSlot(params[i], paramdesc[i], false, w);
      add(this.parameters[i]);
    }
  }
  
  public String getString()
  {
    String s = ", (" + this.name;
    for (ParameterSlot param : this.parameters) {
      if (param.content != null) {
        s = s + param.content.getString();
      }
    }
    s = s + ")";
    return s;
  }
  
  public void revalidate()
  {
    setSize(getPreferredSize());
    if ((getParent() != null) && ((getParent() instanceof JComponent))) {
      ((JComponent)getParent()).revalidate();
    }
  }
  
  private class ParameterDragGestureListener
    implements DragGestureListener, DragSourceMotionListener
  {
    private ParameterDragGestureListener() {}
    
    public void dragGestureRecognized(DragGestureEvent dge)
    {
      Cursor cursor = null;
      ArgumentPanel pp = (ArgumentPanel)dge.getComponent();
      BufferedImage bi = new BufferedImage(ArgumentPanel.this.getWidth(), ArgumentPanel.this.getHeight(), 2);
      ArgumentPanel.this.paint(bi.getGraphics());
      ImageMover.start(bi, MouseInfo.getPointerInfo().getLocation());
      String s = "[ARGUMENT]" + pp.s;
      if (dge.getDragAction() == 1) {
        cursor = DragSource.DefaultCopyDrop;
      }
      dge.startDrag(cursor, new SimpleDragObject.TransferableSimpleDragObject(new SimpleDragObject(s)));
    }
    
    public void dragMouseMoved(DragSourceDragEvent dsde)
    {
      ImageMover.w.setLocation(new Point(dsde.getLocation().x + 2, dsde.getLocation().y + 4));
    }
  }
}
