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
@ParameterDescription(amount = 1, description = "<html>returns the locaiton of the first target in sight (either an entity or the block you look at)<br> Textfield 1: range of parameter</html>",returntype = "Location", name = "TargetInSight")
public class TargetInSight implements IParameter
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
		Entity e = ea.so.getNearestEntityInSight(mPlayer, range);

		Location l = null;
		if (e != null)
		{
			l = e.getLocation();
		} else if (l == null)
		{
			l = ea.so.getBlockInSight(mPlayer, range);
		}
		switch (pt)
		{
		case Location:
			ea.params.addLast(new Location[] { l });
			break;
		default:
			AncientRPG.plugin.getLogger().log(Level.SEVERE, "Syntax error in command " + ea.mCommand.commandString);
		}
	}

	@Override
	public String getName()
	{
		return "targetinsight";
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
		Entity e = so.getNearestEntityInSight(mPlayer, range);
		Location l = null;
		if (e != null)
		{
			l = e.getLocation();
		}
		if (l == null)
		{
			l = so.getBlockInSight(mPlayer, range);
		}
		return l;
	}

}
