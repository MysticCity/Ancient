package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;

public class GetPlayerByName
  extends IArgument
{
  @ArgumentDescription(description="Finds an online player on the server with the specified name", parameterdescription={"name"}, returntype=ParameterType.Player, rparams={ParameterType.String})
  public GetPlayerByName()
  {
    this.returnType = ParameterType.Player;
    this.requiredTypes = new ParameterType[] { ParameterType.String };
    this.name = "getplayerbyname";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if (!(obj[0] instanceof String)) {
      return null;
    }
    return new Player[] { Bukkit.getServer().getPlayer((String)obj[0]) };
  }
}


/* Location:              C:\Users\Jens\Desktop\Spigot Server\plugins\Ancient_v1.1.0 (1).jar!\com\ancientshores\Ancient\Classes\Spells\Conditions\GetPlayerByName.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */