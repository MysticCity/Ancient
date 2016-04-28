package com.ancientshores.Ancient.Classes.Spells.Conditions;

import com.ancientshores.Ancient.Classes.Spells.ArgumentDescription;
import com.ancientshores.Ancient.Classes.Spells.ParameterType;
import com.ancientshores.Ancient.Classes.Spells.SpellInformationObject;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;

public class GetPlayerByUUID
  extends IArgument
{
  @ArgumentDescription(description="Finds an online player on the server with the specified uuid", parameterdescription={"uuid"}, returntype=ParameterType.Player, rparams={ParameterType.UUID})
  public GetPlayerByUUID()
  {
    this.returnType = ParameterType.Player;
    this.requiredTypes = new ParameterType[] { ParameterType.UUID };
    this.name = "getplayerbyuuid";
  }
  
  public Object getArgument(Object[] obj, SpellInformationObject so)
  {
    if (!(obj[0] instanceof String)) {
      return null;
    }
    return new Player[] { Bukkit.getServer().getPlayer(UUID.fromString((String)obj[0])) };
  }
}
