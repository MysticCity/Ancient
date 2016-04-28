package de.pylamo.spellmaker.gui.SpellItems.Variable;

import de.pylamo.spellmaker.gui.SimpleDragObject;
import de.pylamo.spellmaker.gui.SimpleDragObject.TransferableSimpleDragObject;
import de.pylamo.spellmaker.gui.SpellItems.ISpellItem;
import de.pylamo.spellmaker.gui.SpellItems.ImageMover;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.IParameter;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.Parameter;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.ParameterSlot;
import de.pylamo.spellmaker.gui.SpellItems.TopInformation;
import java.awt.Color;
import java.awt.Container;
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
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class VariableOperationItem
  extends ISpellItem
{
  private static final long serialVersionUID = 1L;
  public final ArrayList<ParameterSlot> slots = new ArrayList();
  
  public ISpellItem clone()
  {
    VariableOperationItem voi = new VariableOperationItem(this.b, this.w);
    for (int i = 0; i < this.slots.size(); i++) {
      if (((ParameterSlot)this.slots.get(i)).content != null)
      {
        ((ParameterSlot)voi.slots.get(i)).add(((ParameterSlot)this.slots.get(i)).content.clone());
        ((ParameterSlot)voi.slots.get(i)).content = ((ParameterSlot)this.slots.get(i)).content.clone();
      }
    }
    return voi;
  }
  
  public VariableOperationItem(boolean preview, de.pylamo.spellmaker.gui.Window w)
  {
    super(w);
    ParameterSlot p1 = new ParameterSlot(Parameter.Variable, "Variable", preview, w);
    ParameterSlot p2 = new ParameterSlot(Parameter.Operator, "Op", preview, w);
    ParameterSlot p3 = new ParameterSlot(Parameter.All, "Value", preview, w);
    this.slots.add(p1);
    this.slots.add(p2);
    this.slots.add(p3);
    this.b = (!preview);
    JPanel j = new JPanel()
    {
      private static final long serialVersionUID = 1L;
      
      public void revalidate()
      {
        super.revalidate();
        if (getParent() != null)
        {
          getParent().repaint();
          if ((getParent() != null) && ((getParent() instanceof JComponent))) {
            ((JComponent)getParent()).revalidate();
          }
        }
      }
    };
    j.add(p1);
    j.add(p2);
    j.setBackground(Color.white);
    j.add(p3);
    j.setLayout(new FlowLayout());
    setBorder(BorderFactory.createLineBorder(Color.black));
    setLayout(new BoxLayout(this, 1));
    TopInformation ti = new TopInformation("VariableOperation");
    ti.setDescription("A variable operation");
    add(ti);
    add(j);
    setBackground(Color.WHITE);
    if (preview)
    {
      DragSource ds = new DragSource();
      ItemDragGestureListener sis = new ItemDragGestureListener(null);
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
    else
    {
      addMouseListener(this);
      addMouseMotionListener(this);
    }
  }
  
  public String getItem()
  {
    String left = "";
    if (((ParameterSlot)this.slots.get(0)).content != null) {
      left = ((ParameterSlot)this.slots.get(0)).content.getString();
    }
    if (left.trim().startsWith(",")) {
      left = left.substring(left.indexOf(',') + 1);
    }
    String right = "";
    if (((ParameterSlot)this.slots.get(2)).content != null) {
      right = ((ParameterSlot)this.slots.get(2)).content.getString();
    }
    if (right.trim().startsWith(",")) {
      right = right.substring(right.indexOf(',') + 1);
    }
    String middle = "";
    if (((ParameterSlot)this.slots.get(1)).content != null) {
      middle = ((ParameterSlot)this.slots.get(1)).content.getString();
    }
    return "var " + left.trim() + " " + middle.trim() + " " + right.trim();
  }
  
  private class ItemDragGestureListener
    implements DragGestureListener, DragSourceMotionListener
  {
    private ItemDragGestureListener() {}
    
    public void dragGestureRecognized(DragGestureEvent dge)
    {
      String s = "[VariableOperationItem]";
      SimpleDragObject sdo = new SimpleDragObject(s);
      BufferedImage bi = new BufferedImage(VariableOperationItem.this.getWidth(), VariableOperationItem.this.getHeight(), 2);
      VariableOperationItem.this.paint(bi.getGraphics());
      Cursor cursor = null;
      ImageMover.start(bi, MouseInfo.getPointerInfo().getLocation());
      if (dge.getDragAction() == 1) {
        cursor = DragSource.DefaultCopyDrop;
      }
      VariableOperationItem.this.revalidate();
      dge.startDrag(cursor, new SimpleDragObject.TransferableSimpleDragObject(sdo));
    }
    
    public void dragMouseMoved(DragSourceDragEvent dsde)
    {
      ImageMover.w.setLocation(new Point(dsde.getLocation().x + 2, dsde.getLocation().y + 4));
    }
  }
}
