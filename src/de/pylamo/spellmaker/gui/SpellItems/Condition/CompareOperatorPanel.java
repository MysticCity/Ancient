package de.pylamo.spellmaker.gui.SpellItems.Condition;

import de.pylamo.spellmaker.gui.SimpleDragObject;
import de.pylamo.spellmaker.gui.SimpleDragObject.TransferableSimpleDragObject;
import de.pylamo.spellmaker.gui.SpellItems.ImageMover;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.IParameter;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.Parameter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
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
import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class CompareOperatorPanel
  extends IParameter
{
  private static final long serialVersionUID = 1L;
  private final CompareOperator op;
  
  public IParameter clone()
  {
    return new CompareOperatorPanel(this.op, this.preview);
  }
  
  public CompareOperatorPanel(CompareOperator operator, boolean preview)
  {
    super(preview);
    this.op = operator;
    JLabel l = new JLabel(" " + operator.getToken() + " ");
    setLayout(new BorderLayout());
    add(l);
    setBorder(BorderFactory.createLineBorder(Color.black));
    Parameter param = Parameter.CompareOperator;
    setBackground(param.getColor());
    DragSource ds = new DragSource();
    CompareOperatorDragGestureListener pdgl = new CompareOperatorDragGestureListener(null);
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
    return " " + this.op.getToken() + " ";
  }
  
  private class CompareOperatorDragGestureListener
    implements DragGestureListener, DragSourceMotionListener
  {
    private CompareOperatorDragGestureListener() {}
    
    public void dragGestureRecognized(DragGestureEvent dge)
    {
      Cursor cursor = null;
      CompareOperatorPanel pp = (CompareOperatorPanel)dge.getComponent();
      BufferedImage bi = new BufferedImage(CompareOperatorPanel.this.getWidth(), CompareOperatorPanel.this.getHeight(), 2);
      CompareOperatorPanel.this.paint(bi.getGraphics());
      ImageMover.start(bi, MouseInfo.getPointerInfo().getLocation());
      String s = "[COMPAREOPERATOR]" + pp.op.getToken();
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
