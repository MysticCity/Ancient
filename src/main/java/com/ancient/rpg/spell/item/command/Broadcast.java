package com.ancient.rpg.spell.item.command;

import com.ancient.rpg.parameter.Parameter;
import com.ancient.rpg.parameter.ParameterType;
import com.ancient.rpg.spellmaker.CommandParameterizable;
import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.HelpList;

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
		
		AncientRPG.plugin.getServer().broadcastMessage(message);

		return new Object[]{line};
	}
}
