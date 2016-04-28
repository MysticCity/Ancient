package de.pylamo.spellmaker.gui.SpellItems.Parameter;

import de.pylamo.spellmaker.gui.SimpleDragObject;
import de.pylamo.spellmaker.gui.SimpleDragObject.TransferableSimpleDragObject;
import de.pylamo.spellmaker.gui.SpellItems.ImageMover;
import de.pylamo.spellmaker.gui.SpellItems.ToolTipItem;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Window;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DragSourceMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ParameterPanel
  extends IParameter
{
  private static final long serialVersionUID = 1L;
  public final String name;
  public final Parameter p;
  private String desc;
  private final int subparams;
  final ToolTipItem tip;
  public final ArrayList<JTextField> fields = new ArrayList();
  
  public IParameter clone()
  {
    ParameterPanel pp = new ParameterPanel(this.name, this.p, this.subparams, this.preview);
    for (int i = 0; i < this.fields.size(); i++) {
      ((JTextField)pp.fields.get(i)).setText(((JTextField)this.fields.get(i)).getText());
    }
    return pp;
  }
  
  public ParameterPanel(String name, Parameter p, int subparams, boolean preview)
  {
    super(preview);
    this.p = p;
    this.subparams = subparams;
    
    this.name = name;
    this.tip = new ToolTipItem();
    
    setBorder(BorderFactory.createLineBorder(Color.black));
    JLabel l = new JLabel(name);
    l.setOpaque(false);
    add(l);
    
    setBackground(p.getColor());
    setLayout(new FlowLayout(3, 1, 1));
    for (int i = 0; i < subparams; i++)
    {
      JTextField field = new JTextField();
      field.setPreferredSize(new Dimension(25, 15));
      add(field);
      this.fields.add(field);
    }
    add(this.tip);
    if (!preview) {
      l.addMouseListener(this);
    }
    if (preview)
    {
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
  }
  
  public String getString()
  {
    String s = ", " + this.name;
    for (JTextField field : this.fields) {
      if ((field.getText() != null) && (!field.getText().equals(""))) {
        s = s + ":" + field.getText();
      }
    }
    return s;
  }
  
  public void setTooltip(String s)
  {
    this.desc = s;
    this.tip.setDescription(s);
  }
  
  private class ParameterDragGestureListener
    implements DragGestureListener, DragSourceMotionListener
  {
    private ParameterDragGestureListener() {}
    
    public void dragGestureRecognized(DragGestureEvent dge)
    {
      Cursor cursor = null;
      ParameterPanel pp = (ParameterPanel)dge.getComponent();
      BufferedImage bi = new BufferedImage(ParameterPanel.this.getWidth(), ParameterPanel.this.getHeight(), 2);
      ParameterPanel.this.paint(bi.getGraphics());
      ImageMover.start(bi, MouseInfo.getPointerInfo().getLocation());
      String s = "[PARAMETER]" + pp.name + " | " + pp.subparams + " | " + pp.p.name() + " | " + pp.desc;
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
