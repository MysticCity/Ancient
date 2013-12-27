package com.ancientshores.AncientRPG.Classes.Spells.Parameters;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.AncientRPG.Classes.Spells.IParameter;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.logging.Level;
@ParameterDescription(amount = 2, description = "<html>returns members of the party<br> Textfield 1: range of parameter<br> Textfield 2: maximum amount of targets</html>",returntype = "Player", name = "PartyMembers")
public class PartyMembersParameter implements IParameter
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
		if (subparam != null || ea.so.partyMembers == null)
		{
			Player[] nEntity = ea.so.getPartyMembers(mPlayer, range);
			ea.so.partyMembers = nEntity;
			if (nEntity == null)
				return;
		}

		switch (pt)
		{
		case Player:
			ea.params.addLast(ea.so.partyMembers);
			break;
		case Entity:
			ea.params.addLast(ea.so.partyMembers);
			break;
		case Location:
			Location[] l = new Location[ea.so.partyMembers.length];
			for (int i = 0; i < ea.so.partyMembers.length; i++)
			{
				if (ea.so.partyMembers[i] != null)
				{
					l[i] = ea.so.partyMembers[i].getLocation();
					ea.params.addLast(l);
				}
			}
			break;
		case String:
			String s = "";
			for (Player p : ea.so.partyMembers)
			{
				s += p.getName() + ",";
			}
			ea.params.addLast(s);
			break;
		default:
			AncientRPG.plugin.getLogger().log(Level.SEVERE, "Syntax error in command " + ea.mCommand.commandString);
		}
	}

	@Override
	public String getName()
	{
		return "partymembers";
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
		if (subparam != null || so.partyMembers == null)
		{
			Player[] nEntity = so.getPartyMembers(mPlayer, range);
			so.partyMembers = nEntity;
		}
		return so.partyMembers;
	}

}
