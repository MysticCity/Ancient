package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;

public class CancelSpellCommand extends ICommand
{
	@CommandDescription(description = "<html>Cancels the currently executing spell</html>",
			argnames = {}, name = "CancelSpell", parameters = {})
	public CancelSpellCommand()
	{
		ParameterType[] buffer = { ParameterType.Void };
		this.paramTypes = buffer;
	}

	@Override
	public boolean playCommand(EffectArgs ca)
	{
		ca.so.finished = true;
		return false;
	}
}
