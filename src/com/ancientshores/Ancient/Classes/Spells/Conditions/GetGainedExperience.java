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


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Conditions\GetGainedExperience.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */