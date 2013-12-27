package de.pylamo.spellmaker.gui;

import de.pylamo.spellmaker.gui.SpellItems.Variable.VariableOperationItem;
import de.pylamo.spellmaker.gui.SpellItems.Variable.VariableOperator;
import de.pylamo.spellmaker.gui.SpellItems.Variable.VariableOperatorPanel;
import de.pylamo.spellmaker.gui.SpellItems.Variable.VariablePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

public class VariablePreview extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final HashSet<String> vars = new HashSet<String>();
    private final Window w;

	public VariablePreview(Window w)
	{
        this.w = w;
		this.setLayout(new WrapLayout());
		this.add(new VariableOperationItem(true, w));
		this.add(new AddVariablePanel());
		this.add(new VariableOperatorPanel(VariableOperator.Assign, VariableOperator.Assign.getTooltip(), true));
		this.add(new VariableOperatorPanel(VariableOperator.PlusAssign, VariableOperator.PlusAssign.getTooltip(), true));
		this.add(new VariableOperatorPanel(VariableOperator.MinusAssign, VariableOperator.MinusAssign.getTooltip(), true));
		this.add(new VariableOperatorPanel(VariableOperator.DividedAssign, VariableOperator.DividedAssign.getTooltip(), true));
		this.add(new VariableOperatorPanel(VariableOperator.MultiplyAssign, VariableOperator.MultiplyAssign.getTooltip(), true));
		for (String s : w.m.variables)
		{
			this.add(new VariablePanel(s, true, w));
			vars.add(s);
		}
	}

	class AddVariablePanel extends JPanel
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public AddVariablePanel()
		{
			final JTextField tf = new JTextField();
			tf.setText("variablename");
			this.setLayout(new FlowLayout());
			tf.setLocation(1, 1);
			tf.setSize(120, 30);
			this.add(tf);
			this.setBorder(BorderFactory.createLineBorder(Color.black));
			JButton button = new JButton("Add variable");
			button.setLocation(121, 1);
			button.setSize(129, 30);
			this.setBackground(Color.white);
			this.add(button);
			button.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					if (!vars.contains(tf.getText().trim()))
					{
						VariablePreview.this.add(new VariablePanel(tf.getText().trim(), true, w));
						vars.add(tf.getText().trim());
						VariablePreview.this.repaint();
						VariablePreview.this.revalidate();
					}
				}
			});
		}
	}
}
