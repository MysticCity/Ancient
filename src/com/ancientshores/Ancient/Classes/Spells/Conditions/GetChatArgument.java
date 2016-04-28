package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;

public class GetChatArgument
  extends IArgument
{
  @ArgumentDescription(description="<html>Returns the specified chat argument, starting with 0 <br>arguments are split by spaces in the command, can only be used in chatcommands</html>", parameterdescription={"index"}, returntype=ParameterType.Number, rparams={ParameterType.Number})
  public GetChatArgument()
  {
    this.returnType = ParameterType.Number;
    this.requiredTypes = new ParameterType[] { ParameterType.Number };
    this.name = "getchatargument";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if (!(obj[0] instanceof Number)) {
      return null;
    }
    int i = (int)((Number)obj[0]).doubleValue();
    if (so.chatmessage.length <= i) {
      return null;
    }
    return so.chatmessage[i];
  }
}
