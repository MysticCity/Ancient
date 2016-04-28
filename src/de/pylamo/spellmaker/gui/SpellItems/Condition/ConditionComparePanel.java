package de.pylamo.spellmaker.gui.SpellItems.Condition;

import de.pylamo.spellmaker.gui.SimpleDragObject;
import de.pylamo.spellmaker.gui.SimpleDragObject.TransferableSimpleDragObject;
import de.pylamo.spellmaker.gui.SpellItems.ImageMover;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.IParameter;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.Parameter;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.ParameterSlot;
import de.pylamo.spellmaker.gui.SpellItems.ToolTipItem;
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

public class ConditionComparePanel
  extends IParameter
{
  private static final long serialVersionUID = 1L;
  public final ParameterSlot lefthand;
  public final ParameterSlot operator;
  public final ParameterSlot righthand;
  de.pylamo.spellmaker.gui.Window w;
  
  public IParameter clone()
  {
    ConditionComparePanel ccp = new ConditionComparePanel(this.preview, this.w);
    if (this.lefthand.content != null)
    {
      ccp.lefthand.add(this.lefthand.content.clone());
      ccp.lefthand.content = this.lefthand.content.clone();
    }
    if (this.operator.content != null)
    {
      ccp.operator.add(this.operator.content.clone());
      ccp.operator.content = this.operator.content.clone();
    }
    if (this.righthand.content != null)
    {
      ccp.righthand.add(this.righthand.content.clone());
      ccp.righthand.content = this.righthand.content.clone();
    }
    return ccp;
  }
  
  public ConditionComparePanel(boolean preview, de.pylamo.spellmaker.gui.Window w)
  {
    super(preview);
    FlowLayout fl = new FlowLayout(1, 1, 1);
    setLayout(fl);
    this.w = w;
    this.lefthand = new ParameterSlot(Parameter.CompareItem, "lefthand", preview, w);
    add(this.lefthand);
    this.operator = new ParameterSlot(Parameter.CompareOperator, "op", preview, w);
    add(this.operator);
    this.righthand = new ParameterSlot(Parameter.CompareItem, "righthand", preview, w);
    add(this.righthand);
    ToolTipItem tti = new ToolTipItem();
    tti.setToolTipText("<html>This is a conditioncompareitem</html>");
    add(tti);
    setBackground(new Color(108, 45, 199));
    setBorder(BorderFactory.createLineBorder(Color.black));
    if (preview)
    {
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
  }
  
  public void revalidate()
  {
    super.revalidate();
    if ((getParent() != null) && ((getParent() instanceof JComponent))) {
      ((JComponent)getParent()).revalidate();
    }
  }
  
  public String getString()
  {
    String left = "";
    if (this.lefthand.content != null)
    {
      left = this.lefthand.content.getString();
      left = left.substring(left.indexOf(',') + 1).trim();
    }
    String middle = "";
    if (this.operator.content != null) {
      middle = this.operator.content.getString();
    }
    String right = "";
    if (this.righthand.content != null)
    {
      right = this.righthand.content.getString();
      right = right.substring(right.indexOf(',') + 1).trim();
    }
    return left + " " + middle + " " + right;
  }
  
  private class CompareOperatorDragGestureListener
    implements DragGestureListener, DragSourceMotionListener
  {
    private CompareOperatorDragGestureListener() {}
    
    public void dragGestureRecognized(DragGestureEvent dge)
    {
      Cursor cursor = null;
      BufferedImage bi = new BufferedImage(ConditionComparePanel.this.getWidth(), ConditionComparePanel.this.getHeight(), 2);
      ConditionComparePanel.this.paint(bi.getGraphics());
      ImageMover.start(bi, MouseInfo.getPointerInfo().getLocation());
      String s = "[CONDITION]";
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
