package com.ancientshores.AncientRPG.Classes.Spells.Conditions;

import com.ancientshores.AncientRPG.Classes.Spells.ArgumentDescription;
import com.ancientshores.AncientRPG.Classes.Spells.ParameterType;
import com.ancientshores.AncientRPG.Classes.Spells.SpellInformationObject;

public class GetChatArgumentLength extends IArgument
{
	@ArgumentDescription(
			description = "Returns the amount of arguments in the chat command, can only be used in chat Commands",
			parameterdescription = {}, returntype = ParameterType.Number, rparams ={})
	public GetChatArgumentLength()
	{
		this.pt = ParameterType.Number;
		this.requiredTypes = new ParameterType[] { };
		this.name = "getchatargumentlength";
	}

	@Override
	public Object getArgument(Object obj[], SpellInformationObject so)
	{
		return so.chatmessage.length;
	}
}
