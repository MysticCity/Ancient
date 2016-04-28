package de.pylamo.spellmaker.gui.SpellItems.Variable;

import de.pylamo.spellmaker.gui.SimpleDragObject;
import de.pylamo.spellmaker.gui.SimpleDragObject.TransferableSimpleDragObject;
import de.pylamo.spellmaker.gui.SpellItems.ImageMover;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.IParameter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
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
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class VariablePanel
  extends IParameter
{
  private static final long serialVersionUID = 1L;
  de.pylamo.spellmaker.gui.Window w;
  private final String s;
  
  public IParameter clone()
  {
    return new VariablePanel(this.s, this.preview, this.w);
  }
  
  public VariablePanel(String variableName, boolean preview, de.pylamo.spellmaker.gui.Window w)
  {
    super(preview);
    this.w = w;
    if (preview) {
      w.spellItems.put(variableName, this);
    }
    this.s = variableName;
    JLabel jl = new JLabel(variableName);
    setLayout(new BorderLayout());
    add(jl, "Center");
    setBorder(BorderFactory.createLineBorder(Color.black));
    setBackground(Color.lightGray);
    DragSource ds = new DragSource();
    VariableDragGestureListener pdgl = new VariableDragGestureListener(null);
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
  
  public String getString()
  {
    return ", " + this.s;
  }
  
  private class VariableDragGestureListener
    implements DragGestureListener, DragSourceMotionListener
  {
    private VariableDragGestureListener() {}
    
    public void dragGestureRecognized(DragGestureEvent dge)
    {
      Cursor cursor = null;
      VariablePanel pp = (VariablePanel)dge.getComponent();
      BufferedImage bi = new BufferedImage(VariablePanel.this.getWidth(), VariablePanel.this.getHeight(), 2);
      VariablePanel.this.paint(bi.getGraphics());
      ImageMover.start(bi, MouseInfo.getPointerInfo().getLocation());
      String s = "[VARIABLE]" + pp.s;
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
