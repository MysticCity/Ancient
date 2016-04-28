package de.pylamo.spellmaker.gui.SpellItems.Parameter;

import de.pylamo.spellmaker.gui.Argument;
import de.pylamo.spellmaker.gui.SimpleDragObject;
import de.pylamo.spellmaker.gui.SpellItems.Arguments.ArgumentPanel;
import de.pylamo.spellmaker.gui.SpellItems.Commands.SpellItem;
import de.pylamo.spellmaker.gui.SpellItems.Condition.AndItem;
import de.pylamo.spellmaker.gui.SpellItems.Condition.CompareOperator;
import de.pylamo.spellmaker.gui.SpellItems.Condition.CompareOperatorPanel;
import de.pylamo.spellmaker.gui.SpellItems.Condition.ConditionComparePanel;
import de.pylamo.spellmaker.gui.SpellItems.Condition.ConditionStartPanel;
import de.pylamo.spellmaker.gui.SpellItems.Condition.OrItem;
import de.pylamo.spellmaker.gui.SpellItems.ISpellItem;
import de.pylamo.spellmaker.gui.SpellItems.Variable.VariableOperationItem;
import de.pylamo.spellmaker.gui.SpellItems.Variable.VariableOperator;
import de.pylamo.spellmaker.gui.SpellItems.Variable.VariableOperatorPanel;
import de.pylamo.spellmaker.gui.SpellItems.Variable.VariablePanel;
import de.pylamo.spellmaker.gui.Window;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetListener;
import java.util.HashSet;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ParameterSlot
  extends JPanel
  implements Cloneable
{
  private static final long serialVersionUID = 1L;
  private final Parameter param;
  private final String desc;
  public IParameter content;
  final JLabel l = new JLabel();
  private final Window w;
  
  public ParameterSlot(Parameter p, String s, boolean preview, Window w)
  {
    this.w = w;
    this.desc = s;
    if (!preview) {
      new ParameterSlotDropTargetListener(this);
    }
    this.param = p;
    this.l.setText(this.desc);
    setLayout(new BorderLayout());
    add(this.l, "Center");
    setBackground(p.getColor());
    setBorder(BorderFactory.createLineBorder(Color.black));
  }
  
  public void revalidate()
  {
    super.revalidate();
    if ((getParent() != null) && ((getParent() instanceof JComponent))) {
      ((JComponent)getParent()).revalidate();
    }
  }
  
  public Object clone()
  {
    return new ParameterSlot(this.param, this.desc, false, this.w);
  }
  
  public Component add(Component c)
  {
    if ((getParent() instanceof SpellItem))
    {
      ((ISpellItem)getParent()).recalculateSize();
      if ((getParent() != null) && ((getParent() instanceof JComponent))) {
        ((JComponent)getParent()).revalidate();
      }
    }
    else if ((getParent().getParent() instanceof VariableOperationItem))
    {
      ((ISpellItem)getParent().getParent()).recalculateSize();
      if ((getParent().getParent() != null) && ((getParent().getParent() instanceof JComponent))) {
        ((JComponent)getParent().getParent()).revalidate();
      }
      getParent().getParent().doLayout();
    }
    return super.add(c);
  }
  
  public void add(Component c, Object i)
  {
    super.add(c, i);
    if ((getParent() instanceof SpellItem))
    {
      ((ISpellItem)getParent()).recalculateSize();
      if ((getParent() != null) && ((getParent() instanceof JComponent))) {
        ((JComponent)getParent()).revalidate();
      }
    }
    if ((getParent() instanceof ConditionStartPanel))
    {
      if ((getParent() != null) && ((getParent() instanceof JComponent))) {
        ((JComponent)getParent()).revalidate();
      }
    }
    else if ((getParent() != null) && ((getParent().getParent() instanceof VariableOperationItem)))
    {
      ((ISpellItem)getParent().getParent()).recalculateSize();
      getParent().getParent().doLayout();
      if ((getParent() != null) && ((getParent() instanceof JComponent))) {
        ((JComponent)getParent()).revalidate();
      }
      getParent().doLayout();
      if ((getParent().getParent() != null) && ((getParent().getParent() instanceof JComponent))) {
        ((JComponent)getParent().getParent()).revalidate();
      }
    }
  }
  
  class ParameterSlotDropTargetListener
    extends DropTargetAdapter
    implements DropTargetListener
  {
    private DropTarget dropTarget;
    private final JPanel panel;
    
    public ParameterSlotDropTargetListener(JPanel panel)
    {
      this.panel = panel;
      this.dropTarget = new DropTarget(panel, 1, this, true, null);
    }
    
    public void drop(DropTargetDropEvent event)
    {
      try
      {
        if (!event.isDataFlavorSupported(SimpleDragObject.dataFlavor)) {
          return;
        }
        Transferable tr = event.getTransferable();
        String s = ((SimpleDragObject)tr.getTransferData(SimpleDragObject.dataFlavor)).s;
        if (s.startsWith("[COMPAREOPERATOR]"))
        {
          if (ParameterSlot.this.param != Parameter.CompareOperator)
          {
            event.rejectDrop();
            JOptionPane.showMessageDialog(ParameterSlot.this.w, "The parametertype does not match", "Error", 0);
            return;
          }
          if (ParameterSlot.this.content != null) {
            ParameterSlot.this.remove(ParameterSlot.this.content);
          }
          s = s.replace("[COMPAREOPERATOR]", "");
          ParameterSlot.this.content = new CompareOperatorPanel(CompareOperator.getOperatorByToken(s), false);
          this.panel.remove(ParameterSlot.this.l);
          ParameterSlot.this.add(ParameterSlot.this.content, "Center");
          event.dropComplete(true);
          ParameterSlot.this.content.revalidate();
          if ((ParameterSlot.this.getParent() != null) && ((ParameterSlot.this.getParent() instanceof JComponent))) {
            ((JComponent)ParameterSlot.this.getParent()).revalidate();
          }
          return;
        }
        if (s.startsWith("[VARIABLEOPERATOR]"))
        {
          if (ParameterSlot.this.param != Parameter.Operator)
          {
            event.rejectDrop();
            JOptionPane.showMessageDialog(ParameterSlot.this.w, "The parametertype does not match", "Error", 0);
            return;
          }
          if (ParameterSlot.this.content != null) {
            ParameterSlot.this.remove(ParameterSlot.this.content);
          }
          s = s.replace("[VARIABLEOPERATOR]", "");
          ParameterSlot.this.content = new VariableOperatorPanel(VariableOperator.getOperatorByToken(s), VariableOperator.getOperatorByToken(s).getTooltip(), false);
          this.panel.remove(ParameterSlot.this.l);
          ParameterSlot.this.add(ParameterSlot.this.content, "Center");
          event.dropComplete(true);
          ParameterSlot.this.content.revalidate();
          if ((ParameterSlot.this.getParent() != null) && ((ParameterSlot.this.getParent() instanceof JComponent))) {
            ((JComponent)ParameterSlot.this.getParent()).revalidate();
          }
          return;
        }
        if (s.startsWith("[CONDITION]"))
        {
          if (ParameterSlot.this.param != Parameter.Condition)
          {
            event.rejectDrop();
            JOptionPane.showMessageDialog(ParameterSlot.this.w, "The parametertype does not match", "Error", 0);
            return;
          }
          if (ParameterSlot.this.content != null) {
            ParameterSlot.this.remove(ParameterSlot.this.content);
          }
          ParameterSlot.this.content = new ConditionComparePanel(false, ParameterSlot.this.w);
          this.panel.remove(ParameterSlot.this.l);
          ParameterSlot.this.add(ParameterSlot.this.content, "Center");
          event.dropComplete(true);
          ParameterSlot.this.content.revalidate();
          if ((ParameterSlot.this.getParent() != null) && ((ParameterSlot.this.getParent() instanceof JComponent))) {
            ((JComponent)ParameterSlot.this.getParent()).revalidate();
          }
          return;
        }
        if (s.startsWith("[ANDITEM]"))
        {
          if (ParameterSlot.this.param != Parameter.Condition)
          {
            event.rejectDrop();
            JOptionPane.showMessageDialog(ParameterSlot.this.w, "The parametertype does not match", "Error", 0);
            return;
          }
          if (ParameterSlot.this.content != null) {
            ParameterSlot.this.remove(ParameterSlot.this.content);
          }
          ParameterSlot.this.content = new AndItem(false, ParameterSlot.this.w);
          this.panel.remove(ParameterSlot.this.l);
          ParameterSlot.this.add(ParameterSlot.this.content, "Center");
          event.dropComplete(true);
          ParameterSlot.this.content.revalidate();
          if ((ParameterSlot.this.getParent() != null) && ((ParameterSlot.this.getParent() instanceof JComponent))) {
            ((JComponent)ParameterSlot.this.getParent()).revalidate();
          }
          return;
        }
        if (s.startsWith("[ORITEM]"))
        {
          if (ParameterSlot.this.param != Parameter.Condition)
          {
            event.rejectDrop();
            JOptionPane.showMessageDialog(ParameterSlot.this.w, "The parametertype does not match", "Error", 0);
            return;
          }
          if (ParameterSlot.this.content != null) {
            ParameterSlot.this.remove(ParameterSlot.this.content);
          }
          ParameterSlot.this.content = new OrItem(false, ParameterSlot.this.w);
          this.panel.remove(ParameterSlot.this.l);
          ParameterSlot.this.add(ParameterSlot.this.content, "Center");
          event.dropComplete(true);
          ParameterSlot.this.content.revalidate();
          if ((ParameterSlot.this.getParent() != null) && ((ParameterSlot.this.getParent() instanceof JComponent))) {
            ((JComponent)ParameterSlot.this.getParent()).revalidate();
          }
          return;
        }
        if (s.startsWith("[VARIABLE]"))
        {
          if (!ParameterSlot.this.param.getPossibleTypes().contains(Parameter.Variable))
          {
            event.rejectDrop();
            JOptionPane.showMessageDialog(ParameterSlot.this.w, "The parametertype does not match", "Error", 0);
            return;
          }
          s = s.replace("[VARIABLE]", "");
          if (ParameterSlot.this.content != null) {
            ParameterSlot.this.remove(ParameterSlot.this.content);
          }
          ParameterSlot.this.content = new VariablePanel(s.replace("[VARIABLE]", ""), false, ParameterSlot.this.w);
          this.panel.remove(ParameterSlot.this.l);
          ParameterSlot.this.add(ParameterSlot.this.content, "Center");
          event.dropComplete(true);
          ParameterSlot.this.content.revalidate();
          if ((ParameterSlot.this.getParent() != null) && ((ParameterSlot.this.getParent() instanceof JComponent))) {
            ((JComponent)ParameterSlot.this.getParent()).revalidate();
          }
          return;
        }
        if (s.startsWith("[ARGUMENT]"))
        {
          s = s.replace("[ARGUMENT]", "");
          Argument a = new Argument(s);
          ArgumentPanel p = new ArgumentPanel(a.name, a.param, a.desc, a.parameters, a.paramdesc, ParameterSlot.this.w);
          if (!ParameterSlot.this.param.getPossibleTypes().contains(p.p))
          {
            event.rejectDrop();
            JOptionPane.showMessageDialog(ParameterSlot.this.w, "The parametertype does not match", "Error", 0);
            return;
          }
          if (ParameterSlot.this.content != null) {
            ParameterSlot.this.remove(ParameterSlot.this.content);
          }
          ParameterSlot.this.remove(ParameterSlot.this.l);
          ParameterSlot.this.content = p;
          ParameterSlot.this.add(ParameterSlot.this.content, "Center");
          event.dropComplete(true);
          ParameterSlot.this.content.revalidate();
          if ((ParameterSlot.this.getParent() != null) && ((ParameterSlot.this.getParent() instanceof JComponent))) {
            ((JComponent)ParameterSlot.this.getParent()).revalidate();
          }
          return;
        }
        if (s.startsWith("[PARAMETER]"))
        {
          s = s.replace("[PARAMETER]", "");
          String[] args = s.split(Pattern.quote("|"));
          if (args.length == 4)
          {
            args[0] = args[0].trim();
            if (args[0].equalsIgnoreCase("Number:"))
            {
              if (!ParameterSlot.this.param.getPossibleTypes().contains(Parameter.Number))
              {
                event.rejectDrop();
                JOptionPane.showMessageDialog(ParameterSlot.this.w, "The parametertype does not match", "Error", 0);
                return;
              }
              ParameterSlot.this.content = new NumberParameterPanel(false);
              ParameterSlot.this.content.setLocation(0, 0);
              event.acceptDrop(1);
              ParameterSlot.this.add(ParameterSlot.this.content, "Center");
              ParameterSlot.this.content.revalidate();
              if ((this.panel.getParent() instanceof ISpellItem)) {
                ((ISpellItem)this.panel.getParent()).recalculateSize();
              }
              this.panel.remove(ParameterSlot.this.l);
              ParameterSlot.this.content.doLayout();
              ParameterSlot.this.content.revalidate();
              if ((ParameterSlot.this.getParent() != null) && ((ParameterSlot.this.getParent() instanceof JComponent))) {
                ((JComponent)ParameterSlot.this.getParent()).revalidate();
              }
              event.dropComplete(true);
              return;
            }
            if (args[0].equalsIgnoreCase("Boolean:"))
            {
              if (!ParameterSlot.this.param.getPossibleTypes().contains(Parameter.Boolean))
              {
                event.rejectDrop();
                JOptionPane.showMessageDialog(ParameterSlot.this.w, "The parametertype does not match", "Error", 0);
                return;
              }
              ParameterSlot.this.content = new BooleanParameterPanel(false);
              ParameterSlot.this.content.setLocation(0, 0);
              event.acceptDrop(1);
              ParameterSlot.this.add(ParameterSlot.this.content, "Center");
              ParameterSlot.this.content.revalidate();
              if ((this.panel.getParent() instanceof ISpellItem)) {
                ((ISpellItem)this.panel.getParent()).recalculateSize();
              }
              this.panel.remove(ParameterSlot.this.l);
              ParameterSlot.this.content.doLayout();
              ParameterSlot.this.content.revalidate();
              if ((ParameterSlot.this.getParent() != null) && ((ParameterSlot.this.getParent() instanceof JComponent))) {
                ((JComponent)ParameterSlot.this.getParent()).revalidate();
              }
              event.dropComplete(true);
              return;
            }
            if (args[0].equalsIgnoreCase("String:"))
            {
              if (!ParameterSlot.this.param.getPossibleTypes().contains(Parameter.String))
              {
                event.rejectDrop();
                JOptionPane.showMessageDialog(ParameterSlot.this.w, "The parametertype does not match", "Error", 0);
                return;
              }
              ParameterSlot.this.content = new StringParameterPanel(false);
              ParameterSlot.this.content.setLocation(0, 0);
              event.acceptDrop(1);
              this.panel.remove(ParameterSlot.this.l);
              ParameterSlot.this.add(ParameterSlot.this.content, "Center");
              ParameterSlot.this.content.revalidate();
              if ((this.panel.getParent() instanceof ISpellItem)) {
                ((ISpellItem)this.panel.getParent()).recalculateSize();
              }
              event.dropComplete(true);
              ParameterSlot.this.content.revalidate();
              if ((ParameterSlot.this.getParent() != null) && ((ParameterSlot.this.getParent() instanceof JComponent))) {
                ((JComponent)ParameterSlot.this.getParent()).revalidate();
              }
              return;
            }
            if (ParameterSlot.this.content != null) {
              ParameterSlot.this.remove(ParameterSlot.this.content);
            }
            args[1] = args[1].trim();
            args[2] = args[2].trim();
            args[3] = args[3].trim();
            ParameterPanel p = new ParameterPanel(args[0], Parameter.getParameterByName(args[2]), Integer.parseInt(args[1]), false);
            p.setTooltip(args[3]);
            p.setBorder(null);
            ParameterSlot.this.content = p;
            if (p.p.getPossibleTypes().contains(ParameterSlot.this.param))
            {
              p.setLocation(0, 0);
              event.acceptDrop(1);
              this.panel.remove(ParameterSlot.this.l);
              this.panel.add(p, "Center");
              if ((this.panel.getParent() instanceof SpellItem)) {
                ((SpellItem)this.panel.getParent()).recalculateSize();
              }
              p.revalidate();
              ParameterSlot.this.content.revalidate();
              if ((ParameterSlot.this.getParent() != null) && ((ParameterSlot.this.getParent() instanceof JComponent))) {
                ((JComponent)ParameterSlot.this.getParent()).revalidate();
              }
              event.dropComplete(true);
              return;
            }
            JOptionPane.showMessageDialog(ParameterSlot.this.w, "The parametertype does not match", "Error", 0);
          }
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


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\de\pylamo\spellmaker\gui\SpellItems\Parameter\ParameterSlot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */