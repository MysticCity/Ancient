package com.ancient.spell.item.command;

import com.ancient.parameter.Parameter;
import com.ancient.parameter.ParameterType;
import com.ancient.spellmaker.CommandParameterizable;
import com.ancientshores.Ancient.Ancient;
import com.ancientshores.Ancient.HelpList;

public class Broadcast extends CommandParameterizable {

	public Broadcast(int line) {
		super(	line,
				"<html>Broadcasts the given message</html>",
				new Parameter[]{new Parameter(ParameterType.STRING, "message", false)});
	}

	@Override
	public Object[] execute() throws Exception {
		if (!validValues()) throw new IllegalArgumentException(this.getClass().getName() + " in line " + this.line + " has parameters of a wrong type.");
			
		String message = HelpList.replaceChatColor((String) parameterValues[0]);
		
		Ancient.plugin.getServer().broadcastMessage(message);

		return new Object[]{line};
	}
}
