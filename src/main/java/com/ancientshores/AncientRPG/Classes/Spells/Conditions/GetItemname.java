package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;
import org.bukkit.entity.Player;

/**
 * Created with IntelliJ IDEA.
 * User: Frederik Haselmeier
 * Date: 27.03.13
 * Time: 23:17
 */
public class GetItemname extends IArgument
{
	public GetItemname()
	{
		this.pt = ParameterType.String;
		this.requiredTypes = new ParameterType[]{ParameterType.Player, ParameterType.Number};
		this.name = "getitemname";
	}
	@Override
	public Object getArgument(Object obj[], SpellInformationObject so)
	{
		if(obj.length < 2 || !(obj[0] instanceof Player[]) || ((Player[])obj[0]).length == 0 || !(obj[1] instanceof Number))
			return null;
		try
		{
			Player p = ((Player[])obj[0])[0];
			int slot = ((Number)obj[1]).intValue();
			return p.getInventory().getItem(slot).getItemMeta().getDisplayName();
		}
		catch(Exception e)
		{

		}
		return 0;
	}
}
