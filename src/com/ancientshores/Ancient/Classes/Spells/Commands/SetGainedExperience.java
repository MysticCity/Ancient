package com.ancientshores.Ancient.Classes.Spells.Commands;

import com.ancientshores.Ancient.API.AncientGainExperienceEvent;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import java.util.LinkedList;

public class SetGainedExperience
  extends ICommand
{
  public SetGainedExperience()
  {
    this.paramTypes = new ParameterType[] { ParameterType.Number };
  }
  
  public boolean playCommand(EffectArgs ca)
  {
    if ((ca.getParams().size() == 1) && 
      ((ca.getParams().get(0) instanceof Number)) && 
      ((ca.getSpellInfo().mEvent instanceof AncientGainExperienceEvent)))
    {
      ((AncientGainExperienceEvent)ca.getSpellInfo().mEvent).gainedxp = ((Number)ca.getParams().get(0)).intValue();
      return true;
    }
    return false;
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Commands\SetGainedExperience.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */