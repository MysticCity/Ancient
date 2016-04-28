package de.pylamo.spellmaker.gui.SpellItems.Variable;

import de.pylamo.spellmaker.gui.SimpleDragObject;
import de.pylamo.spellmaker.gui.SimpleDragObject.TransferableSimpleDragObject;
import de.pylamo.spellmaker.gui.SpellItems.ImageMover;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.IParameter;
import de.pylamo.spellmaker.gui.SpellItems.Parameter.Parameter;
import de.pylamo.spellmaker.gui.SpellItems.ToolTipItem;
import java.awt.Color;
import java.awt.Cursor;
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
import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class VariableOperatorPanel
  extends IParameter
{
  private static final long serialVersionUID = 1L;
  private final VariableOperator op;
  private String tooltip;
  
  public IParameter clone()
  {
    return new VariableOperatorPanel(this.op, this.tooltip, this.preview);
  }
  
  public VariableOperatorPanel(VariableOperator operator, String tooltip, boolean preview)
  {
    super(preview);
    this.op = operator;
    this.tooltip = tooltip;
    setLayout(new FlowLayout());
    ToolTipItem tti = new ToolTipItem();
    tti.setDescription(tooltip);
    JLabel l = new JLabel(" " + operator.getToken() + " ");
    add(l);
    add(tti);
    setBorder(BorderFactory.createLineBorder(Color.black));
    Parameter param = Parameter.Operator;
    setBackground(param.getColor());
    DragSource ds = new DragSource();
    VariableOperatorDragGestureListener pdgl = new VariableOperatorDragGestureListener(null);
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
  
  private class VariableOperatorDragGestureListener
    implements DragGestureListener, DragSourceMotionListener
  {
    private VariableOperatorDragGestureListener() {}
    
    public void dragGestureRecognized(DragGestureEvent dge)
    {
      Cursor cursor = null;
      VariableOperatorPanel pp = (VariableOperatorPanel)dge.getComponent();
      BufferedImage bi = new BufferedImage(VariableOperatorPanel.this.getWidth(), VariableOperatorPanel.this.getHeight(), 2);
      VariableOperatorPanel.this.paint(bi.getGraphics());
      ImageMover.start(bi, MouseInfo.getPointerInfo().getLocation());
      String s = "[VARIABLEOPERATOR]" + pp.op.getToken();
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


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\de\pylamo\spellmaker\gui\SpellItems\Variable\VariableOperatorPanel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */