package de.pylamo.spellmaker.gui.SpellItems.Parameter;

import java.awt.Dimension;


public class NumberParameterPanel extends ParameterPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NumberParameterPanel(boolean preview)
	{
		super("Number: ", Parameter.Number, 1, preview);
		this.fields.get(0).setPreferredSize(new Dimension(70, 18));
		this.tip.setDescription("Enter a number in the textfield");
	}

	@Override
	public String getString()
	{
		return ", " + fields.get(0).getText();
	}

}
