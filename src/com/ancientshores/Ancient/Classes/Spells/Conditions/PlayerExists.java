package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import org.bukkit.Bukkit;
import org.bukkit.Server;

public class PlayerExists
  extends IArgument
{
  @ArgumentDescription(description="Returns true if the player with the specified uuid exists and is online on the server, false otherwise", parameterdescription={"playername"}, returntype=ParameterType.Boolean, rparams={ParameterType.String})
  public PlayerExists()
  {
    this.returnType = ParameterType.Boolean;
    this.requiredTypes = new ParameterType[] { ParameterType.String };
    this.name = "playerexists";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if (!(obj[0] instanceof String)) {
      return null;
    }
    return Boolean.valueOf(Bukkit.getServer().getPlayer((String)obj[0]) != null);
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Conditions\PlayerExists.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */