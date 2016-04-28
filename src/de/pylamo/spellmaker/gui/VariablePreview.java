package de.pylamo.spellmaker.gui;

import de.pylamo.spellmaker.Menu;
import de.pylamo.spellmaker.gui.SpellItems.Variable.VariableOperationItem;
import de.pylamo.spellmaker.gui.SpellItems.Variable.VariableOperator;
import de.pylamo.spellmaker.gui.SpellItems.Variable.VariableOperatorPanel;
import de.pylamo.spellmaker.gui.SpellItems.Variable.VariablePanel;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VariablePreview
  extends JPanel
{
  private static final long serialVersionUID = 1L;
  public final HashSet<String> vars = new HashSet();
  private final Window w;
  
  public VariablePreview(Window w)
  {
    this.w = w;
    setLayout(new WrapLayout());
    add(new VariableOperationItem(true, w));
    add(new AddVariablePanel());
    add(new VariableOperatorPanel(VariableOperator.Assign, VariableOperator.Assign.getTooltip(), true));
    add(new VariableOperatorPanel(VariableOperator.PlusAssign, VariableOperator.PlusAssign.getTooltip(), true));
    add(new VariableOperatorPanel(VariableOperator.MinusAssign, VariableOperator.MinusAssign.getTooltip(), true));
    add(new VariableOperatorPanel(VariableOperator.DividedAssign, VariableOperator.DividedAssign.getTooltip(), true));
    add(new VariableOperatorPanel(VariableOperator.MultiplyAssign, VariableOperator.MultiplyAssign.getTooltip(), true));
    for (String s : w.m.variables)
    {
      add(new VariablePanel(s, true, w));
      this.vars.add(s);
    }
  }
  
  class AddVariablePanel
    extends JPanel
  {
    private static final long serialVersionUID = 1L;
    
    public AddVariablePanel()
    {
      final JTextField tf = new JTextField();
      tf.setText("variablename");
      setLayout(new FlowLayout());
      tf.setLocation(1, 1);
      tf.setSize(120, 30);
      add(tf);
      setBorder(BorderFactory.createLineBorder(Color.black));
      JButton button = new JButton("Add variable");
      button.setLocation(121, 1);
      button.setSize(129, 30);
      setBackground(Color.white);
      add(button);
      button.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent arg0)
        {
          if (!VariablePreview.this.vars.contains(tf.getText().trim()))
          {
            VariablePreview.this.add(new VariablePanel(tf.getText().trim(), true, VariablePreview.this.w));
            VariablePreview.this.vars.add(tf.getText().trim());
            VariablePreview.this.repaint();
            VariablePreview.this.revalidate();
          }
        }
      });
    }
  }
}
