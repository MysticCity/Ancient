package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;

public class GetChatArgumentLength
  extends IArgument
{
  @ArgumentDescription(description="Returns the amount of arguments in the chat command, can only be used in chat Commands", parameterdescription={}, returntype=ParameterType.Number, rparams={})
  public GetChatArgumentLength()
  {
    this.returnType = ParameterType.Number;
    this.requiredTypes = new ParameterType[0];
    this.name = "getchatargumentlength";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    return Integer.valueOf(so.chatmessage.length);
  }
}
