package com.ancientshores.AncientRPG.Classes.Spells.Commands;

import com.ancientshores.AncientRPG.Classes.Spells.CommandDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.Variable;

public class CopyVariableCommand extends ICommand
{

	@CommandDescription(description = "<html>Copies the content of the source to the target</html>",
			argnames = {"variable1", "variable2"}, name = "CopyVariable", parameters = {ParameterType.String, ParameterType.String})
	public CopyVariableCommand()
	{
		this.paramTypes = new ParameterType[] { ParameterType.String, ParameterType.String };
	}

	@Override
	public boolean playCommand(EffectArgs ca)
	{
		if (ca.params.size() == 2)
		{
			if (ca.params.get(0) instanceof String && ca.params.get(1) instanceof String)
			{
				String source = (String) ca.params.get(0);
				String target = (String) ca.params.get(1);
				Variable v1 = ca.so.variables.get(source);
				Variable v2 = ca.so.variables.get(target);
				if (v1 != null && v2 != null)
				{
					v2.obj = v1.obj;
					if (Variable.globVars.containsKey(v2.name))
					{
						Variable.globVars.get(v2.name).obj = v2.obj;
					}
				}
				return true;
			}
		}
		return false;
	}

}
