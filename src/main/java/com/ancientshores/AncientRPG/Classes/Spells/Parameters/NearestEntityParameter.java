package com.ancientshores.AncientRPG.Classes.Spells.Parameters;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.AncientRPG.Classes.Spells.IParameter;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.logging.Level;

@ParameterDescription(amount = 1, description = "<html>returns the nearest entity of the caster<br> Textfield: range of parameter</html>",returntype = "Entity", name = "NearestEntity")
public class NearestEntityParameter implements IParameter
{

	@Override
	public void parseParameter(EffectArgs ea, Player mPlayer, String[] subparam, ParameterType pt)
	{
		int range = 10;

		if (subparam != null)
		{
			try
			{
				if (ea.p.variables.contains(subparam[0].toLowerCase()))
					range = ea.so.parseVariable(mPlayer, subparam[0].toLowerCase());
				else
					range = Integer.parseInt(subparam[0]);
			} catch (Exception e)
			{
				AncientRPG.plugin.getLogger().log(Level.WARNING, "Error in subparameter " + subparam + " in command " + ea.mCommand.commandString + " falling back to default");
			}
		}
		if (subparam != null || ea.so.nearestEntity == null)
		{
			Entity nEntity = ea.so.getNearestEntity(mPlayer, range);
			ea.so.nearestEntity = nEntity;
			if (nEntity == null)
				return;
		}
		switch (pt)
		{
		case Entity:
		{
			Entity[] e = { ea.so.nearestEntity };
			ea.params.addLast(e);
			break;
		}
		case Location:
			Location[] l = { ea.so.nearestEntity.getLocation() };
			ea.params.addLast(l);
			break;
		default:
			AncientRPG.plugin.getLogger().log(Level.SEVERE, "Syntax error in command " + ea.mCommand.commandString);
		}
	}

	@Override
	public String getName()
	{
		return "nearestentity";
	}

	@Override
	public Object parseParameter(Player mPlayer, String[] subparam, SpellInformationObject so)
	{
		int range = 10;

		if (subparam != null)
		{
			try
			{
				if (so.mSpell.variables.contains(subparam[0].toLowerCase()))
					range = so.parseVariable(mPlayer, subparam[0].toLowerCase());
				else
					range = Integer.parseInt(subparam[0]);
			} catch (Exception ignored)
			{
			}
		}
		if (subparam != null || so.nearestEntity == null)
		{
			Entity nEntity = so.getNearestEntity(mPlayer, range);
			so.nearestEntity = nEntity;
			if (nEntity == null)
				return null;
		}
		return so.nearestEntity;
	}

}
