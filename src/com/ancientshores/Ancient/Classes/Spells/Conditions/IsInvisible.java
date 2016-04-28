package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.Commands.InvisibleCommand;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import java.util.HashSet;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class IsInvisible
  extends IArgument
{
  @ArgumentDescription(description="true if player is invisible, false otherwise", parameterdescription={"player"}, returntype=ParameterType.Boolean, rparams={ParameterType.Player})
  public IsInvisible()
  {
    this.returnType = ParameterType.Boolean;
    this.requiredTypes = new ParameterType[] { ParameterType.Player };
    this.name = "isinvisible";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if (!(obj[0] instanceof Player[])) {
      return null;
    }
    Player p = ((Player[])(Player[])obj[0])[0];
    return Boolean.valueOf((p.hasPotionEffect(PotionEffectType.INVISIBILITY)) || (InvisibleCommand.invisiblePlayers.contains(p.getUniqueId())));
  }
}
