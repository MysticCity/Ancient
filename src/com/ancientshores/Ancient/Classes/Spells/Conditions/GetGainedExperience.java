package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.API.AncientGainExperienceEvent;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;

public class GetGainedExperience
  extends IArgument
{
  public GetGainedExperience()
  {
    this.returnType = ParameterType.Number;
    this.requiredTypes = new ParameterType[0];
    this.name = "getgainedexperience";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if ((so.mEvent instanceof AncientGainExperienceEvent)) {
      return Integer.valueOf(((AncientGainExperienceEvent)so.mEvent).gainedxp);
    }
    return null;
  }
}
