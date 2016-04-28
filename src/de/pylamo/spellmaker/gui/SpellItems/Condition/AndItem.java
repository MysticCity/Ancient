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
import javax.swing.JLabel;

public class AndItem
  extends IParameter
{
  private static final long serialVersionUID = 1L;
  public final ParameterSlot lefthand;
  public final ParameterSlot righthand;
  de.pylamo.spellmaker.gui.Window w;
  
  public IParameter clone()
  {
    AndItem ai = new AndItem(this.preview, this.w);
    if (this.lefthand.content != null)
    {
      ai.lefthand.add(this.lefthand.content.clone());
      ai.lefthand.content = this.lefthand.content.clone();
    }
    if (this.righthand.content != null)
    {
      ai.righthand.add(this.righthand.content.clone());
      ai.righthand.content = this.righthand.content.clone();
    }
    return ai;
  }
  
  public AndItem(boolean preview, de.pylamo.spellmaker.gui.Window w)
  {
    super(preview);
    this.w = w;
    FlowLayout fl = new FlowLayout(1, 1, 1);
    setLayout(fl);
    this.lefthand = new ParameterSlot(Parameter.Condition, "lefthand", preview, w);
    add(this.lefthand);
    JLabel la = new JLabel(" AND ");
    add(la);
    this.righthand = new ParameterSlot(Parameter.Condition, "righthand", preview, w);
    add(this.righthand);
    ToolTipItem tti = new ToolTipItem();
    tti.setToolTipText("<html>This is an if item, if the specified condition <br> is fulfilled, the following block will be executed</html>");
    add(tti);
    setBackground(new Color(108, 45, 199));
    setBorder(BorderFactory.createLineBorder(Color.black));
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
    if (this.lefthand.content != null) {
      left = this.lefthand.content.getString();
    }
    String right = "";
    if (this.righthand.content != null) {
      right = this.righthand.content.getString();
    }
    return left + " && " + right;
  }
  
  private class ParameterDragGestureListener
    implements DragGestureListener, DragSourceMotionListener
  {
    private ParameterDragGestureListener() {}
    
    public void dragGestureRecognized(DragGestureEvent dge)
    {
      Cursor cursor = null;
      BufferedImage bi = new BufferedImage(AndItem.this.getWidth(), AndItem.this.getHeight(), 2);
      AndItem.this.paint(bi.getGraphics());
      ImageMover.start(bi, MouseInfo.getPointerInfo().getLocation());
      String s = "[ANDITEM]";
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
