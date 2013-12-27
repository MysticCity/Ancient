package com.ancientshores.AncientRPG.Classes.Spells.Parameters;

import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.Spells.Commands.EffectArgs;
import com.ancientshores.AncientRPG.Classes.Spells.IParameter;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityChangeBlockEvent;

import java.util.logging.Level;
@ParameterDescription(amount = 0, description = "Returns the location of the broken block of a blockbreakevent",returntype = "Location", name = "ChangedBlock")
public class ChangedBlockParameter implements IParameter
{

	@Override
	public void parseParameter(EffectArgs ea, Player mPlayer, String[] subparam, ParameterType pt)
	{
		if (!ea.p.active && ea.so.mEvent instanceof EntityChangeBlockEvent)
		{
			EntityChangeBlockEvent event = (EntityChangeBlockEvent) ea.so.mEvent;
			int add = 0;
			if (subparam != null)
				try
				{
					if (ea.p.variables.contains(subparam[0].toLowerCase()))
						add = ea.so.parseVariable(mPlayer, subparam[0].toLowerCase());
					else
						add = Integer.parseInt(subparam[0]);
				} catch (Exception e)
				{
					AncientRPG.plugin.getLogger().log(Level.WARNING, "Error in subparameter " + subparam + " in command " + ea.mCommand.commandString + " falling back to default");
				}
			switch (pt)
			{
			case Location:
				Location[] l = new Location[1];
				l[0] = event.getBlock().getLocation();
				ea.params.addLast(l);
				return;
			case Locx:
				Location loc = new Location(mPlayer.getWorld(), event.getBlock().getX() + add, 0, 0);
				ea.params.addLast(loc);
				return;
			case Locy:
				((Location) ea.params.getLast()).setY(event.getBlock().getY() + add);
				return;
			case Locz:
				((Location) ea.params.getLast()).setZ(event.getBlock().getZ() + add);
				return;
			default:
				break;
			}
		} else
		{
			AncientRPG.plugin.getLogger().log(Level.SEVERE,
					"Invalid usage of ChangedBlock parameter in Command " + ea.mCommand.commandString + " in spell " + ea.p.name + " in line " + ea.mCommand.lineNumber);
		}
	}

	@Override
	public String getName()
	{
		return "changedblock";
	}

	@Override
	public Object parseParameter(Player mPlayer, String[] subparam, SpellInformationObject so)
	{
		if (!so.mSpell.active && so.mEvent instanceof EntityChangeBlockEvent)
		{
			EntityChangeBlockEvent event = (EntityChangeBlockEvent) so.mEvent;
			Location[] l = new Location[1];
			l[0] = event.getBlock().getLocation();
			return l;
		}
		return null;
	}

}
